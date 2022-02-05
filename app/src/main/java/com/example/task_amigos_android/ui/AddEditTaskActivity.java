package com.example.task_amigos_android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.task_amigos_android.R;
import com.example.task_amigos_android.adapter.AddEditFrgAdapter;
import com.example.task_amigos_android.adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class AddEditTaskActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 pager;
    AddEditFrgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        tabLayout = findViewById(R.id.tab_layout);
        pager = findViewById(R.id.view_pager2);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new AddEditFrgAdapter(fm, getLifecycle());
        pager.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Add Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Add Sub Task"));
        tabLayout.addTab(tabLayout.newTab().setText("Attach Media"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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


        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }
}