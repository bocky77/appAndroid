package com.example.appandroid;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class registro_produccion extends AppCompatActivity {

    private AutoCompleteTextView spinnerProductosProcesados;
    private TextInputEditText inputLitrosUsados;
    private TextInputEditText inputCantidadObtenida;
    private TextInputEditText inputFechaProduccion;
    private Button botonGuardarProduccion;
    private Calendar calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_produccion);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas
        spinnerProductosProcesados = findViewById(R.id.spinnerProductosProcesados);
        inputLitrosUsados = findViewById(R.id.inputLitrosUsados);
        inputCantidadObtenida = findViewById(R.id.inputCantidadObtenida);
        inputFechaProduccion = findViewById(R.id.inputFechaProduccion);
        botonGuardarProduccion = findViewById(R.id.botonGuardarProduccion);
        calendario = Calendar.getInstance();

        // Configurar opciones de productos derivados en el menú desplegable
        String[] derivados = {"Queso Campesino", "Queso Costeño", "Yogur de Fresa", "Yogur de Melocotón", "Mantequilla", "Cuajada"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, derivados);
        spinnerProductosProcesados.setAdapter(adapter);

        // Colocar la fecha actual por defecto
        actualizarEtiquetaFecha();

        // Abrir el selector de calendario al presionar sobre la fecha
        inputFechaProduccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCalendario();
            }
        });

        // Evento del botón para almacenar la información
        botonGuardarProduccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarProduccion();
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

        // CORREGIDO: De RegistroProduccionActivity.this a registro_produccion.this
        new DatePickerDialog(registro_produccion.this, dateSetListener,
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void actualizarEtiquetaFecha() {
        String formato = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato, Locale.getDefault());
        inputFechaProduccion.setText(dateFormat.format(calendario.getTime()));
    }

    private void guardarProduccion() {
        String producto = spinnerProductosProcesados.getText().toString();
        String lecheUsada = inputLitrosUsados.getText().toString();
        String productoObtenido = inputCantidadObtenida.getText().toString();
        String fecha = inputFechaProduccion.getText().toString();

        if (producto.isEmpty() || lecheUsada.isEmpty() || productoObtenido.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los datos de producción", Toast.LENGTH_SHORT).show();
        } else {
            // Mensaje de éxito informando el rendimiento del proceso
            String mensaje = "Producción Registrada:\n" + producto + "\nLeche usada: " + lecheUsada + " L\nResultado: " + productoObtenido + "\nFecha: " + fecha;
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();

            // Limpiar campos de texto para un nuevo registro
            inputLitrosUsados.setText("");
            inputCantidadObtenida.setText("");
        }
    }
}