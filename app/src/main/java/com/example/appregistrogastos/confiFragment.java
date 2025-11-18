package com.example.appregistrogastos;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class confiFragment extends Fragment {

    private RecyclerView recyclerCategories;
    private EditText etNewCategory;
    private Button btnAddCategory;
    private CategoryAdapter adapter;
    private List<String> categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confi, container, false);

        recyclerCategories = view.findViewById(R.id.recycler_categories);
        etNewCategory = view.findViewById(R.id.et_new_category);
        btnAddCategory = view.findViewById(R.id.btn_add_category);

        categories = getExampleCategories();
        adapter = new CategoryAdapter(categories, position -> {
            categories.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, categories.size());
            Toast.makeText(getContext(), "Categoría eliminada", Toast.LENGTH_SHORT).show();
        });

        recyclerCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerCategories.setAdapter(adapter);

        btnAddCategory.setOnClickListener(v -> addCategory());

        return view;
    }

    private List<String> getExampleCategories() {
        List<String> exampleList = new ArrayList<>();
        exampleList.add("Comida");
        exampleList.add("Transporte");
        exampleList.add("Ocio");
        return exampleList;
    }

    private void addCategory() {
        String categoryName = etNewCategory.getText().toString().trim();
        if (categoryName.isEmpty()) {
            etNewCategory.setError("Ingrese un nombre de categoría");
            return;
        }

        if (categories.contains(categoryName)) {
            etNewCategory.setError("Esta categoría ya existe");
            return;
        }

        categories.add(categoryName);
        adapter.notifyItemInserted(categories.size() - 1);
        etNewCategory.setText("");
        Toast.makeText(getContext(), "Categoría agregada: " + categoryName, Toast.LENGTH_SHORT).show();
    }
}