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
    }
}