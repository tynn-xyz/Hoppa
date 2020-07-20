//  Copyright 2020 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.time

import android.content.Context
import androidx.startup.Initializer
import com.jakewharton.threetenabp.AndroidThreeTen

@Suppress("unused")
class ThreeTenInitializer : Initializer<Unit> {

    override fun create(context: Context) =
        AndroidThreeTen.init(context)

    override fun dependencies() =
        emptyList<Class<out Initializer<*>>>()
}
