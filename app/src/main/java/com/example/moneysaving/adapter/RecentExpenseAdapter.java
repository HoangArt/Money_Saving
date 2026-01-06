package com.example.moneysaving.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneysaving.R;
import com.example.moneysaving.object.Expense;

import java.util.List;

public class RecentExpenseAdapter
        extends RecyclerView.Adapter<RecentExpenseAdapter.ViewHolder> {

    private final List<Expense> expenses;

    public RecentExpenseAdapter(List<Expense> expenses) {
        this.expenses = expenses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recent_expense, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = expenses.get(position);

        holder.tvCategory.setText(expense.getCategory());
        holder.tvNote.setText(expense.getNote());

        String amountText;
        if ("INCOME".equals(expense.getType())) {
            amountText = "+ " + formatMoney(expense.getAmount());
            holder.tvAmount.setTextColor(0xFF2ECC71); // xanh
        } else {
            amountText = "- " + formatMoney(expense.getAmount());
            holder.tvAmount.setTextColor(0xFFE74C3C); // đỏ
        }

        holder.tvAmount.setText(amountText);
        holder.tvIcon.setText(getIcon(expense.getCategory()));
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIcon, tvCategory, tvNote, tvAmount;

        ViewHolder(View itemView) {
            super(itemView);
            tvIcon = itemView.findViewById(R.id.tvIcon);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvNote = itemView.findViewById(R.id.tvNote);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }

    private String formatMoney(double value) {
        return String.format("%,.0f ₫", value);
    }

    private String getIcon(String category) {
        switch (category.toLowerCase()) {
            case "ăn uống": return "🍔";
            case "mua sắm": return "🛍️";
            case "di chuyển": return "🚗";
            case "lương": return "💰";
            default: return "💸";
        }
    }
}
