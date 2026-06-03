package com.example.appandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Inicializar los botones mediante sus IDs del layout
        Button btnLeche = findViewById(R.id.botonRegistroLeche);
        Button btnVentas = findViewById(R.id.botonRegistroVentas);
        Button btnProduccion = findViewById(R.id.botonRegistroProduccion);
        Button btnGastos = findViewById(R.id.botonRegistroGastos);

        // 2. Redirección a la actividad de registro de leche (.registro_leche)
        btnLeche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLeche = new Intent(menu.this, registro_leche.class);
                startActivity(intentLeche);
            }
        });

        // 3. Redirección a la actividad de registro de ventas (.registro_ventas)
        btnVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVentas = new Intent(menu.this, registro_ventas.class);
                startActivity(intentVentas);
            }
        });

        // 4. Redirección a la actividad de registro de producción (.registro_produccion)
        btnProduccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProduccion = new Intent(menu.this, registro_produccion.class);
                startActivity(intentProduccion);
            }
        });

        // 5. Redirección a la actividad de registro de gastos (.registro_gastos)
        btnGastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGastos = new Intent(menu.this, registro_gastos.class);
                startActivity(intentGastos);
            }
        });
    }
}