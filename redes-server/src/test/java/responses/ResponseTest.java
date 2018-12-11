package responses;

import com.google.gson.Gson;
import org.junit.Test;
import personagem.Personagem;

import static org.junit.Assert.assertEquals;

public class ResponseTest {

    @Test
    public void testaSerializacaoResposta() {
        String respostaSucesso = "{\"codigo\":1,\"descricao\":\"Comando realizado com sucesso\"}";

        Response response = new Response();

        assertEquals(respostaSucesso, response.createResponse(ResponseTypes.SUCESSO));
    }

    @Test
    public void testaSerializacaoPersonagem() {
        Personagem personagem = new Personagem("Matheus", "1234");

        String personagemString = "{\"id\":0,\"nome\":\"Matheus\",\"senha\":\"1234\",\"nivel\":1,\"ouro\":0,\"exp\":0,\"expProxLevel\":100,\"forca\":5,\"vidaAtual\":20,\"vidaMax\":20,\"defesa\":0,\"dano\":5,\"inventario\":[]}";

        assertEquals(personagemString, new Gson().toJson(personagem));
    }
}

