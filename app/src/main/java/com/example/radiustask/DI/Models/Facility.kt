package com.example.radiustask.DI.Models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Facility(
    @PrimaryKey
    var facility_id: String? = null,
    var name: String? = null,
    var options: RealmList<Option>? = null
):RealmObject()