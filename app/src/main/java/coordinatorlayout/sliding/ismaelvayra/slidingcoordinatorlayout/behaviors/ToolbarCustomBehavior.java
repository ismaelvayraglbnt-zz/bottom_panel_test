package coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.behaviors;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;

import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.views.BottomCollapsibleActionBar;
import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.views.BottomToolbar;

/**
 * Created by ismaelvayra on 11/12/15.
 */
@SuppressWarnings("unused")
public class ToolbarCustomBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    private Context ctx;
    private float screenSizeHeight;
    private float toolbarHeight;
    private float startPoint;

    public ToolbarCustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx = context;
        initBehavior();
    }

    private void initBehavior() {
        screenSizeHeight = ctx.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        if (startPoint==0) {
            toolbarHeight = child.getLayoutParams().height;
            startPoint = screenSizeHeight/2;
//            startPoint = toolbarHeight+toolbarHeight/2;
        }
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Toolbar child, View dependency) {
        if (dependency instanceof NestedScrollView) {
            if (dependency.getY()<=startPoint && dependency.getY()>100) {
                child.setAlpha(1-getScaledForAlpha(dependency.getY()));
            } else if (dependency.getY()>startPoint) {
                child.setAlpha(0);
            } else if (dependency.getY() <= 100) {
                child.setAlpha(1);
            }

            return true;
        }
        return false;
    }

    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, Toolbar child, View dependency) {
        super.onDependentViewRemoved(parent, child, dependency);
    }

    private float getScaledForAlpha(float position) {
        return position/startPoint;
    }

}
