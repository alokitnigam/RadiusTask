package com.example.radiustask.DI.Models

import io.realm.RealmList

open class ExclusionItems (
    var id:String?= null,
    var excludedIdList:RealmList<String>? = null
    )