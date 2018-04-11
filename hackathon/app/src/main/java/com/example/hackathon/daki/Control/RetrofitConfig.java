package com.example.hackathon.daki.Control;

import com.example.hackathon.daki.Model.RestInterface;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vaharamus on 22/03/2018.
 */


    /*
    *
    * Essa classe ficará responsável por configurar e instanciar o Retrofit
    *
    * */


public class RetrofitConfig {

        // atributo
        private final Retrofit retrofit;

        // Nossa configuração será feita no construtor
        public RetrofitConfig() {

            // Precisamos construir um objeto do tipo retrofit
            this.retrofit = new Retrofit.Builder()
                    // definimos a url base da nossa aplicação
                    .baseUrl(AuxiliumJson.API_BASE_URL)
                    // precisamos transformar a nossa resposta que vem em JSON para String
                    .addConverterFactory(GsonConverterFactory.create())
                    // precisamos de fato criá-lo
                    .build();

        }

        public RetrofitConfig(OkHttpClient client) {
            // Precisamos construir um objeto do tipo retrofit
            this.retrofit = new Retrofit.Builder()
                    // definimos a url base da nossa aplicação
                    .baseUrl(AuxiliumJson.API_BASE_URL)
                    // caso seja necessário colocar um interceptor
                    .client(client)
                    // precisamos transformar a nossa resposta que vem em JSON para String
                    .addConverterFactory(GsonConverterFactory.create())
                    // precisamos de fato criá-lo
                    .build();
        }

        public RestInterface getRestInterface() {
            return this.retrofit.create(RestInterface.class);
        }

    }


