
/*
Copyright (c) 2011, Sony Ericsson Mobile Communications AB

All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of the Sony Ericsson Mobile Communications AB nor the names
  of its contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.sonyericsson.extras.liveware.extension.androiddevcamp_kyoto_trendwatch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.util.Log;

public class SamplePreferenceActivity extends PreferenceActivity {
	public static final String PREFERENCE_AREA_KEY = "preference_area_key";
    private static final int DIALOG_READ_ME = 1;
    private static final int DIALOG_AREA_SET_1 = 2;
    private static final int DIALOG_AREA_SET_2 = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        // Handle read me
        Preference preference = findPreference(getText(R.string.preference_key_read_me));
        preference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference) {
                showDialog(DIALOG_READ_ME);
                return true;
            }
        });
        
        Preference preference1 = findPreference(getText(R.string.preference_key_area_set_1));
        preference1.setOnPreferenceClickListener(new OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference) {
                showDialog(DIALOG_AREA_SET_1);
                return true;
            }
        });

        Preference preference2 = findPreference(getText(R.string.preference_key_area_set_2));
        preference2.setOnPreferenceClickListener(new OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference) {
                showDialog(DIALOG_AREA_SET_2);
                return true;
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;

        switch (id) {
            case DIALOG_READ_ME:
                dialog = createReadMeDialog();
                break;
            case DIALOG_AREA_SET_1:
            	dialog = createAreaSetDialog(id);
                break;
            case DIALOG_AREA_SET_2:
            	dialog = createAreaSetDialog(id);
                break;
            default:
                Log.w(SampleExtensionService.LOG_TAG, "Not a valid dialog id: " + id);
                break;
        }

        return dialog;
    }

    /**
     * Create the Read me dialog
     *
     * @return the Dialog
     */
    private Dialog createReadMeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.preference_option_read_me_txt)
                .setTitle(R.string.preference_option_read_me)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(android.R.string.ok, new OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
    
    private Dialog createAreaSetDialog(int id) {
    	String text = "";
    	
        Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        switch (id) {
        case DIALOG_AREA_SET_2:
        	text = "Set Area Germany";
            editor.putString(this.PREFERENCE_AREA_KEY, "23424829");
            break;
        case DIALOG_AREA_SET_1:
        default:
        	text = "Set Area Kyoto";
            editor.putString(this.PREFERENCE_AREA_KEY, "15015372");
            break;
        }
        editor.commit();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(text)
                .setTitle(R.string.preference_option_read_me)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(android.R.string.ok, new OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

}
