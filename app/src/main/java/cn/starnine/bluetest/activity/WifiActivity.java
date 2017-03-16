package cn.starnine.bluetest.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import cn.starnine.bluetest.R;

public class WifiActivity extends BaseActivity{


    EditText et_passwd;
    EditText et_ssid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        setTitle("Wifi管理");
        findViewById(R.id.btn_addwifi).setOnClickListener(this);
        findViewById(R.id.btn_reloadwifi).setOnClickListener(this);
        et_passwd=(EditText)findViewById(R.id.et_passwd);
        et_ssid=(EditText)findViewById(R.id.et_ssid);

    }

    @Override
    public void onClick(View v) {
        String ssid,passwd;
        switch (v.getId()){
            case R.id.btn_addwifi:
                ssid=et_ssid.getText().toString().trim();
                passwd=et_passwd.getText().toString().trim();
                if(ssid.equals("")||passwd.equals("")){
                    toast("ssid 密码不能为空");
                }else{
                    parseJson.addWifi(ssid,passwd);
                }
                break;
            case R.id.btn_reloadwifi:
                parseJson.restartWfi();
                break;
        }
    }
}
