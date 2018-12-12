package mapa;

public class Mapa {

    public static Sala iniciar(){
        Sala portao = new Sala("Portao do Anglo");
        Sala biblioteca = new Sala("Biblioteca");
        Sala obraRU = new Sala("Obra do RU");
        Sala estacionamento = new Sala("Estacionamento");
        Sala corredorPrimeiroAndar = new Sala("Corredor 1 Andar");
        Sala cafeteria = new Sala("Cafe Universitario");
        Sala corredorSegundoAndar = new Sala("Corredor 2 Andar");
        Sala corredorTerceiroAndar = new Sala("Corredor 3 Andar");
        Sala corredorQuartoAndar = new Sala("Corredor 4 Andar");

        corredorQuartoAndar.addAdjacente(corredorTerceiroAndar, Direcao.ABAIXO);
        corredorTerceiroAndar.addAdjacente(corredorQuartoAndar, Direcao.A_CIMA);
        corredorTerceiroAndar.addAdjacente(corredorSegundoAndar, Direcao.ABAIXO);
        corredorSegundoAndar.addAdjacente(corredorTerceiroAndar, Direcao.A_CIMA);
        corredorSegundoAndar.addAdjacente(corredorPrimeiroAndar, Direcao.ABAIXO);
        corredorPrimeiroAndar.addAdjacente(corredorSegundoAndar, Direcao.A_CIMA);
        corredorPrimeiroAndar.addAdjacente(cafeteria, Direcao.NORTE);
        cafeteria.addAdjacente(corredorPrimeiroAndar, Direcao.SUL);
        corredorPrimeiroAndar.addAdjacente(estacionamento, Direcao.SUL);
        estacionamento.addAdjacente(corredorPrimeiroAndar, Direcao.NORTE);
        estacionamento.addAdjacente(obraRU, Direcao.SUL);
        obraRU.addAdjacente(estacionamento, Direcao.NORTE);
        obraRU.addAdjacente(portao, Direcao.SUL);
        portao.addAdjacente(obraRU, Direcao.NORTE);
        biblioteca.addAdjacente(portao, Direcao.LESTE);
        portao.addAdjacente(biblioteca, Direcao.OESTE);

        return portao;
    }
}
