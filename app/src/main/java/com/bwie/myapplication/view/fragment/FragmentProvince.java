package com.bwie.myapplication.view.fragment;

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
import com.bwie.myapplication.presenter.GetProvincePresenter;
import com.bwie.myapplication.view.adapter.ProvinceAdapter;
import com.bwie.myapplication.view.iview.GetProvinceInter;

import java.util.List;

public class FragmentProvince extends Fragment implements GetProvinceInter {

    private ListView list_view_addr;
    private GetProvincePresenter getProvincePresenter;

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
        getProvincePresenter = new GetProvincePresenter(this);
        getProvincePresenter.getProvince(getActivity());
    }

    @Override
    public void onGetProvince(final List<ProvinceBean> list) {
        ProvinceAdapter provinceAdpter = new ProvinceAdapter(getActivity(),list);
        list_view_addr.setAdapter(provinceAdpter);
        list_view_addr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,FragmentCity.getInstance(list.get(i))).addToBackStack(null).commit();
            }
        });
    }
}
