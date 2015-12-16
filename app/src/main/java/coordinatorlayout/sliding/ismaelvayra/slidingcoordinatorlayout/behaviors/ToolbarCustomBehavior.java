package coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.behaviors;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;

import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.R;
import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.views.BottomCollapsibleActionBar;
import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.views.BottomToolbar;

/**
 * Created by ismaelvayra on 11/12/15.
 */
@SuppressWarnings("unused")
public class ToolbarCustomBehavior extends AppBarLayout.ScrollingViewBehavior {

    private Context ctx;
    private float screenSizeHeight;
    private float toolbarHeight;
    private float startPoint;
    private float endPoint;

    public ToolbarCustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx = context;
        initBehavior();
    }

    private void initBehavior() {
        screenSizeHeight = ctx.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (startPoint==0) {
            toolbarHeight = child.getLayoutParams().height;
            startPoint = screenSizeHeight/2;
            endPoint = screenSizeHeight/2 + screenSizeHeight/4;
//            startPoint = toolbarHeight+toolbarHeight/2;
        }
        return dependency instanceof BottomCollapsibleActionBar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (dependency instanceof BottomCollapsibleActionBar) {
            float dependencyY = Math.abs(dependency.getY());
            View customToolbar = child.findViewById(R.id.fake_toolbar);
////            if (dependencyY>(startPoint+100f) && dependencyY<=screenSizeHeight) {
////                float alpha = 1-getScaledForAlpha(dependencyY);
////                customToolbar.setAlpha(alpha);
////            } else if (dependencyY<=startPoint) {
////                customToolbar.setAlpha(1);
////            }
//
//            if (dependencyY>=startPoint && dependencyY<=endPoint) {
//                float alpha = 1-getScaledForAlpha(dependencyY);
//                customToolbar.setAlpha(alpha);
//            }
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

}
