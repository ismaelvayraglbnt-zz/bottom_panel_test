package coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.views;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.R;
import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.interfaces.CollapseInterfaceListener;

/**
 * Created by ismaelvayra on 30/11/15.
 */
public class BottomCollapsingToolbarLayout extends CollapsingToolbarLayout {

    private LinearLayout fakeToolbarLayout;
    private Toolbar realToolbar;
    private float height;
    private CollapseInterfaceListener collapseInterfaceListener;

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

        LinearLayout layoutSpace = new LinearLayout(getContext());
        layoutSpace.setId(R.id.layoutSpace);
        layoutSpace.setClickable(true);
        layoutSpace.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) height));
        layoutSpace.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                collapseInterfaceListener.onTouchOutside();
                return true;
            }
        });

//        fakeToolbarLayout = new LinearLayout(getContext());
//        CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
//        fakeToolbarLayout.setLayoutParams(lp);
//        fakeToolbarLayout.setBackgroundColor(getResources().getColor(R.color.teal_700));
//        fakeToolbarLayout.setElevation(20);
//        fakeToolbarLayout.setId(R.id.fake_toolbar);

        layoutContainer.addView(layoutSpace);
//        layoutContainer.addView(fakeToolbarLayout);

        this.addView(layoutContainer);

    }

    public void initRealToolbar() {
//        realToolbar = new Toolbar(getContext());
//        AppBarLayout.LayoutParams lp = new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
//        realToolbar.setFitsSystemWindows(true);
//        lp.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED | AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
//        CollapsingToolbarLayout.LayoutParams collapsLp = new CollapsingToolbarLayout.LayoutParams(lp);
//        collapsLp.setCollapseMode(LayoutParams.COLLAPSE_MODE_PIN);
//        realToolbar.setId(R.id.toolbar_bottom_panel);
//        realToolbar.setLayoutParams(collapsLp);
//        this.addView(realToolbar);
    }

    public LinearLayout getFakeToolbarLayout() {
        return fakeToolbarLayout;
    }

    public Toolbar getRealToolbar() {
        return realToolbar;
    }

    public void setCollapseInterfaceListener(CollapseInterfaceListener collapseInterfaceListener) {
        this.collapseInterfaceListener = collapseInterfaceListener;
    }
}
