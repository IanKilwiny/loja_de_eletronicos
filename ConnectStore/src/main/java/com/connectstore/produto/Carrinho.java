package com.connectstore.produto;

import java.time.LocalDate;

public record Carrinho(int fkCliente, int fkProduto, LocalDate dataInsercao, int quantidade) {
}
