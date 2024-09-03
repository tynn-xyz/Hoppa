//  Copyright 2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.keyboard

import android.app.Activity
import android.view.View
import android.view.View.OnApplyWindowInsetsListener
import android.view.ViewTreeObserver
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type.ime
import androidx.core.view.WindowInsetsCompat.toWindowInsetsCompat
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.slot
import io.mockk.spyk
import io.mockk.unmockkStatic
import io.mockk.verify
import io.mockk.verifyAll
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

internal class KeyboardVisibilityTest {

    private val activity = mockk<Activity> {
        every {
            window.attributes
        } returns mockk(relaxed = true)
        every {
            window.decorView.viewTreeObserver.addOnGlobalFocusChangeListener(any())
        } just runs
        every {
            window.decorView.setOnApplyWindowInsetsListener(any())
        } just runs
    }

    private val imm = mockk<InputMethodManager>(relaxed = true)

    val listener = mockk<(Boolean) -> Unit>(relaxed = true)
    private val focusListener = slot<ViewTreeObserver.OnGlobalFocusChangeListener>()

    private val view = mockk<View>(relaxed = true)
    private val insets = mockk<WindowInsets>(relaxed = true)
    private val insetsCompat = mockk<WindowInsetsCompat>(relaxed = true)

    private val insetsListener = slot<OnApplyWindowInsetsListener>()

    @BeforeTest
    fun setup() {
        mockkStatic(ContextCompat::class, WindowInsetsCompat::class)
        every {
            activity.getSystemService<InputMethodManager>()
        } returns imm
        every {
            toWindowInsetsCompat(insets, view)
        } returns insetsCompat
    }

    @AfterTest
    fun teardown() {
        unmockkStatic(ContextCompat::class, WindowInsetsCompat::class)
    }

    @Test
    fun `setOnKeyboardVisibilityChangeListener should delegate to onApplyWindowInsets of view`() {
        activity.setOnKeyboardVisibilityChangeListener(
            onKeyboardVisibilityChanged = listener,
        )

        verify {
            activity.window.decorView.setOnApplyWindowInsetsListener(
                capture(insetsListener),
            )
        }

        insetsListener.captured.onApplyWindowInsets(view, insets)

        verifyAll {
            listener(insetsCompat.isVisible(ime()))
            view.onApplyWindowInsets(insets)
        }
    }

    @Test
    fun `setOnKeyboardVisibilityChangeListener should delegate to onApplyWindowInsets`() {
        val onApplyWindowInsets = spyk<(View, WindowInsets) -> WindowInsets>()
        activity.setOnKeyboardVisibilityChangeListener(
            onApplyWindowInsets,
            listener,
        )

        verify {
            activity.window.decorView.setOnApplyWindowInsetsListener(
                capture(insetsListener),
            )
        }

        insetsListener.captured.onApplyWindowInsets(view, insets)

        verifyAll {
            listener(insetsCompat.isVisible(ime()))
            onApplyWindowInsets(view, insets)
        }
    }

    @Test
    fun `setOnKeyboardVisibilityChangeListener should delegate to onApplyWindowInsets of delegate`() {
        val delegate = mockk<OnApplyWindowInsetsListener>(relaxed = true)
        activity.setOnKeyboardVisibilityChangeListener(
            delegate,
            listener,
        )

        verify {
            activity.window.decorView.setOnApplyWindowInsetsListener(
                capture(insetsListener),
            )
        }

        insetsListener.captured.onApplyWindowInsets(view, insets)

        verifyAll {
            listener(insetsCompat.isVisible(ime()))
            delegate.onApplyWindowInsets(view, insets)
        }
    }

    @Test
    fun `hideKeyboardOnFocusChange should throw without InputMethodManager`() {
        every {
            activity.getSystemService<InputMethodManager>()
        } returns null

        assertFailsWith<NullPointerException> {
            activity.hideKeyboardOnFocusChange { true }
        }
    }

    @Test
    fun `hideKeyboardOnFocusChange should use provided InputMethodManager`() {
        activity.hideKeyboardOnFocusChange(imm = imm) { false }

        verify(inverse = true) {
            activity.getSystemService<InputMethodManager>()
        }
    }

    @Test
    fun `hideKeyboardOnFocusChange should hide input when predicate is true`() {
        activity.hideKeyboardOnFocusChange { true }

        verify {
            activity.window.decorView.viewTreeObserver.addOnGlobalFocusChangeListener(
                capture(focusListener),
            )
        }

        focusListener.captured.onGlobalFocusChanged(null, view)

        verifyAll {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    @Test
    fun `hideKeyboardOnFocusChange should not hide input when predicate is false`() {
        activity.hideKeyboardOnFocusChange(true) { false }

        verify {
            activity.window.decorView.viewTreeObserver.addOnGlobalFocusChangeListener(
                capture(focusListener),
            )
        }

        focusListener.captured.onGlobalFocusChanged(null, view)

        verifyAll(inverse = true) {
            imm.hideSoftInputFromWindow(view.windowToken, any())
        }
    }

    @Test
    fun `hideKeyboardOnFocusChange should implicitly hide input with isHideImplicitOnly`() {
        activity.hideKeyboardOnFocusChange(true) { false }

        verify {
            activity.window.decorView.viewTreeObserver.addOnGlobalFocusChangeListener(
                capture(focusListener),
            )
        }

        focusListener.captured.onGlobalFocusChanged(view, null)

        verifyAll {
            imm.hideSoftInputFromWindow(view.windowToken, HIDE_IMPLICIT_ONLY)
        }
    }

    @Test
    fun `hideKeyboardOnFocusChange should hide input from window when views are null`() {
        activity.hideKeyboardOnFocusChange { false }

        verifyAll {
            activity.window.decorView.viewTreeObserver.addOnGlobalFocusChangeListener(
                capture(focusListener),
            )
            activity.window.attributes.token
        }

        val token = activity.window.attributes.token
        clearMocks(activity.window, answers = false)
        focusListener.captured.onGlobalFocusChanged(null, null)

        verifyAll {
            imm.hideSoftInputFromWindow(token, 0)
        }
        verify(inverse = true) {
            activity.window.attributes.token
        }
    }
}
