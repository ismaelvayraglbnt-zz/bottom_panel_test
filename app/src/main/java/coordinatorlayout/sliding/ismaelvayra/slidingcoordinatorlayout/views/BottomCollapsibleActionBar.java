package coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.views;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.behaviors.AppBarLayoutSnapBehavior;
import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.interfaces.BottomCollapsibleAppBarListener;
import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.interfaces.CollapseInterfaceListener;

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
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        this.addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (appBarLister!=null) {
                    if (verticalOffset == 0 && !getState().equals(appBarState.ATTACHED)) {
                        state = appBarState.COLLAPSED;
                        appBarLister.OnAppBarCollapsed();
                    } else if (verticalOffset == -(int)screenHeight) {
                        state = appBarState.EXPANDED;
                        appBarLister.OnAppBarExpanded();
                    } else if (verticalOffset == screenHeight/2) {
                        state = appBarState.ATTACHED;
                        appBarLister.OnAppBarAttached();
                    }
                }
            }
        });
    }

    public appBarState getState() {
        return state;
    }

    public void setState(appBarState state) {
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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount()>0 && getChildAt(0) instanceof BottomCollapsingToolbarLayout) {
            setCollapsibleSheet((BottomCollapsingToolbarLayout)getChildAt(0));
            collapsibleSheet.setCollapseInterfaceListener(new CollapseInterfaceListener() {
                @Override
                public void onTouchOutside() {
                    setState(appBarState.COLLAPSED);
                }
            });
        }

        parent = (CoordinatorLayout) this.getParent();
    }

    private void setAttachedAppBar() {
        int attachedHeight = (int) screenHeight/2;
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) this.getLayoutParams();
        AppBarLayoutSnapBehavior behavior = new AppBarLayoutSnapBehavior();
        behavior.animateOffsetTo(-attachedHeight);
        params.setBehavior(behavior);
        this.setLayoutParams(params);
    }

    private void setCollapsedAppBar() {
        setExpanded(true, true);
    }

    private void setExpandedAppBar() {
        setExpanded(true, true);
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
