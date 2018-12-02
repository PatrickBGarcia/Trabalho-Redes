package responses;

import com.google.gson.Gson;

public class Response {
    public int codigo;
    public String descricao;

    public String createResponse(ResponseTypes type){
        this.codigo = type.getCodigo();
        this.descricao = type.getDescricao();

        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
