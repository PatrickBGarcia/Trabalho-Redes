package itens.combate;

import itens.Item;

public class Armadura extends Item {
    public Armadura(){
    }

    public Armadura(String nome, Raridade raridade){
        this.nome = nome;
        this.categoria = Categoria.EQUIPAMENTO;
        this.raridade = raridade;

        if(this.raridade.equals(Raridade.MUITO_RARO)){
            this.valor = 100;
            this.dano = 2;
            this.defesa = 9;
        }else if(this.raridade.equals(Raridade.RARO)){
            this.valor = 60;
            this.dano = 1;
            this.defesa = 6;
        }else{
            this.valor = 30;
            this.dano = 0;
            this.defesa = 2;
        }
    }
}
