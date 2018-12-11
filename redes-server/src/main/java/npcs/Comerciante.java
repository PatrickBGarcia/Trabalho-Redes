package npcs;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import itens.Item;
import javafx.print.Collation;

import java.util.ArrayList;
import java.util.Collection;

@DatabaseTable(tableName = "comerciante")
public class Comerciante {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String nome;
    //@ForeignCollectionField //VERIFICAR ARRAYLIST

    @ForeignCollectionField
    private Collection<Item> itensAVenda;
    //@DatabaseField(dataType= DataType.SERIALIZABLE, foreign = true)
    //public ArrayList<Item> itensAVenda = new ArrayList<Item>();

    public Collection<Item> ofertas(){
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Collection<Item> getItensAVenda() {
        return itensAVenda;
    }

    public void setItensAVenda(Collection<Item> itensAVenda) {
        this.itensAVenda = itensAVenda;
    }
}
