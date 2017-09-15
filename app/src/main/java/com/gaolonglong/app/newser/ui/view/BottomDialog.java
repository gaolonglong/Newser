package com.gaolonglong.app.newser.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gaolonglong.app.newser.R;

/**
 * Created by gaohailong on 2017/9/15.
 */

public class BottomDialog extends Dialog implements View.OnClickListener {

    private View view;
    private Context context;
    private TextView topBotton,middleBotton,cancleBotton;
    private OnClickBtnListener onClickTopBtnListener;
    private OnClickBtnListener onClickMiddleBtnListener;
    private OnClickBtnListener onClickCancleBtnListener;

    public BottomDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.context = context;
        init();
    }

    private void init() {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_layout, null);
        initView();
        addClickListener();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (view != null){
            setContentView(view);
        }
    }

    private void addClickListener() {
        topBotton.setOnClickListener(this);
        middleBotton.setOnClickListener(this);
        cancleBotton.setOnClickListener(this);
    }

    private void initView() {
        topBotton = view.findViewById(R.id.top_button);
        middleBotton = view.findViewById(R.id.middle_button);
        cancleBotton = view.findViewById(R.id.cancel_button);
        initAttributes();
    }

    private void initAttributes() {
        Window window = this.getWindow();
        window.getDecorView().setPadding(0,0,0,0);
        window.setWindowAnimations(R.style.style_bottom_dlg_anim);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.BOTTOM|Gravity.LEFT);
        window.setAttributes(lp);
        setCanceledOnTouchOutside(true);
    }

    public BottomDialog setTopButton(int stringId,OnClickBtnListener listener){
        setTopButton(context.getResources().getString(stringId),listener);
        return this;
    }

    public BottomDialog setTopButton(String text,OnClickBtnListener listener){
        setTopButton(text,R.color.black,listener);
        return this;
    }

    public BottomDialog setTopButton(String text,int colorId,OnClickBtnListener listener){
        setTopText(text,context.getResources().getColor(colorId));
        onClickTopBtnListener = listener;
        return this;
    }

    public BottomDialog setMiddleButton(int stringId,OnClickBtnListener listener){
        setMiddleButton(context.getResources().getString(stringId),listener);
        return this;
    }

    public BottomDialog setMiddleButton(String text,OnClickBtnListener listener){
        setMiddleButton(text,R.color.black,listener);
        return this;
    }

    public BottomDialog setMiddleButton(String text,int colorId,OnClickBtnListener listener){
        setMiddleText(text,context.getResources().getColor(colorId));
        onClickMiddleBtnListener = listener;
        return this;
    }

    public BottomDialog setCancleButton(OnClickBtnListener listener){
        onClickCancleBtnListener = listener;
        return this;
    }

    private void setTopText(String text, int color) {
        topBotton.setText(text);
        topBotton.setTextColor(color);
    }

    private void setMiddleText(String text, int color) {
        middleBotton.setText(text);
        middleBotton.setTextColor(color);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.top_button:
                if (onClickTopBtnListener!=null){
                    onClickTopBtnListener.onClickListener(this);
                }
                break;
            case R.id.middle_button:
                if (onClickMiddleBtnListener!=null){
                    onClickMiddleBtnListener.onClickListener(this);
                }
                break;
            case R.id.cancel_button:
                if (onClickCancleBtnListener!=null){
                    onClickCancleBtnListener.onClickListener(this);
                }
                break;
        }
    }

    public interface OnClickBtnListener{
        void onClickListener(BottomDialog dialog);
    }
}
