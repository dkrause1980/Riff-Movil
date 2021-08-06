package com.example.riff.interfaces;

import com.example.riff.models.CodigoEvento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CodigosAPI {

    @GET("codigos_eventos")
    Call<List<CodigoEvento>> getCodigos();


}
