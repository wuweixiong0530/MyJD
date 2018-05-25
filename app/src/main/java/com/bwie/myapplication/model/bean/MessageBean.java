package com.bwie.myapplication.model.bean;

/**
 * Created by Administrator on 2018/5/25,0025.
 */

public class MessageBean {
    String name;
    String url;
    String id;

    public MessageBean(String keywords,String url,String id) {
        this.name = keywords;
        this.url = url;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}
