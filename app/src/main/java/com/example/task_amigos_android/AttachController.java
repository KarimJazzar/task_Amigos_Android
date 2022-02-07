package com.example.task_amigos_android;// Created by FebinRukfan on 06-02-2022.

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class AttachController {
    public void chooseImage(Activity activity){

        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit" }; // create a menuOption Array

        // create a dialog for showing the optionsMenu

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // set the items in builder

        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(optionsMenu[i].equals("Take Photo")){

                    // Open the camera and get the photo

                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    activity.startActivityForResult(takePicture, 0);

                }
                else if(optionsMenu[i].equals("Choose from Gallery")){

                    // choose from  external storage

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activity.startActivityForResult(pickPhoto, 1);

                }
                else if (optionsMenu[i].equals("Exit")) {
                    dialogInterface.dismiss();
                }

            }
        });
        builder.show();
    }
}
