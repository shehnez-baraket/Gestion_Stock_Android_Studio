package com.example.projetmobile_geststock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
EditText nom,prix;
Button bt;
Helper H = new Helper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nom=findViewById(R.id.nom);
        prix = findViewById(R.id.prix);
        bt= findViewById(R.id.add);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Produit p = new Produit(nom.getText().toString(), Double.parseDouble(prix.getText().toString()));
                H.insertProduct(p);
                Intent i = new Intent(MainActivity.this, ListProduct.class);
                startActivity(i);

            }
        });
    }
}