package com.creativelabs.myshopping.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.creativelabs.myshopping.ProductsActivity;
import com.creativelabs.myshopping.R;
import com.creativelabs.myshopping.entity.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoriesAdapter  extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>{

    private List<Category> categories = new ArrayList<>();

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_item, parent, false);
        return new CategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {

        Category category = categories.get(position);
        holder.tvCategoryName.setText(category.getName());
        if (!category.getImage().isEmpty()) {
            Picasso.get()
                    .load(category.getImage())
                    .error(R.drawable.ic_baseline_shopping_bag_24)
                    .into(holder.civCategoryImage);
        }

        holder.clCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent categoryIntent = new Intent(view.getContext(), ProductsActivity.class);
                categoryIntent.putExtra("CATEGORY_ID", category.getId());
                view.getContext().startActivity(categoryIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civCategoryImage;
        TextView tvCategoryName;
        ConstraintLayout clCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            civCategoryImage = itemView.findViewById(R.id.civCategoryImage);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            clCategory = itemView.findViewById(R.id.clCategory);
        }
    }
}
