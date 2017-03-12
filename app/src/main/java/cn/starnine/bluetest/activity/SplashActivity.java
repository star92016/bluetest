package cn.starnine.bluetest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import cn.starnine.bluetest.BlueConnect;
import cn.starnine.bluetest.ConnectManger;
import cn.starnine.bluetest.MyApplication;
import cn.starnine.bluetest.R;

public class SplashActivity extends AppCompatActivity {
    ConnectManger connectManger = new ConnectManger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    BlueConnect blueConnect = ((MyApplication) getApplication()).getBlueConnect();
                    if (blueConnect == null || !blueConnect.isConect()) {
                        try {
                            blueConnect = connectManger.getBlueConnect();
                            blueConnect.setOnRecive(onRecive);
                            ((MyApplication) getApplication()).setBlueConnect(blueConnect);
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            SplashActivity.this.finish();
                            break;
                        } catch (ConnectManger.NotReadyException e) {
                            e.printStackTrace();

                        }
                    }
                }
            }
        }.start();

    }

    BlueConnect.OnRecive onRecive = new BlueConnect.OnRecive() {
        @Override
        public void reciveJson(JSONObject jsonObject) {

        }
    };
}
