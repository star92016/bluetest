package cn.starnine.bluetest;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by licheng on 2017/3/4 0004.
 */

public class BlueConnect {
    BluetoothSocket bluetoothSocket;
    InputStream inputStream;
    OutputStream outputStream;
    boolean isrun=false;
    public BlueConnect(BluetoothSocket bluetoothSocket) throws IOException{
        this.bluetoothSocket=bluetoothSocket;
        inputStream=bluetoothSocket.getInputStream();
        outputStream=bluetoothSocket.getOutputStream();
        isrun=true;
        new Thread(new ReciveListener()).start();

    }
    public boolean isConect(){
        if(isrun&&bluetoothSocket!=null&&bluetoothSocket.isConnected()){
            return true;
        }else{
            isrun=false;
            return false;
        }
    }
    public void SendString(String string){
        try {
            outputStream.write(string.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendJsonObj(JSONObject jsonObject){
        String jstring=jsonObject.toString();
        SendString(jstring);
    }
    OnRecive onRecive;

    public OnRecive getOnRecive() {
        return onRecive;
    }

    public void setOnRecive(OnRecive onRecive) {
        this.onRecive = onRecive;
    }

    public void close() throws IOException{
        isrun=false;
        inputStream.close();
        outputStream.close();
        bluetoothSocket.close();
    }
    public interface OnRecive{
        void reciveJson(JSONObject jsonObject);
    }
    class ReciveListener implements Runnable{
        private String getBody(String s){
            s=s.substring(8);
            int res=0;
            int i=0;
            for (i=0;;i++){
                if(s.charAt(i)>='0'&&s.charAt(i)<='9'){
                    res=(s.charAt(i)-'0')+res*10;
                }else
                    break;
            }
            s=s.substring(i);
            return s;
        }
        @Override
        public void run() {
            byte[] bytes = new byte[1024];
            while(isrun&&bluetoothSocket.isConnected()){
            try {
                int size = inputStream.read(bytes);
                String str=new String(bytes, 0, size, "UTF-8");
                if(onRecive!=null){
                    JSONObject jsonObject=new JSONObject(str);
                    onRecive.reciveJson(jsonObject);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if(e.getMessage().startsWith("bt socket closed"))
                    isrun=false;
            }}
        }
    }
}
