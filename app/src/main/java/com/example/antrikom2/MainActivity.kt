package com.example.antrikom2

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.antrikom2.databinding.ActivityMainBinding
import com.example.antrikom2.util.ModelAuth
import com.example.antrikom2.util.SharedPref


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Setup ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val colorDrawable = ColorDrawable(Color.parseColor("#FDDCA5"))
        supportActionBar?.setBackgroundDrawable(colorDrawable)
        supportActionBar?.setTitle("AntriKom")
        //Setup Navigation Slide
        navController = findNavController(R.id.nav_host_fragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.dashboardFragment || destination.id == R.id.pengajuanFragment || destination.id == R.id.antrianFragment) {
                binding.navigationView.visibility = View.VISIBLE
                drawerLayout = binding.drawerLayout
                binding.navigationView.setupWithNavController(navController)
                appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
                setupActionBarWithNavController(navController, appBarConfiguration)
                initSideBar()
            } else {
                initSideBar()
                binding.navigationView.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        binding.IDMainBtnLogout.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)

        }

        //Logout
        binding.IDMainBtnLogout.setOnClickListener {
            Logout()
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun Logout(){
        val myPreference = SharedPref(this)
        val data = ModelAuth("","","")
        myPreference.setData(data)
        finish()
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)

    }

    private fun initSideBar(){
        //Setting Header text atau Sidebar
        val myPrefrence = SharedPref(this)
        val header = binding.navigationView.getHeaderView(0)
        val txtNameHeader = header.findViewById<TextView>(R.id.ID_Navheader_TxtNama)
        val txtNimHeader = header.findViewById<TextView>(R.id.ID_Navheader_TxtNim)
        txtNimHeader.text = myPrefrence.getData().NIM
        txtNameHeader.text = myPrefrence.getData().Nama
    }


}