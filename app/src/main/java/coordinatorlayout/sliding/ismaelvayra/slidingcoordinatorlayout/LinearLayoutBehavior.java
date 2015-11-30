package coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by ismaelvayra on 27/11/15.
 */
@SuppressWarnings("unused")
public class LinearLayoutBehavior extends CoordinatorLayout.Behavior<LinearLayout> {

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }
}
