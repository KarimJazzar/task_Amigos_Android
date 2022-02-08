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
    private List<Task> completeList;
    private List<Task> incompleteList;
    private List<Category> categoryList;
    // Entities models
    private TaskViewModel taskVM;
    private CategoryViewModel categoryVM;
    // Atributes for swipe or drag tracking
    private float x1, x2;
    private final float swipeDistance = 150;
    private int currentPosition = 0;

    public static ArrayList<TaskModel> incompleteTaskModels;
    public static ArrayList<TaskModel> completeTaskModels;

    TabLayout tabLayout;
    ViewPager2 pager;
    Button addT;
    FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskVM = new ViewModelProvider(this).get(TaskViewModel.class);
        categoryVM = new ViewModelProvider(this).get(CategoryViewModel.class);

        incompleteTaskModels = new ArrayList<>();
        completeTaskModels = new ArrayList<>();

        /*
        incompleteTaskModels.add(new TaskModel(1,"Incomplete 1","first incomplete task","Work",false, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(), parseDate("2014-02-14"),parseDate("2014-02-14"),false));
        incompleteTaskModels.add(new TaskModel(2,"Incomplete 2","second incomplete task","School",false, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(), parseDate("2014-02-14"),parseDate("2014-02-14"),false));
        incompleteTaskModels.add(new TaskModel(1,"Incomplete 3","first incomplete task","School",false, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(), parseDate("2014-02-14"),parseDate("2014-02-14"),false));
        incompleteTaskModels.add(new TaskModel(2,"Incomplete 4","second incomplete task","Groceries",false, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(), parseDate("2014-02-14"),parseDate("2014-02-14"),false));
        incompleteTaskModels.add(new TaskModel(2,"Incomplete 5","second incomplete task","Shopping",false, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(), parseDate("2014-02-14"),parseDate("2014-02-14"),false));
        incompleteTaskModels.add(new TaskModel(2,"Incomplete 6","second incomplete task","Work",false, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(), parseDate("2014-02-14"),parseDate("2014-02-14"),false));
        completeTaskModels.add(new TaskModel(1,"Complete 1","first incomplete task","Work",true, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(), parseDate("2014-02-14"),parseDate("2014-02-14"),false));
        completeTaskModels.add(new TaskModel(1,"Complete 2","second incomplete task","Work",true, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(), parseDate("2014-02-14"),parseDate("2014-02-14"),false));
        */

        tabLayout = findViewById(R.id.tab_layout);
        pager = findViewById(R.id.taskViewPager);
        pager.setUserInputEnabled(false);

        /*
        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = motionEvent.getX();
                        Log.d("DEBUG", "TOUCH DOWN");
                        break;

                    case MotionEvent.ACTION_MOVE:
                        x2 = motionEvent.getX();
                        Log.d("DEBUG", "TOUCH MOVE");
                        break;

                    case MotionEvent.ACTION_UP:
                        Log.d("DEBUG", "TOUCH DOWN");
                        if (Math.abs(x1 - x1) >= swipeDistance) {
                            if (x1 < x2) {
                                Log.d("DEBUG", "LEFT SWIPE");
                            } else {
                                Log.d("DEBUG", "RIGHT SWIPE");
                            }
                        }
                        break;
                }

                return true;
            }
        });
        */
        //addT = findViewById(R.id.addTask);

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

        /*
        addT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */

        loadCategory();
        loadTask();
    }

    private void loadTask() {
        taskVM.getAllTask().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> taskList) {

                for(Task task: taskList) {
                    if (task.getStatus()) {
                        completeList.add(task);
                    } else {
                        incompleteList.add(task);
                    }
                    System.out.println("=======================");
                    System.out.println(task.getId());
                    System.out.println(task.getTitle());
                    System.out.println(task.getDescription());
                    System.out.println(task.getCategory());
                    System.out.println(task.getImages());
                    System.out.println(task.getAudios());
                    System.out.println(task.getStatus());
                    System.out.println(task.getDueDate());
                    System.out.println(task.getCreationDate());
                }

                fragmentAdapter.updateTaskTableList(incompleteList, completeList);
                // Update recycler view adapters here
                // (recycler view adapter).submitList(incompleteList);
                // (recycler view adapter).submitList(completeList);
            }
        });
    }

    private void loadCategory() {
        categoryVM.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryList = categories;
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

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                Log.d("DEBUG", "TOUCH DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                x2 = event.getX();
                Log.d("DEBUG", "TOUCH MOVE");
                break;

            case MotionEvent.ACTION_UP:
                Log.d("DEBUG", "TOUCH UP");
                if (Math.abs(x1 - x2) >= swipeDistance) {
                    if (x1 < x2) {
                        Log.d("DEBUG", "LEFT SWIPE");
                        currentPosition--;
                    } else {
                        Log.d("DEBUG", "RIGHT SWIPE");
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
                break;
        }

        boolean ret = super.dispatchTouchEvent(event);
        return ret;
    }
}