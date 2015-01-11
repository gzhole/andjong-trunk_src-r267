package jp.sourceforge.andjong;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Settings extends PreferenceActivity {
	private static final String SETTINGS_KYOKUSUU = "settings_kyokusuu";

	private static final String SETTINGS_KUITAN = "settings_kuitan";
	private static final boolean SETTINGS_KUITAN_DEF = true;

	private static final String OPT_HINTS = "hints";
	private static final boolean OPT_HINTS_DEF = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}

	public static String getKyokusuu(Context context) {
		String[] list_entryvalues = context.getResources().getStringArray(R.array.list_entryvalues);
		return PreferenceManager.getDefaultSharedPreferences(context).getString(SETTINGS_KYOKUSUU, list_entryvalues[0]);
	}

	public static boolean isKuitan(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SETTINGS_KUITAN, SETTINGS_KUITAN_DEF);
	}

	public static boolean isAkaDora(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(OPT_HINTS, OPT_HINTS_DEF);
	}

    public static boolean isEndless(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("endless", true);
    }

    public static boolean isSecondFan(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("secondfan", true);
    }

    public static boolean isDebug(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("debug", true);
    }
    public static String getStyle(Context context) {
        String[] style_entryvalues = context.getResources().getStringArray(R.array.style_entryvalues);
        String style = PreferenceManager.getDefaultSharedPreferences(context).getString("styles", style_entryvalues[0]);
        System.out.println("by Gary: " + style);
        return style;
    }
}
