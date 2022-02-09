package com.example.task_amigos_android.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.task_amigos_android.repositories.InfoRepository;
import com.example.task_amigos_android.databinding.FragmentInfoBinding;
import com.example.task_amigos_android.helpers.DateHelper;


public class InfoFragment extends Fragment {


    public String TAG = this.getClass().getName();
    private FragmentInfoBinding binding;
    DateHelper dateHelper;
    InfoRepository infoRepository;
    EditText taskName,taskDesc;
    final String[] statusStr = {"Incomplete", "Complete"};
    final String[] catStr = {"Work", "School", "Shopping", "Groceries"};


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
        infoRepository = new InfoRepository();


        binding = FragmentInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, statusStr);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.statSpinner.setAdapter(adp1);
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

     ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, catStr);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.catSpinner.setAdapter(adp2);
        binding.catSpinner.setSelection(0);

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

        binding.txtCreationDate.setText(dateHelper.todaysDate());
        binding.txtDuedate.setText(dateHelper.todaysDate());
        binding.txtDuedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                infoRepository.selectDate(binding,getContext());
            }
        });

        //taskName = view.findViewById(R.id.edtName);
        //taskDesc = view.findViewById(R.id.edtDesc);

        /*
        if(tName != ""){
            taskName.setText(tName);
            taskDesc.setText(tDesc);
            binding.catSpinner.setSelection(tCategory);
            if(stat == false){
                binding.statSpinner.setSelection(0);
            }else{
                binding.statSpinner.setSelection(1);
            }
        }
         */
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
