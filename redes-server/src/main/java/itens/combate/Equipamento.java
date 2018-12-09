package itens.combate;

import itens.Item;

public class Equipamento extends Item {
    public int dano;
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
