package cn.starnine.bluetest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import cn.starnine.bluetest.BlueConnect;
import cn.starnine.bluetest.ConnectManger;
import cn.starnine.bluetest.MyApplication;
import cn.starnine.bluetest.ParseJson;
import cn.starnine.bluetest.R;

/**
 * 1个byte类型，1：linux命令 2：control
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = MainActivity.class.getName();

    ConnectManger connectManger=new ConnectManger();




    private EditText et_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.btn_send).setOnClickListener(this);
        findViewById(R.id.btn_recive).setOnClickListener(this);
        findViewById(R.id.btn_tosecond).setOnClickListener(this);
        et_msg = (EditText) findViewById(R.id.et_msg);




    }

    Toast toast;

    private void toast(String str) {
        if (toast == null) {
            toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        }
        toast.setText(str);
        toast.show();
    }





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
        String str;

        BlueConnect blueConnect=((MyApplication)getApplication()).getBlueConnect();
        switch (v.getId()) {
            case R.id.button:
                if(blueConnect==null||!blueConnect.isConect()){
                    try {
                        blueConnect=connectManger.getBlueConnect();
                        blueConnect.setOnRecive(onRecive);
                        ((MyApplication)getApplication()).setBlueConnect(blueConnect);
                        toast("连接成功");
                    } catch (ConnectManger.NotReadyException e) {
                        e.printStackTrace();
                        toast(e.getMessage());
                    }
                }
                break;
            case R.id.button2:
                if(blueConnect!=null){
                    try {
                        blueConnect.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                        toast(e.getMessage());
                    }
                }
                break;
            case R.id.btn_send:
                str = et_msg.getEditableText().toString().trim();
                if (!str.equals(""))
                    blueConnect.SendString(str);
                break;
            case R.id.btn_recive:
                str = et_msg.getEditableText().toString().trim();
                if (!str.equals("")){
                    try {
                        toast(ParseJson.buildJson());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case  R.id.btn_tosecond:
                if(blueConnect!=null&&blueConnect.isConect()){
                    Intent intent=new Intent(this,SecondActivity.class);
                    startActivity(intent);
                }else{
                    toast("未连接");
                }
                break;
        }
    }


}
