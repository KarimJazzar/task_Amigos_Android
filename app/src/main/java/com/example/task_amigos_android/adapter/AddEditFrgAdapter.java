package com.example.task_amigos_android.adapter;// Created by FebinRukfan on 03-02-2022.

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.task_amigos_android.fragments.AttachFragment;
import com.example.task_amigos_android.fragments.InfoFragment;
import com.example.task_amigos_android.fragments.SubTaskFragment;

public class AddEditFrgAdapter extends FragmentStateAdapter {
    public AddEditFrgAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if(position == 0){
            return new InfoFragment();
        }else if (position == 1){
            return new SubTaskFragment();
        }else {
            return new AttachFragment();

        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

