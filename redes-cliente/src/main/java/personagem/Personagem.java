package personagem;

import itens.Item;
import itens.combate.Set;

import java.util.ArrayList;

public class Personagem {
    public String nome;
    public int nivel;
    public int exp;
    public int expProxLevel;
    public int ouro;
    public int forca;
    public int vidaAtual;
    public int vidaMax;
    public int defesa;
    public int dano;
    public Set equipamentos;
    public ArrayList<Item> inventario;
}
