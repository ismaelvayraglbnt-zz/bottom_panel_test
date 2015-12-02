package coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * Created by ismaelvayra on 30/11/15.
 */
public class FakeToolbarLayout extends LinearLayout {

    public FakeToolbarLayout(Context context) {
        super(context);
        initLayout();
    }

    public FakeToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public FakeToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    public FakeToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initLayout();
    }

    private void initLayout() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1000);
        this.setBackgroundColor(getResources().getColor(R.color.teal_700));
        this.setLayoutParams(params);
    }
}
