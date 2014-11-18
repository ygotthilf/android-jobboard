package fr.ygo.jobboard;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Yoann on 18/11/2014.
 */
public class AppSettings extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
