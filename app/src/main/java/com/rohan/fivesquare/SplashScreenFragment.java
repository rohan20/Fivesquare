package com.rohan.fivesquare;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SplashScreenFragment extends Fragment {

    @BindView(R.id.logo)
    ImageView mLogo;
    @BindView(R.id.welcome_to_text_view)
    TextView mWelcomeTo;
    @BindView(R.id.five_square_text_view)
    TextView mFiveSquare;

    public SplashScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(3000);

        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);
        mWelcomeTo.setAnimation(animation);
        mLogo.setAnimation(animation);
        mFiveSquare.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .addToBackStack(null)
                        .commit();
            }
        }, 3000);

    }
}
