package cn.starnine.bluetest;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by licheng on 2017/3/4 0004.
 */

public class ConnectManger {
    public class NotReadyException extends Exception{
        public NotReadyException() {
            super();
        }

        public NotReadyException(String message) {
            super(message);
        }

        public NotReadyException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    static BluetoothAdapter mBluetoothAdapter;

    final static String PINAME = "raspberrypi";

    public ConnectManger() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }
public static void close(){
    if(mBluetoothAdapter.isEnabled())
        mBluetoothAdapter.disable();
}
    BluetoothDevice pi;

    public BluetoothDevice getPi() throws NotReadyException{
        if (pi != null)
            return pi;
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice d : pairedDevices) {
            if (d.getName().equals(PINAME)) {
                pi = d;
                return pi;
            }
        }
throw new NotReadyException("没有配对或蓝牙没有打开");

    }
    public BlueConnect getBlueConnect() throws NotReadyException{
        getPi();
        if(pi==null){
            throw new NotReadyException("没有配对或蓝牙没有打开");
        }
        try {
            Method m = pi.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
            BluetoothSocket bluetoothSocket = (BluetoothSocket) m.invoke(pi, Integer.valueOf(1));
            bluetoothSocket.connect();
            return new BlueConnect(bluetoothSocket);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotReadyException("服务器异常",e);
        }

    }

}
