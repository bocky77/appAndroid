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

public class RegistroLecheActivity extends AppCompatActivity {

    private AutoCompleteTextView spinnerProveedores;
    private TextInputEditText inputLitros;
    private TextInputEditText inputFecha;
    private Button botonGuardar;
    private Calendar calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_leche);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Inicializar vistas
        spinnerProveedores = findViewById(R.id.spinnerProveedores);
        inputLitros = findViewById(R.id.inputLitros);
        inputFecha = findViewById(R.id.inputFecha);
        botonGuardar = findViewById(R.id.botonGuardarRegistro);
        calendario = Calendar.getInstance();

        // 2. Configurar el menú desplegable de Proveedores (Datos de prueba)
        String[] proveedores = {"Proveedor Juan", "Finca El Recuerdo", "Lácteos Doña María", "Ganadería Central"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, proveedores);
        spinnerProveedores.setAdapter(adapter);

        // 3. Asignar la fecha local actual por defecto al campo de texto
        actualizarEtiquetaFecha();

        // 4. Listener para abrir el calendario cuando se toque el campo de fecha
        inputFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCalendario();
            }
        });

        // 5. Acción del botón Guardar
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarInformacion();
            }
        });
    }

    // Muestra el DatePickerDialog configurado con la fecha que tenga actualmente el objeto calendario
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

        new DatePickerDialog(RegistroLecheActivity.this, dateSetListener,
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)).show();
    }

    // Formatea la fecha y la coloca en el Input
    private void actualizarEtiquetaFecha() {
        String formato = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato, Locale.getDefault());
        inputFecha.setText(dateFormat.format(calendario.getTime()));
    }

    // Validación simple para procesar el registro
    private void guardarInformacion() {
        String proveedor = spinnerProveedores.getText().toString();
        String litros = inputLitros.getText().toString();
        String fecha = inputFecha.getText().toString();

        if (proveedor.isEmpty() || litros.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            // Aquí puedes conectar posteriormente tu patrón DAO / Base de datos MySQL
            Toast.makeText(this, "Registro Exitoso:\n" + proveedor + " - " + litros + "L - " + fecha, Toast.LENGTH_LONG).show();
        }
    }
}