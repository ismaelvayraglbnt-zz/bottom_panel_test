package coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.behaviors;

import android.animation.ValueAnimator;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.interfaces.AppBarScrollListener;
import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.views.BottomCollapsibleActionBar;

/**
 * Created by ismaelvayra on 01/12/15.
 */
public class AppBarLayoutSnapBehavior extends AppBarLayout.Behavior {

    private ValueAnimator mAnimator;
    private float screenHeight;
    private float anchoredPoint;
    private float anchorPointRangeOver;
    private LinearLayout fakeBar;
    private AppBarScrollListener appBarScrollListener;
    private float initTouchY;
    private float finalTouchY;
    private boolean moved;
    private AppBarLayout appBarLayout;
    private boolean mNestedScrollStarted = false;

    public AppBarLayoutSnapBehavior(float screenHeight, float anchoredPoint, LinearLayout fakeBar, AppBarLayout appBar) {
        this.screenHeight = screenHeight;
        this.anchoredPoint = anchoredPoint;
        this.fakeBar = fakeBar;
        anchorPointRangeOver = anchoredPoint + (anchoredPoint /2);
        appBarLayout = appBar;
    }

    public void animateOffsetTo(int offset) {
        if (mAnimator == null) {
            mAnimator = new ValueAnimator();
            mAnimator.setInterpolator(new DecelerateInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setTopAndBottomOffset((int) animation.getAnimatedValue());
                }
            });
        } else {
            mAnimator.cancel();
        }

        mAnimator.setIntValues(getTopAndBottomOffset(), offset);
        mAnimator.start();
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        BottomCollapsibleActionBar appBar = (BottomCollapsibleActionBar) child;
        float yPosition;
        switch (ev.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                initTouchY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                appBar.stopNestedScroll();
                yPosition = Math.abs(this.getTopAndBottomOffset());
                finalTouchY = ev.getY();
                if (yPosition>=anchoredPoint && yPosition<anchorPointRangeOver) {
                    appBar.setState(BottomCollapsibleActionBar.appBarState.ANCHORED);
                } else if (yPosition>=anchorPointRangeOver) {
                    appBar.setState(BottomCollapsibleActionBar.appBarState.EXPANDED);
                } else if (yPosition<anchoredPoint) {
                    appBar.setState(BottomCollapsibleActionBar.appBarState.COLLAPSED);
                }
                break;
        }
        parent.invalidate();
        return super.onTouchEvent(parent, child, ev);
    }
//
//    @Override
//    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {BottomCollapsibleActionBar appBar = (BottomCollapsibleActionBar) child;
//        float yPosition;
//        switch (ev.getActionMasked()) {
//
//            case MotionEvent.ACTION_DOWN:
//                initTouchY = ev.getY();
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                break;
//
//            case MotionEvent.ACTION_UP:
//                yPosition = Math.abs(this.getTopAndBottomOffset());
//                finalTouchY = ev.getY();
//                if (yPosition>=anchoredPoint && yPosition<anchorPointRangeOver) {
//                    appBar.setState(BottomCollapsibleActionBar.appBarState.ANCHORED);
//                } else if (yPosition>=anchorPointRangeOver) {
//                    appBar.setState(BottomCollapsibleActionBar.appBarState.EXPANDED);
//                } else if (yPosition<anchoredPoint) {
//                    appBar.setState(BottomCollapsibleActionBar.appBarState.COLLAPSED);
//                }
//                break;
//        }
//        parent.invalidate();
//        return super.onInterceptTouchEvent(parent, child, ev);
//    }

    public void setAppBarScrollListener(AppBarScrollListener appBarScrollListener) {
        this.appBarScrollListener = appBarScrollListener;
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        Log.i("AppBarLayout", "OnStartNestedScroll");
        mNestedScrollStarted = super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        if(mNestedScrollStarted && mAnimator != null){ // Condition: (if) we scroll &and& our ValueAnimator object is NOT null, aka it exists, then and only then we cancel the animation.
            mAnimator.cancel();
        }
        return  mNestedScrollStarted; /* Factory return */
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
        BottomCollapsibleActionBar appBar = (BottomCollapsibleActionBar) child;
        if(!mNestedScrollStarted){
            return;
        }

        mNestedScrollStarted = false;

        int yPosition = Math.abs(this.getTopAndBottomOffset());

        if (yPosition>=anchoredPoint && yPosition<anchorPointRangeOver) {
            appBar.setState(BottomCollapsibleActionBar.appBarState.ANCHORED);
        } else if (yPosition>=anchorPointRangeOver) {
            appBar.setState(BottomCollapsibleActionBar.appBarState.EXPANDED);
        } else if (yPosition<anchoredPoint) {
            appBar.setState(BottomCollapsibleActionBar.appBarState.COLLAPSED);
        }
    }
}