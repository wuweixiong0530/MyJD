package com.bwie.myapplication.utils;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private static RetrofitUtil retrofitUtil = null;
    private Retrofit retrofit;

    private RetrofitUtil() {

    }

    private RetrofitUtil(String baseUrl) {
        //初始化OkHttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //.addNetworkInterceptor()//网络拦截器
                .addInterceptor(new CommonParamsInterceptor())//拦截器
                .connectTimeout(15, TimeUnit.SECONDS)//连接超时
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();

        //创建retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())//添加转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//设置支持rxjava 注意是2
                .build();
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static RetrofitUtil getInstance(String baseUrl) {
        if (retrofitUtil == null) {
            synchronized (RetrofitUtil.class) {
                if (retrofitUtil == null) {
                    retrofitUtil = new RetrofitUtil(baseUrl);
                }
            }
        }

        return retrofitUtil;
    }

    public <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }

    public static ApiService getService() {
        return RetrofitUtil.getInstance(Constant.BASE_URL).createService(ApiService.class);
    }

    /**
     * baseUrl是域名   这里可以传活的
     *
     * @param baseUrl
     * @return
     */
    public static ApiService getService(String baseUrl) {
        return RetrofitUtil.getInstance(baseUrl).createService(ApiService.class);
    }

    /**
     * 公共参数的拦截器
     */
    private static class CommonParamsInterceptor implements Interceptor {

        //intercept方法就是拦截的意思....拦截的是一个请求,,,一旦拦截之后可以对请求的参数进行处理
        //Chain chain 链条的意思
        @Override
        public Response intercept(Chain chain) throws IOException {

            //调用request()方法得到拦截的请求
            Request request = chain.request();

            //获取请求的方式
            String method = request.method();//GET POST
            //拦截的请求的路径
            String oldUrl = request.url().toString();

            Log.e("----oldUrl", oldUrl);

            //要添加的公共参数...map
            Map<String, String> map = new HashMap<>();
            map.put("source", "android");

            if ("GET".equals(method)) {
                StringBuilder stringBuilder = new StringBuilder();//创建一个stringBuilder...字符串缓冲区

                stringBuilder.append(oldUrl);

                if (oldUrl.contains("?")) {
                    //?在最后面....2类型
                    if (oldUrl.indexOf("?") == oldUrl.length() - 1) {

                    } else {
                        //3类型...拼接上&
                        stringBuilder.append("&");
                    }
                } else {
                    //不包含? 属于1类型,,,先拼接上?号
                    stringBuilder.append("?");
                }

                //添加公共参数....source=android&appVersion=100&
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    //拼接
                    stringBuilder.append(entry.getKey())
                            .append("=")
                            .append(entry.getValue())
                            .append("&");
                }

                //删掉最后一个&符号
                if (stringBuilder.indexOf("&") != -1) {
                    stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));
                }
                //得到含有公共参数的新路径.....使用新路径去请求
                String newUrl = stringBuilder.toString();

                Log.e("----newUrl", newUrl);

                //新的路径构建一个新的请求
                request = request.newBuilder().url(newUrl).build();

            } else if ("POST".equals(method)) {

                //参数在请求的实体内容上....拿到以前的参数,把以前的参数和公共参数全都添加到请求实体上
                RequestBody requestBody = request.body();
                if (requestBody instanceof FormBody) {//前边是不是后边的子类/实例对象
                    FormBody oldBody = (FormBody) requestBody;//keywords...page

                    FormBody.Builder builder = new FormBody.Builder();

                    //先添加之前的参数
                    for (int i = 0; i < oldBody.size(); i++) {
                        //键值对的形式添加
                        builder.add(oldBody.name(i), oldBody.value(i));
                    }
                    //添加公共参数
                    for (Map.Entry<String, String> entry : map.entrySet()) {

                        builder.add(entry.getKey(), entry.getValue());

                    }

                    //构建一个新的请求
                    request = request.newBuilder().url(oldUrl).post(builder.build()).build();

                }

            }

            //执行请求 获取到响应
            Response response = chain.proceed(request);
            return response;
        }
    }

}
