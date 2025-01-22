package com.example.prototipo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Notificação
    private static final String CHANNEL_ID = "example_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Definir a listagem de produtos com base em escolhas nos botões
        Button btnProdutos = findViewById(R.id.btnProdutos);
        btnProdutos.setOnClickListener(view -> {
            Intent i = new Intent(this, ListarProdutos.class);
            this.startActivity(i);
        });


        Button btnValidade = findViewById(R.id.btnValidade);
        btnValidade.setOnClickListener(view -> {
            Intent i = new Intent(this, ListarProdutos.class);
            i.putExtra("filtroValidade", "0");
            i.putExtra("tipoConsulta", "validade");
            this.startActivity(i);
        });

        Button btnProximo = findViewById(R.id.btnProximos);
        btnProximo.setOnClickListener(view -> {
            Intent i = new Intent(this, ListarProdutos.class);
            i.putExtra("filtroValidade", "3");
            i.putExtra("tipoConsulta", "validade");
            this.startActivity(i);
        });


        Button testeNotificacao = findViewById(R.id.testeNotificacao);
        testeNotificacao.setOnClickListener(view -> {
            SendNotification sn = new SendNotification(this);
            sn.showNotification("Produtos Vencidos!", "Você possui 5 produtos com validade vencida. Verifique agora e evite perdas!", MainActivity.class);
        });


    }
}