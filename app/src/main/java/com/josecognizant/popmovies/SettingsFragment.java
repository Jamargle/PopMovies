package com.josecognizant.popmovies;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Settings fragment
 * Created by 552702 on 26/05/2016.
 */
public class SettingsFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_general);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_sorting_model_key)));
    }

    /**
     * Attaches a listener so the summary is always updated with the preference value.
     * Also fires the listener once, to initialize the summary (so it shows up before the value
     * is changed.)
     */
    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(
                preference.getContext()).getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference instanceof ListPreference) {
            // Look up the correct display value in the preference's 'entries' list
            // (since they have separate labels/values).
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(newValue.toString());
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(newValue.toString());
        }
        return true;
    }
}
