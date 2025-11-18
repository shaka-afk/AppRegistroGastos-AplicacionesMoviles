package com.example.appregistrogastos;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TransaccionFragment extends Fragment {

    private RadioGroup radioGroupType;
    private RadioButton radioIngreso, radioEgreso;
    private EditText etAmount, etDescription;
    private Spinner spinnerCategory;
    private Button btnSelectDate, btnSaveTransaction;
    private TextView tvSelectedDate;
    private Calendar selectedDate;
    private List<String> categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaccion, container, false);

        radioGroupType = view.findViewById(R.id.radio_group_type);
        radioIngreso = view.findViewById(R.id.radio_ingreso);
        radioEgreso = view.findViewById(R.id.radio_egreso);
        etAmount = view.findViewById(R.id.et_amount);
        etDescription = view.findViewById(R.id.et_description);
        spinnerCategory = view.findViewById(R.id.spinner_category);
        btnSelectDate = view.findViewById(R.id.btn_select_date);
        btnSaveTransaction = view.findViewById(R.id.btn_save_transaction);
        tvSelectedDate = view.findViewById(R.id.tv_selected_date);

        selectedDate = Calendar.getInstance();
        updateDateDisplay();

        categories = new ArrayList<>();
        categories.add("Comida");
        categories.add("Transporte");
        categories.add("Ocio");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                categories
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        btnSelectDate.setOnClickListener(v -> showDatePicker());

        radioGroupType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_ingreso) {
                categories.clear();
                categories.add("Salario");
                categories.add("Bonificación");
                categories.add("Otros ingresos");
            } else {
                categories.clear();
                categories.add("Comida");
                categories.add("Transporte");
                categories.add("Ocio");
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getContext(),
                    android.R.layout.simple_spinner_item,
                    categories
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(adapter);
        });

        btnSaveTransaction.setOnClickListener(v -> saveTransaction());

        return view;
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    selectedDate.set(year, month, dayOfMonth);
                    updateDateDisplay();
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateDateDisplay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        tvSelectedDate.setText("Fecha: " + dateFormat.format(selectedDate.getTime()));
    }

    private void saveTransaction() {
        String amountText = etAmount.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        boolean isIncome = radioIngreso.isChecked();

        if (amountText.isEmpty()) {
            etAmount.setError("Ingrese un monto");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                etAmount.setError("El monto debe ser mayor a 0");
                return;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String date = dateFormat.format(selectedDate.getTime());

            Toast.makeText(getContext(), "Transacción guardada: " + category + " - S/ " + amount, Toast.LENGTH_SHORT).show();

            etAmount.setText("");
            etDescription.setText("");
            radioEgreso.setChecked(true);
            selectedDate = Calendar.getInstance();
            updateDateDisplay();

        } catch (NumberFormatException e) {
            etAmount.setError("Ingrese un monto válido");
        }
    }
}