package coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.R;

/**
 * Created by ismaelvayra on 15/12/15.
 */
public class BottomCoordinatorLayout extends CoordinatorLayout {

    private float toolbarHeight;
    private float sheetHeight;
    private float coordinatorHeight;
    private float screenSize;

    public BottomCoordinatorLayout(Context context) {
        super(context);
        initView(null);
    }

    public BottomCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public BottomCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    public void initView(@Nullable AttributeSet attrs) {
        if (attrs!=null) {

            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.BottomCoordinatorLayout,
                    0, 0);

            try {
                toolbarHeight = a.getDimension(R.styleable.BottomCoordinatorLayout_toolbar_height, 0);
                sheetHeight = a.getDimension(R.styleable.BottomCoordinatorLayout_sheet_height, 0);
            } finally {
                a.recycle();
            }

        }
        screenSize = getResources().getDisplayMetrics().heightPixels;
    }

    public float getToolbarHeight() {
        return toolbarHeight;
    }

    public void setToolbarHeight(float toolbarHeight) {
        this.toolbarHeight = toolbarHeight;
    }

    public float getSheetHeight() {
        return sheetHeight;
    }

    public void setSheetHeight(float sheetHeight) {
        this.sheetHeight = sheetHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (toolbarHeight>0) {
            coordinatorHeight = screenSize +toolbarHeight;
        }
        heightMeasureSpec = (int)coordinatorHeight;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
