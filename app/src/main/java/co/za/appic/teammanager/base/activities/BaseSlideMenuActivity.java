package co.za.appic.teammanager.base.activities;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import co.za.appic.teammanager.R;

public abstract class BaseSlideMenuActivity extends BaseNoActionBarActivity implements NavigationView.OnNavigationItemSelectedListener{

    protected ViewStub currentPageLayout;
    protected CoordinatorLayout currentActivityCL;
    protected Toolbar toolbar;
    protected Menu slideMenu;
    protected NavigationView navigationView;
    protected View parentLayout;
    protected FrameLayout placeHolderView;
    protected DrawerLayout drawer;
    protected MenuItem item;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DrawerLayout drawer = (DrawerLayout) ((BaseActivity) this).findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void setcurrentActivityCoordinator() {
        currentActivityCL = (CoordinatorLayout) ((BaseActivity) this).findViewById(R.id.clCurrentActivity);
    }

    @Override
    protected void setBaseActivityDependencies() {
        setContentView(R.layout.base_drawer_layout);
        initViews();
    }

    public void setMainLayout(int pageLayout) {
        currentPageLayout = (ViewStub) ((BaseActivity) this).findViewById(R.id.pageLayout);
        currentPageLayout.setLayoutResource(pageLayout);
        currentPageLayout.setLayoutResource(getLayoutResource());

    }

    protected ViewStub getMainLayout() {
        return currentPageLayout;
    }

    protected void setSlideMenuDependencies(BaseActivity activity, String title, int pageLayout, boolean showIcon) {
        setcurrentActivityCoordinator();
        setMainLayout(pageLayout);
        toolbar = activity.findViewById(R.id.toolbar);

        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        ImageView logo = toolbar.findViewById(R.id.toolbar_logo);

        if(title.isEmpty()){
            mTitle.setVisibility(View.GONE);
        }
        else{
            mTitle.setText(title);
            mTitle.setVisibility(View.VISIBLE);
        }

        if(showIcon)
            logo.setVisibility(View.VISIBLE);
        else
            logo.setVisibility(View.GONE);

        toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);

        drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) activity);
        navigationView.inflateMenu(getSideMenu());

        slideMenu = navigationView.getMenu();

        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {

            }

            @Override
            public void onDrawerOpened(View view) {

            }

            @Override
            public void onDrawerClosed(View view) {
                if(item == null)
                    return;

                handleSlideMenuItemClicked(item);
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        this.item = item;
        drawer.closeDrawers();
        return true;
    }

    protected boolean handleSlideMenuItemClicked(MenuItem item) {
        this.item = null;
        return true;
    }

    protected void hidePlaceHoderView(){
        placeHolderView.setVisibility(View.GONE);
    }

    protected abstract int getLayoutResource();
    protected abstract ViewGroup getParentLayout();
    protected abstract int getSideMenu();
}