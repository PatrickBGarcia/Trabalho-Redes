package mapa;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import inimigos.Monstro;
import npcs.Comerciante;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Sala {

    private int id;
    private final String nome;
    private Comerciante npc = new Comerciante();
    public HashMap<Direcao, Sala> salaAoRedor = new HashMap<>();
    public ArrayList<Monstro> monstros = new ArrayList<>();


    public Sala(String nome) {
       this.nome = nome;
    }

    public void addAdjacente(Sala sala, Direcao direcao) {
        if(salaAoRedor != null) {
            if (salaAoRedor.get(direcao) == null) {
                salaAoRedor.put(direcao, sala);
            }
        }else{
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Map<Direcao, Sala> getSalaAoRedor() {
        return salaAoRedor;
    }

    public void setSalaAoRedor(HashMap<Direcao, Sala> salaAoRedor) {
        this.salaAoRedor = salaAoRedor;
    }

    public ArrayList<Monstro> getMonstros() {
        return monstros;
    }

    public void setMonstros(ArrayList<Monstro> monstros) {
        this.monstros = monstros;
    }

    public Comerciante getNpc() {
        return npc;
    }

    public void setNpc(Comerciante npc) {
        this.npc = npc;
    }
}
