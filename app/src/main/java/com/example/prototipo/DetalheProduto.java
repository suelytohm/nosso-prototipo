package com.example.prototipo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



public class DetalheProduto extends AppCompatActivity {

    public String typeButton;

    public EditText nome;
    public EditText marca;
    public EditText codigoBarras;
    public EditText quantidade;
    public EditText validade;

    public Button btnZerarProduto;

    public int idProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nome = findViewById(R.id.etNomeProduto);
        marca = findViewById(R.id.etMarca);
        codigoBarras = findViewById(R.id.etCodigoBarras);
        quantidade = findViewById(R.id.etQuantidade);
        validade = findViewById(R.id.etValidade);

        btnZerarProduto = findViewById(R.id.btnZerarProduto);

        // Teste


        Produto produto = (Produto) getIntent().getSerializableExtra("produto");

        if (produto != null) {
            setTitle("Detalhes do Produto");

            idProduto = produto.getId();
            nome.setText(produto.getNomeProduto());
            marca.setText(produto.getMarcaProduto());
            codigoBarras.setText(produto.getCodigoBarras());
            quantidade.setText(String.valueOf(produto.getQuantidade()));
            validade.setText(formatarValidade(produto.getValidade()));

            quantidade.requestFocus();
            quantidade.setSelection(quantidade.getText().length());

            typeButton = "put";
        } else {
            setTitle("Novo Produto");
            btnZerarProduto.setVisibility(View.GONE);
            nome.requestFocus();

            typeButton = "post";
        }


        btnZerarProduto.setOnClickListener(view -> {
            quantidade.setText("0");

            Services s = new Services();
            Produto p = new Produto();
            p.setId(idProduto);
            p.setNomeProduto(String.valueOf(nome.getText()));
            p.setMarcaProduto(String.valueOf(marca.getText()));
            p.setCodigoBarras(String.valueOf(codigoBarras.getText()));
            p.setQuantidade(Integer.valueOf(String.valueOf(quantidade.getText())));
            p.setValidade(s.formatarData(String.valueOf(validade.getText())));

            ApiClient.alterarProduto(p, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // Tratando falha na requisição
                    Log.e("ErroAPI", "Falha na comunicação com o servidor: " + e.getMessage());
                    runOnUiThread(() -> Toast.makeText(DetalheProduto.this,
                            "Erro na comunicação com o servidor."  + e.getMessage(), Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        Log.d("RespostaAPI", "Produto alterado com sucesso: " + responseBody);

                        runOnUiThread(() -> Toast.makeText(DetalheProduto.this,
                                "Produto alterado com sucesso!", Toast.LENGTH_SHORT).show());

                        Intent i = new Intent(DetalheProduto.this, ListarProdutos.class);
                        DetalheProduto.this.startActivity(i);
                    } else {
                        Log.e("ErroAPI", "Erro na resposta: Código " + response.code() + " - " + response.message());
                        runOnUiThread(() -> Toast.makeText(DetalheProduto.this,
                                "Erro na alteração do produto: " + response.message(), Toast.LENGTH_SHORT).show());
                    }
                }
            });
        });


        EditText etValidade = findViewById(R.id.etValidade);

        etValidade.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    DetalheProduto.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // Formatar a data para dd/mm/yyyy
                        String formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                        etValidade.setText(formattedDate);
                    },
                    year, month, day);

            datePickerDialog.show();
        });




        Button btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(v -> {

            if (typeButton.equals("put")){

                Services s = new Services();
                Produto p = new Produto();
                p.setId(idProduto);
                p.setNomeProduto(String.valueOf(nome.getText()));
                p.setMarcaProduto(String.valueOf(marca.getText()));
                p.setCodigoBarras(String.valueOf(codigoBarras.getText()));
                p.setQuantidade(Integer.valueOf(String.valueOf(quantidade.getText())));
                p.setValidade(s.formatarData(String.valueOf(validade.getText())));


                ApiClient.alterarProduto(p, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // Tratando falha na requisição
                        Log.e("ErroAPI", "Falha na comunicação com o servidor: " + e.getMessage());
                        runOnUiThread(() -> Toast.makeText(DetalheProduto.this,
                                "Erro na comunicação com o servidor.", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful() && response.body() != null) {
                            String responseBody = response.body().string();
                            Log.d("RespostaAPI", "Produto alterado com sucesso: " + responseBody);

                            runOnUiThread(() -> Toast.makeText(DetalheProduto.this,
                                    "Produto alterado com sucesso!", Toast.LENGTH_SHORT).show());

                            Intent i = new Intent(DetalheProduto.this, ListarProdutos.class);
                            DetalheProduto.this.startActivity(i);
                        } else {
                            Log.e("ErroAPI", "Erro na resposta: Código " + response.code() + " - " + response.message());
                            runOnUiThread(() -> Toast.makeText(DetalheProduto.this,
                                    "Erro na alteração do produto: " + response.message(), Toast.LENGTH_SHORT).show());
                        }
                    }
                });

            } else {

                Services s = new Services();
                Produto p = new Produto();
                p.setNomeProduto(String.valueOf(nome.getText()));
                p.setMarcaProduto(String.valueOf(marca.getText()));
                p.setCodigoBarras(String.valueOf(codigoBarras.getText()));
                p.setQuantidade(Integer.valueOf(String.valueOf(quantidade.getText())));
                p.setValidade(s.formatarData(String.valueOf(validade.getText())));


                ApiClient.cadastroProduto(p, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // Tratando falha na requisição
                        Log.e("ErroAPI", "Falha na comunicação com o servidor: " + e.getMessage());
                        runOnUiThread(() -> Toast.makeText(DetalheProduto.this,
                                "Erro na comunicação com o servidor.", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful() && response.body() != null) {
                            String responseBody = response.body().string();
                            Log.d("RespostaAPI", "Produto cadastrado com sucesso: " + responseBody);

                            runOnUiThread(() -> Toast.makeText(DetalheProduto.this,
                                    "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show());

                            Intent i = new Intent(DetalheProduto.this, ListarProdutos.class);
                            DetalheProduto.this.startActivity(i);
                        } else {
                            Log.e("ErroAPI", "Erro na resposta: Código " + response.code() + " - " + response.message());
                            runOnUiThread(() -> Toast.makeText(DetalheProduto.this,
                                    "Erro no cadastro do produto: " + response.message(), Toast.LENGTH_SHORT).show());
                        }
                    }
                });
            }
        });



        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_arrow_left); // Ícone padrão ou personalizado
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Retorna para a tela anterior
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String formatarValidade(String validade) {
        String year = validade.substring(0, 4);
        String month = validade.substring(5, 7);
        String day = validade.substring(8, 10);

        return day + "/" + month + "/" + year;

    }


}