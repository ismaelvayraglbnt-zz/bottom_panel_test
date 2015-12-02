package coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by ismaelvayra on 30/11/15.
 */
public class BottomCollapsingToolbarLayout extends CollapsingToolbarLayout {

    private LinearLayout fakeToolbarLayout;
    private Toolbar realToolbar;
    private float height;

    public BottomCollapsingToolbarLayout(Context context) {
        super(context);
        initFakeToolBar();
        initRealToolbar();
    }

    public BottomCollapsingToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFakeToolBar();
        initRealToolbar();
    }

    public BottomCollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFakeToolBar();
        initRealToolbar();
    }

    private void initFakeToolBar() {
        height = getContext().getResources().getDisplayMetrics().heightPixels;

        LinearLayout layoutContainer = new LinearLayout(getContext());
        layoutContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutContainer.setOrientation(LinearLayout.VERTICAL);
        layoutContainer.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        fakeToolbarLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
        fakeToolbarLayout.setLayoutParams(lp);
        lp.topMargin = (int)height;
        fakeToolbarLayout.setBackgroundColor(getResources().getColor(R.color.teal_700));
        fakeToolbarLayout.setElevation(10);

        layoutContainer.addView(fakeToolbarLayout,0);

        this.addView(layoutContainer);

    }

    public void initRealToolbar() {
        realToolbar = new Toolbar(getContext());
        AppBarLayout.LayoutParams lp = new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        realToolbar.setFitsSystemWindows(true);
        lp.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED | AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
        CollapsingToolbarLayout.LayoutParams collapsLp = new CollapsingToolbarLayout.LayoutParams(lp);
        collapsLp.setCollapseMode(LayoutParams.COLLAPSE_MODE_PIN);
        realToolbar.setLayoutParams(collapsLp);
        realToolbar.setId(R.id.toolbar_bottom_panel);
        this.addView(realToolbar);
    }

    public LinearLayout getFakeToolbarLayout() {
        return fakeToolbarLayout;
    }
}
