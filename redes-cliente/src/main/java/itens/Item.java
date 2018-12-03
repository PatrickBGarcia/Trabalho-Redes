package itens;

public class Item {
    public String nome;
    public int valor;
    public Raridade raridade;

    public enum Raridade{
        NORMAL, RARO, MUITO_RARO;
    }
}
