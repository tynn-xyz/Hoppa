//  Copyright 2020-2021 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.example

import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import xyz.tynn.hoppa.keyboard.hideKeyboardOnFocusChange
import xyz.tynn.hoppa.keyboard.setOnKeyboardVisibilityChangeListener

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBarAndNavigation(findNavController())
        hideKeyboardOnFocusChange { it !is EditText }
        setOnKeyboardVisibilityChangeListener { navigation.isGone = it }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> findNavController().navigateUp()
        else -> super.onOptionsItemSelected(item)
    }

    private fun setupActionBarAndNavigation(nav: NavController) {
        setupActionBarWithNavController(nav)
        navigation.setupWithNavController(nav)
    }

    private fun findNavController() = supportFragmentManager
        .findFragmentById(R.id.nav_graph)!!
        .findNavController()
}
