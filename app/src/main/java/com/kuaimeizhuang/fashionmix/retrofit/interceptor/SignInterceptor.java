package com.kuaimeizhuang.fashionmix.retrofit.interceptor;

import com.kuaimeizhuang.fashionmix.config.Constants;
import com.kuaimeizhuang.fashionmix.utils.EncryptUtil;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>自定义签名拦截器</p>
 * Created on 17/4/7.
 *
 * @author Shi GuangXiang.
 */

public class SignInterceptor implements Interceptor {
    private String reqParams;
    private String reqUrl;
    private String[] encrypArray = new String[0];
    private HashMap<String, String> map = new HashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {
        //首先拿到request对象
        Request request = chain.request();
        //获取请求地址链接
        String url = request.url().toString();
        //判断是GET和DELETE请求
        if (request.method().equalsIgnoreCase("GET") || request.method().equalsIgnoreCase("DELETE")) {
            //如果链接里面包含有？
            if (url.contains("?")) {
                //获取请求地址域名
                reqUrl = url.substring(0, url.indexOf("?"));
                //获取请求参数
                reqParams = url.substring(url.indexOf("?") + 1);
                String[] split = reqParams.split("&");
                encrypArray = new String[split.length];
                for (int i = 0; i < split.length; i++) {
                    encrypArray[i] = EncryptUtil.encodeUrl(split[i]);
                }
            } else {
                reqUrl = url;
                reqParams = "";
            }
        } else {//其他请求方法，比如：POST
            if (url.contains("?file_md5")) {//如果是文件上传处理
                int indexOf = url.indexOf("?");
                reqParams = url.substring(indexOf + 1, url.length());
                reqUrl = url.substring(0, indexOf);
            }
            if (request.body() instanceof FormBody) {//如果是表单body获取出表单进行字符串处理
                FormBody formBody = ((FormBody) request.body());
                encrypArray = new String[formBody.size()];
                for (int i = 0; i < formBody.size(); i++) {
                    String encodedName = formBody.encodedName(i);
                    String encodedValue = formBody.encodedValue(i);
                    String decode = URLDecoder.decode(encodedName, "UTF-8");
                    map.put(decode, encodedValue);
                    encrypArray[i] = decode;
                }
            }
        }

        //对表单数据进行排序
        Arrays.sort(encrypArray);

        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < encrypArray.length; i++) {
            if (request.body() instanceof FormBody) {
                arrayList.add(EncryptUtil.encodeUrl(encrypArray[i]) + "=" +
                        EncryptUtil.encodeUrl(map.get(encrypArray[i])));
            } else {
                arrayList.add(encrypArray[i]);
            }
        }

        //拼接成字符串
        StringBuilder stringBuilder = new StringBuilder();
        if (arrayList.size() > 0) {
            for (String str : arrayList) {
                stringBuilder.append(str);
                stringBuilder.append("&");
            }
            reqParams = stringBuilder.substring(0, stringBuilder.length() - 1);
        }

        //拼接签名原文  格式：方法名 + URL地址  + 参数
        String signStr = request.method() + "\n" + reqUrl + "\n" + reqParams;
        String sign = EncryptUtil.SHA1(EncryptUtil.MD5(signStr) + Constants.SALT);
        LogUtils.d("签名原文--->\n" + signStr);
        LogUtils.d("签名密文--->\n" + sign);
        Request requestBuilder = request.newBuilder().header("X-Signature", sign).method(request
                .method(), request.body()).build();
        LogUtils.d("Method:" + request.method() + " URL:" + reqUrl + " Params:" + reqParams);
        return chain.proceed(requestBuilder);
    }
}
