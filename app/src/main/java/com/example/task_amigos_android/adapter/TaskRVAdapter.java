package com.example.task_amigos_android.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_amigos_android.R;
import com.example.task_amigos_android.activities.AddEditTaskActivity;
import com.example.task_amigos_android.activities.MainActivity;
import com.example.task_amigos_android.entities.Category;
import com.example.task_amigos_android.entities.Task;
import com.example.task_amigos_android.helpers.CategoryHelper;
import com.example.task_amigos_android.helpers.DateHelper;

public class TaskRVAdapter extends ListAdapter<Task, TaskRVAdapter.ViewHolder> {

    public static Task taskSelected;
    // creating a variable for on item click listener.
    private OnItemClickListener listener;

    // creating a constructor class for our adapter class.
    public TaskRVAdapter() {
        super(DIFF_CALLBACK);
    }

    // creating a call back for item of recycler view.
    private static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK = new DiffUtil.ItemCallback<Task>() {
        @Override
        public boolean areItemsTheSame(Task oldItem, Task newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Task oldItem, Task newItem) {
            // below line is to check the task title
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is use to inflate our layout
        // file for each item of our recycler view.
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_rv_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // below line of code is use to set data to
        // each item of our recycler view.
        Task model = getTaskAt(position);
        holder.titleTV.setText(model.getTitle());
        holder.dueDateTV.setText(DateHelper.dateToString(model.getDueDate()));
        Category category = CategoryHelper.getCategory(model.getCategory());
        holder.colorFL.setBackgroundColor(category.getColor());
        holder.categoryTV.setText(category.getName());
        holder.categoryTV.setTextColor(category.getColor());
    }

    // creating a method to get task modal for a specific position.
    public Task getTaskAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // view holder class to create a variable for each view.
        TextView titleTV;
        TextView categoryTV;
        TextView dueDateTV;
        FrameLayout colorFL;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing each view of our recycler view.
            titleTV = itemView.findViewById(R.id.taskTitle);
            categoryTV = itemView.findViewById(R.id.taskCategory);
            dueDateTV = itemView.findViewById(R.id.taskDuedate);
            colorFL = itemView.findViewById(R.id.taskColor);

            // adding on click listener for each item of recycler view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // inside on click listener we are passing
                    // position to our item of recycler view.
                    int position = getAdapterPosition();
                    Context context = v.getContext();

                    Intent intent = new Intent(context, AddEditTaskActivity.class);
                    intent.putExtra("Task", getTaskAt(position));
                    taskSelected = getTaskAt(position);
                    context.startActivity(intent);

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Task model);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
