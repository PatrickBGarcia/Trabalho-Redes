package npcs;

import itens.Item;

import java.util.Collection;

public class Comerciante {
    private String nome;
    private Collection<Item> itensAVenda;

    public Comerciante(String nome){
        this.nome = nome;
    }

    public Collection<Item> ofertas(){
        return this.itensAVenda;
    }

    public void adicionarItem(Item item){
        item.valor += 20;
        this.itensAVenda.add(item);
    }

    public void removerItem(Item item){
        this.itensAVenda.remove(item);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Collection<Item> getItensAVenda() {
        return itensAVenda;
    }

    public void setItensAVenda(Collection<Item> itensAVenda) {
        this.itensAVenda = itensAVenda;
    }
}
