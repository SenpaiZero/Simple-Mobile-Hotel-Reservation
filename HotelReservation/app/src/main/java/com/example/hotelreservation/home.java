package com.example.hotelreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import Fragments.BookFragment;
import Fragments.MyBookFragment;
import Fragments.MyProfileFragment;
import Fragments.PagerAdapter;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try
        {
            getSupportActionBar().hide();
        } catch (Exception ex)
        {
            try {
                getActionBar().hide();
            } catch (Exception exx)
            {
                Log.e("Action Bar", exx.getMessage());
            }
        }

        // Find the TabLayout and ViewPager
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        // Create an adapter to manage the fragments for each tab
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());

        // Add your fragments to the adapter
        adapter.addFragment(new BookFragment(), "Book");
        adapter.addFragment(new MyBookFragment(), "My Book");
        adapter.addFragment(new MyProfileFragment(), "Profile");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setScrollPosition(1,0f,true);
        viewPager.setCurrentItem(1);
    }

}