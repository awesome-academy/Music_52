package com.vn.kienphung.music_52.ui.splash;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.vn.kienphung.music_52.R;
import com.vn.kienphung.music_52.ui.main.MainActivity;
import com.vn.kienphung.music_52.ui.maincontent.MainFragment;
import com.vn.kienphung.music_52.utils.Constant;
import com.vn.kienphung.music_52.utils.FragmentManagerUtils;

public class SplashFragment extends Fragment implements SplashContract.View {

    private SplashContract.Presenter mPresenter;
    private MainActivity mMainActivity;
    public ImageView mIconSplash ;
    private TextView mNameSplash ;
    public static SplashFragment newInstance(int timeDelay) {
        SplashFragment fragment = new SplashFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.ARGUMENT_TIME_DELAY, timeDelay);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        int timeDelay = getArguments().getInt(Constant.ARGUMENT_TIME_DELAY);
        setUpAnimation(view);
        mPresenter = new SplashPresenter(this);
        mPresenter.startingDelay(timeDelay);
    }

    private void setUpAnimation(View view){
        final Animation animationIcon = AnimationUtils.loadAnimation(mMainActivity,
                R.anim.show_icon_app);
        view.findViewById(R.id.img_app_icon).startAnimation(animationIcon);
        final Animation animationName = AnimationUtils.loadAnimation(mMainActivity,
                R.anim.show_app_name);
        view.findViewById(R.id.text_app_name).startAnimation(animationName);
    }

    @Override
    public void showMainApp() {
        FragmentManager manager = mMainActivity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(this.getClass().getSimpleName());
        if (fragment != null)
            manager.beginTransaction().remove(fragment).commit();
        MainFragment mainFragment = MainFragment.newInstance(false);
        FragmentManagerUtils.addFragment(manager, mainFragment, R.id.app_content,
                mainFragment.getClass().getSimpleName(), false);
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) context;
    }
}
