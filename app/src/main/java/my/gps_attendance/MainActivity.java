package my.gps_attendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private PermissionSupport permission;

    Intent intent;

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionCheck();
        intent = getIntent();
        init();
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
                        break;
                    case R.id.tab2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();
                        break;
                    case R.id.tab3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment3).commit();
                        break;
                    case R.id.tab4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment4).commit();
                        break;
                }
                return true;
            }
        });
    }

    private void permissionCheck() {
        permission = new PermissionSupport(this, this);

        if (!permission.checkPermission())
            permission.requestPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (!permission.permissionResult(requestCode, permissions, grantResults))
            permission.requestPermission();

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    void init(){
        UserInfo.USER_NUM = intent.getIntExtra("num", 0);
        UserInfo.USER_NAME = intent.getStringExtra("name");
        UserInfo.USER_MAJOR = intent.getStringExtra("major");
        UserInfo.USER_GRADE = intent.getIntExtra("grade", 0);
        Log.d("hi", "학번 : " + intent.getIntExtra("num", 0) +
                ", 이름 : " + intent.getStringExtra("name") +
                ", 학과 : " + intent.getStringExtra("major") +
                ", 학년 : " + intent.getIntExtra("grade", 0));
    }
}