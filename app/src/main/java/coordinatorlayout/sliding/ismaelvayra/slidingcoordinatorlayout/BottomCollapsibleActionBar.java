package coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.exceptions.LayoutNotFoundException;

/**
 * Created by ismaelvayra on 01/12/15.
 */
public class BottomCollapsibleActionBar extends AppBarLayout {

    private CoordinatorLayout parent;
    private BottomCollapsingToolbarLayout collapsibleSheet;
    private float screenHeight;
    private BottomCollapsibleAppBarListener appBarLister;

    public enum appBarState {
        COLLAPSED,
        ATTACHED,
        EXPANDED,
    }

    private appBarState state;

    public BottomCollapsibleActionBar(Context context) {
        super(context);
        initItems();
    }

    public BottomCollapsibleActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initItems();
    }

    private void initItems() {
        parent = (CoordinatorLayout) this.getParent();
        screenHeight = getResources().getDisplayMetrics().heightPixels;
    }

    public appBarState getState() {
        return state;
    }

    public void setState(appBarState state) throws LayoutNotFoundException {
        switch (state) {
            case ATTACHED:
                setAttachedAppBar();
                break;
            case COLLAPSED:
                setCollapsedAppBar();
                break;
            case EXPANDED:
                setExpandedAppBar();
                break;
        }

        this.state = state;
    }

    private void setAttachedAppBar() throws LayoutNotFoundException {
        int attachedHeight = (int) screenHeight/2;
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) this.getLayoutParams();
        AppBarLayoutSnapBehavior behavior = new AppBarLayoutSnapBehavior();
        behavior.animateOffsetTo(-attachedHeight);
        params.setBehavior(behavior);
        this.setLayoutParams(params);

        if (appBarLister!=null) {
            appBarLister.OnAppBarAttached();
        }
    }

    private void setCollapsedAppBar() throws LayoutNotFoundException {
        setExpanded(true, true);
        if (appBarLister!=null) {
            appBarLister.OnAppBarCollapse();
        }
    }

    private void setExpandedAppBar() throws LayoutNotFoundException {
        setExpanded(true, true);
        if (appBarLister!=null) {
            appBarLister.OnAppBarExpanded();
        }
    }

    public void collapseToolbar(){
        this.setExpanded(false, true);
    }

    public void expandToolbar(){
        this.setExpanded(true, false);
    }

    public void setAppBarLister(BottomCollapsibleAppBarListener appBarLister) {
        this.appBarLister = appBarLister;
    }

    public BottomCollapsingToolbarLayout getCollapsibleSheet() {
        return collapsibleSheet;
    }

    public void setCollapsibleSheet(BottomCollapsingToolbarLayout collapsibleSheet) {
        this.collapsibleSheet = collapsibleSheet;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = (int)screenHeight + 300;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
