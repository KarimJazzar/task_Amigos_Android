package com.example.task_amigos_android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.task_amigos_android.R;
import com.example.task_amigos_android.model.TaskModel;
import com.example.task_amigos_android.ui.AddEditTaskActivity;

import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {
    ArrayList<TaskModel> data=new ArrayList<>();
    LayoutInflater inflater;//
    Activity activity;

    //constructor
    public TaskAdapter(Context context, ArrayList<TaskModel>data,Activity a)
    {
        this.data=data;
        inflater=LayoutInflater.from(context);
        activity = a;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null)
        {
            view=inflater.inflate(R.layout.task_view_layout,null);
            holder=new ViewHolder();
            holder.name =view.findViewById(R.id.name);
            holder.category =view.findViewById(R.id.category);
            holder.dueDate =view.findViewById(R.id.dueDate);
            view.setTag(holder);

        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(data.get(i).getName());
        holder.category.setText(data.get(i).getCategory());
        holder.dueDate.setText(data.get(i).getDueDate().toString());

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                activity.startActivity(new Intent(activity, AddEditTaskActivity.class));
//            }
//        });
        return view;
    }
    static class ViewHolder{
        private TextView name;
        private TextView category;
        private TextView dueDate;
    }
}
