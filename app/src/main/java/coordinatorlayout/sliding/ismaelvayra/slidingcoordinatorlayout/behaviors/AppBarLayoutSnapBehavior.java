package coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.behaviors;

import android.animation.ValueAnimator;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.views.BottomCollapsibleActionBar;

/**
 * Created by ismaelvayra on 01/12/15.
 */
public class AppBarLayoutSnapBehavior extends AppBarLayout.Behavior {

    private ValueAnimator mAnimator;
    private float anchoredPoint;
    private float anchorPointRangeOver;
    private boolean mNestedScrollStarted = false;
    private float screenHeight;

    public AppBarLayoutSnapBehavior(float anchoredPoint, float screen) {
        screenHeight = screen;
        this.anchoredPoint = anchoredPoint;
        anchorPointRangeOver = anchoredPoint + (anchoredPoint /4);
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
        return false;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        mNestedScrollStarted = super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        if(mNestedScrollStarted && mAnimator != null){
            mAnimator.cancel();
        }
        return  mNestedScrollStarted;
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
        if (yPosition>anchoredPoint && yPosition<anchorPointRangeOver) {
            appBar.setState(BottomCollapsibleActionBar.appBarState.ANCHORED);
        } else if (yPosition>anchorPointRangeOver && yPosition!=screenHeight) {
            appBar.setState(BottomCollapsibleActionBar.appBarState.EXPANDED);
        } else if (yPosition<anchoredPoint && yPosition!=0) {
            appBar.setState(BottomCollapsibleActionBar.appBarState.COLLAPSED);
        }
    }

    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, AppBarLayout child, View dependency) {
        super.onDependentViewRemoved(parent, child, dependency);
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY, boolean consumed) {

        return false;
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY) {
        BottomCollapsibleActionBar bottomCollapsibleActionBar = (BottomCollapsibleActionBar) child;
        if (bottomCollapsibleActionBar.getState().equals(BottomCollapsibleActionBar.appBarState.EXPANDED)) {
            return false;
        }
        return true;
    }
}