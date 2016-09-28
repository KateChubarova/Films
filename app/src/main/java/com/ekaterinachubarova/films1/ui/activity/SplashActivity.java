package com.ekaterinachubarova.films1.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.ui.fragment.FacebookLoginFragment;
import com.facebook.AccessToken;
import com.facebook.Profile;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ekaterinachubarova on 27.09.16.
 */

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.froggy_image) ImageView frog;

    @OnClick(R.id.froggy_image)
    public void onFrogClick () {
        openNextActivity();
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splach_activity);

        ButterKnife.bind(this);

        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(frog, View.SCALE_X, 3f, 2f).setDuration(5000);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(frog, View.SCALE_Y, 3f, 2f).setDuration(5000);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(frog, View.ALPHA, 0 , 1).setDuration(5000);

        ObjectAnimator rotate = ObjectAnimator.ofFloat(frog, View.ROTATION_X, 360).setDuration(3000);
        //rotate.setRepeatCount(5);
        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY).with(alpha);
        scaleDown.play(rotate).after(scaleDownX);
        scaleDown.play(rotate);
        scaleDown.start();

        scaleDown.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                frog.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                frog.setClickable(true);
                openNextActivity();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void openNextActivity () {
        boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
        Profile profile = Profile.getCurrentProfile();

        if (enableButtons && profile != null) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else {
            DialogFragment dialogFragment = new FacebookLoginFragment();
            dialogFragment.show(getSupportFragmentManager(), "login");
        }
    }
}
