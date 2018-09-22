package com.akhutornoy.transformerswar.repository.sharedpreferences;

public class AllSparkPreferences {
    private final Preferences preferences;

    private static final String PREF_ALL_SPARK = AllSparkPreferences.class.getName() + "_PREF_ALL_SPARK";

    public AllSparkPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public String getAllSpark() {
        return preferences.getPreferences()
                .getString(PREF_ALL_SPARK, "");
    }

    public void setAllSpark(String allSpark) {
        preferences.getEditor()
                .putString(PREF_ALL_SPARK, allSpark)
                .apply();
    }
}
