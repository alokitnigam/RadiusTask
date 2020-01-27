
package com.example.radiustask.DI.prefs;



public interface PreferencesHelper {

    boolean isSyncedForFirstTime();

    void syncedForFirstTime();

    Long getSyncTimeStamp();

    void setSyncTimeStamp(Long timeStamp);


}
