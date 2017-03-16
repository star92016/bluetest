package cn.starnine.bluetest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONObject;

import cn.starnine.bluetest.BlueConnect;
import cn.starnine.bluetest.R;

public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(SplashActivity.this, FirstActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }

        }.start();

    }

    BlueConnect.OnRecive onRecive = new BlueConnect.OnRecive() {
        @Override
        public void reciveJson(JSONObject jsonObject) {

        }
    };
}
