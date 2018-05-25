package com.bwie.myapplication.view.acitvity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.bwie.myapplication.R;
import com.bwie.myapplication.util.ChenJinUtil;
import com.bwie.myapplication.view.fragment.FragmentFaXian;
import com.bwie.myapplication.view.fragment.FragmentFenLei;
import com.bwie.myapplication.view.fragment.FragmentHome;
import com.bwie.myapplication.view.fragment.FragmentMy;
import com.bwie.myapplication.view.fragment.FragmentShoppingCart;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    private FragmentManager manager;
    private FragmentHome fragmentHome;
    private FragmentFenLei fragmentFenLei;
    private FragmentFaXian fragmentFaXian;
    private FragmentShoppingCart fragmentShoppingCart;
    private FragmentMy fragmentMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //调用沉浸式的工具类，实现沉浸式效果
        ChenJinUtil.startChenJin(this);

        manager = getSupportFragmentManager();
        fragmentHome = new FragmentHome();
        manager.beginTransaction().add(R.id.frame, fragmentHome).commit();
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragmentHome != null) {
            transaction.hide(fragmentHome);
        }
        if (fragmentFenLei != null) {
            transaction.hide(fragmentFenLei);
        }
        if (fragmentFaXian != null) {
            transaction.hide(fragmentFaXian);
        }
        if (fragmentShoppingCart != null) {
            transaction.hide(fragmentShoppingCart);
        }
        if (fragmentMy != null) {
            transaction.hide(fragmentMy);
        }
        switch (i) {
            default:
                break;
            case R.id.radio_01:
                if (fragmentHome == null) {
                    fragmentHome = new FragmentHome();
                    transaction.add(R.id.frame, fragmentHome);
                } else {
                    transaction.show(fragmentHome);
                }
                break;
            case R.id.radio_02:
                if (fragmentFenLei == null) {
                    fragmentFenLei = new FragmentFenLei();
                    transaction.add(R.id.frame, fragmentFenLei);
                } else {
                    transaction.show(fragmentFenLei);
                }
                break;
            case R.id.radio_03:
                if (fragmentFaXian == null) {
                    fragmentFaXian = new FragmentFaXian();
                    transaction.add(R.id.frame, fragmentFaXian);
                } else {
                    transaction.show(fragmentFaXian);
                }
                break;
            case R.id.radio_04:
                if (fragmentShoppingCart == null) {
                    fragmentShoppingCart = new FragmentShoppingCart();
                    transaction.add(R.id.frame, fragmentShoppingCart);
                } else {
                    transaction.show(fragmentShoppingCart);
                }
                break;
            case R.id.radio_05:
                if (fragmentMy == null) {
                    fragmentMy = new FragmentMy();
                    transaction.add(R.id.frame, fragmentMy);
                } else {
                    transaction.show(fragmentMy);
                }
                break;
        }
        transaction.commit();
    }
}
