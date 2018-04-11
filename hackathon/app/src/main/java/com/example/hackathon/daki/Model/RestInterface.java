package com.example.hackathon.daki.Model;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Vaharamus on 22/03/2018.
 */

public interface RestInterface {

    @POST("login")
    Call<ResponseBody> buscarLogin(@Body Usuario usuario);

    @POST("cadastro")
    Call<ResponseBody> cadastrar (@Body Usuario usuario);

    @GET("anuncios/aprovados")
    Call<List<AnuncioB>> listarAnunciosAprovados();

    @GET("anuncios/pendentes")
    Call<List<AnuncioB>> listarAnunciosPendentes();

    @POST("anuncios")
    Call<ResponseBody> salvarAnuncios(@Body AnuncioB anuncio);

    @PUT("anuncios/{id}")
    Call<Void> validarAnuncio(@Path("id") int id, @Body AnuncioB anuncioB);

//    novo
    @PUT("anuncios/{id}/true")
    Call<ResponseBody> putDeAnuncio(@Path("id") int id);

    @GET("anuncios/{id}")
    Call<AnuncioB> buscarAnuncios(@Path("id") int id);

    @DELETE("anuncios/{id}")
    Call<ResponseBody> deleteAnuncio(@Path("id") int id);


}



