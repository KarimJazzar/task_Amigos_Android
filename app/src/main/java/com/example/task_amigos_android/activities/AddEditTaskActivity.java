package com.example.task_amigos_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.task_amigos_android.R;
import com.example.task_amigos_android.adapter.AddEditFrgAdapter;
import com.google.android.material.tabs.TabLayout;

public class AddEditTaskActivity extends AppCompatActivity {

    ViewPager2 pager;
    AddEditFrgAdapter adapter;
    String TAG = this.getClass().getName();
    private ActivityAddEditTaskBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditTaskBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pager = findViewById(R.id.view_pager2);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new AddEditFrgAdapter(fm, getLifecycle());
        pager.setAdapter(adapter);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Add Info"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Add Sub Task"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Attach Media"));

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case android.R.id.home:
                Log.e(TAG,"back");
                Intent intent = new Intent(AddEditTaskActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}