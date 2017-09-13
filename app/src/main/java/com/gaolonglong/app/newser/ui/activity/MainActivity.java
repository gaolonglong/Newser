package com.gaolonglong.app.newser.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gaolonglong.app.newser.R;
import com.gaolonglong.app.newser.ui.fragment.DouBanFragment;
import com.gaolonglong.app.newser.ui.fragment.ThemeDialogFragment;
import com.gaolonglong.app.newser.ui.fragment.WeiXinFragment;
import com.gaolonglong.app.newser.ui.fragment.ZhiHuFragment;
import com.gaolonglong.app.newser.utils.ShareUtil;
import com.gaolonglong.app.newser.utils.SharedPrefUtil;
import com.gaolonglong.app.newser.utils.ThemeUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements ZhiHuFragment.OnFabShowListener, NavigationView.OnNavigationItemSelectedListener,ThemeDialogFragment.OnChangeThemeListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private LinearLayout navHeaderLL;
    private List<Fragment> fragmentList;
    private String[] titles = {"知乎","微信","豆瓣"};
    private Fragment[] fragments = {new ZhiHuFragment(),new WeiXinFragment(),new DouBanFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.setAppTheme(this);
        setContentView(R.layout.activity_main);

        //最终方案
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //5.0 全透明实现
        //getWindow.setStatusBarColor(Color.TRANSPARENT)
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View nav_header = navigationView.inflateHeaderView(R.layout.nav_header_main);
        navHeaderLL = nav_header.findViewById(R.id.nav_header_ll);

        initView();

        List<Integer> colorList = SharedPrefUtil.getThemeColor(this);
        if (colorList != null && colorList.get(0) != 0 && colorList.get(1) != 0){
            if (!SharedPrefUtil.getNightTag(this)){
                onChangeTheme(colorList.get(0),colorList.get(1));
            }
        }
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onChangeTheme(int colorPrimaryDark, int colorPrimary) {
        toolbar.setBackgroundColor(colorPrimary);
        tabLayout.setBackgroundColor(colorPrimary);
        navHeaderLL.setBackgroundColor(colorPrimary);
        getWindow().setStatusBarColor(colorPrimaryDark);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
        } else if (id == R.id.favorite) {
            startActivity(new Intent(this,FavoriteActivity.class));
        } else if (id == R.id.chang_skin) {
            ThemeDialogFragment dialogFragment = new ThemeDialogFragment();
            dialogFragment.show(getSupportFragmentManager(),"ThemeDialog");
        } else if (id == R.id.setting) {
            startActivity(new Intent(this,SettingActivity.class));
        } else if (id == R.id.nav_share) {
            ShareUtil.shareText(this, "Newser聚合阅读APP，带你发现更大的世界！下载地址：http://www.xxxx.com");
        } else if (id == R.id.nav_about) {
            Toast.makeText(this,"关于",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
