package com.example.projetmobile_geststock;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ListProduct extends AppCompatActivity {
    private Helper h = new Helper(ListProduct.this);
    private ListView ls;
    private Button addProductButton, sortButton;
    private SearchView searchView;
    private Spinner sortSpinner;
    private Cursor originalCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        ls = findViewById(R.id.lst);
        addProductButton = findViewById(R.id.addProductButton);
        searchView = findViewById(R.id.searchView);
        sortSpinner = findViewById(R.id.sortSpinner);
        sortButton = findViewById(R.id.sortButton);

        originalCursor = h.getAllProducts();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                ListProduct.this,
                R.layout.item,
                originalCursor,
                new String[]{originalCursor.getColumnName(0), originalCursor.getColumnName(1), originalCursor.getColumnName(2)},
                new int[]{R.id.id, R.id.nom, R.id.prix},
                1
        );

        ls.setAdapter(adapter);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.sort_options,
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(spinnerAdapter);

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortProducts();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor filteredCursor = h.searchProducts(newText);
                adapter.changeCursor(filteredCursor);
                return true;
            }
        });

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t = view.findViewById(R.id.id);
                Intent x = new Intent(ListProduct.this, DetailsProduct.class);
                x.putExtra("id", t.getText().toString());
                finish();
                startActivity(x);
            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListProduct.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sortProducts() {
        String sortBy = sortSpinner.getSelectedItem().toString();

        Cursor sortedCursor;
        switch (sortBy) {
            case "ID":
                sortedCursor = h.sortProductsById();
                break;
            case "Nom":
                sortedCursor = h.sortProductsByName();
                break;
            case "Prix":
                sortedCursor = h.sortProductsByPrice();
                break;
            default:
                sortedCursor = originalCursor;
        }

        ((SimpleCursorAdapter) ls.getAdapter()).changeCursor(sortedCursor);
    }
}
