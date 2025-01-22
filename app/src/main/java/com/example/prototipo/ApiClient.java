package com.example.prototipo;

import android.util.Log;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class ApiClient {
    // private static final String API_URL = "http://192.168.0.5:3000/produtos/validade/5";
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final String API_URL = "https://api-produtos-sz39.onrender.com";
    // private static final String API_URL = "http://192.168.0.5:3000";

    public static String getProdutos(String consulta) throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL + consulta)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                Log.e("ErroAPI", "getProdutos: " + response.body().toString());
                throw new Exception("Erro na requisição: " + response.code());
            }
        }
    }

    public static String getProduto(String codigoBarras) throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL + "/produto/codBarras/" + codigoBarras)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                Log.e("ErroAPI", "getProdutos: " + response.body().toString());
                throw new Exception("Erro na requisição: " + response.code());
            }
        }
    }


    public static String getProdutosMarca(String marca) throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL + "/produtos/marca/" + marca)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                Log.e("ErroAPI", "getProdutos: " + response.body().toString());
                throw new Exception("Erro na requisição: " + response.code());
            }
        }
    }


    public static void cadastroProduto(Produto produto, Callback callback) {
        // Serializando o objeto Produto para JSON
        String jsonProduto = new Gson().toJson(produto);

        // Criando o corpo da requisição com o JSON
        RequestBody body = RequestBody.create(jsonProduto, JSON_MEDIA_TYPE);

        // Construindo a requisição POST
        Request request = new Request.Builder()
                .url(API_URL + "/produto")
                .post(body)
                .build();

        // Criando o cliente HTTP
        OkHttpClient client = new OkHttpClient();

        // Executando a requisição de forma assíncrona
        client.newCall(request).enqueue(callback);
    }

    public static void alterarProduto(Produto produto, Callback callback) {
        // Serializando o objeto Produto para JSON
        String jsonProduto = new Gson().toJson(produto);

        Log.i("alterarProduto", "alterarProduto: " + jsonProduto);

        // Criando o corpo da requisição com o JSON
        RequestBody body = RequestBody.create(jsonProduto, JSON_MEDIA_TYPE);

        // Construindo a requisição POST
        Request request = new Request.Builder()
                .url(API_URL + "/produto/" + produto.getId())
                .put(body)
                .build();

        // Criando o cliente HTTP
        OkHttpClient client = new OkHttpClient();

        // Executando a requisição de forma assíncrona
        client.newCall(request).enqueue(callback);
    }

}
