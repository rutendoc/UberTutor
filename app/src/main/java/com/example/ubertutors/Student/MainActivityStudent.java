package com.example.ubertutors.Student;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.example.ubertutors.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivityStudent extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ViewPager pager;
    TabLayout sTabLayout;
    TabItem myModulesTab;
    TabItem myTutsTab;
    TabItem inboxTab;
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_student);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        pager = findViewById(R.id.viewpager);
        sTabLayout = findViewById(R.id.tabLayout);
        myModulesTab = findViewById(R.id.my_modules_tab);
        myTutsTab = findViewById(R.id.my_tuts_tab);
        inboxTab = findViewById(R.id.inbox_tab);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        //so that drawer opens on click of hamburger sign and close when you click anywhere else
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        adapter = new PagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                sTabLayout.getTabCount());
        pager.setAdapter(adapter);

        sTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(sTabLayout));
//
//
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home_student, R.id.nav_profile_student, R.id.nav_payment_student,
//                R.id.nav_help_student)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this,
//                R.id.nav_host_fragment_student);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_student);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
   }

   //Menu Nav stuff!!!!!!!!!!!!!!!!!!!!!!!!!!
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId() == R.id.nav_home_student)
            Toast.makeText(this, "Work in Progress", Toast.LENGTH_SHORT).show();
        if(item.getItemId() == R.id.nav_profile_student){
            startActivity(new Intent(getApplicationContext(),
                    com.example.ubertutors.Student.Profile.ProfileStudent.class));
        }
        if(item.getItemId() == R.id.nav_payment_student)
            Toast.makeText(this, "Work in Progress", Toast.LENGTH_SHORT).show();
        if(item.getItemId() == R.id.nav_help_student)
            Toast.makeText(this, "Work in Progress", Toast.LENGTH_SHORT).show();
        return false;
    }

}
