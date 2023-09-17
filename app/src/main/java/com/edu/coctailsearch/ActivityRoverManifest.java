package com.edu.coctailsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.edu.coctailsearch.fragments.FragmentCuriosity;
import com.edu.coctailsearch.fragments.FragmentOportunity;
import com.edu.coctailsearch.fragments.FragmentSpirit;
import com.edu.coctailsearch.modelo.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ActivityRoverManifest extends AppCompatActivity{
    ViewPagerAdapter adapter;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_manifest);

        viewPager2 = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        //Añadir fragmento
        adapter.addFragment(new FragmentCuriosity());
        adapter.addFragment(new FragmentOportunity());
        adapter.addFragment(new FragmentSpirit());

        viewPager2.setAdapter(adapter);

        //
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Curiosity");
                        break;
                    case 1:
                        tab.setText("Opportunity");
                        break;
                    case 2:
                        tab.setText("Spirit");
                        break;
                }
            }
        }).attach();
    }
}