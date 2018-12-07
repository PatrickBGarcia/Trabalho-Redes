package itens.combate;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import itens.Item;

@DatabaseTable(tableName = "equipamento")
public class Equipamento extends Item {
    @DatabaseField
    public int dano;
    @DatabaseField
    public int defesa;
    public Equipamento(){}

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }
}
