package com.bwie.myapplication.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.GetAllAddrBean;
import com.bwie.myapplication.presenter.GetAllAddrPresenter;
import com.bwie.myapplication.util.CommonUtil;
import com.bwie.myapplication.utils.Constant;
import com.bwie.myapplication.utils.RetrofitUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ManageAddrAdapter extends BaseAdapter {
    private GetAllAddrPresenter getAllAddrPresenter;
    private List<GetAllAddrBean.DataBean> data;
    private Context context;

    public ManageAddrAdapter(Context context, List<GetAllAddrBean.DataBean> data, GetAllAddrPresenter getAllAddrPresenter) {
        this.context = context;
        this.data = data;
        this.getAllAddrPresenter = getAllAddrPresenter;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.manage_addr_item_layout, null);
            holder = new ViewHolder();

            holder.text_name = view.findViewById(R.id.text_name);
            holder.text_phone = view.findViewById(R.id.text_phone);
            holder.text_addr = view.findViewById(R.id.text_addr);
            holder.checkBox = view.findViewById(R.id.check_box_default);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final GetAllAddrBean.DataBean dataBean = data.get(position);
        holder.text_name.setText(dataBean.getName());
        holder.text_phone.setText(String.valueOf(dataBean.getMobile()));
        holder.text_addr.setText(dataBean.getAddr());

        //根据status选中checkBox
        if (dataBean.getStatus() == 1) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        //点击事件...设置默认地址uid=71&addrid=3&status=1
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> params = new HashMap<>();
                params.put("uid", CommonUtil.getString("uid"));
                params.put("addrid", String.valueOf(dataBean.getAddrid()));
                params.put("status", String.valueOf(holder.checkBox.isChecked() ? 1 : 0));
                RetrofitUtil.getService().doGet(Constant.SET_DEFAULT_ADDR_URL, params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                getAllAddrPresenter.getGetAllAddr(Constant.GET_ALL_ADDR_URL, CommonUtil.getString("uid"));
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });


        return view;
    }

    private class ViewHolder {
        TextView text_name;
        TextView text_phone;
        TextView text_addr;
        CheckBox checkBox;
    }
}
