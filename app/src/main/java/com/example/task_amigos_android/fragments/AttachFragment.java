package com.example.task_amigos_android.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.task_amigos_android.AttachController;
import com.example.task_amigos_android.R;
import com.example.task_amigos_android.databinding.FragmentAttachBinding;
import com.example.task_amigos_android.helpers.PermissionsHelper;

public class AttachFragment extends Fragment {

   FragmentAttachBinding fragmentAttachBinding;
   PermissionsHelper permissionsHelper;
   AttachController attachController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAttachBinding = FragmentAttachBinding.inflate(inflater, container, false);
        View view = fragmentAttachBinding.getRoot();

        permissionsHelper = new PermissionsHelper();
        attachController = new AttachController();

        permissionsHelper.checkAndRequestPermissions(getActivity());

        fragmentAttachBinding.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permissionsHelper.checkAndRequestPermissions(getActivity())){
                    attachController.chooseImage(getActivity());
                }
            }
        });


        return view;
    }
}