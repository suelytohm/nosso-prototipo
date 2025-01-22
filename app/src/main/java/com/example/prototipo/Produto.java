package com.example.prototipo;
import java.io.Serializable;

public class Produto implements Serializable {
    private int id;
    private String nomeProduto;
    private String marcaProduto;
    private String codigoBarras;
    private int quantidade;
    private String validade;

    private int diasValidade;

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    public String getMarcaProduto() { return marcaProduto; }
    public void setMarcaProduto(String marcaProduto) { this.marcaProduto = marcaProduto; }
    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public String getValidade() { return validade; }
    public void setValidade(String validade) { this.validade = validade; }

    public int getDiasValidade() {
        return diasValidade;
    }

    public void setDiasValidade(int diasValidade) {
        this.diasValidade = diasValidade;
    }
}
