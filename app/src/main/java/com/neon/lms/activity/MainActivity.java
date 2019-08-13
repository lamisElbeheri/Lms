package com.neon.lms.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.neon.lms.BaseAppClass;
import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetOfferData;
import com.neon.lms.ResponceModel.NetSuccess;
import com.neon.lms.adapter.DrawerAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.callBack.OpenFragment;
import com.neon.lms.callBack.TwoButtonListener;
import com.neon.lms.databinding.ActivityMainBinding;
import com.neon.lms.fragment.FragmentHome;
import com.neon.lms.model.Drawer;
import com.neon.lms.model.MainActivityModel;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AlertDialogAndIntents;
import com.neon.lms.util.AppConstant;
import com.neon.lms.util.Constants;
import com.neon.lms.util.CustomProgressDialog;

import java.util.ArrayList;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends BaseActivity implements MainActivityModel.BottomBtnClick,
        OpenFragment, View.OnClickListener {
    private MainActivityModel model;
    private ActivityMainBinding binding;
    public Fragment currentFragment;
    public int mCurrentSelectedPosition;
    private FragmentHome homeFragment;
    private ArrayList<Drawer> drawerArrayList;
    public ImageView imgDelete, imgAdd;
    private RelativeLayout.LayoutParams menuParam;

    String language;
    CustomProgressDialog dialog ;

    public static boolean isOpened = false;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isOpened = true;


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setModelAndBinding() {

        model = new MainActivityModel(this, this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.included.imgSearch.setOnClickListener(this);
        binding.included.imgBack.setOnClickListener(this);
        binding.included.searchView.setOnClickListener(this);
        binding.included.imgClose.setOnClickListener(this);
        menuParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        setUpDrawer();
        openHome();


    }

    private void setUpDrawer() {
        binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {

            }

            @Override
            public void onDrawerOpened(View view) {
            }

            @Override
            public void onDrawerClosed(View view) {
                // your refresh code can be called from here

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });


    }


    @Override
    public void setToolBar() {
        setSupportActionBar(binding.included.toolbar);

    }

    @Override
    public void initViews() {
        dialog= new CustomProgressDialog(Constants.PROGRESS_IMAGE, MainActivity.this).createProgressBar();
        mDrawerToggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setRecyclerView();


    }


    public void closeNavigationDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START);

    }


    //set recyclerview of drawer
    private void setRecyclerView() {
        drawerArrayList = new ArrayList<>();

        drawerArrayList.add(new Drawer("", "", Constants.ACCOUNT, R.drawable.draw_contact, R.drawable.draw_contact, DrawerAdapter.TYPE_HEADER, false, false));
        drawerArrayList.add(new Drawer("", getString(R.string.home), Constants.HOME, R.drawable.draw_home, R.drawable.draw_home, DrawerAdapter.TYPE_ITEM, false, false));
        drawerArrayList.add(new Drawer("", getString(R.string.blog), Constants.BLOG, R.drawable.draw_blog, R.drawable.draw_blog, DrawerAdapter.TYPE_ITEM, true, false));
        drawerArrayList.add(new Drawer("", getString(R.string.course), Constants.COURSE, R.drawable.draw_home, R.drawable.draw_home, DrawerAdapter.TYPE_ITEM, true, false));
        drawerArrayList.add(new Drawer("", getString(R.string.myPurchase), Constants.MYPURCHASE, R.drawable.draw_home, R.drawable.draw_home, DrawerAdapter.TYPE_ITEM, true, false));
        drawerArrayList.add(new Drawer("", getString(R.string.forums), Constants.FORUMS, R.drawable.draw_comment, R.drawable.draw_comment, DrawerAdapter.TYPE_ITEM, true, false));
        drawerArrayList.add(new Drawer("", getString(R.string.contactUs), Constants.CONTACT, R.drawable.draw_phone, R.drawable.draw_phone, DrawerAdapter.TYPE_ITEM, true, false));
        drawerArrayList.add(new Drawer("", getString(R.string.about), Constants.ABOUTUS, R.drawable.draw_info, R.drawable.draw_info, DrawerAdapter.TYPE_ITEM, true, false));
        drawerArrayList.add(new Drawer("", getString(R.string.feedback), Constants.FEEDBACK, R.drawable.draw_feedback, R.drawable.draw_feedback, DrawerAdapter.TYPE_ITEM, true, false));
        drawerArrayList.add(new Drawer("", getString(R.string.logout), Constants.LOGOUT, R.drawable.lock, R.drawable.lock, DrawerAdapter.TYPE_ITEM, false, false));
        drawerArrayList.add(new Drawer("", getString(R.string.language), Constants.LANGUAGE, R.drawable.contact, R.drawable.contact, DrawerAdapter.TYPE_ITEM, true, false));


        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        binding.recyclerView.setAdapter(new DrawerAdapter(MainActivity.this, drawerArrayList, new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {

                DrawerItemClick(position, type);


            }
        }));
        binding.recyclerView.setNestedScrollingEnabled(false);
    }

    private void DrawerItemClick(int position, int type) {

        closeNavigationDrawer();

        switch (drawerArrayList.get(position).getType()) {


            case Constants.ACCOUNT:
                openAccount();
                setSelection(position);
                break;
            case Constants.HOME:
                openHome();
                setSelection(position);
                break;

            case Constants.COURSE:
                openCourseList();
                setSelection(position);
                break;
            case Constants.BLOG:
                openBlogList();
                setSelection(position);
                break;
            case Constants.ABOUTUS:
                openAboutus();
                setSelection(position);
                break;
            case Constants.FAQ:
                openFaqList();
                setSelection(position);
                break;

            case Constants.MYPURCHASE:
                openMyPurchase();
                setSelection(position);
                break;

            case Constants.FORUMS:
                openForumList();
                setSelection(position);
                break;

            case Constants.CONTACT:
                openContactUs();
                setSelection(position);
                break;
            case Constants.FEEDBACK:
                openContactUs();
                setSelection(position);
                break;

            case Constants.LANGUAGE:
                BaseAppClass.changeLang(MainActivity.this, "ar");
                recreate();
                setSelection(position);
                break;

                case Constants.LOGOUT:
                    logOutApi();
                setSelection(position);
                break;


        }

    }

    private void setSelection(int position) {
        for (int i = 0; i < drawerArrayList.size(); i++) {
            drawerArrayList.get(i).setSelected(false);
        }
        drawerArrayList.get(position).setSelected(true);
        binding.recyclerView.getAdapter().notifyDataSetChanged();

    }

    public void openHome() {
        homeFragment = new FragmentHome();
        currentFragment = homeFragment;
        makeFilterFragmentVisible(homeFragment, FragmentHome.TAG);
        mCurrentSelectedPosition = Constants.HOME;

    }

    private void openMyPurchase() {
        startActivity(new Intent(this, MyPurchaseListActivity.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openContactUs() {
        startActivity(new Intent(this, ContactUsActivity.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openAccount() {
        startActivity(new Intent(this, AccountActivity.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openCourseList() {
        startActivity(new Intent(this, CourseListActivity.class)
                .putExtra(CourseListActivity.VALUE, "none"));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openBlogList() {
        startActivity(new Intent(this, BlogListActivity.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openAboutus()
    {
    }

    private void openFaqList() {
        startActivity(new Intent(this, FaqListActivity.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openForumList() {
        startActivity(new Intent(this, ForumListActivity.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            manageBackPress(true);
        }
    }


    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    public void onBottomBtnClick(Fragment fragToReplace, String tag) {
        if (model.getStrBottomImgTag().equals(tag))
            return;


    }


    private void manageBackPress(boolean isBackPress) {
        FragmentManager fragMgr = getSupportFragmentManager();

        if (sendBackPressToFragmentOnTop()) {
            return;
        }
        if (fragMgr.getBackStackEntryCount() > 1) {
            fragMgr.popBackStackImmediate();
            Fragment backStackedFrag = getSupportFragmentManager().findFragmentById(R.id.contentFrame);
            backStackedFrag.onResume();
        } else if (binding.included.toolbarSearch.getVisibility() == View.VISIBLE) {
            binding.included.imgClose.performClick();
        } else {
//            AlertDialogAndIntents.closeAppPopup(MainActivity.this);
            if (isBackPress)
                AlertDialogAndIntents.closeDialog(MainActivity.this, new TwoButtonListener() {
                    @Override
                    public void positiveClick() {
                        finish();

                    }

                    @Override
                    public void negativeClick() {

                    }
                });
            else
                openDrawer();
        }
    }

    public void openDrawer() {
        AppConstant.hideKeyboard(MainActivity.this, binding.included.toolbar);
        binding.drawerLayout.openDrawer(binding.leftDrawer);
        if (binding.recyclerView != null && drawerArrayList != null && drawerArrayList.size() > 0)
            binding.recyclerView.scrollToPosition(0);
    }


    public void makeFilterFragmentVisible(Fragment fragmentToShow, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.contentFrame, fragmentToShow);
        ft.addToBackStack(title);
        ft.commit();
    }

    @Override
    public void addNewFragment(Fragment fragToShow, Fragment fragToHide, String TAG) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.contentFrame, fragToShow);
        ft.hide(fragToHide);
        ft.addToBackStack(TAG);
        ft.commit();
    }


    private boolean sendBackPressToFragmentOnTop() {

        return false;
    }

    @Override
    public void setTitleAndToolbar(String title, boolean isBack, boolean isAdd, boolean isCart, boolean isSearch, boolean isNotification, String otherText) {
        setTitle(title);
        binding.included.imgBack.setImageResource(isBack ? R.drawable.btn_bg : R.drawable.menu);
        binding.included.imgSearch.setVisibility(isSearch ? View.VISIBLE : View.GONE);

        binding.included.linMain.post(new Runnable() {
            @Override
            public void run() {
                int width = binding.included.linMain.getWidth();
                if (width > AppConstant.dp2px(MainActivity.this, 56)) {
                    menuParam.setMargins(width, 0, width, 0);
                } else {
                    menuParam.setMargins(AppConstant.dp2px(MainActivity.this, 56), 0, AppConstant.dp2px(MainActivity.this, 56), 0);
                }
                binding.included.txtTitle.setLayoutParams(menuParam);
            }
        });


    }


    private void setTitle(String title) {
        binding.included.txtTitle.setText(title != null ? title : "");
    }

    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.contentFrame);
    }


    @Override
    public void setToolbarHeight(int height) {

    }

    @Override
    public void setToolbarMenuIcon(int icon) {


    }

    @Override
    public void selectedData() {

    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.imgBack:
//                onBackPressed();
                manageBackPress(false);
                break;


        }

    }


    public void closeSearchView() {
        binding.included.imgClose.performClick();
    }

    public void openSearch(String str) {
        binding.included.imgSearch.performClick();
        binding.included.searchView.setQuery(str, false);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    public void logOutApi() {
        dialog.setCancelable(false);
        dialog.show();
        RetrofitClient.getInstance().getRestOkClient().
                logout("",
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            dialog.hide();
            NetSuccess netSuccess = (NetSuccess) object;
            BaseAppClass.getPreferences().clearUserData();
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();


        }
    };
}
