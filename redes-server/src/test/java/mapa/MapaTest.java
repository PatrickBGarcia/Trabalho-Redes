package mapa;

import org.junit.Assert;
import org.junit.Test;

public class MapaTest {

    @Test
    public void criaMapa(){
        Sala inicial = Mapa.iniciar();
        Assert.assertEquals(inicial.getNome(), "Portao do Anglo");
        Sala obraRU = inicial.getSalaAoRedor().get(Direcao.NORTE);
        Assert.assertEquals(obraRU.getNome(), "Obra do RU");
    }
}
