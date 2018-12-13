package itens.consumivel;

import itens.Item;

public class Pot extends Item {
    public Pot(){}

    public Pot(String nome, Raridade raridade){
        this.nome = nome;
        this.categoria = Categoria.POT;
        this.raridade = raridade;

        if(this.raridade.equals(Raridade.MUITO_RARO)){
            this.valor = 70;
            this.vidaRegenerada = 100;
        }else if(this.raridade.equals(Raridade.RARO)){
            this.valor = 45;
            this.vidaRegenerada = 50;
        }else{
            this.valor = 30;
            this.vidaRegenerada = 20;
        }
    }
}
