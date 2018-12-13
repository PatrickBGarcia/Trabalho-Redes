package inimigos;

public class Boss extends Monstro {

    public Boss(int nivel, String nome) {
        super(nivel, nome);
        this.setDano(this.getDano() * 2);
        this.setVidaMax(this.getVidaMax() * 2);
        this.setVidaAtual(this.getVidaMax());
        this.setDefesa(this.getDefesa() * 2);
        this.setExpDada(this.getExpDada() * 4);
        this.setOuroDado(this.getOuroDado() * 5);
        this.tipoMonstro = TipoMonstro.BOSS;
    }
}
