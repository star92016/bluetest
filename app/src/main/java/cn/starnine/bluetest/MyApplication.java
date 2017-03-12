package cn.starnine.bluetest;

import android.app.Application;

import java.io.IOException;

/**
 * Created by licheng on 2017/3/5 0005.
 */

public class MyApplication extends Application{
    BlueConnect blueConnect;

    public BlueConnect getBlueConnect() {
        return blueConnect;
    }

    public void setBlueConnect(BlueConnect blueConnect) {
        this.blueConnect = blueConnect;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if(blueConnect!=null&&blueConnect.isConect()){
            try {
                blueConnect.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            blueConnect=null;
        }
        ConnectManger.close();

    }
}
