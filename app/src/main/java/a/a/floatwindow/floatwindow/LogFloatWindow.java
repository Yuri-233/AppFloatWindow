package a.a.floatwindow.floatwindow;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONObject;

import a.a.floatwindow.MainActivity;
import a.a.floatwindow.R;


class LogFloatWindow extends AbsFloatWindow implements View.OnClickListener, View.OnTouchListener, CompoundButton.OnCheckedChangeListener {
    public static String TAG = MainActivity.TAG;
    private LinearLayout mLayoutLog;
    private ImageView mImgClose;
    private Button mBtnStart;

    private Handler mHandler;
    private int x;
    private int y;


    public LogFloatWindow(FloatWindowMgr mgr) {
        super(mgr);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate() {
        mLayoutParams.gravity = Gravity.CENTER;
        mLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        setRootView(R.layout.float_log);
        mLayoutLog = (LinearLayout) getViewById(R.id.layout_log);
        mLayoutLog.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = (int) event.getRawX();
                        y = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int nowX = (int) event.getRawX();
                        int nowY = (int) event.getRawY();
                        int movedX = nowX - x;
                        int movedY = nowY - y;
                        x = nowX;
                        y = nowY;

                        // 更新悬浮窗控件布局
                        mLayoutParams.x = mLayoutParams.x - movedX;
                        mLayoutParams.y = mLayoutParams.y + movedY;
                        updateWindow();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        mImgClose = (ImageView) getViewById(R.id.img_close);
        mBtnStart = (Button) getViewById(R.id.btn_start);

        mImgClose.setOnClickListener(this);
        mBtnStart.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int resId = v.getId();

        if (resId == R.id.img_close ){
            mFloatWindowMgr.showMenu();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Toast.makeText(mContext, "请先填写参数保存，获取采集数据", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.e(TAG,"onCheckedChanged isChecked = " + isChecked);
        int resId = buttonView.getId();

    }

    @Override
    protected void showWindow() {
        super.showWindow();

    }

    @Override
    public void onDataUpdate(JSONObject data) {
        super.onDataUpdate(data);


    }

}
