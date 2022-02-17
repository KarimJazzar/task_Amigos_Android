package com.example.task_amigos_android.fragments;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.task_amigos_android.activities.AddEditTaskActivity;
import com.example.task_amigos_android.adapter.ImagesAdapter;
import com.example.task_amigos_android.entities.Task;
import com.example.task_amigos_android.repositories.AttachRespository;
import com.example.task_amigos_android.databinding.FragmentAttachBinding;
import com.example.task_amigos_android.helpers.PermissionsHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class AttachFragment extends Fragment {

   FragmentAttachBinding fragmentAttachBinding;
   PermissionsHelper permissionsHelper;
   AttachRespository attachRespository;
   String TAG = this.getClass().getName();
   ArrayList<String> tempimages ;
   private boolean isEditMode = false;

   ActivityResultLauncher<Intent> activityResultCamera;
   ActivityResultLauncher<String> activityResultGallery;
    private Task selectedTask = new Task();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tempimages = new ArrayList();


        activityResultCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {


                    Bundle extras = result.getData().getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                     tempimages.add(attachRespository.saveImage(imageBitmap,getActivity()));
                    ImagesAdapter adapter = new ImagesAdapter(getContext(),tempimages);
                    fragmentAttachBinding.rvImages.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                    fragmentAttachBinding.rvImages.setAdapter(adapter);

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("images", String.valueOf(tempimages));
                    myEdit.apply();


                }
            }
        });

        activityResultGallery = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                Uri imageUri = result;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

                    tempimages.add(attachRespository.saveImage(bitmap,getActivity()));
                    ImagesAdapter adapter = new ImagesAdapter(getContext(),tempimages);
                    fragmentAttachBinding.rvImages.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                    fragmentAttachBinding.rvImages.setAdapter(adapter);

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("images", String.valueOf(tempimages));
                    myEdit.apply();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAttachBinding = FragmentAttachBinding.inflate(inflater, container, false);
        View view = fragmentAttachBinding.getRoot();

        if(isEditMode){

            for(int i=0;i<selectedTask.getImages().size();i++){
                tempimages.add(selectedTask.getImages().get(i).replace("[","").replace("]","").replace(" ",""));
                Log.v(TAG," huy "+ selectedTask.getImages().get(i).replace("[","").replace("]","").replace(" ",""));

            }

            ImagesAdapter adapter = new ImagesAdapter(getContext(),tempimages);
            fragmentAttachBinding.rvImages.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            fragmentAttachBinding.rvImages.setAdapter(adapter);

        }else {
            ArrayList<String> def = new ArrayList<>();

            for(int i=0;i<3;i++){
                def.add(" ");

            }

            ImagesAdapter adapter = new ImagesAdapter(getContext(),def);
            fragmentAttachBinding.rvImages.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            fragmentAttachBinding.rvImages.setAdapter(adapter);
        }
        permissionsHelper = new PermissionsHelper();
        attachRespository = new AttachRespository();

        permissionsHelper.checkAndRequestPermissions(getActivity());

        fragmentAttachBinding.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permissionsHelper.checkAndRequestPermissions(getActivity())){


                    final CharSequence[] optionsMenu = {"Take Photo",  "Choose from Gallery","Exit" }; // create a menuOption Array
                    // create a dialog for showing the optionsMenu
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    // set the items in builder

                    builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if(optionsMenu[i].equals("Take Photo")){

                                Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                                activityResultCamera.launch(takePicture);

                            }
                            else if(optionsMenu[i].equals("Choose from Gallery")){

                                activityResultGallery.launch("image/*");

                            }
                            else if (optionsMenu[i].equals("Exit")) {
                                dialogInterface.dismiss();
                            }

                        }
                    });
                    builder.show();
                }
            }
        });



        return view;
    }


    public void setSelectedTask(Task task) {
        isEditMode = true;
        selectedTask = task;
    }
}