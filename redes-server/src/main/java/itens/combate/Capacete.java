package itens.combate;

import itens.Item;

public class Capacete extends Item {
    public Capacete(){
        this.categoria = Categoria.EQUIPAMENTO;
    }

    public Capacete(String nome, Raridade raridade){
        this.nome = nome;
        this.categoria = Categoria.EQUIPAMENTO;
        this.raridade = raridade;

        if(this.raridade.equals(Raridade.MUITO_RARO)){
            this.valor = 80;
            this.dano = 2;
            this.defesa = 6;
        }else if(this.raridade.equals(Raridade.RARO)){
            this.valor = 55;
            this.dano = 1;
            this.defesa = 3;
        }else{
            this.valor = 30;
            this.dano = 0;
            this.defesa = 2;
        }
    }
}
