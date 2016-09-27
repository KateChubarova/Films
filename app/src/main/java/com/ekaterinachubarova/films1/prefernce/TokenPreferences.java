package com.ekaterinachubarova.films1.prefernce;

import com.shawnlin.preferencesmanager.PreferencesManager;


/**
 * Created by ekaterinachubarova on 26.09.16.
 */

public class TokenPreferences {
    private String accessToken;
    private final String accessTokenConst = "accessToken";

    public TokenPreferences (String accessToken) {
        this.accessToken = accessToken;
    }

    public void savePreference () {
        PreferencesManager.putString(accessTokenConst, accessToken);
    }

    public String getAccessToken () {
        return PreferencesManager.getString(accessTokenConst);
    }


}
