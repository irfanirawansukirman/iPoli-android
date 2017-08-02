package io.ipoli.android.player

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by vini on 8/2/17.
 */
open class Player(
        @PrimaryKey var id: String = "",
        var coins: Int = 0,
        var experience: Int = 0
) : RealmObject()