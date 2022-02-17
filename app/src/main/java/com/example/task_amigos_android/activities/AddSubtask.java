package com.example.task_amigos_android.activities;

import static com.example.task_amigos_android.fragments.InfoFragment.taskSelected;
import static com.example.task_amigos_android.fragments.SubTaskFragment.editSubtask;
import static com.example.task_amigos_android.fragments.SubTaskFragment.selectedSubtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task_amigos_android.R;
import com.example.task_amigos_android.databinding.FragmentInfoBinding;
import com.example.task_amigos_android.entities.Category;
import com.example.task_amigos_android.entities.Subtask;
import com.example.task_amigos_android.entities.Task;
import com.example.task_amigos_android.helpers.CategoryHelper;
import com.example.task_amigos_android.helpers.DateHelper;
import com.example.task_amigos_android.model.SubtaskViewModel;
import com.example.task_amigos_android.model.TaskViewModel;
import com.example.task_amigos_android.repositories.InfoRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddSubtask extends AppCompatActivity {

    private FragmentInfoBinding binding;
    private InfoRepository infoRepository;
    private EditText taskName,taskDesc;
    private Button savebtn, deleteBtn;
    private List<Category> categories;
    private ArrayAdapter<String> statusAdapter;
    private ArrayAdapter<String> categoryAdapter;
    private List<String> catStr = new ArrayList<>();
    private boolean isEditMode = false;
    private SubtaskViewModel taskVM;
    private Subtask selectedTask = new Subtask();
    private final String[] statusStr = {"Incomplete", "Complete"};
    private Spinner catSpin,statSpin;
    private TextView dueD,createdD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subtask);
        taskName = findViewById(R.id.edtName2);
        taskDesc = findViewById(R.id.edtDesc2);
        catSpin = findViewById(R.id.catSpinner2);
        statSpin = findViewById(R.id.statSpinner2);
        dueD = findViewById(R.id.txtDuedate2);
        createdD = findViewById(R.id.txtCreationDate2);
        savebtn = findViewById(R.id.btnSave2);
        deleteBtn = findViewById(R.id.btnDelete2);
        taskVM = new ViewModelProvider(this).get(SubtaskViewModel.class);



        setTitle("Add Subtask Task");
        infoRepository = new InfoRepository();

        statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusStr);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        statSpin.setAdapter(statusAdapter);
        statSpin.setSelection(0);

        statSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                statSpin.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, catStr);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpin.setAdapter(categoryAdapter);
        catSpin.setSelection(0);
        setCategoryList(CategoryHelper.categories);

        catSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                catSpin.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        catSpin.setVisibility(View.INVISIBLE);

        createdD.setText(DateHelper.getCurrentDateAsString());
        dueD.setText(DateHelper.getCurrentDateAsString());
        dueD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoRepository.selectDate(binding,view.getContext());
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask(view);
                finish();
            }
        });

        if (!editSubtask) {
            deleteBtn.setAlpha(0);
            deleteBtn.setEnabled(false);
            statSpin.setEnabled(false);
        } else {
            taskName.setText(selectedSubtask.getTitle());
            taskDesc.setText(selectedSubtask.getDescription());
            //catSpin.setSelection(CategoryHelper.getCategoryIndex(selectedTask.getCategory()));
            statSpin.setSelection(selectedSubtask.getStatus() ? 1 : 0);
            dueD.setText(DateHelper.dateToString(selectedSubtask.getDueDate()));
            createdD.setText(DateHelper.dateToString(selectedSubtask.getCreationDate()));
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTask(view);
                finish();
            }
        });
    }

    private void deleteTask(View view) {
        clearInputs();
        taskVM.delete(selectedSubtask);
        Toast.makeText(view.getContext(), "Task deleted.", Toast.LENGTH_SHORT).show();
    }

    private void saveTask(View view) {
        String title = taskName.getText().toString();
        String description = taskDesc.getText().toString();
        //int category = CategoryHelper.getCategoryId(binding.catSpinner.getSelectedItem().toString());
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
            duDate = DateHelper.stringToDate(dueD.getText().toString());
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Task due date is not valid.", Toast.LENGTH_LONG).show();
            return;
        }

        Subtask tempTask = new Subtask();
        tempTask.setTitle(title);
        //tempTask.setCategory(category);
        tempTask.setDescription(description);
        tempTask.setDueDate(duDate);
        tempTask.setCreationDate(DateHelper.getCurrentDate());
        if(statSpin.getSelectedItem().toString() == "Incomplete"){
            tempTask.setStatus(false);
        }else{
            tempTask.setStatus(true);
        }

        if (editSubtask) {
            tempTask.setId(selectedSubtask.getId());
            if(taskSelected != null){
                tempTask.setTaskId(taskSelected.getId());
            }else{
                tempTask.setTaskId(0);
            }
            taskVM.update(tempTask);
            Toast.makeText(view.getContext(), "Subtask updated.", Toast.LENGTH_SHORT).show();
        } else {
            if(taskSelected != null){
                tempTask.setTaskId(taskSelected.getId());
            }

            taskVM.insert(tempTask);
            Toast.makeText(view.getContext(), "Subtask created.", Toast.LENGTH_SHORT).show();
            clearInputs();
        }
    }

    private void clearInputs() {
        taskName.setText("");
        taskDesc.setText("");
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

}