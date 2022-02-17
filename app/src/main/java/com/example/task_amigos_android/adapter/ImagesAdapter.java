package com.example.task_amigos_android.adapter;// Created by FebinRukfan on 07-02-2022.

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_amigos_android.R;
import com.example.task_amigos_android.helpers.DateHelper;
import com.example.task_amigos_android.model.ImagesModel;

import java.io.File;
import java.util.ArrayList;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder>
{

    private Context context;
    private ArrayList<String> imagesAL;
    private LayoutInflater inflater;

    public ImagesAdapter(Context context, ArrayList<String> imagesModels){
        this.inflater=LayoutInflater.from(context);
        this.imagesAL=imagesModels;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.images_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

              if(!imagesAL.get(position).equals(" ")) {
                  holder.txtName.setText("Name: "+ imagesAL.get(position)+".jpg");
                  File path = context.getDir("imageDir", Context.MODE_PRIVATE);
                  Bitmap mBitmap = BitmapFactory.decodeFile(path.getPath()+"/"+ imagesAL.get(position)+".jpg");

                  holder.imgImage.setImageBitmap(mBitmap);

              }else {
                  holder.imgDel.setVisibility(View.GONE);
              }

        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagesAL.remove(position);
                ImagesAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagesAL.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView txtName;
        ImageView imgImage,imgDel;
        ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtImg);
            imgImage = itemView.findViewById(R.id.imgImage);
            imgDel = itemView.findViewById(R.id.imgDel);
        }


    }



}
