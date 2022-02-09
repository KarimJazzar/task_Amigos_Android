package com.example.task_amigos_android.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.task_amigos_android.R;
import com.example.task_amigos_android.activities.MainActivity;
import com.example.task_amigos_android.adapter.CategoryRVAdapter;
import com.example.task_amigos_android.entities.Category;
import com.example.task_amigos_android.helpers.AnimationHelper;
import com.example.task_amigos_android.model.CategoryViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    private EditText categoryName;
    private Button selectColorBtn, closePicker, openPicker, saveCaegoryBtn;
    private SeekBar redBar, greenBar, blueBar;
    private FrameLayout createdColor;
    private LinearLayout pickerModal;
    private RecyclerView categoryRV;
    private CategoryViewModel categoryVM;
    private CategoryRVAdapter categoryAdapter = new CategoryRVAdapter();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public void updateAdapterList(List<Category> categories) {
        categoryAdapter.submitList(categories);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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

        categoryVM = new ViewModelProvider(this).get(CategoryViewModel.class);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Context context = getActivity().getApplicationContext();

        categoryRV = (RecyclerView) view.findViewById(R.id.categoryRV);
        categoryRV.setLayoutManager(new LinearLayoutManager(context));
        categoryRV.setHasFixedSize(true);
        categoryRV.setAdapter(categoryAdapter);

        categoryName = (EditText) view.findViewById(R.id.categoryNameET);

        createdColor = (FrameLayout) view.findViewById(R.id.createdColorView);
        pickerModal = (LinearLayout) view.findViewById(R.id.colorPickerVIew);

        openPicker = (Button) view.findViewById(R.id.openColorPockerBtn);
        closePicker = (Button) view.findViewById(R.id.closeColorPickerBtn);
        selectColorBtn = (Button) view.findViewById(R.id.SelectColorBtn);
        saveCaegoryBtn = (Button) view.findViewById(R.id.saveCategoryBtn);

        View.OnClickListener toggleColorPicker = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float alpha = pickerModal.getAlpha() == 0 ? 1 : 0;
                MainActivity.ignoreSwipe = alpha == 1;
                AnimationHelper.animateAlpha(alpha, pickerModal);
            }
        };

        openPicker.setOnClickListener(toggleColorPicker);
        closePicker.setOnClickListener(toggleColorPicker);

        selectColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color = Color.rgb(redBar.getProgress(),greenBar.getProgress(),blueBar.getProgress());
                openPicker.setBackgroundColor(color);
                openPicker.setTextColor(color);
                AnimationHelper.animateAlpha(0, pickerModal);
            }
        });

        saveCaegoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = categoryName.getText().toString();

                if(name.equals("")) {
                    Toast.makeText(view.getContext(), "Category name can't be empty.", Toast.LENGTH_LONG).show();
                    return;
                }

                Category newCategory = new Category();
                newCategory.setName(name);
                ColorDrawable bgColor = (ColorDrawable) openPicker.getBackground();
                newCategory.setColor(bgColor.getColor());

                categoryVM.insert(newCategory);
                Toast.makeText(view.getContext(), "Category saved", Toast.LENGTH_SHORT).show();

                categoryName.setText("");
            }
        });

        redBar = (SeekBar) view.findViewById(R.id.redValue);
        greenBar = (SeekBar) view.findViewById(R.id.greenValue);
        blueBar = (SeekBar) view.findViewById(R.id.blueValue);

        SeekBar.OnSeekBarChangeListener seeckListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    int color = Color.rgb(redBar.getProgress(),greenBar.getProgress(),blueBar.getProgress());
                    createdColor.setBackgroundColor(color);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        };

        redBar.setOnSeekBarChangeListener(seeckListener);
        greenBar.setOnSeekBarChangeListener(seeckListener);
        blueBar.setOnSeekBarChangeListener(seeckListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }
}