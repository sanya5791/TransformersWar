package com.akhutornoy.transformerswar.repository.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
	private static final String PREF_NAME = "innovo";

	private final Context context;

	public Preferences(Context context) {
		this.context = context;
	}

	public SharedPreferences getPreferences() {
		return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}

	public SharedPreferences.Editor getEditor() {
		return getPreferences().edit();
	}
}
