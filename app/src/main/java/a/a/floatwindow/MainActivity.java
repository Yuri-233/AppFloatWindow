package a.a.floatwindow;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import a.a.floatwindow.floatwindow.FloatWindowMgr;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "FloatWinodw";

    private Activity mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        if (FloatWindowMgr.requestFloatWindowPermission(mContext)) {
            FloatWindowMgr.getSingleInstance(mContext).showMenu();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FloatWindowMgr.onActivityResult(mContext, requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        int resId = v.getId();

    }

    private void initView() {
        setContentView(R.layout.activity_main);
        try {
            PackageInfo packInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            ApplicationInfo appInfo = mContext.getApplicationInfo();
            setTitle(mContext.getResources().getString(appInfo.labelRes) + packInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}