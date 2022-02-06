package com.example.task_amigos_android.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.task_amigos_android.InfoController;
import com.example.task_amigos_android.R;
import com.example.task_amigos_android.databinding.FragmentInfoBinding;
import com.example.task_amigos_android.helpers.DateHelper;


public class InfoFragment extends Fragment {


    public String TAG = this.getClass().getName();
    private FragmentInfoBinding binding;
    DateHelper dateHelper;
    InfoController infoController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle("Add Task");
        dateHelper = new DateHelper();
        infoController = new InfoController();


        binding = FragmentInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.txtCreationDate.setText(dateHelper.todaysDate());
        binding.txtDuedate.setText(dateHelper.todaysDate());
        binding.txtDuedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                infoController.selectDate(binding,getContext());
            }
        });
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
