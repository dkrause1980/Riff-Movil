package com.example.riff.interfaces;

import com.example.riff.models.Evento;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EventosAPI {

    @POST("insert_evento")
    Call<Evento> postEvento(@Body Evento evento);
}
