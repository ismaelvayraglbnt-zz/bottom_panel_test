package coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.views;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.behaviors.ToolbarCustomBehavior;

/**
 * Created by ismaelvayra on 11/12/15.
 */
//@CoordinatorLayout.DefaultBehavior(ToolbarCustomBehavior.class)
public class BottomToolbar extends Toolbar {

    ToolbarCustomBehavior toolBehavior;

    public BottomToolbar(Context context) {
        super(context);
    }

    public BottomToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initToolbar();
    }

    public BottomToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initToolbar();
    }

    private void initToolbar() {
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
}
