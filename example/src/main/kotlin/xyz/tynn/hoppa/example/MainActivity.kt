//  Copyright 2020-2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.example

import android.R.id.home
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import xyz.tynn.hoppa.binding.setContentView
import xyz.tynn.hoppa.binding.viewBinding
import xyz.tynn.hoppa.example.databinding.ActivityMainBinding
import xyz.tynn.hoppa.keyboard.hideKeyboardOnFocusChange
import xyz.tynn.hoppa.keyboard.setOnKeyboardVisibilityChangeListener

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(
        ActivityMainBinding::inflate,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding)
        setupActionBarAndNavigation(findNavController())
        hideKeyboardOnFocusChange { it !is EditText }
        setOnKeyboardVisibilityChangeListener { binding.navigation.isGone = it }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        home -> findNavController().navigateUp()
        else -> super.onOptionsItemSelected(item)
    }

    private fun findNavController() = supportFragmentManager
        .findFragmentById(R.id.nav_graph)!!
        .findNavController()

    private fun setupActionBarAndNavigation(nav: NavController) {
        setupActionBarWithNavController(nav)
        binding.navigation.setupWithNavController(nav)
    }
}
