package com.bwie.myapplication.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.FenLeiBean;
import com.bwie.myapplication.presenter.FragmentHomeP;
import com.bwie.myapplication.util.ChenJinUtil;
import com.bwie.myapplication.utils.Constant;
import com.bwie.myapplication.view.acitvity.CustomCaptrueActivity;
import com.bwie.myapplication.view.acitvity.SuoSouActivity;
import com.bwie.myapplication.view.acitvity.WebViewActivity;
import com.bwie.myapplication.view.adapter.FenLeiAdapter;
import com.bwie.myapplication.view.iview.InterFragmentHome;
import com.google.gson.Gson;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;

public class FragmentFenLei extends Fragment implements InterFragmentHome {
    @BindView(R.id.sao_yi_sao)
    LinearLayout sao_yi_sao;
    @BindView(R.id.sou_suo)
    LinearLayout sou_suo;
    @BindView(R.id.fen_lei_list_view)
    ListView fen_lei_list_view;
    @BindView(R.id.fen_lei_frame)
    FrameLayout fen_lei_frame;
    private Unbinder unbinder;
    private FragmentHomeP fragmentHomeP;
    private FenLeiAdapter fenLeiAdapter;
    private FragmentFenLeiRight fragmentFenLeiRight;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fen_lei_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initChenJin();
        fragmentHomeP = new FragmentHomeP(this);
        fragmentHomeP.getFenLeiData(Constant.FEN_LEI_URL);
    }

    private void initChenJin() {
        ChenJinUtil.setStatusBarColor(getActivity(), getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initChenJin();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccessHome(ResponseBody responseBody) {

    }

    @Override
    public void onSuccessFenLei(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            final FenLeiBean fenLeiBean = new Gson().fromJson(string, FenLeiBean.class);
            //给listView设置适配器
            fenLeiAdapter = new FenLeiAdapter(getActivity(), fenLeiBean);
            fen_lei_list_view.setAdapter(fenLeiAdapter);

            //默认显示京东超市右边对应的数据
            FragmentFenLeiRight fragmentFenLeiRight = FragmentFenLeiRight.getInstance(fenLeiBean.getData().get(0).getCid());

            getChildFragmentManager().beginTransaction().replace(R.id.fen_lei_frame,fragmentFenLeiRight).commit();


            //条目点击事件
            fen_lei_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    //设置适配器当前位置的方法
                    fenLeiAdapter.setCurPositon(i);
                    //刷新适配器...getView重新执行
                    fenLeiAdapter.notifyDataSetChanged();
                    //滚动到指定的位置,,,第一个参数是滚动哪一个条目,,,滚动到哪个位置/偏移量
                    fen_lei_list_view.smoothScrollToPositionFromTop(i,(adapterView.getHeight()-view.getHeight())/2);

                    //使用fragment替换右边frameLayout....fragment之间的传值
                    FragmentFenLeiRight fragmentFenLeiRight = FragmentFenLeiRight.getInstance(fenLeiBean.getData().get(i).getCid());


                    getChildFragmentManager().beginTransaction().replace(R.id.fen_lei_frame,fragmentFenLeiRight).commit();


                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.sao_yi_sao, R.id.sou_suo})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.sao_yi_sao:
                Intent intent = new Intent(getActivity(), CustomCaptrueActivity.class);
                startActivityForResult(intent, 1001);
                break;
            case R.id.sou_suo:
                Intent intent1 = new Intent(getActivity(), SuoSouActivity.class);
                startActivity(intent1);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (result.startsWith("http://")) {
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("detaiUrl", result);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "暂不支持此二维码", Toast.LENGTH_LONG).show();
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
