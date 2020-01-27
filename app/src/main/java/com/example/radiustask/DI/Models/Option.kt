package com.example.radiustask.DI.Models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Option (
    @PrimaryKey
    var id: String? =null,
    var icon: String? =null,
    var name: String? =null,
    var excludedOptionsIds: RealmList<String>? = null

): RealmObject()