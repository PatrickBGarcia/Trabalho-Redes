package responses;

import com.google.gson.Gson;
import personagem.Personagem;

public class SecondResponse {
    public Personagem personagem;
    public String mensagemAdicional;

    public String toJson(Personagem personagem, String mensagem){
        this.personagem = personagem;
        this.mensagemAdicional = mensagem;

        return new Gson().toJson(this);
    }
}
