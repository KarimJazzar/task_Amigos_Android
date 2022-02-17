package com.example.task_amigos_android.fragments;

import static com.example.task_amigos_android.activities.MainActivity.isSubtask;
import static com.example.task_amigos_android.fragments.InfoFragment.taskSelected;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.task_amigos_android.R;
import com.example.task_amigos_android.activities.AddSubtask;
import com.example.task_amigos_android.adapter.TaskAdapter;
import com.example.task_amigos_android.entities.Subtask;
import com.example.task_amigos_android.model.SubtaskViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubTaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SubTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubTaskFragment newInstance(String param1, String param2) {
        SubTaskFragment fragment = new SubTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    ListView subList;
    Button addSubtask;
    SubtaskViewModel subtaskViewModel;
    List<Subtask> subtasks;
    List<Subtask> subtasksShown;
    public static List<Subtask> subtasksNew;

    TaskAdapter subAdapter;

    public static boolean editSubtask;
    public static Subtask selectedSubtask;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addSubtask = (Button) view.findViewById(R.id.addSubtask);
        subList = view.findViewById(R.id.subtaskList);
        isSubtask = true;
        subtaskViewModel = new ViewModelProvider(this).get(SubtaskViewModel.class);
        subtasks = new ArrayList<>();
        subtasksShown = new ArrayList<>();
        subtasksNew = new ArrayList<>();

        loadTask();


        addSubtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSubtask = false;
                startActivity(new Intent(view.getContext(), AddSubtask.class));
            }
        });

        subList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editSubtask = true;
                if(!subtasksShown.isEmpty()){
                    selectedSubtask = subtasksShown.get(position);
                }else if(!subtasksNew.isEmpty()){
                    selectedSubtask = subtasksNew.get(position);
                }
                startActivity(new Intent(view.getContext(), AddSubtask.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        subtasks.clear();
        loadTask();
        subAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_task, container, false);
    }

    private void loadTask() {
        subtaskViewModel.getAllSubtask().observe(getViewLifecycleOwner(), new Observer<List<Subtask>>() {
            @Override
            public void onChanged(@Nullable final List<Subtask> taskList) {
                subtasks.clear();
                subtasksShown.clear();
                subtasksNew.clear();
                for(Subtask task: taskList) {
                    //subtaskViewModel.delete(task);
                    subtasks.add(task);
                }

                if(taskSelected != null){
                    for(Subtask subtask: subtasks){
                        if(subtask.getTaskId() == taskSelected.getId()){
                            subtasksShown.add(subtask);

                        }
                    }
                    subAdapter = new TaskAdapter(getContext(),subtasksShown);
                    subList.setAdapter(subAdapter);
                }else{
                    for(Subtask subtask: subtasks){
                        if(subtask.getTaskId() == 0){
                            subtasksNew.add(subtask);
                        }
                    }
                    subAdapter = new TaskAdapter(getContext(),subtasksNew);
                    subList.setAdapter(subAdapter);
                }
                subAdapter.notifyDataSetChanged();
            }
        });
    }

    private void deleteSubtasksForNewTask(){
        subtaskViewModel.getAllSubtask().observe(getViewLifecycleOwner(), new Observer<List<Subtask>>() {
            @Override
            public void onChanged(@Nullable final List<Subtask> taskList) {
                for(Subtask task: taskList) {
                    if(task.getTaskId() == 0){
                        subtaskViewModel.delete(task);
                    }
                }
            }
        });
    }
}