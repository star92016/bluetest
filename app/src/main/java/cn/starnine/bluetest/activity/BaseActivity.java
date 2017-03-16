package cn.starnine.bluetest.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.starnine.bluetest.BlueConnect;
import cn.starnine.bluetest.MyApplication;
import cn.starnine.bluetest.ParseJson;

/**
 * Created by licheng on 2017/3/12 0012.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private Toast toast;
    public ParseJson parseJson = null;

    public void toast(String str) {
        if (toast == null) {
            toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        }
        toast.setText(str);
        toast.show();
    }

    //BlueConnect.OnRecive oldonRecive;
    private BlueConnect.OnRecive onRecive = new BlueConnect.OnRecive() {
        @Override
        public void reciveJson(JSONObject jsonObject) {
            Message msg = new Message();
            msg.obj = jsonObject.toString();
            handler.sendMessage(msg);
        }
    };
    public SharedPreferences sp;
    public SharedPreferences.Editor editor;

    @Override
    protected void onResume() {

        if (sp == null) {
            sp = getSharedPreferences("my_data", Activity.MODE_PRIVATE);
            editor = sp.edit();
        }
        ((MyApplication) getApplication()).getBlueConnect().setOnRecive(onRecive);
        if (parseJson == null)
            parseJson = new ParseJson(((MyApplication) getApplication()).getBlueConnect());
        super.onResume();
    }

    public void setParam(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public void setParam(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public void setParam(String key, double value) {
        editor.putFloat(key, (float) value);
        editor.apply();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (sp == null) {
            sp = getSharedPreferences("my_data", Activity.MODE_PRIVATE);
            editor = sp.edit();
        }
        ((MyApplication) getApplication()).getBlueConnect().setOnRecive(onRecive);
        if (parseJson == null)
            parseJson = new ParseJson(((MyApplication) getApplication()).getBlueConnect());

    }

    public static final int SENDING = 1;
    public static final int NOVOICE = 2;
    public static final int BUFFERI = 3;

    public void onUpdateParam(String key) {
    }

    public void onVstate(int state) {
    }

    public void onFromMessage(String msg) {
    }

    public void onToMessage(String msg) {
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                JSONObject jsonObject = new JSONObject(msg.obj.toString());
                String type = jsonObject.getString("type");
                String result = jsonObject.getString("result");
                if (type.equals("shell")) {
                    toast("执行结果:" + result);
                } else if (type.equals("broad")) {
                    // toast(result);
                    if (result.startsWith("set:")) {
                        if (result.equals("set:ok")) {
                            toast("设置成功");
                        } else if (result.equals("set:fail")) {
                            toast("设置失败");
                        } else toast("未知设置" + result);
                    } else if (result.startsWith("get:")) {
                        if (result.startsWith("get:token=")) {
                            setParam("token", result.substring(10));
                        } else if (result.startsWith("get:zcr2=")) {
                            setParam("zcr2", Integer.valueOf(result.substring("get:zcr2=".length())));
                        } else if (result.startsWith("get:amp2=")) {
                            setParam("amp2", Double.valueOf(result.substring("get:amp2=".length())));
                        } else if (result.startsWith("get:amp1=")) {
                            setParam("amp1", Double.valueOf(result.substring("get:amp1=".length())));
                        } else if (result.startsWith("get:min_voice_dif=")) {
                            setParam("min_voice_dif", Integer.valueOf(result.substring("get:min_voice_dif=".length())));
                        } else if (result.startsWith("get:min_len=")) {
                            setParam("min_len", Integer.valueOf(result.substring("get:min_len=".length())));
                        } else if (result.startsWith("get:min_silence=")) {
                            setParam("min_silence", Integer.valueOf(result.substring("get:min_silence=".length())));
                        } else if (result.startsWith("get:frame_off=")) {
                            setParam("frame_off", Integer.valueOf(result.substring("get:frame_off=".length())));
                        } else toast("未知参数" + result);
                        if (result.indexOf("=") > 0)
                            onUpdateParam(result.substring(4, result.indexOf("=")));
                    } else if (result.startsWith("vstate:")) {
                        if (result.equals("vstate:sending")) {
                            onVstate(SENDING);
                        } else if (result.equals("vstate:buffing")) {
                            onVstate(BUFFERI);
                        } else if (result.equals("vstate:novoice")) {
                            onVstate(NOVOICE);
                        } else
                            toast("未知状态" + result);
                    } else if (result.startsWith("voicerecerror:")) {
                        toast("语音解析错误" + result);
                    } else if (result.startsWith("frommsg:")) {
                        onFromMessage(result.substring("frommsg:".length()));
                    } else if (result.startsWith("tomsg:")) {
                        onToMessage(result.substring("tomsg:".length()));
                    } else toast("未知错误" + result);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //toast(msg.obj.toString());
        }
    };
    //public abstract Handler getHandler();
}
