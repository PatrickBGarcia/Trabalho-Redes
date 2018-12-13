package inimigos;

public class Minichefe extends Monstro {

    public Minichefe(int nivel, String nome) {
        super(nivel, nome);
        this.setDano(this.getDano() + 3);
        this.setVidaMax(this.getVidaMax() + 5);
        this.setVidaAtual(this.getVidaMax());
        this.setDefesa(this.getDefesa() + 3);
        this.setExpDada(this.getExpDada() * 2);
        this.setOuroDado(this.getOuroDado() * 2);
        this.tipoMonstro = TipoMonstro.MINICHEFE;
    }
}
