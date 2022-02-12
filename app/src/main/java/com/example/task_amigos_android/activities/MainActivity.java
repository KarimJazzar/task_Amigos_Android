package com.example.task_amigos_android.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.task_amigos_android.adapter.FragmentAdapter;
import com.example.task_amigos_android.R;
import com.example.task_amigos_android.entities.Category;
import com.example.task_amigos_android.entities.Task;
import com.example.task_amigos_android.model.CategoryViewModel;
import com.example.task_amigos_android.model.TaskModel;
import com.example.task_amigos_android.model.TaskViewModel;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // List containers
    private List<Task> completeList = new ArrayList<>();
    private List<Task> incompleteList = new ArrayList<>();
    private List<Category> categoryList;
    // Entities models
    private TaskViewModel taskVM;
    private CategoryViewModel categoryVM;
    // Atributes for swipe or drag tracking
    private float x1, x2;
    private final float swipeDistance = 150;
    private int currentPosition = 0;

    private TabLayout tabLayout;
    private ViewPager2 pager;
    private FragmentAdapter fragmentAdapter;
    public static boolean ignoreSwipe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskVM = new ViewModelProvider(this).get(TaskViewModel.class);
        categoryVM = new ViewModelProvider(this).get(CategoryViewModel.class);

        tabLayout = findViewById(R.id.tab_layout);
        pager = findViewById(R.id.mainViewPager);
        pager.setUserInputEnabled(false);

        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fm, getLifecycle());
        pager.setAdapter(fragmentAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Tasks"));
        tabLayout.addTab(tabLayout.newTab().setText("Categories"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });


        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        loadCategory();
        loadTask();
    }

    private void loadTask() {
        taskVM.getAllTask().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> taskList) {
                completeList.clear();
                incompleteList.clear();

                for(Task task: taskList) {
                    if (task.getStatus() == true) {
                        completeList.add(task);
                    } else {
                        incompleteList.add(task);
                    }
                }

                fragmentAdapter.updateTaskTableList(incompleteList, completeList);
            }
        });
    }

    private void loadCategory() {
        categoryVM.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryList = categories;
                fragmentAdapter.updateCategoryTableList(categories);
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if(ignoreSwipe) { return ret; }

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                x2 = event.getX();
                break;

            case MotionEvent.ACTION_UP:
                if (Math.abs(x1 - x2) >= swipeDistance) {
                    if (x1 < x2) {
                        currentPosition--;
                    } else {
                        currentPosition++;
                    }

                    currentPosition = currentPosition < 0 ? 0 : currentPosition;
                    currentPosition = currentPosition > 2 ? 2 : currentPosition;

                    if (currentPosition == 2) {
                        pager.setCurrentItem(1);
                    } else {
                        pager.setCurrentItem(0);
                        fragmentAdapter.updateTaskTablePosition(currentPosition);
                    }
                }

                x1 = 0;
                x2 = 0;

                break;
        }


        return ret;
    }
}