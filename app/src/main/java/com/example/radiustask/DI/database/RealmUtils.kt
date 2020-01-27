@file:JvmName("RealmUtils") // pretty name for utils class if called from
package com.example.radiustask.DI.database

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults


fun <T:RealmModel> RealmResults<T>.asLiveData() = LiveRealmData<T>(this)