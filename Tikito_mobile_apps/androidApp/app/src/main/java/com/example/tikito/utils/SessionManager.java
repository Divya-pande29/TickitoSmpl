package com.example.tikito.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tikito.constants.AppConstants;

public class SessionManager {

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        preferences = context.getSharedPreferences(
                AppConstants.PREF_NAME,
                Context.MODE_PRIVATE
        );
        editor = preferences.edit();
    }

    public void saveLoginSession(String token,
                                 Integer userId,
                                 String email,
                                 String firstName,
                                 String lastName,
                                 String role) {

        editor.putString(AppConstants.TOKEN, token);
        editor.putInt(AppConstants.USER_ID, userId);
        editor.putString(AppConstants.EMAIL, email);
        editor.putString(AppConstants.FIRST_NAME, firstName);
        editor.putString(AppConstants.LAST_NAME, lastName);
        editor.putString(AppConstants.ROLE, role);
        editor.putBoolean(AppConstants.IS_LOGGED_IN, true);

        editor.apply();
    }

    public String getToken() {
        return preferences.getString(AppConstants.TOKEN, "");
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(AppConstants.IS_LOGGED_IN, false);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }

    public int getUserId() {
        return preferences.getInt(AppConstants.USER_ID, 0);
    }

    public String getEmail() {
        return preferences.getString(AppConstants.EMAIL, "");
    }

    public String getFirstName() {
        return preferences.getString(AppConstants.FIRST_NAME, "");
    }

    public String getLastName() {
        return preferences.getString(AppConstants.LAST_NAME, "");
    }

    public String getRole() {
        return preferences.getString(AppConstants.ROLE, "");
    }
}
