package personagem;

import itens.Item;
import itens.combate.Set;
import itens.consumivel.Pot;

import java.util.ArrayList;

public class Personagem {
    public final String nome;
    public int nivel = 1;
    public int exp = 0;
    public int expProxLevel = 100;
    public int forca = 5;
    public int vidaAtual = 20;
    public int vidaMax = 20;
    public int defesa = 0;
    public int dano = 5;
    public Set equipamentos = new Set();
    public ArrayList<Item> inventario = new ArrayList<Item>();


    public Personagem(String nome){
        this.nome = nome;
    }

    public void aumentaExp(int exp){
        this.exp += exp;
        if(this.exp > this.expProxLevel){
            sobeNivel();
        }
    }

    public void sobeNivel(){
        this.exp -= this.expProxLevel;
        this.expProxLevel *= 2;
        this.nivel++;
        this.vidaMax += 20;
        this.vidaAtual = this.vidaMax;
        this.forca += 5;
        this.dano += 5;
        this.defesa += 2;
    }

    public Personagem morrer(){
        return new Personagem(this.nome);
    }

    public void recalculaDano(){
        int dano = 0;
        if(this.equipamentos != null){
            if(this.equipamentos.armor != null){
                dano += this.equipamentos.armor.dano;
            }
            if(this.equipamentos.helmet != null){
                dano += this.equipamentos.helmet.dano;
            }
            if(this.equipamentos.legs != null){
                dano += this.equipamentos.legs.dano;
            }
            if(this.equipamentos.shield != null){
                dano += this.equipamentos.shield.dano;
            }
            if(this.equipamentos.shoes != null){
                dano += this.equipamentos.shoes.dano;
            }
            if(this.equipamentos.sword != null){
                dano += this.equipamentos.sword.dano;
            }
        }
        this.dano = dano;
    }

    public void recalculaDefesa(){
        int defesa = 0;
        if(this.equipamentos != null){
            if(this.equipamentos.armor != null){
                defesa += this.equipamentos.armor.defesa;
            }
            if(this.equipamentos.helmet != null){
                defesa += this.equipamentos.helmet.defesa;
            }
            if(this.equipamentos.legs != null){
                defesa += this.equipamentos.legs.defesa;
            }
            if(this.equipamentos.shield != null){
                defesa += this.equipamentos.shield.defesa;
            }
            if(this.equipamentos.shoes != null){
                defesa += this.equipamentos.shoes.defesa;
            }
            if(this.equipamentos.sword != null){
                defesa += this.equipamentos.sword.defesa;
            }
        }
        this.defesa = defesa;
    }

    public boolean usarPocao(Pot pot){
        if(this.inventario.contains(pot)){
            curar(pot.vidaRegenerada);
            this.inventario.remove(pot);
            return true;
        }
        return false;
    }

    public void curar(int valor){
        if((this.vidaAtual + valor) > this.vidaMax){
            this.vidaAtual = this.vidaMax;
        }else{
            this.vidaAtual += valor;
        }
    }

    public boolean pegarItem(Item item){
        if(this.inventario.size() > 20){
            return false;
        }else{
            this.inventario.add(item);
            return true;
        }
    }

    public boolean descartarItem(Item item){
        if(this.inventario.contains(item)){
            this.inventario.remove(item);
            return true;
        }
        return false;
    }

}
