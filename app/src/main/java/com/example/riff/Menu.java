package com.example.riff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    TextView bienvenido;
    Button nuevoEvento, cerrarSesion,btn_salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();
        bienvenido = (TextView) findViewById(R.id.bienvenido);
        cerrarSesion= (Button) findViewById(R.id.cerrarSesion);
        nuevoEvento = (Button) findViewById(R.id.nuevoEvento);
        btn_salir = findViewById(R.id.btn_salir);

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("nombre","Usuario desconocido");
        bienvenido.setText("Bienvenido "+user);

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preferences.edit().clear().apply();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();

            }
        });
        nuevoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Menu.this,"Ingresando a nuevo evento",Toast.LENGTH_LONG).show();

            }
        });

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });





    }
}