package inimigos;

public class Minichefe extends Monstro {
    public Minichefe(){}
    public Minichefe(int nivel, String nome) {
        super(nivel, nome);
        this.tipoMonstro = TipoMonstro.MINICHEFE;
    }
}
