package cn.starnine.bluetest;

import android.provider.Settings;

import org.json.JSONObject;
import org.junit.Test;

/**
 * Created by licheng on 2017/3/5 0005.
 */

public class JSonTest {
    @org.junit.Test
    public void testFirst() throws Exception{
        String data="{\"type\":\"commond\",\"content\":\"hehene\"}";
        JSONObject jsonObject=new JSONObject(data);
        System.out.println(jsonObject.getString("type"));
        System.out.println(jsonObject.getString("content"));
    }
}
