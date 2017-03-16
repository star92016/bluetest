package cn.starnine.bluetest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import cn.starnine.bluetest.R;

public class ControlActivity extends BaseActivity{


    EditText et_shell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        setTitle("控制中心");

findViewById(R.id.btn_run_shell).setOnClickListener(this);
        et_shell=(EditText)findViewById(R.id.et_shell);

        findViewById(R.id.btn_wifiman).setOnClickListener(this);
        findViewById(R.id.btn_halt).setOnClickListener(this);
        findViewById(R.id.btn_reboot).setOnClickListener(this);
        findViewById(R.id.btn_stop_tomcat).setOnClickListener(this);
        findViewById(R.id.btn_start_tomcat).setOnClickListener(this);

    }





    @Override
    public void onClick(View v) {
        String str=et_shell.getEditableText().toString();
        Intent intent;
        switch (v.getId()){
            case R.id.btn_run_shell:
                if(!str.equals(""))
                    parseJson.runShell(str);
                break;
            case R.id.btn_wifiman:
                intent=new Intent(this,WifiActivity.class);
                startActivity(intent);
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
        }
    }


}
