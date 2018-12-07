package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import personagem.Personagem;

@DatabaseTable(tableName = "usuario")
public class Usuario {
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField
    public String usuario;
    @DatabaseField
    public String senha;

    public Usuario(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
