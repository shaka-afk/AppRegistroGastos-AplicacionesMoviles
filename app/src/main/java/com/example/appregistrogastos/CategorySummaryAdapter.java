package com.example.appregistrogastos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategorySummaryAdapter extends RecyclerView.Adapter<CategorySummaryAdapter.CategoryViewHolder> {

    private List<CategorySummary> categories;
    private double totalExpenses;

    public CategorySummaryAdapter(List<CategorySummary> categories, double totalExpenses) {
        this.categories = categories;
        this.totalExpenses = totalExpenses;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_summary, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategorySummary category = categories.get(position);
        holder.bind(category, totalExpenses);
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCategoryName;
        private TextView tvCategoryAmount;
        private TextView tvCategoryPercentage;
        private ProgressBar progressBar;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
            tvCategoryAmount = itemView.findViewById(R.id.tv_category_amount);
            tvCategoryPercentage = itemView.findViewById(R.id.tv_category_percentage);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }

        public void bind(CategorySummary category, double totalExpenses) {
            tvCategoryName.setText(category.getCategoryName());
            tvCategoryAmount.setText("S/ " + String.format("%.2f", category.getAmount()));

            int percentage = totalExpenses > 0 ? (int) ((category.getAmount() / totalExpenses) * 100) : 0;
            tvCategoryPercentage.setText(percentage + "%");
            progressBar.setProgress(percentage);
        }
    }
}

