package com.example.yjgdoodle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.example.yjgdoodle.androids.utils.FileUtils;
import com.example.yjgdoodle.doodle.DoodleActivity;
import com.example.yjgdoodle.doodle.DoodleParams;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_DOODLE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoodleParams params = new DoodleParams(); // 涂鸦参数
                //  String path = FileUtils.getPath(this, uri);
                // params.mImagePath = path; // the file path of image
                DoodleActivity.startActivityForResult(MainActivity.this, params, REQ_CODE_DOODLE);
            }
        });

//        Intent intent = new Intent(Intent.ACTION_PICK, null);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        startActivityForResult(intent, 2);


      //  Log.e(this.getClass().getName(), "Uri:" + String.valueOf(uri));
    }



    /**
     * 请求许可权限
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 先判断有没有权限
            if (android.os.Environment.isExternalStorageManager()) {
                //自动获取权限
                autoObtainLocationPermission();
            } else {
                //跳转到设置界面引导用户打开
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 1101);
            }
        } else {
            //自动获取权限
            autoObtainLocationPermission();
        }
    }
    /**
     * 自动获取权限
     */
    private void autoObtainLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            //|| ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.INTERNET},
                    1102);
        } else {
        }
    }
    //1101 文件读写权限   1102 普通定位等权限 1000 文件权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        if (requestCode == 1101) {
            //initial();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {

                    Log.d("jsti","未授权"+ permissions[i].toString());
                }else{
                    Log.d("jsti", "已授权"+permissions[i].toString());
                }
            }
        }else if(requestCode == 1102) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Log.d("jsti", "未授权"+permissions[i].toString());
                }else{
                    Log.d("jsti", "已授权"+permissions[i].toString());
                }
            }
        } else if(requestCode == 1111) {

        }else if(requestCode ==1101){
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Log.d("jsti", "未授权" + permissions[i].toString());
                } else {
                    Log.d("jsti", "已授权" + permissions[i].toString());

                    // mapCommon.setOpenTDDataSource();
                }

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1101 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (android.os.Environment.isExternalStorageManager()) {
                Log.d("权限判断--------》", "已经拥有文件权限");
                //自动获取权限
                autoObtainLocationPermission();
            } else {
                Log.d("权限判断--------》", "获取权限失败");
                //跳转到设置界面引导用户打开
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 1102);
            }

        }
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();

                DoodleParams params = new DoodleParams(); // 涂鸦参数
                String path = FileUtils.getPath(this, uri);
                params.mImagePath = path; // the file path of image
                DoodleActivity.startActivityForResult(MainActivity.this, params, REQ_CODE_DOODLE);
                Log.e(this.getClass().getName(), "Uri:" + String.valueOf(uri));
            }
    }if(requestCode == 1) {
            if(data != null)
            {
                Intent intent = data;
                Log.d("TAG",data.getStringExtra(DoodleActivity.KEY_IMAGE_PATH));
            }




        }


}}