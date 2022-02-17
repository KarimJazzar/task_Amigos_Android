package com.example.task_amigos_android.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.task_amigos_android.R;
import com.example.task_amigos_android.entities.Subtask;
import com.example.task_amigos_android.helpers.DateHelper;
import com.example.task_amigos_android.model.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends BaseAdapter {
    List<Subtask> data=new ArrayList<>();
    LayoutInflater inflater;//
    Activity activity;

    //constructor
    public TaskAdapter(Context context, List<Subtask>data)
    {
        this.data=data;
        inflater=LayoutInflater.from(context);
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

            view=inflater.inflate(R.layout.subtask_view_layout,null);
            holder=new ViewHolder();
            holder.name =view.findViewById(R.id.nameSub);
            holder.status =view.findViewById(R.id.statusSub);
            holder.dueDate =view.findViewById(R.id.dueDate);
            view.setTag(holder);


        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(data.get(i).getTitle());
        if(data.get(i).getStatus()){
            holder.status.setText("Complete");
        }else{
            holder.status.setText("Incomplete");
        }
        String subDate = DateHelper.dateToString(data.get(i).getDueDate());
        holder.dueDate.setText(subDate);


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
        private TextView status;
        private TextView dueDate;
    }
}
