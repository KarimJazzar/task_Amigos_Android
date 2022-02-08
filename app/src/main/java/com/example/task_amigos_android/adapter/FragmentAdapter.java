package com.example.task_amigos_android.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.task_amigos_android.entities.Task;
import com.example.task_amigos_android.fragments.TaskFragment;
import com.example.task_amigos_android.fragments.CompleteFragment;

import java.util.List;

public class FragmentAdapter extends FragmentStateAdapter {
    private TaskFragment taskFragment = new TaskFragment();

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void updateTaskFramentTables(List<Task> incompleteTask, List<Task> completeTask) {
        taskFragment.updateAdaptersList(incompleteTask, completeTask);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if(position == 0){
            return taskFragment;
            //return new IncompleteFragment();
        }else{
            return new CompleteFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
