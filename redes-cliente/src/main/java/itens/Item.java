package itens;

public class Item {
    public int id;
    public String nome;
    public int valor;
    public int dano;
    public int defesa;
    public int vidaRegenerada;
    public Raridade raridade;
    public Categoria categoria;

    public enum Raridade{
        NORMAL, RARO, MUITO_RARO;
    }

    public enum Categoria{
        EQUIPAMENTO, POT;
    }
}
