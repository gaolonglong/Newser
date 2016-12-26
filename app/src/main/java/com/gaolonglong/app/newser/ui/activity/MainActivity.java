package com.gaolonglong.app.newser.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gaolonglong.app.newser.R;
import com.gaolonglong.app.newser.ui.fragment.DouBanFragment;
import com.gaolonglong.app.newser.ui.fragment.WeiXinFragment;
import com.gaolonglong.app.newser.ui.fragment.ZhiHuFragment;
import com.gaolonglong.app.newser.utils.ThemeUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ZhiHuFragment.OnFabShowListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private List<Fragment> fragmentList;
    private String[] titles = {"知乎","微信","豆瓣"};
    private Fragment[] fragments = {new ZhiHuFragment(),new WeiXinFragment(),new DouBanFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.setAppTheme(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();

    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        if (fragmentList == null){
            fragmentList = new ArrayList<>();
        }
        for (Fragment fragment : fragments){
            fragmentList.add(fragment);
        }

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void isShow(boolean show) {
        if (show){
            fab.show();
        }else {
            fab.hide();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.action_change_theme){
            if (ThemeUtil.isNightTheme){
                ThemeUtil.changeAppTheme(this, ThemeUtil.DAY_THEME);
                ThemeUtil.isNightTheme = false;
            }else {
                ThemeUtil.changeAppTheme(this, ThemeUtil.NIGHT_THEME);
                ThemeUtil.isNightTheme = true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
