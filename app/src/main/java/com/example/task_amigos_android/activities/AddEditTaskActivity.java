package com.example.task_amigos_android.activities;

import static com.example.task_amigos_android.adapter.TaskRVAdapter.taskSelected;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.task_amigos_android.R;
import com.example.task_amigos_android.adapter.AddEditFrgAdapter;
import com.example.task_amigos_android.entities.Category;
import com.example.task_amigos_android.entities.Subtask;
import com.example.task_amigos_android.entities.Task;
import com.example.task_amigos_android.model.CategoryViewModel;
import com.example.task_amigos_android.model.SubtaskViewModel;
import com.example.task_amigos_android.model.TaskViewModel;
import com.example.task_amigos_android.repositories.AttachRespository;
import com.example.task_amigos_android.databinding.ActivityAddEditTaskBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class AddEditTaskActivity extends AppCompatActivity {
    // Entities models
    private TaskViewModel taskVM;
    private SubtaskViewModel subtaskVM;
    private AddEditFrgAdapter fragmentAdapter;
    private ActivityAddEditTaskBinding binding;
    private List<Subtask>  subtaskList;
    private Boolean isEditMode = false;
    private Task selectedTask;

    private ViewPager2 pager;
    private String TAG = this.getClass().getName();
    private AttachRespository attachRespository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityAddEditTaskBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        attachRespository = new AttachRespository();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pager = findViewById(R.id.view_pager2);

        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new AddEditFrgAdapter(fm, getLifecycle());
        pager.setAdapter(fragmentAdapter);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Info"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Subtask"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Attach"));

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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            boolean value = extras.getBoolean("currentStatus");
        }

        taskVM = new ViewModelProvider(this).get(TaskViewModel.class);

        if (isEditMode) {
            subtaskVM = new ViewModelProvider(this).get(SubtaskViewModel.class);
            loadSubtask();
        }

        Task selectedTask = (Task) getIntent().getSerializableExtra("Task");

        if(selectedTask != null) {
            fragmentAdapter.sendTaskToInfoFragment(selectedTask);
            fragmentAdapter.sendTaskToAttachFragment(selectedTask);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case android.R.id.home:
                Log.e(TAG,"back");

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("images", "");
                myEdit.putString("audio", "");
                myEdit.apply();

                Intent intent = new Intent(AddEditTaskActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadSubtask() {
        subtaskList = subtaskVM.getSubtaskByTaskId(selectedTask.getId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==this.RESULT_OK){
            super.onActivityResult(requestCode, resultCode, data);
            setResult(RESULT_OK, data);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("images", "");
        myEdit.putString("audio", "");
        myEdit.apply();
    }
}