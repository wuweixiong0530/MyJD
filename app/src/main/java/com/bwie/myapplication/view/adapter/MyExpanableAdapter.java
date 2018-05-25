package com.bwie.myapplication.view.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.CartBean;
import com.bwie.myapplication.model.bean.CountPriceBean;
import com.bwie.myapplication.presenter.FragmentCartPresenter;
import com.bwie.myapplication.util.CommonUtil;
import com.bwie.myapplication.utils.Constant;
import com.bwie.myapplication.utils.RetrofitUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MyExpanableAdapter extends BaseExpandableListAdapter {
    Context context;
    CartBean cartBean;
    Handler handler;
    RelativeLayout relative_progress;
    FragmentCartPresenter fragmentCartPresenter;
    private int childIndex;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private int allIndex;

    public MyExpanableAdapter(Context context, CartBean cartBean, Handler handler, RelativeLayout relative_progress, FragmentCartPresenter fragmentCartPresenter) {
        this.cartBean = cartBean;
        this.context = context;
        this.handler = handler;
        this.relative_progress = relative_progress;
        this.fragmentCartPresenter = fragmentCartPresenter;
    }

    @Override
    public int getGroupCount() {
        return cartBean.getData().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return cartBean.getData().get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return cartBean.getData().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return cartBean.getData().get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        final GroupHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.cart_group_item_layout, null);
            holder = new GroupHolder();
            holder.group_check_box = view.findViewById(R.id.group_check_box);
            holder.group_shop_name = view.findViewById(R.id.group_shop_name);
            view.setTag(holder);
        } else {
            holder = (GroupHolder) view.getTag();
        }
        final CartBean.DataBean dataBean = cartBean.getData().get(groupPosition);
        holder.group_shop_name.setText(dataBean.getSellerName());
        holder.group_check_box.setChecked(dataBean.isGroupChecked());
        holder.group_check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relative_progress.setVisibility(View.VISIBLE);
                childIndex = 0;
                updateChildInGroup(holder.group_check_box.isChecked(), dataBean);
            }
        });
        return view;
    }

    private void updateChildInGroup(final boolean checked, final CartBean.DataBean dataBean) {
        CartBean.DataBean.ListBean listBean = dataBean.getList().get(childIndex);
        Map<String, String> params = new HashMap<>();
        params.put("uid", CommonUtil.getString("uid"));
        params.put("sellerid", String.valueOf(listBean.getSellerid()));
        params.put("pid", String.valueOf(listBean.getPid()));
        params.put("selected", String.valueOf(checked ? 1 : 0));
        params.put("num", String.valueOf(listBean.getNum()));
        RetrofitUtil.getService().doGet(Constant.UPDATE_CART_URL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        childIndex++;
                        if (childIndex < dataBean.getList().size()) {
                            updateChildInGroup(checked, dataBean);
                        } else {
                            fragmentCartPresenter.getCartData(Constant.SELECT_CART, CommonUtil.getString("uid"));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        ChildHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.cart_child_item_layout, null);
            holder = new ChildHolder();
            holder.child_check_box = view.findViewById(R.id.child_check_box);
            holder.child_image = view.findViewById(R.id.child_image);
            holder.child_title = view.findViewById(R.id.child_title);
            holder.child_text_add = view.findViewById(R.id.child_text_add);
            holder.child_text_num = view.findViewById(R.id.child_text_num);
            holder.child_text_jian = view.findViewById(R.id.child_text_jian);
            holder.child_price = view.findViewById(R.id.child_price);
            holder.child_text_delete = view.findViewById(R.id.child_text_delete);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        final CartBean.DataBean.ListBean listBean = cartBean.getData().get(groupPosition).getList().get(childPosition);
        holder.child_check_box.setChecked(listBean.getSelected() == 1 ? true : false);
//        Glide.with(context).load(listBean.getImages().split("\\|")[0]).into(holder.child_image);
        holder.child_title.setText(listBean.getTitle());
        holder.child_price.setText("￥" + listBean.getBargainPrice());
        holder.child_text_num.setText(listBean.getNum() + "");
        holder.child_check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relative_progress.setVerticalGravity(View.VISIBLE);
                Map<String, String> params = new HashMap();
                params.put("uid", CommonUtil.getString("uid"));
                params.put("sellerid", String.valueOf(listBean.getSellerid()));
                params.put("pid", String.valueOf(listBean.getPid()));
                params.put("selected", String.valueOf(listBean.getSelected() == 0 ? 1 : 0));
                params.put("num", String.valueOf(listBean.getNum()));
                RetrofitUtil.getService().doGet(Constant.UPDATE_CART_URL, params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                fragmentCartPresenter.getCartData(Constant.SELECT_CART, CommonUtil.getString("uid"));
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
        holder.child_text_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relative_progress.setVerticalGravity(View.VISIBLE);
                Map<String, String> params = new HashMap();
                params.put("uid", CommonUtil.getString("uid"));
                params.put("sellerid", String.valueOf(listBean.getSellerid()));
                params.put("pid", String.valueOf(listBean.getPid()));
                params.put("selected", String.valueOf(listBean.getSelected()));
                params.put("num", String.valueOf(listBean.getNum() + 1));
                RetrofitUtil.getService().doGet(Constant.UPDATE_CART_URL, params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                fragmentCartPresenter.getCartData(Constant.SELECT_CART, CommonUtil.getString("uid"));
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
        holder.child_text_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = listBean.getNum();
                if (num == 1) {
                    return;
                }
                relative_progress.setVerticalGravity(View.VISIBLE);
                Map<String, String> params = new HashMap();
                params.put("uid", CommonUtil.getString("uid"));
                params.put("sellerid", String.valueOf(listBean.getSellerid()));
                params.put("pid", String.valueOf(listBean.getPid()));
                params.put("selected", String.valueOf(listBean.getSelected()));
                params.put("num", String.valueOf(num - 1));
                RetrofitUtil.getService().doGet(Constant.UPDATE_CART_URL, params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                fragmentCartPresenter.getCartData(Constant.SELECT_CART, CommonUtil.getString("uid"));
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
        holder.child_text_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relative_progress.setVerticalGravity(View.VISIBLE);
                Map<String, String> params = new HashMap();
                params.put("uid", CommonUtil.getString("uid"));
                params.put("pid", String.valueOf(listBean.getPid()));
                RetrofitUtil.getService().doGet(Constant.DELETE_CART_URL, params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                fragmentCartPresenter.getCartData(Constant.SELECT_CART, CommonUtil.getString("uid"));
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

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void sendPriceAndCount() {
        double price = 0;
        int count = 0;
        for (int i = 0; i < cartBean.getData().size(); i++) {
            List<CartBean.DataBean.ListBean> list = cartBean.getData().get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getSelected() == 1) {
                    price = price + list.get(j).getBargainPrice() * list.get(j).getNum();
                    count += list.get(j).getNum();
                }
            }
        }
        String priceString = decimalFormat.format(price);
        CountPriceBean countPriceBean = new CountPriceBean(priceString, count);
        Message msg = Message.obtain();
        msg.what = 1;
        msg.obj = countPriceBean;
        handler.sendMessage(msg);
    }

    public void setAllChildChecked(boolean checked) {
        List<CartBean.DataBean.ListBean> allList = new ArrayList<>();
        for (int i = 0; i < cartBean.getData().size(); i++) {
            List<CartBean.DataBean.ListBean> list = cartBean.getData().get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                allList.add(list.get(j));
            }
        }
        relative_progress.setVerticalGravity(View.VISIBLE);
        allIndex = 0;
        updateAllChild(allList, checked);
    }

    private void updateAllChild(final List<CartBean.DataBean.ListBean> allList, final boolean checked) {
        CartBean.DataBean.ListBean listBean = allList.get(allIndex);
        Map<String, String> params = new HashMap<>();
        params.put("uid", CommonUtil.getString("uid"));
        params.put("sellerid", String.valueOf(listBean.getSellerid()));
        params.put("pid", String.valueOf(listBean.getPid()));
        params.put("selected", String.valueOf(checked ? 1 : 0));
        params.put("num", String.valueOf(listBean.getNum()));
        RetrofitUtil.getService().doGet(Constant.UPDATE_CART_URL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        allIndex++;
                        if (allIndex < allList.size()) {
                            updateAllChild(allList, checked);
                        } else {
                            fragmentCartPresenter.getCartData(Constant.SELECT_CART, CommonUtil.getString("uid"));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private class GroupHolder {
        CheckBox group_check_box;
        TextView group_shop_name;
    }

    private class ChildHolder {
        CheckBox child_check_box;
        ImageView child_image;
        TextView child_title;
        TextView child_price;
        TextView child_text_jian;
        TextView child_text_num;
        TextView child_text_add;
        TextView child_text_delete;
    }
}
