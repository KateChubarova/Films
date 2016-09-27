package com.ekaterinachubarova.films1.ui.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.ui.fragment.FacebookLoginDialogFragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.Sharer;
import com.facebook.share.widget.ShareDialog;

import java.util.Arrays;

/**
 * Created by ekaterinachubarova on 27.09.16.
 */

public class SplashActivity extends AppCompatActivity {

    private ProfilePictureView profilePictureView;
    private TextView greeting;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private ShareDialog shareDialog;
    private FacebookCallback<Sharer.Result> shareCallback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onCancel() {
            Log.d("HelloFacebook", "Canceled");
        }

        @Override
        public void onError(FacebookException error) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
            String title = getString(R.string.error);
            String alertMessage = error.getMessage();
            showResult(title, alertMessage);
        }

        @Override
        public void onSuccess(Sharer.Result result) {
            Log.d("HelloFacebook", "Success!");
            if (result.getPostId() != null) {
                String title = getString(R.string.success);
                String id = result.getPostId();
                String alertMessage = getString(R.string.successfully_posted_post, id);
                showResult(title, alertMessage);
            }
        }

        private void showResult(String title, String alertMessage) {
            new AlertDialog.Builder(SplashActivity.this)
                    .setTitle(title)
                    .setMessage(alertMessage)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        }
    };

    public void login () {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        updateUI();
                    }

                    @Override
                    public void onCancel() {
                        showAlert();
                        updateUI();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        showAlert();
                        updateUI();
                    }

                    private void showAlert() {
                        new AlertDialog.Builder(SplashActivity.this)
                                .setTitle(R.string.cancelled)
                                .setMessage(R.string.permission_not_granted)
                                .setPositiveButton(R.string.ok, null)
                                .show();
                    }
                });

        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(
                callbackManager,
                shareCallback);


        setContentView(R.layout.activity_main);


        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                updateUI();
            }
        };
        DialogFragment dialogFragment = new FacebookLoginDialogFragment();
        dialogFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
    }

    public void updateUI() {
        boolean enableButtons = AccessToken.getCurrentAccessToken() != null;

        Profile profile = Profile.getCurrentProfile();
        if (enableButtons && profile != null) {
            Toast.makeText(this, "You was login with Facebook.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please, login with Facebook", Toast.LENGTH_LONG).show();

        }
    }
}
