package personagem;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import itens.Item;
import itens.combate.*;
import itens.consumivel.Pot;

import java.util.ArrayList;

@DatabaseTable(tableName = "personagem")
public class Personagem {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String nome;
    @DatabaseField(canBeNull = false)
    private String senha;
    @DatabaseField(canBeNull = false)
    private int nivel = 1;
    @DatabaseField(canBeNull = false)
    private int ouro = 0;
    @DatabaseField(canBeNull = false)
    private int exp = 0;
    @DatabaseField(canBeNull = false)
    private int expProxLevel = 100;
    @DatabaseField(canBeNull = false)
    private int forca = 5;
    @DatabaseField(canBeNull = false)
    private int vidaAtual = 20;
    @DatabaseField(canBeNull = false)
    private int vidaMax = 20;
    @DatabaseField(canBeNull = false)
    private int defesa = 0;
    @DatabaseField(canBeNull = false)
    private int dano = 5;
    @DatabaseField(foreign=true)
    private Capacete capacete;
    @DatabaseField(foreign=true)
    private Armadura armadura;
    @DatabaseField(foreign=true)
    private Espada espada;
    @DatabaseField(foreign=true)
    private Escudo escudo;
    @DatabaseField(foreign=true)
    private Perneira perneira;
    @DatabaseField(foreign=true)
    private Calcado calcado;
    //@DatabaseField(foreign = true)   PESQUISAR ARRAY NO ORMLite
    public ArrayList<Item> inventario = new ArrayList<>();

    public Personagem(){}
    public Personagem(String nome,String senha){
        this.nome = nome;
        this.senha = senha;
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
        return new Personagem(this.nome,this.senha);
    }

    public void recalculaDano(){
        int dano = 0;
        if(this.capacete != null){
            dano += this.capacete.dano;
        }
        if(this.armadura != null){
            dano += this.armadura.dano;
        }
        if(this.espada != null){
            dano += this.espada.dano;
        }
        if(this.escudo != null){
            dano += this.escudo.dano;
        }
        if(this.perneira != null){
            dano += this.perneira.dano;
        }
        if(this.calcado != null){
            dano += this.calcado.dano;
        }
        dano += this.forca;
        this.dano = dano;
    }

    public void recalculaDefesa(){
        int defesa = 0;
        if(this.capacete != null){
            defesa += this.capacete.defesa;
        }
        if(this.armadura != null){
            defesa += this.armadura.defesa;
        }
        if(this.espada != null){
            defesa += this.espada.defesa;
        }
        if(this.escudo != null){
            defesa += this.escudo.defesa;
        }
        if(this.perneira != null){
            defesa += this.perneira.defesa;
        }
        if(this.calcado != null){
            defesa += this.calcado.defesa;
        }
        defesa += this.defesa;
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

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getSenha() { return senha;}

    public void setSenha(String senha) { this.senha = senha; }

    public Capacete getCapacete() {return capacete;}

    public void setCapacete(Capacete capacete) { this.capacete = capacete; }

    public Armadura getArmadura() { return armadura; }

    public void setArmadura(Armadura armadura) { this.armadura = armadura;}

    public Espada getEspada() { return espada;}

    public void setEspada(Espada espada) { this.espada = espada; }

    public Escudo getEscudo() {return escudo;}

    public void setEscudo(Escudo escudo) {this.escudo = escudo; }

    public Perneira getPerneira() { return perneira;}

    public void setPerneira(Perneira perneira) { this.perneira = perneira; }

    public Calcado getCalcado() { return calcado;}

    public void setCalcado(Calcado calcado) { this.calcado = calcado; }

    public void aumentarOuro(int valor){
        this.ouro += valor;
    }

    public void diminuirOuro(int valor){
        this.ouro -= valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getOuro() {
        return ouro;
    }

    public void setOuro(int ouro) {
        this.ouro = ouro;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getExpProxLevel() {
        return expProxLevel;
    }

    public void setExpProxLevel(int expProxLevel) {
        this.expProxLevel = expProxLevel;
    }

    public int getForca() {
        return forca;
    }

    public void setForca(int forca) {
        this.forca = forca;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = vidaAtual;
    }

    public int getVidaMax() {
        return vidaMax;
    }

    public void setVidaMax(int vidaMax) {
        this.vidaMax = vidaMax;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public ArrayList<Item> getInventario() {
        return inventario;
    }

    public void setInventario(ArrayList<Item> inventario) {
        this.inventario = inventario;
    }
}
