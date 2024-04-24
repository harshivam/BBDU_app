package com.example.bbdu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawerLayout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)


        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Home()).commit()
            navigationView.setCheckedItem(R.id.nav_home)

        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Home()).commit()

            R.id.nav_contact -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Contact()).commit()

            R.id.nav_logout -> {
                showLogoutConfirmationDialog()
            }
            R.id.nav_share -> {
                shareAppLink(this)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.logout_confirmation_message))
            .setPositiveButton(getString(R.string.logout_confirm)) { _, _ ->
                finishAffinity()
            }
            .setNegativeButton(getString(R.string.logout_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
    private fun shareAppLink(context: Context) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out BBDU app on GitHub: https://github.com/harshivam/Todo-app")
        context.startActivity(Intent.createChooser(shareIntent, "Share link via:"))
    }


    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() {
        super.onBackPressed()
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}