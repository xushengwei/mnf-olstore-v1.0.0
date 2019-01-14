package com.mshd.olstore.retrofit;

import android.net.Uri;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;


import java.util.Map;

/**
 * @author xushengwei
 * @date 2018/12/5
 */
public class HttpGetParams {
    public static String appendParams(Map<String,String> map ){
        Uri.Builder builder = Uri.parse("").buildUpon();

        if (map==null){
            return "";
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.appendQueryParameter(entry.getKey(),entry.getValue());
        }
//        builder.appendQueryParameter("millis", TimeUtils.getNowMills()+"");
//        builder.appendQueryParameter("sid", SPUtils.getInstance().getString(SpKey.SPRING_SESSION)+"");
        return builder.toString();
    }
}
