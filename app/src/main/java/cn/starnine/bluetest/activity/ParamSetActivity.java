package cn.starnine.bluetest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import cn.starnine.bluetest.R;

public class ParamSetActivity extends BaseActivity {
EditText et_token;
    EditText et_zcr;
    EditText et_amp1;
    EditText et_amp2;
    EditText et_mindiff;
    EditText et_minlen;
    EditText et_maxslen;
    EditText et_foff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param_set);
        setTitle("参数中心");
        findViewById(R.id.btn_updatedata).setOnClickListener(this);
        findViewById(R.id.btn_getnewdata).setOnClickListener(this);
        et_token=(EditText)findViewById(R.id.et_token);
        et_zcr=(EditText)findViewById(R.id.et_zcr);
        et_amp1=(EditText)findViewById(R.id.et_amp1);
        et_amp2=(EditText)findViewById(R.id.et_amp2);
        et_mindiff=(EditText)findViewById(R.id.et_mindiff);
        et_minlen=(EditText)findViewById(R.id.et_minlen);
        et_maxslen=(EditText)findViewById(R.id.et_maxslen);
        et_foff=(EditText)findViewById(R.id.et_foff);
        loadConf();
    }
public void loadConf(){
    et_token.setText(sp.getString("token",""));
    et_zcr.setText(sp.getInt("zcr2",0)+"");
    et_amp1.setText(sp.getFloat("amp1",0)+"");
    et_amp2.setText(sp.getFloat("amp2",0)+"");
    et_mindiff.setText(sp.getInt("min_voice_dif",0)+"");
    et_minlen.setText(sp.getInt("min_len",0)+"");
    et_maxslen.setText(sp.getInt("min_silence",0)+"");
    et_foff.setText(sp.getInt("frame_off",0)+"");
}

    @Override
    public void onUpdateParam(String key) {
        super.onUpdateParam(key);
        loadConf();
    }

    public void downParam(){
        parseJson.sendAsound("get=token");
        parseJson.sendAsound("get=zcr2");
        parseJson.sendAsound("get=amp1");
        parseJson.sendAsound("get=amp2");
        parseJson.sendAsound("get=min_voice_dif");
        parseJson.sendAsound("get=min_len");
        parseJson.sendAsound("get=min_silence");
        parseJson.sendAsound("get=frame_off");

    }
    public void sendParam(){
        Bean b=checkValide();
        parseJson.sendAsound("token="+b.token);
        parseJson.sendAsound("zcr2="+b.zcr);
        parseJson.sendAsound("amp1="+b.amp1);
        parseJson.sendAsound("amp2="+b.amp2);
        parseJson.sendAsound("min_voice_dif="+b.mindiff);
        parseJson.sendAsound("min_len="+b.minlen);
        parseJson.sendAsound("min_silence="+b.maxslen);
        parseJson.sendAsound("frame_off="+b.foff);
        parseJson.sendAsound("save=hehe");
    }
private class Bean{
    String token;
    int zcr;
    float amp1;
    float amp2;
    int mindiff;
    int minlen;
    int maxslen;
    int foff;
}
    private Bean checkValide() {

        Bean b=new Bean();
        b.token=et_token.getText().toString().trim();
        b.zcr=Integer.valueOf(et_zcr.getText().toString().trim());
        b.amp1=Float.valueOf(et_amp1.getText().toString().trim());
        b.amp2=Float.valueOf(et_amp2.getText().toString().trim());
        b.mindiff=Integer.valueOf(et_mindiff.getText().toString().trim());
        b.maxslen=Integer.valueOf(et_maxslen.getText().toString().trim());
        b.minlen=Integer.valueOf(et_minlen.getText().toString().trim());
        b.foff=Integer.valueOf(et_foff.getText().toString().trim());
        return b;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_getnewdata:
                try {
                    downParam();
                    toast("获取成功");
                }catch (Exception e){
                    e.printStackTrace();
                    toast(e.getMessage());
                }
                break;
            case R.id.btn_updatedata:
                try {
                    sendParam();
                    downParam();
                    toast("更新成功");
                }catch (Exception e){
                    e.printStackTrace();
                    toast(e.getMessage());
                }
                break;
        }
    }
}
