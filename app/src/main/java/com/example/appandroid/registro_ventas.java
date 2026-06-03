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

public class registro_ventas extends AppCompatActivity {

    private AutoCompleteTextView spinnerProductos;
    private TextInputEditText inputCantidad;
    private TextInputEditText inputMonto;
    private TextInputEditText inputFechaVenta;
    private Button botonGuardarVenta;
    private Calendar calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_ventas);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas
        spinnerProductos = findViewById(R.id.spinnerProductos);
        inputCantidad = findViewById(R.id.inputCantidad);
        inputMonto = findViewById(R.id.inputMonto);
        inputFechaVenta = findViewById(R.id.inputFechaVenta);
        botonGuardarVenta = findViewById(R.id.botonGuardarVenta);
        calendario = Calendar.getInstance();

        // Configurar productos lácteos en el desplegable
        String[] productos = {"Leche Entera (Litro)", "Queso Campesino (Kg)", "Queso Costeño (Kg)", "Yogur Moralba (Litro)", "Crema de Leche"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, productos);
        spinnerProductos.setAdapter(adapter);

        // Cargar fecha actual por defecto
        actualizarEtiquetaFecha();

        // Control del calendario emergente
        inputFechaVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCalendario();
            }
        });

        // Guardar la venta
        botonGuardarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarVenta();
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

        // CORREGIDO: De RegistroVentasActivity.this a registro_ventas.this
        new DatePickerDialog(registro_ventas.this, dateSetListener,
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void actualizarEtiquetaFecha() {
        String formato = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato, Locale.getDefault());
        inputFechaVenta.setText(dateFormat.format(calendario.getTime()));
    }

    private void guardarVenta() {
        String producto = spinnerProductos.getText().toString();
        String cantidad = inputCantidad.getText().toString();
        String monto = inputMonto.getText().toString();
        String fecha = inputFechaVenta.getText().toString();

        if (producto.isEmpty() || cantidad.isEmpty() || monto.isEmpty()) {
            Toast.makeText(this, "Por favor llene todos los datos de la venta", Toast.LENGTH_SHORT).show();
        } else {
            // Mensaje temporal de éxito
            String mensaje = "Venta Registrada:\n" + cantidad + " x " + producto + "\nTotal: $" + monto + "\nFecha: " + fecha;
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();

            // Aquí puedes limpiar los campos después de guardar si lo deseas:
            inputCantidad.setText("");
            inputMonto.setText("");
        }
    }
}