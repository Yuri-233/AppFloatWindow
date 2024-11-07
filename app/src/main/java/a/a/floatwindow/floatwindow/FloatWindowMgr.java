package a.a.floatwindow.floatwindow;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import org.json.JSONObject;

import a.a.floatwindow.MainActivity;

public class FloatWindowMgr {

    public static String TAG =MainActivity.TAG;


    private static final int REQUEST_CODE = 1001;
    private static FloatWindowMgr mInstance;

    private Context mContext;
    private MenuFloatWindow mMenuFloatWindow;
    private LogFloatWindow mLogFloatWindow;

    private FloatWindowMgr(Context context) {
        mContext = context.getApplicationContext();
        mMenuFloatWindow = new MenuFloatWindow(this);
        mMenuFloatWindow.onCreate(context);
        mLogFloatWindow = new LogFloatWindow(this);
        mLogFloatWindow.onCreate(context);
    }

    public static FloatWindowMgr getSingleInstance(Context context) {
        if (mInstance == null) {
            synchronized (FloatWindowMgr.class) {
                if (mInstance == null) {
                    mInstance = new FloatWindowMgr(context);
                }
            }
        }
        return mInstance;
    }

    public static boolean requestFloatWindowPermission(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(act)) {
                Log.e(TAG,"申请悬浮窗权限！");
                askForDrawOverlay(act);
                return false;
            } else {
                Log.e(TAG,"已获取悬浮窗权限！");
            }
        } else {
            Log.e(TAG,"系统版本小于23，不需要申请悬浮窗权限！");
        }
        return true;
    }

    private static void askForDrawOverlay(Activity act) {
        AlertDialog alertDialog = new AlertDialog.Builder(act)
                .setTitle("允许显示悬浮框")
                .setMessage("为了APP正常工作，请允许这项权限")
                .setPositiveButton("去设置", (dialog, which) -> {
                    act.startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + act.getPackageName())), REQUEST_CODE);
                    dialog.dismiss();
                })
                .setNegativeButton("稍后再说", (dialog, which) -> dialog.dismiss())
                .create();

        alertDialog.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        alertDialog.show();
    }

    public static void onActivityResult(Activity act, int requestCode, int resultCode, @Nullable Intent data) {
        Log.e(TAG,"requestCode = " + requestCode + ", resultCode = " + resultCode);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requestCode == REQUEST_CODE) {
                if (!Settings.canDrawOverlays(act)) {
                    Log.e(TAG,"授权失败！");
                } else {
                    Log.e(TAG,"授权成功！");
                    FloatWindowMgr.getSingleInstance(act).showMenu();
                }
            }
        }
    }

    public void showMenu() {
        mLogFloatWindow.hideWindow();
        mMenuFloatWindow.showWindow();
    }

    public void showLog() {
        mMenuFloatWindow.hideWindow();
        mLogFloatWindow.showWindow();
    }

    public boolean isShowLog() {
        return mLogFloatWindow.isShow();
    }

    public void closeFloatWindow() {
        mMenuFloatWindow.hideWindow();
        mLogFloatWindow.hideWindow();
    }

    public void updateLogData(JSONObject data) {
        // if (mLogFloatWindow.isShow()) {
        mLogFloatWindow.onDataUpdate(data);
        // }
    }
}
