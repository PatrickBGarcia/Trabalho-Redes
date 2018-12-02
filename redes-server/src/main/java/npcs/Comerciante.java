package npcs;

import itens.Item;

import java.util.ArrayList;

public class Comerciante {
    public String nome;
    public ArrayList<Item> itensAVenda = new ArrayList<Item>();

    public ArrayList<Item> ofertas(){
        return this.itensAVenda;
    }

    public void adicionarItem(Item item){
        item.valor += 20;
        this.itensAVenda.add(item);
    }

    public void removerItem(Item item){
        this.itensAVenda.remove(item);
    }
}
