package personagem;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import itens.Item;
import itens.combate.Set;
import itens.consumivel.Pot;

import java.util.ArrayList;

@DatabaseTable(tableName = "personagem")
public class Personagem {
    @DatabaseField
    public String nome;
    //public final String nome;     tive que criar um atributo nome sem ser final pro construtor do ORMLite
    @DatabaseField
    public int nivel = 1;
    @DatabaseField
    public int ouro = 0;
    @DatabaseField
    public int exp = 0;
    @DatabaseField
    public int expProxLevel = 100;
    @DatabaseField
    public int forca = 5;
    @DatabaseField
    public int vidaAtual = 20;
    @DatabaseField
    public int vidaMax = 20;
    @DatabaseField
    public int defesa = 0;
    @DatabaseField
    public int dano = 5;
    @DatabaseField
    public Set equipamentos = new Set();
    @DatabaseField(foreign = true)
    public ArrayList<Item> inventario = new ArrayList<Item>();

    public Personagem(){}
    public Personagem(String nome){
        this.nome = nome;
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
        return new Personagem(this.nome);
    }

    public void recalculaDano(){
        int dano = 0;
        if(this.equipamentos != null){
            if(this.equipamentos.armor != null){
                dano += this.equipamentos.armor.dano;
            }
            if(this.equipamentos.helmet != null){
                dano += this.equipamentos.helmet.dano;
            }
            if(this.equipamentos.legs != null){
                dano += this.equipamentos.legs.dano;
            }
            if(this.equipamentos.shield != null){
                dano += this.equipamentos.shield.dano;
            }
            if(this.equipamentos.shoes != null){
                dano += this.equipamentos.shoes.dano;
            }
            if(this.equipamentos.sword != null){
                dano += this.equipamentos.sword.dano;
            }
        }
        dano += this.forca;
        this.dano = dano;
    }

    public void recalculaDefesa(){
        int defesa = 0;
        if(this.equipamentos != null){
            if(this.equipamentos.armor != null){
                defesa += this.equipamentos.armor.defesa;
            }
            if(this.equipamentos.helmet != null){
                defesa += this.equipamentos.helmet.defesa;
            }
            if(this.equipamentos.legs != null){
                defesa += this.equipamentos.legs.defesa;
            }
            if(this.equipamentos.shield != null){
                defesa += this.equipamentos.shield.defesa;
            }
            if(this.equipamentos.shoes != null){
                defesa += this.equipamentos.shoes.defesa;
            }
            if(this.equipamentos.sword != null){
                defesa += this.equipamentos.sword.defesa;
            }
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

    public Set getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(Set equipamentos) {
        this.equipamentos = equipamentos;
    }

    public ArrayList<Item> getInventario() {
        return inventario;
    }

    public void setInventario(ArrayList<Item> inventario) {
        this.inventario = inventario;
    }
}
