package com.mshd.olstore.retrofit;

import com.blankj.utilcode.util.SPUtils;
import com.mshd.olstore.base.SpKey;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();
        String token = SPUtils.getInstance().getString(SpKey.SPRING_SESSION);
        String userId = SPUtils.getInstance().getString(SpKey.USER_ID);
        Request request = original.newBuilder()
                //sessionId
                .header("Cookie", "SPRINGSESSION=" + token)
                //.header("Cookie", "SPRINGSESSION=" + Contact.TOKEN)
                //.header("Cookie", "SPRINGSESSION=111111" )
                .header("fromId", Contact.FROM_ID)
                //'Content-Type': handle['content-type'] || 'application/json'
                //              .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")

                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }

}