package com.simulation.calendary.activities

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.simulation.calendary.R
import com.simulation.calendary.fragments.CalendarFragment
import com.simulation.calendary.fragments.TaskFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupListeners()
    }

    private fun initViews(){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout,CalendarFragment())
            commit()
        }
    }

    private fun setupListeners(){
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavBar)
        bottomNavigationView.setOnItemSelectedListener {item ->
            val clickedFragment = when(item.itemId){
                R.id.bnCalendar -> CalendarFragment()
                R.id.bnTasks -> TaskFragment()
                else -> CalendarFragment()
            }
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout,clickedFragment)
                commit()
            }
            true
        }
    }
}