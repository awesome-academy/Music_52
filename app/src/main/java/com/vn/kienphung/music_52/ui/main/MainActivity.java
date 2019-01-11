package com.vn.kienphung.music_52.ui.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.vn.kienphung.music_52.R;
import com.vn.kienphung.music_52.ui.splash.SplashFragment;
import com.vn.kienphung.music_52.utils.FragmentManagerUtils;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private final int SPLASH_DISPLAY_LENGTH = 3500;
    private MainContract.Presenter mPresenter;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.title_home));
        setSupportActionBar(mToolbar);
        mPresenter = new MainPresenter(this);
        mPresenter.startPlashScreen();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void showSplashScreen() {
        SplashFragment fragment = SplashFragment.newInstance(SPLASH_DISPLAY_LENGTH);
        FragmentManager manager = getSupportFragmentManager();
        FragmentManagerUtils.addFragment(manager, fragment, R.id.main_content,
                fragment.getClass().getSimpleName(), false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashbroad, menu);
        MenuItem mSearch = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint(getString(R.string.hint_search_message));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}

