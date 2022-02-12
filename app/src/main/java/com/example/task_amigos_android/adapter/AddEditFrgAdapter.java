package com.example.task_amigos_android.adapter;// Created by FebinRukfan on 03-02-2022.

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.task_amigos_android.entities.Category;
import com.example.task_amigos_android.fragments.AttachFragment;
import com.example.task_amigos_android.fragments.InfoFragment;
import com.example.task_amigos_android.fragments.SubTaskFragment;

import java.util.List;

public class AddEditFrgAdapter extends FragmentStateAdapter {
    private InfoFragment infoFrag = new InfoFragment();
    private SubTaskFragment subtaskFrag = new SubTaskFragment();
    private AttachFragment attachFrag = new AttachFragment();

    public AddEditFrgAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if(position == 0){
            return infoFrag;
        }else if (position == 1){
            return subtaskFrag;
        }else {
            return attachFrag;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void sendCategoriesToInfoFrag(List<Category> categories) {
        infoFrag.setCategoryList(categories);
    }
}

