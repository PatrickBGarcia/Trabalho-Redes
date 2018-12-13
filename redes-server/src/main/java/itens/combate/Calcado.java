package itens.combate;

import itens.Item;

public class Calcado extends Item {
    public Calcado(){
        this.categoria = Categoria.EQUIPAMENTO;
    }

    public Calcado(String nome, Raridade raridade){
        this.nome = nome;
        this.categoria = Categoria.EQUIPAMENTO;
        this.raridade = raridade;

        if(this.raridade.equals(Raridade.MUITO_RARO)){
            this.valor = 75;
            this.dano = 2;
            this.defesa = 6;
        }else if(this.raridade.equals(Raridade.RARO)){
            this.valor = 40;
            this.dano = 1;
            this.defesa = 3;
        }else{
            this.valor = 15;
            this.dano = 0;
            this.defesa = 2;
        }
    }
}
