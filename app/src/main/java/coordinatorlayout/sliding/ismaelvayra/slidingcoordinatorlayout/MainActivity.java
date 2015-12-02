package coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import coordinatorlayout.sliding.ismaelvayra.slidingcoordinatorlayout.exceptions.LayoutNotFoundException;

@SuppressWarnings("all")
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.coord_main_layout)
    CoordinatorLayout coord_main_layout;
    @Bind(R.id.app_bar_layout_bottom)
    BottomCollapsibleActionBar app_bar_layout_bottom;
    @Bind(R.id.bottom_coord_layout)
    CoordinatorLayout coord_bootom_panel_layout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_bottom_panel)
    Toolbar toolbar_bottom;
    @Bind(R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        ButterKnife.bind(this);
        coord_bootom_panel_layout.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        if (coord_bootom_panel_layout.getVisibility() == View.GONE) {
            setSupportActionBar(toolbar_bottom);
            coord_bootom_panel_layout.setVisibility(View.VISIBLE);
            try {
                app_bar_layout_bottom.setState(BottomCollapsibleActionBar.appBarState.ATTACHED);
            } catch (LayoutNotFoundException e) {
                e.printStackTrace();
            }
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    public void onBackPressed() {
        if (coord_bootom_panel_layout.getVisibility() == View.VISIBLE) {
            setSupportActionBar(toolbar);
            try {
                app_bar_layout_bottom.setState(BottomCollapsibleActionBar.appBarState.COLLAPSED);
            } catch (LayoutNotFoundException e) {
                e.printStackTrace();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    coord_bootom_panel_layout.setVisibility(View.GONE);
                }
            }, 1500);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
