package com.gaolonglong.app.newser.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaolonglong.app.newser.R;
import com.gaolonglong.app.newser.utils.SharedPrefUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gaohailong on 2017/9/12.
 */

public class ThemeDialogFragment extends DialogFragment implements View.OnClickListener {

    private CircleImageView color01,color02,color03,color04,color05,color06,color07,color08,color09;
    private OnChangeThemeListener onChangeThemeListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.theme_dialog_layout, container);
        initView(view);
        return view;
    }

    private void initView(View view) {
        color01 = view.findViewById(R.id.color_01);
        color02 = view.findViewById(R.id.color_02);
        color03 = view.findViewById(R.id.color_03);
        color04 = view.findViewById(R.id.color_04);
        color05 = view.findViewById(R.id.color_05);
        color06 = view.findViewById(R.id.color_06);
        color07 = view.findViewById(R.id.color_07);
        color08 = view.findViewById(R.id.color_08);
        color09 = view.findViewById(R.id.color_09);

        onChangeThemeListener = (OnChangeThemeListener) getActivity();

        color01.setOnClickListener(this);
        color02.setOnClickListener(this);
        color03.setOnClickListener(this);
        color04.setOnClickListener(this);
        color05.setOnClickListener(this);
        color06.setOnClickListener(this);
        color07.setOnClickListener(this);
        color08.setOnClickListener(this);
        color09.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.color_01:
                setThemeColor(R.color.color_10,R.color.color_11);
                break;
            case R.id.color_02:
                setThemeColor(R.color.color_20,R.color.color_21);
                break;
            case R.id.color_03:
                setThemeColor(R.color.color_30,R.color.color_31);
                break;
            case R.id.color_04:
                setThemeColor(R.color.color_40,R.color.color_41);
                break;
            case R.id.color_05:
                setThemeColor(R.color.color_50,R.color.color_51);
                break;
            case R.id.color_06:
                setThemeColor(R.color.color_60,R.color.color_61);
                break;
            case R.id.color_07:
                setThemeColor(R.color.color_70,R.color.color_71);
                break;
            case R.id.color_08:
                setThemeColor(R.color.color_80,R.color.color_81);
                break;
            case R.id.color_09:
                setThemeColor(R.color.color_90,R.color.color_91);
                break;
        }
    }

    public void setThemeColor(int colorPrimaryDark,int colorPrimary){
        onChangeThemeListener.onChangeTheme(getResources().getColor(colorPrimaryDark),getResources().getColor(colorPrimary));
        SharedPrefUtil.setThemeColor(getActivity(),getResources().getColor(colorPrimaryDark),getResources().getColor(colorPrimary));
    }

    public interface OnChangeThemeListener{
        void onChangeTheme(int colorPrimaryDark,int colorPrimary);
    }
}
