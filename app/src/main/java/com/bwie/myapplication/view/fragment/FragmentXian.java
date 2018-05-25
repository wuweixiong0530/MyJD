package com.bwie.myapplication.view.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.ProvinceBean;
import com.bwie.myapplication.model.db.AddrDao;
import com.bwie.myapplication.view.adapter.ProvinceAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentXian extends Fragment {
    private ListView list_view_addr;
    private ProvinceBean provinceBean;
    private ProvinceBean cityBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addr_choose_layout,container,false);
        list_view_addr = view.findViewById(R.id.list_view_addr);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //获取
        Bundle bundle = getArguments();
        if (bundle != null) {
            //省的数据
            provinceBean = (ProvinceBean) bundle.getSerializable("provinceBean");
            //获取市的数据
            cityBean = (ProvinceBean) bundle.getSerializable("cityBean");

            //根据市查询线
            selectXian();
        }


    }

    private void selectXian() {
        final List<ProvinceBean> list = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = new AddrDao(getActivity()).initAddrDao();

        //select * from table where parentid = regionid
        Cursor cursor = sqLiteDatabase.query("bma_regions", null, "parentid = ?", new String[]{String.valueOf(cityBean.getRegionid())}, null, null, null);
        while (cursor.moveToNext()) {
            int regionid = cursor.getInt(cursor.getColumnIndex("regionid"));//获取数据的方法里面的参数是列的角标...根据列名去获取列的角标
            String name = cursor.getString(cursor.getColumnIndex("name"));

            //封装province对象
            ProvinceBean xianBean = new ProvinceBean(regionid, name);

            list.add(xianBean);
        }

        cursor.close();
        sqLiteDatabase.close();

        //设置适配器
        //设置适配器
        ProvinceAdapter provinceAdapter = new ProvinceAdapter(getActivity(), list);
        list_view_addr.setAdapter(provinceAdapter);

        list_view_addr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                //回传值...省 市 区
                Intent intent = new Intent();
                intent.putExtra("addr",provinceBean.getName().trim()+cityBean.getName().trim()+list.get(i).getName().trim());

                getActivity().setResult(3002,intent);
                getActivity().finish();

            }
        });
    }

    public static FragmentXian getInstance(ProvinceBean provinceBean, ProvinceBean cityBean) {
        FragmentXian fragmentXian = new FragmentXian();

        Bundle bundle = new Bundle();

        bundle.putSerializable("provinceBean",provinceBean);
        bundle.putSerializable("cityBean",cityBean);

        fragmentXian.setArguments(bundle);

        return fragmentXian;
    }
}
