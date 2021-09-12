//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage.fixtures

import dagger.Component
import xyz.tynn.hoppa.storage.DatabaseBuilderTest
import javax.inject.Singleton

@[Singleton Component(modules = [AndroidProvider::class])]
internal interface DefaultDatabaseBuilderFactory {
    fun inject(test: DatabaseBuilderTest)
}

