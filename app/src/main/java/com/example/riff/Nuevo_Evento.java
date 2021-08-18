package com.example.riff;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.riff.interfaces.EventosAPI;
import com.example.riff.models.Evento;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Nuevo_Evento extends AppCompatActivity implements View.OnClickListener {

    ImageView im_tipo_evento, im_direccion, im_ubicacion, im_comment;
    private LocationManager locationManager;
    EditText pt_longitud,pt_latitud,pt_tipo_evento,pt_calle,pt_altura,pt_piso,pt_depto,pt_comentario;
    private Button bt_enviar;
    Evento evento;
    int id_evento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_evento);

        im_tipo_evento = findViewById(R.id.im_tipo_evento);
        im_direccion = findViewById(R.id.im_direccion);
        im_ubicacion = findViewById(R.id.im_ubicacion);
        im_comment = findViewById(R.id.im_comment);
        im_tipo_evento.setOnClickListener(this);
        im_direccion.setOnClickListener(this);
        im_ubicacion.setOnClickListener(this);
        im_comment.setOnClickListener(this);
        pt_latitud = findViewById(R.id.pt_latitud);
        pt_longitud = findViewById(R.id.pt_longitud);
        pt_tipo_evento = findViewById(R.id.pt_tipo_evento);
        pt_calle = findViewById(R.id.pt_calle);
        pt_altura = findViewById(R.id.pt_altura);
        pt_piso = findViewById(R.id.pt_piso);
        pt_depto = findViewById(R.id.pt_depto);
        pt_comentario = findViewById(R.id.pt_comentario);
        bt_enviar = findViewById(R.id.bt_enviar);
        bt_enviar.setOnClickListener(this);
        pt_latitud.setText("");
        pt_longitud.setText("");



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.im_tipo_evento:

                // creo el dialogo
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("TIPO DE EVENTO");


                //creo adaptador
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listaCodigos());
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String seleccion = adapter.getItem(which);
                        String[] valor = seleccion.split("-");
                        Toast.makeText(getApplicationContext(),"Seleccionó: "+valor[0],Toast.LENGTH_LONG).show();
                        pt_tipo_evento.setText(valor[1]+"-"+valor[2]);
                        id_evento = Integer.parseInt(valor[0]);
                        Log.d("id_evento",String.valueOf(id_evento));
                    }
                });
                //builder.show();
                AlertDialog dialog = builder.create();
                dialog.show();



                break;
            case R.id.im_direccion:

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Nuevo_Evento.this);
                final View vista = getLayoutInflater().inflate(R.layout.custom_dialog_direccion,null);

                builder1.setView(vista);
                builder1.setTitle("DIRECCION DEL EVENTO");
                EditText et_calle,et_altura,et_piso,et_depto;
                et_calle = vista.findViewById(R.id.et_calle);
                et_altura = vista.findViewById(R.id.et_altura);
                et_piso = vista.findViewById(R.id.et_piso);
                et_depto = vista.findViewById(R.id.et_depto);
                et_calle.requestFocus();
                builder1.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }


                });
                builder1.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog1 = builder1.create();
                dialog1.show();
                dialog1.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        boolean listo = false;
                        if(et_calle.getText().toString().isEmpty() || et_altura.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(),"Calle y Altura son campos obligatorios",Toast.LENGTH_LONG).show();
                        }else{
                            if(et_piso.getText().toString().isEmpty()) {
                                et_piso.setText("-");
                            }
                            if(et_depto.getText().toString().isEmpty()){
                                et_depto.setText("-");
                            }
                            pt_calle.setText(et_calle.getText());
                            pt_altura.setText(et_altura.getText());
                            pt_piso.setText(et_piso.getText());
                            pt_depto.setText(et_depto.getText());
                            listo = true;
                        }
                        if(listo){
                            dialog1.dismiss();
                        }

                    }
                });

                break;

            case R.id.im_ubicacion:

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                            1000
                    );
                }

                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if(locationManager!=null){

                    pt_latitud.setText(String.valueOf(location.getLatitude()));
                    pt_longitud.setText(String.valueOf(location.getLongitude()));

                }

                break;

            case R.id.im_comment:

                AlertDialog.Builder builder2 = new AlertDialog.Builder(Nuevo_Evento.this);
                builder2.setTitle("COMENTARIO");
                final EditText comment = new EditText(this);

                builder2.setView(comment);
                comment.requestFocus();
                builder2.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    pt_comentario.setText(comment.getText().toString());
                    }
                });
                builder2.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder2.show();

                break;

            case R.id.bt_enviar:

                evento = new Evento();
                Calendar calendar = Calendar.getInstance();
                String fecha_creacion = calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DATE);
                evento.setFecha_creacion(fecha_creacion);
                SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                String legajo_tecnico = preferences.getString("legajo","xxxxxx");
                evento.setLegajo_tecnico(legajo_tecnico);


                if(!pt_tipo_evento.getText().toString().isEmpty()){

                    evento.setId_tipo_falla(id_evento);

                    if (!pt_calle.getText().toString().isEmpty()){
                        evento.setCalle(pt_calle.getText().toString().trim());
                        evento.setNumero(pt_altura.getText().toString().trim());
                        evento.setPiso(pt_piso.getText().toString().trim());
                        evento.setDepto(pt_depto.getText().toString().trim());

                        if(!pt_latitud.getText().toString().equals("")){

                            double lat = Double.parseDouble(pt_latitud.getText().toString());
                            double longi = Double.parseDouble(pt_longitud.getText().toString());

                            evento.setLatitud(lat);
                            evento.setLongitud(longi);

                            if(!pt_comentario.getText().toString().isEmpty()){

                                evento.setComentario(pt_comentario.getText().toString());


                                postEv(evento);


                            }else {
                                Toast.makeText(getApplicationContext(),"Ingrese un comentario",Toast.LENGTH_LONG).show();
                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "Ingrese ubicación", Toast.LENGTH_LONG).show();
                        }

                    } else{

                        Toast.makeText(getApplicationContext(),"Ingrese dirección ",Toast.LENGTH_LONG).show();

                    }


                }else{

                    Toast.makeText(getApplicationContext(),"Ingrese tipo de evento",Toast.LENGTH_LONG).show();

                }
        }

    }



    public ArrayList<String> listaCodigos(){
        BufferedReader br = null;
        ArrayList<String> datos = new ArrayList<>();
        String linea;
        try {
            br = new BufferedReader(new FileReader("/data/user/0/com.example.riff/files/codigos.csv"));

            while ((linea = br.readLine())!=null){
                int i = 0;
                String val= linea.replace( ",","-");
                datos.add(i,val);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(datos);

        return datos;


    }

    public void postEv(Evento evento){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.231:5000/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        EventosAPI eventosAPI = retrofit.create(EventosAPI.class);
        Call<Evento> call = eventosAPI.postEvento(evento);
        call.enqueue(new Callback<Evento>() {
            @Override
            public void onResponse(Call<Evento> call, Response<Evento> response) {
            try {
                if (response.isSuccessful()) {
                    Evento ev = response.body();
                    Toast.makeText(getApplicationContext(), "EVENTO ENVIADO", Toast.LENGTH_LONG).show();
                    pt_tipo_evento.setText("");
                    pt_calle.setText("");
                    pt_altura.setText("");
                    pt_piso.setText("");
                    pt_depto.setText("");
                    pt_latitud.setText("");
                    pt_longitud.setText("");
                    pt_comentario.setText("");

                } else {
                    Toast.makeText(getApplicationContext(), "ERROR, INTENTE MAS TARDE " + response.code(), Toast.LENGTH_LONG).show();

                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "ERROR, INTENTE MAS TARDE " + e.getMessage(), Toast.LENGTH_LONG).show();

            }
            }

            @Override
            public void onFailure(Call<Evento> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"ERROR: "+t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

}