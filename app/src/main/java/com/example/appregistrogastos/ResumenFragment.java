package com.example.appregistrogastos;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ResumenFragment extends Fragment {

    private RecyclerView recyclerCategories;
    private TextView tvSummaryIngresos, tvSummaryEgresos, tvSummaryBalance, tvSummaryMonth;
    private CategorySummaryAdapter adapter;
    private List<CategorySummary> categorySummaries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resumen, container, false);

        recyclerCategories = view.findViewById(R.id.recycler_categories);
        tvSummaryIngresos = view.findViewById(R.id.tv_summary_ingresos);
        tvSummaryEgresos = view.findViewById(R.id.tv_summary_egresos);
        tvSummaryBalance = view.findViewById(R.id.tv_summary_balance);
        tvSummaryMonth = view.findViewById(R.id.tv_summary_month);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy", new Locale("es", "ES"));
        tvSummaryMonth.setText(monthFormat.format(calendar.getTime()));

        categorySummaries = getExampleCategorySummaries();
        double totalEgresos = calculateTotalEgresos(categorySummaries);
        double totalIngresos = 2500.00;
        double balance = totalIngresos - totalEgresos;

        tvSummaryIngresos.setText("S/ " + String.format("%.2f", totalIngresos));
        tvSummaryEgresos.setText("S/ " + String.format("%.2f", totalEgresos));
        tvSummaryBalance.setText("S/ " + String.format("%.2f", balance));

        adapter = new CategorySummaryAdapter(categorySummaries, totalEgresos);
        recyclerCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerCategories.setAdapter(adapter);

        return view;
    }

    private List<CategorySummary> getExampleCategorySummaries() {
        List<CategorySummary> summaries = new ArrayList<>();
        summaries.add(new CategorySummary("Comida", 150.00));
        summaries.add(new CategorySummary("Transporte", 80.00));
        summaries.add(new CategorySummary("Ocio", 120.00));
        return summaries;
    }

    private double calculateTotalEgresos(List<CategorySummary> summaries) {
        double total = 0;
        for (CategorySummary summary : summaries) {
            total += summary.getAmount();
        }
        return total;
    }
}