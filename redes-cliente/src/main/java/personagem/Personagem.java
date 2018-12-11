package personagem;

import itens.Item;
import itens.combate.*;

import java.util.ArrayList;

public class Personagem {
    public int id;
    public String nome;
    public String senha;
    public int nivel;
    public int ouro;
    public int exp;
    public int expProxLevel;
    public int forca;
    public int vidaAtual;
    public int vidaMax;
    public int defesa;
    public int dano;
    public Capacete capacete;
    public Armadura armadura;
    public Espada espada;
    public Escudo escudo;
    public Perneira perneira;
    public Calcado calcado;
    public ArrayList<Item> inventario;
}
