//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage.fixtures

import dagger.Binds
import dagger.Component
import dagger.Module
import xyz.tynn.hoppa.storage.DatabaseBuilder
import xyz.tynn.hoppa.storage.InMemoryDatabaseBuilder
import xyz.tynn.hoppa.storage.InMemoryDatabaseBuilderTest
import javax.inject.Singleton

@[Singleton Component(modules = [AndroidProvider::class, InMemoryModule::class])]
internal interface InMemoryDatabaseBuilderFactory {
    fun inject(test: InMemoryDatabaseBuilderTest)
}

@Module
private interface InMemoryModule {
    @Binds
    fun bindDatabaseBuilder(
        builder: InMemoryDatabaseBuilder,
    ): DatabaseBuilder
}
