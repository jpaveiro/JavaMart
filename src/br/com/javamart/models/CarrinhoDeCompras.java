package br.com.javamart.models;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoDeCompras implements CarrinhoDeComprasInterface {
    private List<Produto> itensNoCarrinho = new ArrayList<>();
    private double total;

    public CarrinhoDeCompras() {
        Controle.cadastrarProduto(
                "Fone de Ouvido Sem Fio",
                79.99,
                3
        );
        Controle.cadastrarProduto(
                "Secador de Cabelo",
                94.99,
                1
        );
        Controle.cadastrarProduto(
                "Camiseta",
                34.99,
                7
        );
        Controle.cadastrarProduto(
                "Carregador Powerbank",
                67.90,
                2
        );
        Controle.cadastrarProduto(
                "Capinha para Celular",
                28.90,
                14
        );
    }

    public void adicionarAoCarrinho(Produto produto, int quantidade) {
        if (quantidade > produto.getQuantidade()) {
            System.out.println("Erro! Estoque insuficiente!");
            return;
        }
        total += produto.getPreco(); // Aumenta o total
        produto.diminuirQuantidade(quantidade); // Diminui a quantidade no estoque
        produto.aumentarQuantidadeCarrinho(quantidade); // Aumenta a quantidade no Carrinho
        itensNoCarrinho.add(produto); // Adiciona o item a lista carrinho
        System.out.println("Produto adicionado ao carrinho!");
    }

    public void removerDoCarrinho(Produto produto) {
        if (!itensNoCarrinho.contains(produto)) {
            System.out.println("O produto não está dentro do carrinho.");
            return;
        }
        total -= produto.getPreco();
        produto.aumentarQuantidade(produto.getQuantidadeNoCarrinho());
        produto.diminuirQuantidadeCarrinho(produto.getQuantidade());
        itensNoCarrinho.remove(produto);
        System.out.println("Produto removido do carrinho!");
    }

    public void verCarrinho() {
        if (itensNoCarrinho.isEmpty()) {
            System.out.println("Nenhum produto foi colocado ainda.");
            return;
        }
        for (Produto produto : itensNoCarrinho) {
            System.out.println(produto);
        }
        System.out.println("Total: R$ " + total);
    }

    public void finalizarCompra(Cliente cliente) {
        if (total > cliente.getSaldo()) {
            System.out.println("Erro! Você não tem saldo suficiente!");
            return;
        }
        Compra compra = new Compra(itensNoCarrinho, cliente);
        Controle.getHistoricoVendas().add(compra);
        cliente.removeSaldo(total);
        itensNoCarrinho.clear();
        total = 0;
        System.out.println("Compra efetuada com sucesso!");
    }
}
