package itens.combate;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import personagem.Personagem;

@DatabaseTable(tableName = "set")
public class Set {
    @DatabaseField
    public int id;
    @DatabaseField(foreign = true)
    public Personagem personagem;
    @DatabaseField
    public Capacete helmet;
    @DatabaseField
    public Armadura armor;
    @DatabaseField
    public Perneira legs;
    @DatabaseField
    public Calcado shoes;
    @DatabaseField
    public Espada sword;
    @DatabaseField
    public Escudo shield;

    public Set(){}

    public Capacete getHelmet() {
        return helmet;
    }

    public void setHelmet(Capacete helmet) {
        this.helmet = helmet;
    }

    public Armadura getArmor() {
        return armor;
    }

    public void setArmor(Armadura armor) {
        this.armor = armor;
    }

    public Perneira getLegs() {
        return legs;
    }

    public void setLegs(Perneira legs) {
        this.legs = legs;
    }

    public Calcado getShoes() {
        return shoes;
    }

    public void setShoes(Calcado shoes) {
        this.shoes = shoes;
    }

    public Espada getSword() {
        return sword;
    }

    public void setSword(Espada sword) {
        this.sword = sword;
    }

    public Escudo getShield() {
        return shield;
    }

    public void setShield(Escudo shield) {
        this.shield = shield;
    }
}
