package com.bwie.myapplication.view.SouSuoSql;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Administrator on 2018/5/12,0012.
 */

@Entity
public class MySearBean {
    @Id(autoincrement = true)
    Long id;
    @Property
    String name;

    @Generated(hash = 1059414763)
    public MySearBean() {
    }

    @Generated(hash = 990682223)
    public MySearBean(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
