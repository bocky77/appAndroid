package com.example.appandroid;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView; // <-- Importación que solucionará el error
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class registro_gastos extends AppCompatActivity {

    private AutoCompleteTextView spinnerCategorias;
    private TextInputEditText inputDescripcion;
    private TextInputEditText inputMontoGasto;
    private TextInputEditText inputFechaGasto;
    private Button botonGuardarGasto;
    private Calendar calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_gastos);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas
        spinnerCategorias = findViewById(R.id.spinnerCategorias);
        inputDescripcion = findViewById(R.id.inputDescripcion);
        inputMontoGasto = findViewById(R.id.inputMontoGasto);
        inputFechaGasto = findViewById(R.id.inputFechaGasto);
        botonGuardarGasto = findViewById(R.id.botonGuardarGasto);
        calendario = Calendar.getInstance();

        // Categorías de gastos
        String[] categorias = {
                "Alimento / Concentrado",
                "Medicamentos / Vacunas",
                "Insumos de Ordeño",
                "Mantenimiento de Maquinaria",
                "Servicios Públicos",
                "Pago de Jornales / Nómina",
                "Transporte / Fletes"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categorias);
        spinnerCategorias.setAdapter(adapter);

        actualizarEtiquetaFecha();

        inputFechaGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCalendario();
            }
        });

        botonGuardarGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarGasto();
            }
        });
    }

    private void mostrarCalendario() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendario.set(Calendar.YEAR, year);
                calendario.set(Calendar.MONTH, month);
                calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                actualizarEtiquetaFecha();
            }
        };

        new DatePickerDialog(registro_gastos.this, dateSetListener,
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void actualizarEtiquetaFecha() {
        String formato = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato, Locale.getDefault());
        inputFechaGasto.setText(dateFormat.format(calendario.getTime()));
    }

    private void guardarGasto() {
        String categoria = spinnerCategorias.getText().toString();
        String monto = inputMontoGasto.getText().toString();
        String fecha = inputFechaGasto.getText().toString();

        if (categoria.isEmpty() || monto.isEmpty()) {
            Toast.makeText(this, "Por favor seleccione la categoría e ingrese el monto", Toast.LENGTH_SHORT).show();
        } else {
            String mensaje = "Gasto Guardado:\n" + categoria + "\nMonto: $" + monto + "\nFecha: " + fecha;
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();

            inputDescripcion.setText("");
            inputMontoGasto.setText("");
        }
    }
}