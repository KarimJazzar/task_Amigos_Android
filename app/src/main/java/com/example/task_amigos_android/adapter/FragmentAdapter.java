package com.example.task_amigos_android.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.task_amigos_android.TaskFragment;
import com.example.task_amigos_android.controller.CompleteFragment;
import com.example.task_amigos_android.controller.IncompleteFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if(position == 0){
            return new TaskFragment();
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
