package cn.starnine.bluetest.activity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.starnine.bluetest.R;

public class VoiceCenterActivity extends BaseActivity{
ImageView iv_state;
    TextView tv_state;
    TextView tv_tomsg;
    TextView tv_frommsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        setTitle("语音中心");
        iv_state=(ImageView)findViewById(R.id.iv_state);
        tv_state=(TextView)findViewById(R.id.tv_state);
        tv_frommsg=(TextView)findViewById(R.id.tv_from);
        tv_tomsg=(TextView)findViewById(R.id.tv_to);
        findViewById(R.id.btn_pause).setOnClickListener(this);
        findViewById(R.id.btn_playback).setOnClickListener(this);
        findViewById(R.id.btn_setenv).setOnClickListener(this);
        findViewById(R.id.btn_control).setOnClickListener(this);
        findViewById(R.id.btn_playtext).setOnClickListener(this);
    }

    @Override
    public void onVstate(int state) {
        super.onVstate(state);
        if (state==BUFFERI){
            iv_state.setImageDrawable(getResources().getDrawable(R.drawable.p_recoder));
            tv_state.setText("缓存中");
        }else if(state==SENDING){
            iv_state.setImageDrawable(getResources().getDrawable(R.drawable.p_recoder));
            tv_state.setText("录制中");
        }else if(state==NOVOICE){
            iv_state.setImageDrawable(getResources().getDrawable(R.drawable.p_stop));
            tv_state.setText("没有声音");
        }
    }

    @Override
    public void onFromMessage(String msg) {
        super.onFromMessage(msg);
        tv_frommsg.setText("识别结果："+msg);
    }

    @Override
    public void onToMessage(String msg) {
        super.onToMessage(msg);
        tv_tomsg.setText("响应消息："+msg);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_pause:
                break;
            case R.id.btn_playback:
                break;
            case R.id.btn_setenv:
                intent=new Intent(this,ParamSetActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_control:
                intent=new Intent(this,ControlActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_playtext:
                break;
        }
    }

}
