package npcs;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import itens.Item;

import java.util.ArrayList;
@DatabaseTable(tableName = "comerciante")
public class Comerciante {

    @DatabaseField
    public String nome;
    @DatabaseField(foreign = true)
    public ArrayList<Item> itensAVenda = new ArrayList<Item>();
    //o que é isso, jovial?
    public ArrayList<Item> ofertas(){
        return this.itensAVenda;
    }

    public Comerciante(){}
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

    public ArrayList<Item> getItensAVenda() {
        return itensAVenda;
    }

    public void setItensAVenda(ArrayList<Item> itensAVenda) {
        this.itensAVenda = itensAVenda;
    }
}
