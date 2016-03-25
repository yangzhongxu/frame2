package yzx.study.frame2.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public abstract class AbsPullRefreshLayout extends LinearLayout {

    public AbsPullRefreshLayout(Context context) {
        super(context);
    }

    public AbsPullRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsPullRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //
    //
    //


    private View mHeaderView;
    private View mContentView;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 1)
            throw new IllegalStateException("必须有且只有一个子View");
        setOrientation(VERTICAL);
        if (mHeaderView == null) {
            mContentView = getChildAt(0);
            mHeaderView = genHeaderView();
            LayoutParams params = new LayoutParams(-1, getHeaderViewHeight());
            params.topMargin = -getHeaderViewHeight();
            mHeaderView.setLayoutParams(params);
            addView(mHeaderView, 0);

            onStateNormal();
        }
    }

    private static final int state_ready = 1;
    private static final int state_normal = 2;
    private static int state = state_normal;

    private boolean isRefreshing = false;
    private boolean enable = true;

    private boolean isHorizontalTouch = false;
    private boolean isVerticalTouch = false;
    private boolean hasDispatchCancelToChild = false;
    private boolean hasDealTouchByChild = false;

    private int downY;
    private int downX;
    private int lastY;

    private ValueAnimator anim;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isRefreshing || isHorizontalTouch || hasDealTouchByChild || !enable)
            return super.dispatchTouchEvent(ev);
        if (anim != null && anim.isRunning())
            return true;
        boolean atTop = !ViewCompat.canScrollVertically(mContentView, -1);
        if (!atTop)
            return super.dispatchTouchEvent(ev);

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            hasDealTouchByChild = hasDispatchCancelToChild = isVerticalTouch = isHorizontalTouch = false;
            downX = (int) ev.getX();
            downY = (int) ev.getY();
            lastY = downY;
            return super.dispatchTouchEvent(ev);

        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {

            int nowX = (int) ev.getX();
            int nowY = (int) ev.getY();
            int distanceX = nowX - downX;
            int distanceY = nowY - downY;

            if (Math.abs(distanceX) > Math.abs(distanceY) && !isVerticalTouch) {
                isHorizontalTouch = true;
                lastY = nowY;
                return super.dispatchTouchEvent(ev);
            }

            isVerticalTouch = true;
            int gap = nowY - lastY;
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mHeaderView.getLayoutParams();

            if (gap < 0 && lp.topMargin <= -getHeaderViewHeight() && !hasDispatchCancelToChild) {
                hasDealTouchByChild = true;
                lastY = nowY;
                return super.dispatchTouchEvent(ev);
            }

            dispatchCancelToChild(ev);

            lp.topMargin += gap;
            if (lp.topMargin < -getHeaderViewHeight())
                lp.topMargin = -getHeaderViewHeight();
            mHeaderView.setLayoutParams(lp);

            if (lp.topMargin > (getHeaderViewHeightOnRefreshing() - getHeaderViewHeight()) && state != state_ready) {
                state = state_ready;
                onStateReady();
            } else if (lp.topMargin <= (getHeaderViewHeightOnRefreshing() - getHeaderViewHeight()) && state != state_normal) {
                state = state_normal;
                onStateNormal();
            }

            lastY = nowY;
            return true;

        } else if (ev.getAction() == MotionEvent.ACTION_UP) {

            if (!hasDispatchCancelToChild)
                return super.dispatchTouchEvent(ev);

            hasDealTouchByChild = hasDispatchCancelToChild = isVerticalTouch = isHorizontalTouch = false;
            if (state == state_ready) {
                isRefreshing = true;
                animToRefreshing();
                onStateRefreshing();
                if (onRefreshListener != null)
                    onRefreshListener.run();
            } else if (state == state_normal) {
                animHeaderBack();
            }
            return super.dispatchTouchEvent(ev);

        } else
            return super.dispatchTouchEvent(ev);
    }


    private void dispatchCancelToChild(MotionEvent me) {
        if (hasDispatchCancelToChild)
            return;
        hasDispatchCancelToChild = true;
        me.setAction(MotionEvent.ACTION_CANCEL);
        mContentView.dispatchTouchEvent(me);
    }

    private void animHeaderBack() {
        final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mHeaderView.getLayoutParams();
        final int nowMarginTop = lp.topMargin;
        anim = ValueAnimator.ofInt(nowMarginTop, -getHeaderViewHeight()).setDuration(300);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                lp.topMargin = (int) animation.getAnimatedValue();
                mHeaderView.setLayoutParams(lp);
            }
        });
        anim.start();
        state = state_normal;
        isRefreshing = false;
    }

    private void animToRefreshing() {
        final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mHeaderView.getLayoutParams();
        final int nowMarginTop = lp.topMargin;
        int toMarginTop = getHeaderViewHeightOnRefreshing();
        anim = ValueAnimator.ofInt(nowMarginTop, toMarginTop).setDuration(150);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                lp.topMargin = (int) animation.getAnimatedValue();
                mHeaderView.setLayoutParams(lp);
            }
        });
        anim.start();
        isRefreshing = true;
    }

    //
    //
    //

    protected abstract int getHeaderViewHeight();

    protected abstract int getHeaderViewHeightOnRefreshing();

    protected abstract View genHeaderView();

    protected abstract void onStateNormal();

    protected abstract void onStateRefreshing();

    protected abstract void onStateReady();

    protected abstract void onRefreshComplete(boolean success);


    //
    //
    //


    private Runnable onRefreshListener;

    public void setOnRefreshListener(Runnable onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public void setRefreshComplete(boolean success) {
        onRefreshComplete(success);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                onStateNormal();
                animHeaderBack();
            }
        }, 600);
    }

    public void setUsed(boolean use) {
        this.enable = use;
    }

}
