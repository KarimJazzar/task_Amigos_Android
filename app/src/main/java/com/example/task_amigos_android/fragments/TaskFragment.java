package com.example.task_amigos_android.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.example.task_amigos_android.R;
import com.example.task_amigos_android.adapter.TaskRVAdapter;
import com.example.task_amigos_android.entities.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {

    private FrameLayout completeCol;
    private FrameLayout incompleteCol;
    private RecyclerView completeRV;
    private RecyclerView incompleteRV;
    private TaskRVAdapter completeAdapter = new TaskRVAdapter();
    private TaskRVAdapter incompleteAdapter = new TaskRVAdapter();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaskFragment() {
        // Required empty public constructor
    }

    public void updateAdaptersList(List<Task> incompleteTask, List<Task> completeTask) {
        incompleteAdapter.submitList(incompleteTask);
        completeAdapter.submitList(completeTask);
    }

    public void updateColPosition() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance(String param1, String param2) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Context context = getActivity().getApplicationContext();

        completeCol = (FrameLayout) view.findViewById(R.id.completeCol);
        incompleteCol = (FrameLayout) view.findViewById(R.id.incompleteCol);

        completeRV = (RecyclerView) view.findViewById(R.id.completeRV);
        completeRV.setLayoutManager(new LinearLayoutManager(context));
        completeRV.setHasFixedSize(true);
        completeRV.setAdapter(completeAdapter);

        incompleteRV = (RecyclerView) view.findViewById(R.id.incompleteRV);
        incompleteRV.setLayoutManager(new LinearLayoutManager(context));
        incompleteRV.setHasFixedSize(true);
        incompleteRV.setAdapter(incompleteAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false);
    }
}