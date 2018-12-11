package mapa;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import inimigos.Monstro;

import java.util.ArrayList;
import java.util.Map;

@DatabaseTable(tableName = "sala")
public class Sala {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private final String nome;
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

    public void setSalaAoRedor(Map<Direcao, Sala> salaAoRedor) {
        this.salaAoRedor = salaAoRedor;
    }

    public ArrayList<Monstro> getMonstros() {
        return monstros;
    }

    public void setMonstros(ArrayList<Monstro> monstros) {
        this.monstros = monstros;
    }
}
