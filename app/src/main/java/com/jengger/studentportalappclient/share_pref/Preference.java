package com.jengger.studentportalappclient.share_pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preference {
    static final String KEY_NPM = "npm",
            KEY_TOKEN = "token",
            KEY_KELAS = "kelas",
            KEY_SEMESTER = "semester",
            KEY_STATUS_LOGIN = "status_logged_in",
            KEY_STATUS_AKUN = "status_akun",
            KEY_NIK_AYAH = "nip_ayah",
            KEY_NIK_IBU = "nip_ibu",
            KEY_NIK_WALI = "nip_wali",
            KEY_NIK = "nik";

    private static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedNPM(Context context, Integer npm) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(KEY_NPM, npm);
        editor.apply();
    }

    public static Integer getLoggedNPM(Context context) {
        return getSharedPreference(context).getInt(KEY_NPM, 0);
    }

    public static void setLoggedToken(Context context, String token) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public static String getLoggedToken(Context context) {
        return getSharedPreference(context).getString(KEY_TOKEN, "");
    }

    public static void setLoggedKelas(Context context, String kelas) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_KELAS, kelas);
        editor.apply();
    }

    public static String getLoggedKelas(Context context) {
        return getSharedPreference(context).getString(KEY_KELAS, "");
    }

    public static void setLoggedSemester(Context context, Integer semester) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(KEY_SEMESTER, semester);
        editor.apply();
    }

    public static Integer getLoggedSemester(Context context) {
        return getSharedPreference(context).getInt(KEY_SEMESTER, 0);
    }

    public static void setLoggedStatus(Context context, boolean status) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(KEY_STATUS_LOGIN, status);
        editor.apply();
    }

    public static Boolean getLoggedStatus(Context context) {
        return getSharedPreference(context).getBoolean(KEY_STATUS_LOGIN, false);
    }

    public static void setLoggedStatusAkun(Context context, String status) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_STATUS_AKUN, status);
        editor.apply();
    }

    public static String getLoggedStatusAkun(Context context) {
        return getSharedPreference(context).getString(KEY_STATUS_AKUN, "");
    }

    public static void setKeyNikAyah(Context context, String nik) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_NIK_AYAH, nik);
        editor.apply();
    }

    public static String getKeyNikAyah(Context context) {
        return getSharedPreference(context).getString(KEY_NIK_AYAH, "");
    }

    public static void setKeyNikIbu(Context context, String nik) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_NIK_IBU, nik);
        editor.apply();
    }

    public static String getKeyNikIbu(Context context) {
        return getSharedPreference(context).getString(KEY_NIK_IBU, "");
    }

    public static void setKeyNikWali(Context context, String nik) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_NIK_WALI, nik);
        editor.apply();
    }

    public static String getKeyNikWali(Context context) {
        return getSharedPreference(context).getString(KEY_NIK_WALI, "");
    }

    public static void setKeyNik(Context context, String nik) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_NIK, nik);
        editor.apply();
    }

    public static String getKeyNik(Context context) {
        return getSharedPreference(context).getString(KEY_NIK, "");
    }

    public static void clearLoggedIn(Context context) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_TOKEN);
        editor.remove(KEY_NPM);
        editor.remove(KEY_SEMESTER);
        editor.remove(KEY_KELAS);
        editor.remove(KEY_STATUS_LOGIN);
        editor.remove(KEY_STATUS_AKUN);
        editor.apply();
    }

}
