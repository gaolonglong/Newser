package com.gaolonglong.app.newser.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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
import com.gaolonglong.app.newser.ui.view.BottomDialog;
import com.gaolonglong.app.newser.utils.ShareUtil;
import com.gaolonglong.app.newser.utils.SharedPrefUtil;
import com.gaolonglong.app.newser.utils.ThemeUtil;
import com.gaolonglong.app.newser.utils.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ThemeDialogFragment.OnChangeThemeListener {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 10002;
    private static final int PICK_ACTIVITY_REQUEST_CODE = 10003;
    private static final int CROP_ACTIVITY_REQUEST_CODE = 10008;
    private static final int REQUEST_CODE_PERMISSION = 0x00099;
    private String imageFilePath; //拍照和选择照片后图片路径
    private File cropFile; //裁剪后的图片文件
    private Uri pickPhotoImageUri; //API22以下相册选择图片uri

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private LinearLayout navHeaderLL;
    private CircleImageView headImage;
    private List<Fragment> fragmentList;
    private String[] titles = {"知乎","微信","豆瓣"};
    private Fragment[] fragments = {new ZhiHuFragment(),new WeiXinFragment(),new DouBanFragment()};
    private OnFabShowListener onFabShowListener;

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
                ZhiHuFragment zhiHuFragment = new ZhiHuFragment();
                zhiHuFragment.recyclerToTop();
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
        headImage = nav_header.findViewById(R.id.head_image);
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialog dialog = new BottomDialog(MainActivity.this);
                dialog.setTopButton("拍照", new BottomDialog.OnClickBtnListener() {
                    @Override
                    public void onClickListener(BottomDialog dialog) {
                        dialog.dismiss();
                        takePhoto();
                    }
                }).setMiddleButton("从手机相册选择照片", new BottomDialog.OnClickBtnListener() {
                    @Override
                    public void onClickListener(BottomDialog dialog) {
                        dialog.dismiss();
                        pickPhoto();
                    }
                }).setCancleButton(new BottomDialog.OnClickBtnListener() {
                    @Override
                    public void onClickListener(BottomDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        initView();

        List<Integer> colorList = SharedPrefUtil.getThemeColor(this);
        if (colorList != null && colorList.get(0) != 0 && colorList.get(1) != 0){
            if (!SharedPrefUtil.getNightTag(this)){
                onChangeTheme(colorList.get(0),colorList.get(1));
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestPermission(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                });
            }
        }, 2000);
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onChangeTheme(int colorPrimaryDark, int colorPrimary) {
        toolbar.setBackgroundColor(colorPrimary);
        tabLayout.setBackgroundColor(colorPrimary);
        navHeaderLL.setBackgroundColor(colorPrimary);
        getWindow().setStatusBarColor(colorPrimaryDark);
    }

    //拍照获取图片
    private void takePhoto() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File imageFile = new File(Environment.getExternalStorageDirectory(), "/newser/temp/" + System.currentTimeMillis() + ".jpg");
            if (!imageFile.getParentFile().exists()) imageFile.getParentFile().mkdirs();
            imageFilePath = imageFile.getPath();
            //兼容性判断
            Uri imageUri;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                imageUri = Util.file2Uri(this, imageFile);
            } else {
                imageUri = Uri.fromFile(imageFile);
            }
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI

            List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                grantUriPermission(packageName, imageUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    //从相册中取图片
    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //CropHelper.handleResult(this, requestCode, resultCode, data);
        switch (requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                //拍照
                if (resultCode == Activity.RESULT_OK) {
                    crop(false);
                }
                break;

            case CROP_ACTIVITY_REQUEST_CODE:
                //裁剪完成
                if (data != null) {
                    Bitmap bitmap;
                    try {
                        bitmap = BitmapFactory.decodeFile(cropFile.getPath());
                        headImage.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case PICK_ACTIVITY_REQUEST_CODE:
                //从相册选择
                if (data != null && resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                        imageFilePath = Util.getPathByUri4kitkat(this, data.getData());
                    } else {
                        pickPhotoImageUri = data.getData();
                    }

                    crop(true);
                }
                break;
        }

    }

    /**
     * 裁剪
     *
     * @param isPick 是否是从相册选择
     */
    private void crop(boolean isPick) {
        cropFile = new File(Environment.getExternalStorageDirectory(), "/newser/temp/" + System.currentTimeMillis() + ".jpg");
        if (!cropFile.getParentFile().exists()) cropFile.getParentFile().mkdirs();
        Uri outputUri, imageUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            outputUri = Util.file2Uri(this, cropFile);
            imageUri = Util.file2Uri(this, new File(imageFilePath));
        } else {
            outputUri = Uri.fromFile(cropFile);
            imageUri = isPick ? pickPhotoImageUri : Uri.fromFile(new File(imageFilePath));
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection

        //授予"相机"保存文件的权限 针对API24+
        List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            grantUriPermission(packageName, outputUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(intent, CROP_ACTIVITY_REQUEST_CODE);
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
            startActivity(new Intent(this,AboutActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     */
    public void requestPermission(String[] permissions) {
        if (checkPermissions(permissions)) {
            permissionSuccess();
        } else {
            List<String> needPermissions = getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()]),
                    REQUEST_CODE_PERMISSION);
        }
    }

    /**
     * 检测所有的权限是否都已授权
     *
     * @param permissions
     * @return
     */
    private boolean checkPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    private List<String> getDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }

    /**
     * 系统请求权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (verifyPermissions(grantResults)) {
                permissionSuccess();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("提示信息")
                        .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                permissionFail();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startAppSettings();
                            }
                        })
                        .setCancelable(false).show();
            }
        }
    }

    private void permissionSuccess() {
        //Toast.makeText(this,"授权成功！",Toast.LENGTH_SHORT).show();
    }

    private void permissionFail() {
        finish();
    }

    /**
     * 确认所有的权限是否都已授权
     *
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 启动当前应用设置页面
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    public interface OnFabShowListener {
        void isShow(boolean show);
    }

}
