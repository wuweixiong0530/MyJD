package com.bwie.myapplication.view.acitvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.GetAllAddrBean;
import com.bwie.myapplication.presenter.GetAllAddrPresenter;
import com.bwie.myapplication.util.CommonUtil;
import com.bwie.myapplication.utils.Constant;
import com.bwie.myapplication.view.adapter.GetAllAddrAdapter;
import com.bwie.myapplication.view.iview.GetAllAddrInter;
import com.google.gson.Gson;

import okhttp3.ResponseBody;

public class ChooseAddrActivity extends AppCompatActivity implements View.OnClickListener, GetAllAddrInter {

    private ImageView detail_image_back;
    private RelativeLayout detai_relative;
    private ListView list_view_addr;
    /**
     * 管理
     */
    private TextView text_manage;
    private GetAllAddrPresenter getAllAddrPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_addr);
        detail_image_back = findViewById(R.id.detail_image_back);
        text_manage = findViewById(R.id.text_manage);
        detai_relative = findViewById(R.id.detai_relative);
        list_view_addr = findViewById(R.id.list_view_addr);
        //点击事件
        detail_image_back.setOnClickListener(this);
        text_manage.setOnClickListener(this);
        getAllAddrPresenter = new GetAllAddrPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取地址列表的最新数据
        getAllAddrPresenter.getGetAllAddr(Constant.GET_ALL_ADDR_URL, CommonUtil.getString("uid"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_image_back:
                finish();
                break;
            case R.id.text_manage://管理地址
                Intent intent = new Intent(ChooseAddrActivity.this, ManageAddrActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onGetAllAddrSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            final GetAllAddrBean getAllAddrBean = new Gson().fromJson(string, GetAllAddrBean.class);
            if ("0".equals(getAllAddrBean.getCode())) {
                //设置适配器
                GetAllAddrAdapter getAllAddrAdapter = new GetAllAddrAdapter(ChooseAddrActivity.this, getAllAddrBean.getData());
                list_view_addr.setAdapter(getAllAddrAdapter);

                //设置条目的点击事件
                list_view_addr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Intent intent = new Intent();
                        intent.putExtra("addrBean", getAllAddrBean.getData().get(position));
                        setResult(2002, intent);
                        finish();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
