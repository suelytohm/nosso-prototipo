package com.example.prototipo;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private final List<Produto> produtos;
    private final OnProdutoClickListener listener;

    public interface OnProdutoClickListener {
        void onProdutoClick(Produto produto);
    }

    public ProdutoAdapter(List<Produto> produtos, OnProdutoClickListener listener) {
        this.produtos = produtos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = produtos.get(position);
        holder.bind(produto, listener);
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNomeProduto;
        private final TextView tvMarcaProduto;
        private final TextView tvValidadeProduto;
        private final TextView tvDiasValidade;

        private final LinearLayout llItem; // Removido `static`

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeProduto = itemView.findViewById(R.id.tvNomeProduto);
            tvMarcaProduto = itemView.findViewById(R.id.tvMarcaProduto);
            tvValidadeProduto = itemView.findViewById(R.id.tvValidadeProduto);
            tvDiasValidade = itemView.findViewById(R.id.tvDiasValidade);

            llItem = itemView.findViewById(R.id.llItem);
        }

        @SuppressLint("ResourceAsColor")
        public void bind(Produto produto, OnProdutoClickListener listener) {
            tvNomeProduto.setText(produto.getNomeProduto());
            tvMarcaProduto.setText(produto.getMarcaProduto());
            tvDiasValidade.setText(produto.getDiasValidade() + " Dias");

            Services s = new Services();
            tvValidadeProduto.setText("Validade: " + s.dateFormat(produto.getValidade()));

            // Atualização correta do background
            if (produto.getDiasValidade() > 0 && produto.getDiasValidade() <= 7) {
                llItem.setBackgroundResource(R.color.yellow);
            } else if (produto.getDiasValidade() <= 0) {
                llItem.setBackgroundResource(R.color.red);
            } else {
                llItem.setBackgroundResource(R.color.green);
            }

            itemView.setOnClickListener(v -> listener.onProdutoClick(produto));
        }
    }
}
