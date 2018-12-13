package mapa;

import inimigos.Monstro;
import npcs.Comerciante;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Sala {

    private final String nome;
    public Comerciante npc;
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

    public Sala mover(String direcao){
        if("norte".equals(direcao)){
            return moverPara(Direcao.NORTE);
        }else if("sul".equals(direcao)){
            return moverPara(Direcao.SUL);
        }else if("leste".equals(direcao)){
            return moverPara(Direcao.LESTE);
        }else if("oeste".equals(direcao)){
            return moverPara(Direcao.OESTE);
        }else if("acima".equals(direcao)){
            return moverPara(Direcao.A_CIMA);
        }else if("abaixo".equals(direcao)){
            return moverPara(Direcao.ABAIXO);
        }else{
            return null;
        }
    }

    private Sala moverPara(Direcao direcao) {
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
