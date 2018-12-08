package responses;

public enum ResponseTypes {
    SUCESSO, USUARIO_JA_EXISTE, SENHAS_DIFERENTES, USUARIO_NAO_EXISTE, SENHA_INCORRETA, MOVER_INVALIDO, ATACAR_INVALIDO, VER_INVALIDO,
    USAR_INVALIDO, CONVERSAR_INVALIDO, COMPRA_VENDA_ITEM_NAO_ENCONTRADO, SEM_QUANTIDADE_PRA_VENDER, SEM_OURO_SUFICIENTE,
    COMANDO_INVALIDO, ERRO_INTERNO;

    public String getDescricao(){
        switch(this){
            case SUCESSO:
                return "Comando realizado com sucesso";
            case USUARIO_JA_EXISTE:
                return "Este usuário ja existe";
            case SENHAS_DIFERENTES:
                return "As senhas informadas não são iguais";
            case USUARIO_NAO_EXISTE:
                return "Usuário não existe";
            case SENHA_INCORRETA:
                return "Senha incorreta";
            case MOVER_INVALIDO:
                return "Não existe saída desta sala por está direção";
            case ATACAR_INVALIDO:
                return "Não existe oponente com o nome informado";
            case VER_INVALIDO:
                return "Não encontrado o que você deseja ver";
            case USAR_INVALIDO:
                return "Você não possui o item que deseja utilizar";
            case CONVERSAR_INVALIDO:
                return "Não encontrado NPC com este nome";
            case COMPRA_VENDA_ITEM_NAO_ENCONTRADO:
                return "Não encontrado item com este nome";
            case SEM_QUANTIDADE_PRA_VENDER:
                return "Você não possui a quantidade informada do item que deseja vender";
            case SEM_OURO_SUFICIENTE:
                return "Você não possui ouro suficiente";
            case COMANDO_INVALIDO:
                return "Comando invalido";
            case ERRO_INTERNO:
                return "Erro interno";

                default:
                    return "Erro interno";
        }
    }

    public int getCodigo(){
        switch(this){
            case SUCESSO:
                return 1;
            case USUARIO_JA_EXISTE:
                return -1;
            case SENHAS_DIFERENTES:
                return -2;
            case USUARIO_NAO_EXISTE:
                return -3;
            case SENHA_INCORRETA:
                return -4;
            case MOVER_INVALIDO:
                return -5;
            case ATACAR_INVALIDO:
                return -6;
            case VER_INVALIDO:
                return -7;
            case USAR_INVALIDO:
                return -8;
            case CONVERSAR_INVALIDO:
                return -9;
            case COMPRA_VENDA_ITEM_NAO_ENCONTRADO:
                return -10;
            case SEM_QUANTIDADE_PRA_VENDER:
                return -11;
            case SEM_OURO_SUFICIENTE:
                return -12;
            case COMANDO_INVALIDO:
                return 0;
            case ERRO_INTERNO:
                return 500;

                default:
                    return 500;
        }
    }
}
