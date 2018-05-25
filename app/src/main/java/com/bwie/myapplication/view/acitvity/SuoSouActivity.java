package com.bwie.myapplication.view.acitvity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.MessageBean;
import com.bwie.myapplication.view.SouSuoSql.MySearBean;
import com.bwie.myapplication.view.adapter.GreenDaoAdapter;
import com.bwie.myapplication.view.custom.LiuShiBuJu;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import dao.DaoMaster;
import dao.DaoSession;
import dao.MySearBeanDao;

public class SuoSouActivity extends AppCompatActivity {
    private String mNames[] = {
            "袜子", "汾酒", "婴儿爬行垫",
            "电压力锅", "煲汤锅砂锅", "榨汁机",
            "羽毛球拍", "游戏显卡"};
    private ImageView image_fanhui;
    private EditText text_name;
    private LiuShiBuJu ls;
    private ListView lv;
    private Button btn;
    private MySearBeanDao beanDao;
    private String name;
    private MySearBean mySearchBean;

    List<MySearBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suo_sou);
        image_fanhui = findViewById(R.id.image_fanhui);
        text_name = findViewById(R.id.text_name);
        ls = findViewById(R.id.ls);
        lv = findViewById(R.id.lv);
        btn = findViewById(R.id.btn);


        //初始化greendao
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "green_dao.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        beanDao = daoSession.getMySearBeanDao();

        //进入就查询展示
        inSelect();

        image_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //点击条目将值赋给搜索框
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(android.R.id.text1);
                String string = textView.getText().toString();
                textView.setText(string);
                text_name.setText(string);
                Toast.makeText(SuoSouActivity.this, string, Toast.LENGTH_SHORT).show();
            }
        });

        initChildViews();
    }

    private void initChildViews() {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 10;
        lp.rightMargin = 10;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for (int i = 0; i < mNames.length; i++) {
            TextView view = new TextView(this);
            view.setText(mNames[i]);
            view.setTextColor(Color.WHITE);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    text_name.setText(mNames[finalI]);
                    Toast.makeText(SuoSouActivity.this, mNames[finalI], Toast.LENGTH_SHORT).show();
                }
            });
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
            ls.addView(view, lp);
        }
    }

    public void delall(View view) {
        beanDao.deleteAll();
        inSelect();
    }

    public void add(View view) {
        name = text_name.getText().toString();
        //添加到greendao
        mySearchBean = new MySearBean(null, name);
        beanDao.insert(mySearchBean);
        Log.i("sousuoactivity", "添加的数据是:" + mySearchBean.getName());
        //添加之后查询
        inSelect();
        //eventbus跳转传值(因为是在注册之前传的值，所以用postSticky粘性事件)
        MessageBean messageBean = new MessageBean(name,null,null);
        EventBus.getDefault().postSticky(messageBean);
        Intent intent = new Intent(SuoSouActivity.this, ProductListActivity.class);
//        intent.putExtra("keywords", name);
        startActivity(intent);
        Toast.makeText(this,messageBean.getName()+"+"+name,Toast.LENGTH_SHORT).show();
    }

    /**
     * 查询出展示数据
     *
     * 的方法
     */
    private void inSelect() {
        //再查询展示出来
        list = beanDao.queryBuilder().build().list();
        //用baseadapter显示数据
        GreenDaoAdapter adapter = new GreenDaoAdapter(this, list);
        lv.setAdapter(adapter);

        if (list.size() >= 1) {
            btn.setVisibility(View.VISIBLE);
        }
    }
}