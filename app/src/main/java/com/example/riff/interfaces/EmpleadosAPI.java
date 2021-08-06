package com.example.riff.interfaces;

import com.example.riff.models.CodigoEvento;
import com.example.riff.models.Empleado;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EmpleadosAPI {

    @GET("/login/{legajo}")
    Call<Empleado> find(@Path("legajo") String legajo);


}

