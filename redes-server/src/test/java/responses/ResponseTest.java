package responses;

import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseTest {

    @Test
    public void testaSerializacao() {
        String respostaSucesso = "{\"codigo\":1,\"descricao\":\"Comando realizado com sucesso\"}";
        Gson gson = new Gson();

        Response response = new Response();

        assertEquals(respostaSucesso, response.createResponse(ResponseTypes.SUCESSO));
    }
}

