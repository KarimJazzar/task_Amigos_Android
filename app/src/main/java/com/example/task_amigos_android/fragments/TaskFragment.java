package com.example.task_amigos_android.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.task_amigos_android.R;
import com.example.task_amigos_android.activities.AddEditTaskActivity;
import com.example.task_amigos_android.activities.AudioActivity;
import com.example.task_amigos_android.activities.MainActivity;
import com.example.task_amigos_android.adapter.TaskRVAdapter;
import com.example.task_amigos_android.entities.Task;
import com.example.task_amigos_android.helpers.AnimationHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {

    private Button addTaskBtn, clearBtn, searchBtn;
    private EditText searchInput;
    private Spinner sortDropdown;
    private FrameLayout completeCol;
    private FrameLayout incompleteCol;
    private RecyclerView completeRV;
    private RecyclerView incompleteRV;
    private List<Task> incompleteTask = new ArrayList<>();
    private List<Task> completeTask = new ArrayList<>();
    private TaskRVAdapter completeAdapter = new TaskRVAdapter();
    private TaskRVAdapter incompleteAdapter = new TaskRVAdapter();
    private int animType = TranslateAnimation.ABSOLUTE;
    private float colMargin = 0;
    private int selectedSort = 0;

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
        this.incompleteTask = incompleteTask;
        this.completeTask = completeTask;

        incompleteAdapter.submitList(incompleteTask);
        completeAdapter.submitList(completeTask);
    }

    public void refreshAdapter() {
        incompleteAdapter.notifyDataSetChanged();
        completeAdapter.notifyDataSetChanged();
    }

    public void updateColPosition(int position) {
        colMargin = colMargin == 0 ? incompleteCol.getLeft() : colMargin;

        float leftComplete = 0;
        float leftIcomplete = 0;

        if(position != 0) {
            float left = (colMargin * 2) + (completeCol.getWidth() - getView().getWidth());
            leftComplete = left;
            leftIcomplete = left;
        }

        AnimationHelper.animateX(leftComplete, completeCol);
        AnimationHelper.animateX(leftIcomplete, incompleteCol);
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

        addTaskBtn = (Button) view.findViewById(R.id.addNewTaskBtn);

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), AddEditTaskActivity.class);

                //Intent for audio testing
                //Intent myIntent = new Intent(getActivity(), AudioActivity.class);
                startActivity(myIntent);
            }
        });

        searchInput = (EditText) view.findViewById(R.id.searchInput);
        clearBtn = (Button) view.findViewById(R.id.clearSearchBtn);
        searchBtn = (Button) view.findViewById(R.id.searchBtn);
        sortDropdown = (Spinner) view.findViewById(R.id.sortDropdown);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = searchInput.getText().toString();

                if (keyword.equals("")) {
                    Toast.makeText(view.getContext(), "Write a word to search.", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Task> tempIncomplete = new ArrayList<>();
                List<Task> tempComplete = new ArrayList<>();

                for(Task task : incompleteTask) {
                    if(task.getTitle().contains(keyword) || task.getDescription().contains(keyword)) {
                        tempIncomplete.add(task);
                    }
                }

                for(Task task : completeTask) {
                    if(task.getTitle().contains(keyword) || task.getDescription().contains(keyword)) {
                        tempComplete.add(task);
                    }
                }

                incompleteAdapter.submitList(tempIncomplete);
                completeAdapter.submitList(tempComplete);
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInput.setText("");

                incompleteAdapter.submitList(incompleteTask);
                completeAdapter.submitList(completeTask);

                sortTask();
            }
        });


        sortDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedSort = position;
                sortTask();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

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

    private void sortTask() {
        if (selectedSort == 0) {
            Comparator<Task> sortByTitle = (o1, o2) -> o1.getCreationDate().compareTo(o2.getCreationDate());
            Collections.sort(incompleteTask, sortByTitle);
            Collections.sort(completeTask, sortByTitle);
        } else if (selectedSort == 1) {
            Comparator<Task> sortByTitle = (o1, o2) -> o1.getDueDate().compareTo(o2.getDueDate());
            Collections.sort(incompleteTask, sortByTitle);
            Collections.sort(completeTask, sortByTitle);
        } else {
            Comparator<Task> sortByTitle = (o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle());
            Collections.sort(incompleteTask, sortByTitle);
            Collections.sort(completeTask, sortByTitle);
        }

        refreshAdapter();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false);
    }
}