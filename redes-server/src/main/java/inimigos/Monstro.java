package inimigos;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import itens.Item;

import java.util.ArrayList;
import java.util.Random;

@DatabaseTable(tableName = "monstro")
public class Monstro {
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(canBeNull = false)
    public String nome;
    @DatabaseField(canBeNull = false)
    public int vidaAtual;
    @DatabaseField(canBeNull = false)
    public int vidaMax;
    @DatabaseField(canBeNull = false)
    public int expDada;
    @DatabaseField(canBeNull = false)
    public int ouroDado;
    @DatabaseField(canBeNull = false)
    public int nivel;
    @DatabaseField(canBeNull = false)
    public int defesa;
    @DatabaseField(canBeNull = false)
    public int dano;
    @DatabaseField
    public TipoMonstro tipoMonstro = TipoMonstro.MONSTRO;


    //@DatabaseField
    public ArrayList<Item> drop = new ArrayList<Item>();

    public Monstro(){}

    public String getNome() {
        return nome;
    }




    public Monstro(int nivel, String nome){
        this.nome = nome;
        this.nivel = nivel;
        this.vidaMax = 20 * nivel;
        this.vidaAtual = this.vidaMax;
        this.expDada = 20 * nivel;
        this.defesa = (int)(1.5 * nivel);
        this.dano = 3 * nivel;
        this.ouroDado = 2 * nivel;
    }

    public void possiveisDrops(ArrayList<Item> itens){
        this.drop = itens;
    }

    public ArrayList<Item> dropar(){
        Random gerador = new Random();
        int random = gerador.nextInt(101);
        ArrayList<Item> itensDropados = new ArrayList<Item>();

        if(random < 60){
            for(Item item: this.drop){
                if(Item.Raridade.NORMAL.equals(item.raridade)){
                    itensDropados.add(item);
                }
            }
        }else if(random < 80){
            for(Item item: this.drop){
                if(Item.Raridade.RARO.equals(item.raridade)){
                    itensDropados.add(item);
                }
            }
        }else if(random < 90) {
            for (Item item : this.drop) {
                if (Item.Raridade.NORMAL.equals(item.raridade) || Item.Raridade.RARO.equals(item.raridade)) {
                    itensDropados.add(item);
                }
            }
        }else if(random < 100){
            for(Item item: this.drop){
                if(Item.Raridade.MUITO_RARO.equals(item.raridade)){
                    itensDropados.add(item);
                }
            }
        }else{
            return this.drop;
        }


        return itensDropados;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = vidaAtual;
    }

    public int getVidaMax() {
        return vidaMax;
    }

    public void setVidaMax(int vidaMax) {
        this.vidaMax = vidaMax;
    }

    public int getExpDada() {
        return expDada;
    }

    public void setExpDada(int expDada) {
        this.expDada = expDada;
    }

    public int getOuroDado() {
        return ouroDado;
    }

    public void setOuroDado(int ouroDado) {
        this.ouroDado = ouroDado;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public ArrayList<Item> getDrop() {
        return drop;
    }

    public void setDrop(ArrayList<Item> drop) {
        this.drop = drop;
    }

    public TipoMonstro getTipoMonstro() {
        return tipoMonstro;
    }

    public void setTipoMonstro(TipoMonstro tipoMonstro) {
        this.tipoMonstro = tipoMonstro;
    }

}
