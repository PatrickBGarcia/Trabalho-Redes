package inimigos;

import itens.Item;

import java.util.ArrayList;
import java.util.Random;

public class Monstro {
    public String nome;
    public int vidaAtual;
    public int vidaMax;
    public int expDada;
    public int ouroDado;
    public int nivel;
    public int defesa;
    public int dano;
    public ArrayList<Item> drop = new ArrayList<Item>();

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
}
