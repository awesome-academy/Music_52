package com.vn.kienphung.music_52.ui.main;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vn.kienphung.music_52.R;
import com.vn.kienphung.music_52.ui.splash.SplashFragment;
import com.vn.kienphung.music_52.utils.FragmentManagerUtils;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private final int SPLASH_DISPLAY_LENGTH = 3500;
    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showSplashScreen();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void showSplashScreen() {
        SplashFragment fragment = SplashFragment.newInstance(SPLASH_DISPLAY_LENGTH);
        FragmentManager manager = getSupportFragmentManager();
        FragmentManagerUtils.addFragment(manager, fragment, R.id.main_content,
                fragment.getClass().getSimpleName(), false);
    }
}
