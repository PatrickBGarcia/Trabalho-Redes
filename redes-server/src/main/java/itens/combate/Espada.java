package itens.combate;

import itens.Item;

public class Espada extends Item {
    public Espada(){
        this.categoria = Categoria.EQUIPAMENTO;
    }

    public Espada(String nome, Raridade raridade){
        this.nome = nome;
        this.categoria = Categoria.EQUIPAMENTO;
        this.raridade = raridade;

        if(this.raridade.equals(Raridade.MUITO_RARO)){
            this.valor = 100;
            this.dano = 15;
            this.defesa = 7;
        }else if(this.raridade.equals(Raridade.RARO)){
            this.valor = 70;
            this.dano = 10;
            this.defesa = 2;
        }else{
            this.valor = 30;
            this.dano = 5;
            this.defesa = 0;
        }
    }
}
