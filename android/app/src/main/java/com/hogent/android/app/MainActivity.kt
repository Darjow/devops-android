package com.hogent.android.app

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.customview.widget.ViewDragHelper
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer

import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.hogent.android.R
import com.hogent.android.databinding.ActivityMainBinding
import com.hogent.android.util.AuthenticationManager
import java.lang.reflect.Field

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var menu: Menu;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        drawerLayout = binding.drawerLayout;
        menu = binding.navView.menu;

        val navController = this.findNavController(R.id.nav_host_fragment);

        binding.navView.setupWithNavController(navController);
        NavigationUI.setupWithNavController(binding.navView, navController);
        setDrawerOptions();

        AuthenticationManager.getInstance(this.application).authenticationState.observe(this, Observer {
            onCreateOptionsMenu(menu)
        })
    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear();
        if(AuthenticationManager.getInstance(this.application).loggedIn()){
            menuInflater.inflate(R.menu.navdrawer_menu_logged_in, menu);
        }else {
            menuInflater.inflate(R.menu.navdrawer_menu_logged_out, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun setDrawerOptions() {
        val field: Field = drawerLayout.javaClass.getDeclaredField("mLeftDragger");
        field.isAccessible = true;
        val dragger: ViewDragHelper = field.get(drawerLayout) as ViewDragHelper;
        val drag_size: Field = dragger.javaClass.getDeclaredField("mEdgeSize");
        drag_size.isAccessible = true;
        drag_size.setInt(dragger, 100);
    }

    fun logOut(item: MenuItem){
        AuthenticationManager.getInstance(this.application).logOut();
        findNavController(R.id.nav_host_fragment).navigate(R.id.loginFragment);


    }

    //GEEN navigation methods hier, de logout is enkel omdat deze een functie is en geen fragment.
    // zorg dat je de juiste +id gebruikt om te refereren van u menu naar u navgraph
    // niet : +id\all_vms_fragment  en   +id\vmListFragment
}

    