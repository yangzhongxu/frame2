package yzx.study.frame2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;

public class ThroughLayout extends FrameLayout {

    public ThroughLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mDelegateView == null || !canThrough)
            return false;

        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
            while ((parent = parent.getParent()) != null)
                parent.requestDisallowInterceptTouchEvent(true);
        }

        mDelegateView.dispatchTouchEvent(event);
        return true;
    }


    private View mDelegateView;
    private boolean canThrough;


    public void setViewOnLowLayer(View view) {
        mDelegateView = view;
    }

    public void setCanThrough(boolean can) {
        canThrough = can;
    }

}
