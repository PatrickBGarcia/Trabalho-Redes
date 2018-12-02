package requests;

import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequisicaoTest {

    @Test
    public void testaSerializacao(){
        Requisicao requisicao = new Requisicao();
        requisicao.acao = "registrar";
        requisicao.argumentos = new String[3];
        requisicao.argumentos[0] = "matheus";
        requisicao.argumentos[1] = "1234";
        requisicao.argumentos[2] = "1234";

        String reqString = "{\"acao\":\"registrar\",\"argumentos\":[\"matheus\",\"1234\",\"1234\"]}";
        Gson gson = new Gson();

        assertEquals(reqString, gson.toJson(requisicao));
    }

    @Test
    public void testaDesserializacao(){
        String reqString = "{\"acao\": \"registrar\", \"argumentos\":[\"matheus\", \"1234\", \"1234\"]}";
        Gson gson = new Gson();
        Requisicao requisicao = gson.fromJson(reqString, Requisicao.class);

        assertEquals(requisicao.acao, "registrar");
        assertEquals(requisicao.argumentos[0], "matheus");
        assertEquals(requisicao.argumentos[1], "1234");
        assertEquals(requisicao.argumentos[2], "1234");
    }
}
