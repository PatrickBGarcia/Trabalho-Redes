package itens.combate;

import itens.Item;

public class Escudo extends Item {
    public Escudo(){
        this.categoria = Categoria.EQUIPAMENTO;
    }

    public Escudo(String nome, Raridade raridade){
        this.nome = nome;
        this.categoria = Categoria.EQUIPAMENTO;
        this.raridade = raridade;

        if(this.raridade.equals(Raridade.MUITO_RARO)){
            this.valor = 80;
            this.dano = 2;
            this.defesa = 10;
        }else if(this.raridade.equals(Raridade.RARO)){
            this.valor = 50;
            this.dano = 1;
            this.defesa = 5;
        }else{
            this.valor = 30;
            this.dano = 0;
            this.defesa = 2;
        }
    }
}
