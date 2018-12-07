package itens;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "item")
public class Item {
    @DatabaseField
    public String nome;
    @DatabaseField
    public int valor;
    @DatabaseField
    public Raridade raridade;
    //falta procura esse pra enum
    public enum Raridade{
        NORMAL, RARO, MUITO_RARO;
    }
    public Item(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Raridade getRaridade() {
        return raridade;
    }

    public void setRaridade(Raridade raridade) {
        this.raridade = raridade;
    }
}
