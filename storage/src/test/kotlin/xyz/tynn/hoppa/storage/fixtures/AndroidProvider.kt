//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.storage.fixtures

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.mockk.every
import io.mockk.mockk
import javax.inject.Singleton

@Module
internal object AndroidProvider {
    @[Provides Singleton]
    fun provideContext() = mockk<Context>()

    @[Provides Singleton]
    fun provideApplication(
        context: Context,
    ) = mockk<Application> {
        every {
            applicationContext
        } returns context
    }
}
