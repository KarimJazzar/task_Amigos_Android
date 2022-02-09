package com.example.task_amigos_android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.task_amigos_android.R;
import com.example.task_amigos_android.entities.Category;
import com.example.task_amigos_android.model.CategoryViewModel;

public class CategoryRVAdapter extends ListAdapter<Category, CategoryRVAdapter.ViewHolder> {
    // creating a variable for on item click listener.
    private CategoryRVAdapter.OnItemClickListener listener;
    CategoryViewModel categoryVM;

    // creating a constructor class for our adapter class.
    public CategoryRVAdapter() {
        super(DIFF_CALLBACK);
    }

    // creating a call back for item of recycler view.
    private static final DiffUtil.ItemCallback<Category> DIFF_CALLBACK = new DiffUtil.ItemCallback<Category>() {
        @Override
        public boolean areItemsTheSame(Category oldItem, Category newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Category oldItem, Category newItem) {
            // below line is to check the category name
            return oldItem.getName().equals(newItem.getName());
        }
    };

    @NonNull
    @Override
    public CategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is use to inflate our layout
        // file for each item of our recycler view.
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_rv_item, parent, false);
        // ==========================
        // THIS VIEW MODEL CAN STAY HERE
        // TEMPORAL SOLUTION
        categoryVM = new ViewModelProvider((ViewModelStoreOwner)  parent.getContext()).get(CategoryViewModel.class);
        // ==========================
        return new CategoryRVAdapter.ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRVAdapter.ViewHolder holder, int position) {
        // below line of code is use to set data to
        // each item of our recycler view.
        Category model = getCategoryAt(position);
        holder.tempID = model.getId();
        holder.nameTV.setText(model.getName());
        holder.nameTV.setTextColor(model.getColor());
        holder.colorFL.setBackgroundColor(model.getColor());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "" + holder.tempID);
                categoryVM.deleteById(holder.tempID);
                Toast.makeText(view.getContext(), "Category deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // creating a method to get category modal for a specific position.
    public Category getCategoryAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // view holder class to create a variable for each view.
        TextView nameTV;
        FrameLayout colorFL;
        Button deleteBtn;
        Context context;
        int tempID;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing each view of our recycler view.
            nameTV = itemView.findViewById(R.id.categoryName);
            colorFL = itemView.findViewById(R.id.categoryColor);
            deleteBtn = itemView.findViewById(R.id.deleteCategory);

            // adding on click listener for each item of recycler view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // inside on click listener we are passing
                    // position to our item of recycler view.
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Category model);
    }
    public void setOnItemClickListener(CategoryRVAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}