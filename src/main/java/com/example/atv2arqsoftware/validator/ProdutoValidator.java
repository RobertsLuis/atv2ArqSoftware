// Classe base para validação
package com.example.atv2arqsoftware.validator;

import com.example.atv2arqsoftware.model.Produto;

public abstract class ProdutoValidator {
    public abstract boolean validar(Produto produto);

    public String getMensagemErro() {
        return "Validação falhou";
    }
}