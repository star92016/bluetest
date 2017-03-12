package cn.starnine.bluetest.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import cn.starnine.bluetest.BlueConnect;
import cn.starnine.bluetest.MyApplication;
import cn.starnine.bluetest.ParseJson;
import cn.starnine.bluetest.R;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{
    Toast toast;

    private void toast(String str) {
        if (toast == null) {
            toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        }
        toast.setText(str);
        toast.show();
    }
    ParseJson parseJson;
    EditText et_shell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        parseJson=new ParseJson(((MyApplication)getApplication()).getBlueConnect());
findViewById(R.id.btn_run_shell).setOnClickListener(this);
        et_shell=(EditText)findViewById(R.id.et_shell);
        oldonRecive=((MyApplication)getApplication()).getBlueConnect().getOnRecive();
        ((MyApplication)getApplication()).getBlueConnect().setOnRecive(onRecive);
        findViewById(R.id.btn_add_wifi).setOnClickListener(this);
        findViewById(R.id.btn_halt).setOnClickListener(this);
        findViewById(R.id.btn_reboot).setOnClickListener(this);
        findViewById(R.id.btn_stop_tomcat).setOnClickListener(this);
        findViewById(R.id.btn_start_tomcat).setOnClickListener(this);
        findViewById(R.id.btn_load_wifi).setOnClickListener(this);


    }
    BlueConnect.OnRecive oldonRecive;
    BlueConnect.OnRecive onRecive=new BlueConnect.OnRecive() {
        @Override
        public void reciveJson(JSONObject jsonObject) {
            Message msg=new Message();
            msg.obj=jsonObject.toString();
            handler.sendMessage(msg);
        }
    };
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            toast(msg.obj.toString());
        }
    };
    @Override
    public void onClick(View v) {
        String str=et_shell.getEditableText().toString();
        switch (v.getId()){
            case R.id.btn_run_shell:
                if(!str.equals(""))
                    parseJson.runShell(str);
                break;

            case R.id.btn_add_wifi:
                String s[]=str.split(" ");
                if(s.length==2){
                    parseJson.addWifi(s[0],s[1]);
                }
                break;
            case R.id.btn_halt:
                parseJson.halt();
                break;
            case R.id.btn_reboot:
                parseJson.reboot();
                break;
            case R.id.btn_stop_tomcat:
                parseJson.stopTomcat();
                break;
            case R.id.btn_start_tomcat:
                parseJson.startTomcat();
                break;
            case R.id.btn_load_wifi:
                parseJson.restartWfi();
                break;
        }
    }

}
