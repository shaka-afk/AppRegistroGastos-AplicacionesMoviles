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

public class HomeFragment extends Fragment {

    private RecyclerView recyclerTransactions;
    private TextView tvTotalIngresos, tvTotalEgresos, tvBalance, tvCurrentMonth;
    private TransactionAdapter adapter;
    private List<Transaction> transactions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerTransactions = view.findViewById(R.id.recycler_transactions);
        tvTotalIngresos = view.findViewById(R.id.tv_total_ingresos);
        tvTotalEgresos = view.findViewById(R.id.tv_total_egresos);
        tvBalance = view.findViewById(R.id.tv_balance);
        tvCurrentMonth = view.findViewById(R.id.tv_current_month);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy", new Locale("es", "ES"));
        tvCurrentMonth.setText(monthFormat.format(calendar.getTime()));

        transactions = getExampleTransactions();
        adapter = new TransactionAdapter(transactions);
        recyclerTransactions.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerTransactions.setAdapter(adapter);

        updateSummary();

        return view;
    }
    //Método para obtener una lista de transacciones de ejemplo
    private List<Transaction> getExampleTransactions() {
        List<Transaction> exampleList = new ArrayList<>();
        exampleList.add(new Transaction("Comida", "Almuerzo en restaurante", 25.50, "2025-11-17", false));
        exampleList.add(new Transaction("Transporte", "Taxi al trabajo", 15.00, "2025-11-17", false));
        exampleList.add(new Transaction("Ocio", "Cine con amigos", 30.00, "2025-11-16", false));
        exampleList.add(new Transaction("Ingreso", "Salario mensual", 2500.00, "2025-11-15", true));
        exampleList.add(new Transaction("Comida", "Desayuno", 8.50, "2025-11-15", false));
        exampleList.add(new Transaction("Transporte", "Pasaje de bus", 2.50, "2025-11-14", false));
        return exampleList;
    }

    private void updateSummary() {
        double totalIngresos = 0;
        double totalEgresos = 0;

        for (Transaction transaction : transactions) {
            if (transaction.isIncome()) {
                totalIngresos += transaction.getAmount();
            } else {
                totalEgresos += transaction.getAmount();
            }
        }

        double balance = totalIngresos - totalEgresos;

        tvTotalIngresos.setText("S/ " + String.format("%.2f", totalIngresos));
        tvTotalEgresos.setText("S/ " + String.format("%.2f", totalEgresos));
        tvBalance.setText("S/ " + String.format("%.2f", balance));
    }
}