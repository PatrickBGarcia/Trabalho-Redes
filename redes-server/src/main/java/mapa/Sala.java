package mapa;

import com.j256.ormlite.field.DatabaseField;
import inimigos.Monstro;

import java.util.ArrayList;
import java.util.Map;

public class Sala {
    @DatabaseField
    public int id;
    @DatabaseField
    public final String nome;
    public Map<Direcao, Sala> salaAoRedor;
    public ArrayList<Monstro> monstros = new ArrayList<>();


    public Sala(String nome) {
       this.nome = nome;
    }

    public void addAdjacente(Sala sala, Direcao direcao) {
        if(salaAoRedor.get(direcao) == null) {
            salaAoRedor.put(direcao, sala);
        }
    }

    public Sala moverPara(Direcao direcao) {
        if(salaAoRedor.get(direcao) != null) {
            return salaAoRedor.get(direcao);
        }
        return null;
    }

    public void addMonstro(Monstro monstro){
        this.monstros.add(monstro);
    }

    public void removeMonstro(Monstro monstro){
        this.monstros.remove(monstro);
    }

}
