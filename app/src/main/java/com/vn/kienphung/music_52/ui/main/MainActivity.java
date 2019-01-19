package com.vn.kienphung.music_52.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.vn.kienphung.music_52.R;
import com.vn.kienphung.music_52.data.model.Track;
import com.vn.kienphung.music_52.data.source.local.PreferenceManager;
import com.vn.kienphung.music_52.ui.home.HomeFragment;
import com.vn.kienphung.music_52.ui.maincontent.MainFragment;
import com.vn.kienphung.music_52.ui.playmusic.PlayMusicFragment;
import com.vn.kienphung.music_52.ui.splash.SplashFragment;
import com.vn.kienphung.music_52.utils.FragmentManagerUtils;

import java.util.List;

import static com.vn.kienphung.music_52.utils.Constant.ACTION_MAIN;

public class MainActivity extends AppCompatActivity implements MainContract.View, HomeFragment.IPlayTrack {

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
        Intent intent = getIntent();
        String action = intent.getAction();
        if (action != null && action.equals(ACTION_MAIN)) {
            FragmentManager manager = getSupportFragmentManager();
            MainFragment fragment = MainFragment.newInstance(true);
            FragmentManagerUtils.addFragment(manager, fragment, R.id.main_content,
                    fragment.getClass().getName(), false);
        } else {
            mPresenter.startPlashScreen();
        }
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

    @Override
    public void playTrack(List<Track> tracks, int position) {
        PreferenceManager.setLastPosition(this, position);
        PreferenceManager.putTracks(this, new Gson().toJson(tracks));
        PreferenceManager.setImageUrl(this, tracks.get(position).getArtworkUrl());
        PlayMusicFragment playMusicFragment = PlayMusicFragment.newInstance(tracks);
        FragmentManager manager = getSupportFragmentManager();
        FragmentManagerUtils.addFragment(manager, playMusicFragment,
                R.id.main_content, playMusicFragment.getClass().getName(), true);
    }
}

