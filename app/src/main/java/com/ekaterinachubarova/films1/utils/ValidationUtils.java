package com.ekaterinachubarova.films1.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.ekaterinachubarova.films1.BuildConfig;
import com.ekaterinachubarova.films1.R;

import java.util.ArrayList;

/**
 * Created by ekaterinachubarova on 12.10.16.
 */

public class ValidationUtils {

    public static final int PERMISSION_REQUEST_CODE = 13;
    public static final int SETTINGS_REQUEST_CODE = 14;

    private static ArrayList<String> deniedPermissions;
    private static ArrayList<String> neverAskPermissions;

    public static boolean checkPermissions(final Activity activity, View root, String... permissionList) {
        boolean result = true;
        deniedPermissions = new ArrayList<>();
        neverAskPermissions = new ArrayList<>();
        getPermissionGroup(activity, permissionList);
        if (!neverAskPermissions.isEmpty()) {
            displaySnackbar(root, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent settingsIntent = getSettingsActivityIntent(activity);
                    activity.startActivityForResult(settingsIntent, SETTINGS_REQUEST_CODE);
                }
            });
            result = false;
        } else if (!deniedPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(activity,
                    deniedPermissions.toArray(new String[deniedPermissions.size()]), PERMISSION_REQUEST_CODE);
            result = false;
        }
        return result;
    }

    public static boolean checkPermissions(final Fragment fragment, View root, String... permissionList) {
        boolean result = true;
        deniedPermissions = new ArrayList<>();
        neverAskPermissions = new ArrayList<>();
        final FragmentActivity activity = fragment.getActivity();
        getPermissionGroup(activity, permissionList);
        if (!neverAskPermissions.isEmpty()) {
            displaySnackbar(root, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent settingsIntent = getSettingsActivityIntent(activity);
                    fragment.startActivityForResult(settingsIntent, SETTINGS_REQUEST_CODE);
                }
            });
            result = false;
        } else if (!deniedPermissions.isEmpty()) {
            fragment.requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]),
                    PERMISSION_REQUEST_CODE);
            result = false;
        }
        return result;
    }

    private static void getPermissionGroup(Activity activity,
                                           String[] permissionList) {
        for (String permission : permissionList) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    neverAskPermissions.add(permission);
                } else {
                    deniedPermissions.add(permission);
                }
            }
        }
    }

    private static void displaySnackbar(View root, View.OnClickListener listener) {
        System.out.println(root + " root");
        if (root != null) {
            Snackbar.make(root, R.string.action_required_additional_permission, Snackbar.LENGTH_LONG)
                    .setAction(R.string.settings, listener)
                    .show();
        }
    }

    @NonNull
    private static Intent getSettingsActivityIntent(Activity activity) {
        Intent settingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        settingsIntent.setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID));
        if (settingsIntent.resolveActivity(activity.getPackageManager()) == null) {
            settingsIntent = new Intent(Settings.ACTION_SETTINGS);
        }
        return settingsIntent;
    }

    public static boolean checkPermissionsGranted(@NonNull int[] grantResults) {
        boolean value = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                value = false;
                break;
            }
        }
        return value;
    }
}
