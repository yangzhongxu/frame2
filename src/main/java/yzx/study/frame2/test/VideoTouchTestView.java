package yzx.study.frame2.test;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.TextView;


/**
 * 模拟播放视频左右滑动的事件
 */
public class VideoTouchTestView extends TextView {

    public VideoTouchTestView(Context context) {
        super(context);
        gestureDetector = new GestureDetector(context, touchDeal);
        setTextColor(Color.BLACK);
        setGravity(Gravity.CENTER);
    }

    public VideoTouchTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(context, touchDeal);
        setTextColor(Color.BLACK);
        setGravity(Gravity.CENTER);
    }

    public VideoTouchTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gestureDetector = new GestureDetector(context, touchDeal);
        setTextColor(Color.BLACK);
        setGravity(Gravity.CENTER);
    }


    private float nowLight = 5;
    private GestureDetector gestureDetector;
    private GestureDetector.SimpleOnGestureListener touchDeal = new GestureDetector.SimpleOnGestureListener() {
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            int sx = getContext().getResources().getDisplayMetrics().widthPixels;
            int sy = getContext().getResources().getDisplayMetrics().heightPixels;

            if (e1.getRawX() < sx / 4) {
                int maxLight = 15;
                int maxTouchToMax = sy / 2;
                float per = maxTouchToMax / maxLight;
                float change = distanceY / per;

                nowLight += change;

                if (nowLight < 0)
                    nowLight = 0;
                else if (nowLight > maxLight)
                    nowLight = maxLight;

                setText("" + (int) nowLight);
            } else if (e1.getRawX() > sx / 4 * 3) {
                int gap = (int) (e2.getRawY() - e1.getRawY());

                setText(gap + "");
            }
            return true;
        }

        public boolean onSingleTapUp(MotionEvent e) {
            setText("single tag");
            return true;
        }
    };


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

}
