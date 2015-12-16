package coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.behaviors;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.R;
import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.views.BottomCollapsibleActionBar;

/**
 * Created by ismaelvayra on 11/12/15.
 */
@SuppressWarnings("unused")
public class ToolbarCustomBehavior extends AppBarLayout.ScrollingViewBehavior {

    private Context ctx;
    private float screenSizeHeight;
    private boolean mNestedScrollStarted = false;
    private float startPoint;
    private float endPoint;
    private ValueAnimator mAnimator;

    public ToolbarCustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx = context;
        initBehavior();
    }

    private void initBehavior() {
        screenSizeHeight = ctx.getResources().getDisplayMetrics().heightPixels;
        startPoint = screenSizeHeight/2;
        endPoint = screenSizeHeight/2 + screenSizeHeight/4;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof BottomCollapsibleActionBar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (dependency instanceof BottomCollapsibleActionBar) {
            float dependencyY = Math.abs(dependency.getY());
            View customToolbar = child.findViewById(R.id.fake_toolbar);
            customToolbar.setAlpha(getScaledForAlpha(dependencyY));
        }

        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    private float getScaledForAlpha(float position) {

        float alpha;
        if (position<startPoint) {
            alpha = 1;
        } else if (position >= startPoint && position <= endPoint) {
            alpha = 1-(1/(endPoint-startPoint))*position +startPoint/(endPoint-startPoint);
        } else {
            alpha = 0;
        }

        return alpha;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

}
