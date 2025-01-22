package com.example.prototipo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class ProdutoService {

    public static List<Produto> parseProdutos(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Produto>>() {}.getType();
        return gson.fromJson(json, listType);
    }

}
