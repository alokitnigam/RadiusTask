/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.example.radiustask.DI.prefs;

import android.content.Context;
import android.content.SharedPreferences;


import javax.inject.Inject;


public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_IS_SYNCED = "issynced";
    private static final String PREF_SYNC_TIMESTAMP = "synctimestamp";


    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }
    @Override
    public boolean isSyncedForFirstTime() {
        return mPrefs.getBoolean(PREF_IS_SYNCED,false);
    }

    @Override
    public void syncedForFirstTime() {
        mPrefs.edit().putBoolean(PREF_IS_SYNCED,true).apply();
    }

    @Override
    public Long getSyncTimeStamp() {
        return mPrefs.getLong(PREF_SYNC_TIMESTAMP,0);
    }

    @Override
    public void setSyncTimeStamp(Long timeStamp) {
        mPrefs.edit().putLong(PREF_SYNC_TIMESTAMP,0).apply();

    }
}
