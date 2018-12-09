package inimigos;

public class Boss extends Monstro {
    //public Boss(){}
    public Boss(int nivel, String nome) {
        super(nivel, nome);
        this.tipoMonstro = TipoMonstro.BOSS;
    }
}
