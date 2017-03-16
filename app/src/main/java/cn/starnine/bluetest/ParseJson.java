package cn.starnine.bluetest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by licheng on 2017/3/5 0005.
 */

public class ParseJson {
    BlueConnect blueConnect;
    public ParseJson(BlueConnect blueConnect){
        this.blueConnect=blueConnect;
    }
    public void runShell(String shell){
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type","shell");
            jsonObject.put("msg",shell);
            blueConnect.sendJsonObj(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendAsound(String param){
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type","asound");
            jsonObject.put("msg",param);
            blueConnect.sendJsonObj(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void reboot(){
        runShell("sudo reboot");
    }
    public void halt(){
        runShell("sudo halt");
    }
    public void startTomcat(){
        runShell("sudo /home/pi/apache-tomcat-7.0.75/bin/startup.sh");
    }
    public void stopTomcat(){
        runShell("sudo /home/pi/apache-tomcat-7.0.75/bin/shutdown.sh");
    }
    public void reStartServer(){
        //重启蓝牙守护进程
    }
    public void addWifi(String name,String passwd){
        runShell("sudo /home/pi/xiaoyu/bluetooth/wifi/wifi add "+name+" "+passwd);
    }
    public void restartWfi(){
        runShell("sudo /home/pi/xiaoyu/bluetooth/wifi/load.sh");
    }
    public static String buildJson() throws Exception{
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("commond","hehfew");
        jsonObject.put("content","fasd fdas3&435@#$\nfds");
        return jsonObject.toString();
    }
}
