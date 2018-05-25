package com.bwie.myapplication.view.fragment;

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

public class FragmentCity extends Fragment {
    private ListView list_view_addr;
    private ProvinceBean provinceBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_addr_choose_layout,container,false);
        list_view_addr = view.findViewById(R.id.list_view_addr);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        provinceBean = (ProvinceBean)getArguments().getSerializable("provinceBean");
        selectData();
    }

    private void selectData() {
        final List<ProvinceBean> list=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = new AddrDao(getActivity()).initAddrDao();
        Cursor cursor = sqLiteDatabase.query("bma_regions", null, "parentid=?", new String[]{String.valueOf(provinceBean.getRegionid())}, null, null, null);
        while (cursor.moveToNext()){
            int regionid = cursor.getInt(cursor.getColumnIndex("regionid"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            ProvinceBean cityBean = new ProvinceBean(regionid, name);
            list.add(cityBean);
        }
        cursor.close();
        sqLiteDatabase.close();
        ProvinceAdapter provinceAdpter = new ProvinceAdapter(getActivity(),list);
        list_view_addr.setAdapter(provinceAdpter);
        list_view_addr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,FragmentXian.getInstance(provinceBean,list.get(i))).addToBackStack(null).commit();
            }
        });
    }
    public static FragmentCity getInstance(ProvinceBean provinceBean) {
        FragmentCity fragmentCity = new FragmentCity();
        Bundle bundle = new Bundle();
        bundle.putSerializable("provinceBean",provinceBean);
        fragmentCity.setArguments(bundle);
        return fragmentCity;
    }
}
