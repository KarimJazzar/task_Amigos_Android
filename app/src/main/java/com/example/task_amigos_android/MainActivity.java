package com.example.task_amigos_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<TaskModel> incompleteTaskModels;
    public static ArrayList<TaskModel> completeTaskModels;

    TabLayout tabLayout;
    ViewPager2 pager;
    FragmentAdapter adapter;
    Button addT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        incompleteTaskModels = new ArrayList<>();
        completeTaskModels = new ArrayList<>();

        incompleteTaskModels.add(new TaskModel(1,"Incomplete 1","first incomplete task","Work",false, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(), parseDate("2014-02-14"),parseDate("2014-02-14"),false));
        incompleteTaskModels.add(new TaskModel(2,"Incomplete 2","second incomplete task","Work",false, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(), parseDate("2014-02-14"),parseDate("2014-02-14"),false));

        completeTaskModels.add(new TaskModel(1,"Complete 1","first incomplete task","Work",true, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(), parseDate("2014-02-14"),parseDate("2014-02-14"),false));
        completeTaskModels.add(new TaskModel(1,"Complete 2","second incomplete task","Work",true, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(), parseDate("2014-02-14"),parseDate("2014-02-14"),false));


        tabLayout = findViewById(R.id.tab_layout);
        pager = findViewById(R.id.view_pager2);
        addT = findViewById(R.id.addTask);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm, getLifecycle());
        pager.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Incomplete Tasks"));
        tabLayout.addTab(tabLayout.newTab().setText("Complete Tasks"));

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

        addT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}