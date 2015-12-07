package coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.behaviors;

import android.animation.ValueAnimator;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
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
    private float[] anchorPointRange;
    private LinearLayout fakeBar;
    private AppBarScrollListener appBarScrollListener;

    public AppBarLayoutSnapBehavior(float screenHeight, float anchoredPoint, LinearLayout fakeBar) {
        this.screenHeight = screenHeight;
        this.anchoredPoint = anchoredPoint;
        anchorPointRange = new float[2];
        this.fakeBar = fakeBar;
        setRanges();
    }

    private void setRanges() {
        // Range to do anchor action
        anchorPointRange[0] = (screenHeight - anchoredPoint) /2;
        anchorPointRange[1] = anchoredPoint + anchoredPoint/2;
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
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

//    @Override
//    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
//        BottomCollapsibleActionBar appBar = (BottomCollapsibleActionBar) child;
//        if (ev.getAction() == MotionEvent.ACTION_UP) {
//            float yPosition = appBar.getScrollY();
//            if (yPosition>anchorPointRange[0] && yPosition<anchorPointRange[1]) {
////                animateOffsetTo((int)anchoredPoint);
//            } else if (yPosition<=anchorPointRange[0]) {
////                appBar.setState(BottomCollapsibleActionBar.appBarState.EXPANDED);
//            } else if (yPosition<=anchorPointRange[1]) {
//                //TODO: collapse
////                appBar.setState(BottomCollapsibleActionBar.appBarState.COLLAPSED);
//            }
//        }
//        return super.onTouchEvent(parent, child, ev);
//    }



    public void setAppBarScrollListener(AppBarScrollListener appBarScrollListener) {
        this.appBarScrollListener = appBarScrollListener;
    }
}