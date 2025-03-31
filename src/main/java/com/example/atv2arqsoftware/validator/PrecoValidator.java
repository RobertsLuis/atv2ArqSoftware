package com.example.atv2arqsoftware.validator;

import com.example.atv2arqsoftware.model.Produto;

public class PrecoValidator extends ProdutoValidator{
    @Override
    public boolean validar(Produto produto) {
        return produto.getPreco() != null && produto.getPreco() > 0;
    }

    @Override
    public String getMensagemErro() {
        return "O preço deve ser maior que zero.";
    }
}
