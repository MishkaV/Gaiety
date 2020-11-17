package com.example.gaiety

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun click(view: View) {
        var profile = profileFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, profile)
            .commit()
    }
}
