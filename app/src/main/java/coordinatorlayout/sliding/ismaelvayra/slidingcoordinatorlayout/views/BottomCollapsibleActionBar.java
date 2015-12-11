
package coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.views;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;

import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.R;
import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.behaviors.AppBarLayoutSnapBehavior;
import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.interfaces.BottomCollapsibleAppBarListener;
import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.interfaces.CollapseInterfaceListener;

/**
 * Created by ismaelvayra on 01/12/15.
 */
public class BottomCollapsibleActionBar extends AppBarLayout {

    private WeakReference<CoordinatorLayout> parent;
    private BottomCollapsingToolbarLayout collapsibleSheet;
    private float screenHeight;
    private BottomCollapsibleAppBarListener appBarLister;
    private AppBarLayoutSnapBehavior behavior;
    private CoordinatorLayout.LayoutParams params;
    private LinearLayout fakeToolbar;

    public enum appBarState {
        COLLAPSED,
        ANCHORED,
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
                if (appBarLister != null) {
                    if (verticalOffset == 0 && !getState().equals(appBarState.ANCHORED)) {
                        state = appBarState.COLLAPSED;
                        appBarLister.onAppBarCollapsed();
                    } else if (verticalOffset == -(int) screenHeight) {
                        state = appBarState.EXPANDED;
                        appBarLister.onAppBarExpanded();
                    } else {
                        state = appBarState.ANCHORED;
                        appBarLister.onAppBarAttached();
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
            case ANCHORED:
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
    protected void onAttachedToWindow() {
        parent = new WeakReference<>((CoordinatorLayout) getParent());
        if (getChildCount()>0 && getChildAt(0) instanceof BottomCollapsingToolbarLayout) {
            setCollapsibleSheet((BottomCollapsingToolbarLayout)getChildAt(0));
            collapsibleSheet.setCollapseInterfaceListener(new CollapseInterfaceListener() {
                @Override
                public void onTouchOutside() {
                    setState(appBarState.COLLAPSED);
                }
            });
        }

        fakeToolbar = (LinearLayout)collapsibleSheet.findViewById(R.id.fake_toolbar);
        behavior = new AppBarLayoutSnapBehavior(screenHeight/2, screenHeight);
        behavior.setDragCallback(new Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                if (((BottomCollapsibleActionBar) appBarLayout).getState().equals(appBarState.COLLAPSED)) {
                    return false;
                }
                return true;
            }
        });
        super.onAttachedToWindow();
    }

    private void setAttachedAppBar() {
        int attachedHeight = (int) screenHeight/2;
        params = (CoordinatorLayout.LayoutParams) this.getLayoutParams();
        behavior.animateOffsetTo(-attachedHeight);
        params.setBehavior(behavior);
        this.setLayoutParams(params);
    }

    private void setCollapsedAppBar() {
        setExpanded(true, true);
    }

    private void setExpandedAppBar() {
        setExpanded(false, true);
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
        heightMeasureSpec = (int) screenHeight + 300;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
