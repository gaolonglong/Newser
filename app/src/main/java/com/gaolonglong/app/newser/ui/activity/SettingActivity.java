package com.gaolonglong.app.newser.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.gaolonglong.app.newser.R;
import com.gaolonglong.app.newser.utils.SharedPrefUtil;

/**
 * Created by gaohailong on 2017/9/13.
 */

public class SettingActivity extends AppCompatActivity {

    private CheckBox offlineCaching;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("设置");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        offlineCaching = (CheckBox) findViewById(R.id.offline_caching);
        offlineCaching.setChecked(SharedPrefUtil.getCacheTag(SettingActivity.this));
        offlineCaching.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    Intent i = new Intent("com.gaolonglong.app.newser.ui.fragment.OfflineCacheReceive2");
                    i.putExtra("isOfflineCaching",true);
                    sendBroadcast(i);
                    SharedPrefUtil.setCacheTag(SettingActivity.this,true);
                }else {
                    Intent i = new Intent("com.gaolonglong.app.newser.ui.fragment.OfflineCacheReceive2");
                    i.putExtra("isOfflineCaching",false);
                    sendBroadcast(i);
                    SharedPrefUtil.setCacheTag(SettingActivity.this,false);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
