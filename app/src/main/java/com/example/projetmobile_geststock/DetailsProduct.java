package com.example.projetmobile_geststock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DetailsProduct extends AppCompatActivity {
    EditText nom, prix;
    Button mod, supp;
    String id;
    Helper h = new Helper(DetailsProduct.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);;
        nom= findViewById(R.id.nom1);
        prix = findViewById(R.id.prix1);
        mod = findViewById(R.id.mod);
        supp= findViewById(R.id.supp);

        id = getIntent().getStringExtra("id");
        Produit p = h.getProductById(Integer.parseInt(id));
        nom.setText(p.getNom());
        prix.setText(String.valueOf(p.getPrix()));
        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Produit p = new Produit(Integer.parseInt(id), nom.getText().toString(),Double.parseDouble(prix.getText().toString()));
                h.updateProduct(p);
                Intent i= new Intent(DetailsProduct.this,ListProduct.class);
                startActivity(i);
            }
        });

        supp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h.deleteProduct(Integer.parseInt(id));
                //Log.d("DetailsProduct", "Produit supprimé avec succès");
                Intent i= new Intent(DetailsProduct.this,ListProduct.class);
                startActivity(i);

            }
        });
            }
}