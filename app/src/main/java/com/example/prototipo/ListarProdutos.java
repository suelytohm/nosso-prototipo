package com.example.prototipo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class ListarProdutos extends AppCompatActivity {

    private Button btnOrfeu;
    private String busca;
    private String marca;
    private String consulta;
    private EditText pesquisarProduto;


    private Handler handler = new Handler(); // Handler para controlar o debounce
    private Runnable searchRunnable; // Runnable para executar a busca
    private static final int DEBOUNCE_DELAY = 1000; // Atraso em milissegundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_produtos);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pesquisarProduto = findViewById(R.id.pesquisarProduto);

        String filtro = getIntent().getStringExtra("filtroValidade");
        String tipoConsulta = getIntent().getStringExtra("tipoConsulta");

        marca = getIntent().getStringExtra("marca");

        btnOrfeu = findViewById(R.id.btnOrfeu);

        setTitle("Produtos");

        carregarProdutos(recyclerView);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        btnOrfeu.setOnClickListener(view -> {
            busca = "Orfeu";
            carregarProdutos(recyclerView);
        });


        pesquisarProduto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Não utilizado
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Cancela a execução anterior, caso exista
                if (searchRunnable != null) {
                    handler.removeCallbacks(searchRunnable);
                }

                // Define uma nova tarefa para executar após o atraso
                searchRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (charSequence.length() > 1) {
                            busca = charSequence.toString();
                            carregarProdutos(recyclerView);
                        } else {
                            marca = null;
                            busca = null;
                            carregarProdutos(recyclerView);
                        }
                    }
                };

                // Agenda a execução após o atraso definido
                handler.postDelayed(searchRunnable, DEBOUNCE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Não utilizado
            }
        });
    }

    private void carregarProdutos(RecyclerView recyclerView) {
        new Thread(() -> {
            try {
                if(marca != null){
                    consulta = "/produtos/buscar/" + marca;
                } else if (busca != null || marca != null) {
                    consulta = "/produtos/buscar/" + busca;
                } else {
                    consulta = "/produtos";
                }

                String json = ApiClient.getProdutos(consulta);
                List<Produto> produtos = ProdutoService.parseProdutos(json);
                runOnUiThread(() -> {
                    ProdutoAdapter adapter = new ProdutoAdapter(produtos, produto -> {
                        Intent intent = new Intent(ListarProdutos.this, DetalheProduto.class);
                        intent.putExtra("produto", produto);
                        startActivity(intent);
                    });
                    recyclerView.setAdapter(adapter);
                });
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listagem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent i = new Intent(this, DetalheProduto.class);
            this.startActivity(i);
        }

        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Retorna para a tela anterior
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
