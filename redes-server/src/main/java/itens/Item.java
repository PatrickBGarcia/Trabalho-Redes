package itens;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import npcs.Comerciante;
import personagem.Personagem;

@DatabaseTable(tableName = "item")
public class Item {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    protected String nome;
    @DatabaseField
    public int valor;
    @DatabaseField
    public int dano;
    @DatabaseField
    public int defesa;
    @DatabaseField
    public int vidaRegenerada;

    @DatabaseField(foreign = true)
    private Personagem personagem;

    @DatabaseField
    public Raridade raridade;

    public enum Raridade{
        NORMAL, RARO, MUITO_RARO;
    }
    @DatabaseField
    public Categoria categoria;

    public enum Categoria{
        EQUIPAMENTO, POT;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getVidaRegenerada() {
        return vidaRegenerada;
    }

    public void setVidaRegenerada(int vidaRegenerada) {
        this.vidaRegenerada = vidaRegenerada;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Personagem getPersonagem() {
        return personagem;
    }

    public void setPersonagem(Personagem personagem) {
        this.personagem = personagem;
    }
}
