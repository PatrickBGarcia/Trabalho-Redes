package itens.combate;

import itens.Item;

public class Perneira extends Item {
    public Perneira(){
        this.categoria = Categoria.EQUIPAMENTO;
    }

    public Perneira(String nome, Raridade raridade){
        this.nome = nome;
        this.categoria = Categoria.EQUIPAMENTO;
        this.raridade = raridade;

        if(this.raridade.equals(Raridade.MUITO_RARO)){
            this.valor = 70;
            this.dano = 2;
            this.defesa = 8;
        }else if(this.raridade.equals(Raridade.RARO)){
            this.valor = 45;
            this.dano = 1;
            this.defesa = 5;
        }else{
            this.valor = 30;
            this.dano = 0;
            this.defesa = 3;
        }
    }
}
