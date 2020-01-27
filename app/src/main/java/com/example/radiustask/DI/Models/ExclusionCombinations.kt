package com.example.radiustask.DI.Models

import com.google.gson.annotations.Expose
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class ExclusionCombinations (
    var facility_id:String?= null,
    var options_id:String? = null,
    @Expose(serialize = false)
    var excluded_facility_id:String?= null,
    @Expose(serialize = false)
    var excluded_options_id:String? = null
): RealmObject()