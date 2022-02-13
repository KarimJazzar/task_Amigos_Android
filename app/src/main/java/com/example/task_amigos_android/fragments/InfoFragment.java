package com.example.task_amigos_android.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task_amigos_android.entities.Category;
import com.example.task_amigos_android.entities.Task;
import com.example.task_amigos_android.helpers.CategoryHelper;
import com.example.task_amigos_android.model.CategoryViewModel;
import com.example.task_amigos_android.model.TaskViewModel;
import com.example.task_amigos_android.repositories.InfoRepository;
import com.example.task_amigos_android.databinding.FragmentInfoBinding;
import com.example.task_amigos_android.helpers.DateHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class InfoFragment extends Fragment {
    public String TAG = this.getClass().getName();
    private FragmentInfoBinding binding;
    private InfoRepository infoRepository;
    private EditText taskName,taskDesc;
    private Button savebtn, deleteBtn;
    private List<Category> categories;
    private ArrayAdapter<String> statusAdapter;
    private ArrayAdapter<String> categoryAdapter;
    private List<String> catStr = new ArrayList<>();
    private boolean isEditMode = false;
    private TaskViewModel taskVM;
    private Task selectedTask = new Task();
    private final String[] statusStr = {"Incomplete", "Complete"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskVM = new ViewModelProvider(this).get(TaskViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle("Add Task");
        infoRepository = new InfoRepository();


        binding = FragmentInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        statusAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, statusStr);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.statSpinner.setAdapter(statusAdapter);
        binding.statSpinner.setSelection(0);

        binding.statSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
              binding.statSpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        categoryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, catStr);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.catSpinner.setAdapter(categoryAdapter);
        binding.catSpinner.setSelection(0);
        setCategoryList(CategoryHelper.categories);

        binding.catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
              binding.catSpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        binding.txtCreationDate.setText(DateHelper.getCurrentDateAsString());
        binding.txtDuedate.setText(DateHelper.getCurrentDateAsString());
        binding.txtDuedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                infoRepository.selectDate(binding,getContext());
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask(view);
            }
        });

        if (!isEditMode) {
            binding.btnDelete.setAlpha(0);
            binding.btnDelete.setEnabled(false);
            binding.statSpinner.setEnabled(false);
        } else {
            binding.edtName.setText(selectedTask.getTitle());
            binding.edtDesc.setText(selectedTask.getDescription());
            binding.catSpinner.setSelection(CategoryHelper.getCategoryIndex(selectedTask.getCategory()));
            binding.statSpinner.setSelection(selectedTask.getStatus() ? 1 : 0);
            binding.txtDuedate.setText(DateHelper.dateToString(selectedTask.getDueDate()));
            binding.txtCreationDate.setText(DateHelper.dateToString(selectedTask.getCreationDate()));
        }

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Context context = getActivity().getApplicationContext();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void saveTask(View view) {
        String title = binding.edtName.getText().toString();
        String description = binding.edtDesc.getText().toString();
        int category = CategoryHelper.getCategoryId(binding.catSpinner.getSelectedItem().toString());
        boolean isComplete = false;
        Date duDate = new Date();

        if (title.equals("")) {
            Toast.makeText(view.getContext(), "Task title can't be empty.", Toast.LENGTH_LONG).show();
            return;
        }

        if (description.equals("")) {
            Toast.makeText(view.getContext(), "Task description can't be empty.", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            duDate = DateHelper.stringToDate(binding.txtDuedate.getText().toString());
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Task due date is not valid.", Toast.LENGTH_LONG).show();
            return;
        }

        Task tempTask = new Task();
        tempTask.setTitle(title);
        tempTask.setCategory(category);
        tempTask.setDescription(description);
        tempTask.setDueDate(duDate);
        tempTask.setCreationDate(DateHelper.getCurrentDate());
        //tempTask.setImages();
        //tempTask.setAudios();
        tempTask.setStatus(isComplete);

        if (isEditMode) {
            Toast.makeText(view.getContext(), "Task updated.", Toast.LENGTH_SHORT).show();
        } else {
            taskVM.insert(tempTask);
            Toast.makeText(view.getContext(), "Task created.", Toast.LENGTH_SHORT).show();
            clearInputs();
        }
    }

    private void clearInputs() {
        binding.edtName.setText("");
        binding.edtDesc.setText("");
    }

    public void setCategoryList(List<Category> categories) {
        this.categories = categories;

        if(categoryAdapter == null) {
            return;
        }

        categoryAdapter.clear();
        categoryAdapter.add("None");

        for (Category category : categories) {
            categoryAdapter.add(category.getName());
        }

        categoryAdapter.notifyDataSetChanged();
    }

    public void setSelectedTask(Task task) {
        isEditMode = true;
        selectedTask = task;
    }
}
