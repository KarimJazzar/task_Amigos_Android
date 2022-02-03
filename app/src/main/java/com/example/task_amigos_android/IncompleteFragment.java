package com.example.task_amigos_android;

import static com.example.task_amigos_android.MainActivity.incompleteTaskModels;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Locale;

public class IncompleteFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IncompleteFragment() {
        // Required empty public constructor
    }

    public static IncompleteFragment newInstance(String param1, String param2) {
        IncompleteFragment fragment = new IncompleteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ListView incompleteT;
    SearchView searchTask;
    Button all,work,school,shopping,groceries;
    private String selectedFilter = "all";
    private String currentFilterText = "";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        incompleteT = (ListView) view.findViewById(R.id.incompleteTasks);
        TaskAdapter ia = new TaskAdapter(getContext(), incompleteTaskModels);
        ia.notifyDataSetChanged();
        incompleteT.setAdapter(ia);

        searchTask = (SearchView) view.findViewById(R.id.incompleteListSearchView);
        searchTask.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<TaskModel> searchedTasks = new ArrayList<TaskModel>();
                currentFilterText = newText;

                for(TaskModel task: incompleteTaskModels){
                    if(task.getName().toLowerCase().contains(newText.toLowerCase())){
                        searchedTasks.add(task);
                    }
                }
                TaskAdapter ia = new TaskAdapter(getContext(), searchedTasks);
                incompleteT.setAdapter(ia);

                return false;
            }
        });

        all = (Button) view.findViewById(R.id.allFilter);
        work = (Button) view.findViewById(R.id.workFilter);
        school = (Button) view.findViewById(R.id.schoolFilter);
        shopping = (Button) view.findViewById(R.id.shoppingFilter);
        groceries = (Button) view.findViewById(R.id.groceriesFilter);

        all.setOnClickListener(this);
        work.setOnClickListener(this);
        school.setOnClickListener(this);
        shopping.setOnClickListener(this);
        groceries.setOnClickListener(this);

    }

    private void filterList(String category)
    {
        selectedFilter = category;

        ArrayList<TaskModel> filteredTasks = new ArrayList<TaskModel>();

        for(TaskModel task: incompleteTaskModels)
        {
            if(task.getCategory().toLowerCase().contains(category)){
                if(currentFilterText == "")
                {
                    filteredTasks.add(task);
                }
                else
                {
                    if(task.getName().toLowerCase().contains(currentFilterText.toLowerCase()))
                    {
                        filteredTasks.add(task);

                    }

                }
            }

        }

        TaskAdapter ia = new TaskAdapter(getContext(), filteredTasks);
        incompleteT.setAdapter(ia);
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
        return inflater.inflate(R.layout.fragment_incomplete, container, false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allFilter:
                searchTask.setQuery("",false);
                searchTask.clearFocus();
                TaskAdapter ia = new TaskAdapter(getContext(), incompleteTaskModels);
                incompleteT.setAdapter(ia);
                break;
            case R.id.workFilter:
                filterList("work");
                break;
            case R.id.shoppingFilter:
                filterList("shopping");
                break;
            case R.id.schoolFilter:
                filterList("school");
                break;
            case R.id.groceriesFilter:
                filterList("groceries");
                 break;
            default:
                break;
        }
    }
}