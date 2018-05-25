package com.bwie.myapplication.view.acitvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.GetAllAddrBean;
import com.bwie.myapplication.presenter.GetAllAddrPresenter;
import com.bwie.myapplication.util.CommonUtil;
import com.bwie.myapplication.utils.Constant;
import com.bwie.myapplication.view.adapter.ManageAddrAdapter;
import com.bwie.myapplication.view.iview.GetAllAddrInter;
import com.google.gson.Gson;

import okhttp3.ResponseBody;

public class ManageAddrActivity extends AppCompatActivity implements GetAllAddrInter, View.OnClickListener {

    private ImageView detail_image_back;
    private ListView list_view_addr;
    /**
     * +添加新地址
     */
    private TextView text_add_new;
    private GetAllAddrPresenter getAllAddrPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_addr);
        detail_image_back = (ImageView) findViewById(R.id.detail_image_back);
        list_view_addr = (ListView) findViewById(R.id.list_view_addr);
        text_add_new = (TextView) findViewById(R.id.text_add_new);
        getAllAddrPresenter = new GetAllAddrPresenter(this);
        text_add_new.setOnClickListener(this);
        detail_image_back.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllAddrPresenter.getGetAllAddr(Constant.GET_ALL_ADDR_URL, CommonUtil.getString("uid"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_image_back:
                finish();
                break;
            case R.id.text_add_new:
                Intent intent = new Intent(ManageAddrActivity.this, AddNewAddrAcitvity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onGetAllAddrSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            GetAllAddrBean getAllAddrBean = new Gson().fromJson(string, GetAllAddrBean.class);
            if ("0".equals(getAllAddrBean.getCode())) {
                ManageAddrAdapter manageAddrAdapter = new ManageAddrAdapter(ManageAddrActivity.this, getAllAddrBean.getData(), getAllAddrPresenter);
                list_view_addr.setAdapter(manageAddrAdapter);
                list_view_addr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
