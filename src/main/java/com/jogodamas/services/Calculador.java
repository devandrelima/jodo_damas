package com.jogodamas.services;

import com.jogodamas.domain.Coordenada;
import com.jogodamas.domain.Peca;
import com.jogodamas.domain.Jogo;

public class Calculador {
    public Coordenada[] calcularPossiveisJogadas(int id, Jogo tabuleiro) {
        Peca pecaAtual = tabuleiro.buscarPecaPorID(id);
        Coordenada coordenadasCalculadas[] = new Coordenada[31];

        coordenadasCalculadas = passarVetorMenorParaMaior(coordenadasCalculadas,
                buscarCoordenadaDireitaComedora(pecaAtual, tabuleiro));

        coordenadasCalculadas = passarVetorMenorParaMaior(coordenadasCalculadas,
                buscarCoordenadaEsquerdaComedora(pecaAtual, tabuleiro));

        if(coordenadasCalculadas[0] == null) { // Obriga a voltar comendo se não for nulo
            coordenadasCalculadas = passarVetorMenorParaMaior(coordenadasCalculadas,
                    buscarCoordenadaDireitaNatural(pecaAtual, tabuleiro));

            try {
                if((coordenadasCalculadas[0].getX() == pecaAtual.getCoordenadas().getX() + 2 && coordenadasCalculadas[0].getY() == pecaAtual.getCoordenadas().getY() + 2)
                        || (coordenadasCalculadas[0].getX() == pecaAtual.getCoordenadas().getX() - 2 && coordenadasCalculadas[0].getY() == pecaAtual.getCoordenadas().getY() + 2)) {

                    return coordenadasCalculadas;
                }
            } catch (NullPointerException e) {

            }

            coordenadasCalculadas = passarVetorMenorParaMaior(coordenadasCalculadas,
                    buscarCoordenadaEsquerdaNatural(pecaAtual, tabuleiro));
        }

        return coordenadasCalculadas;
    }

    private Coordenada[] passarVetorMenorParaMaior(Coordenada maior[], Coordenada menor[]){
        int contador = 0;
        Coordenada caixaDeSeguranca;

        while(maior[contador] != null){
            contador++;
        }

        for(int j = 0; j < menor.length; j++){
            caixaDeSeguranca = menor[j];

            if(caixaDeSeguranca == null){
                break;
            }

            maior[contador] = caixaDeSeguranca;
        }

        return maior;
    }

    // DIREITA NATURAL
    /* OBS: A direção é a do XX

        [--]
    [--] 08 [--]
        [--] XX
    [--] 12 [--]
        [--]

     */

    private Coordenada[] buscarCoordenadaDireitaNatural(Peca pecaAtual, Jogo tabuleiro) {

        if(!pecaAtual.isRainha()) { // Não é rainha

            if (pecaAtual.getId() <= 11) { // Peças do Jogador de Cima
                return buscarCoordenadaDireitaNaturalJogadorDeCimaPecaNormal(pecaAtual, tabuleiro, 0);

            } else { // Peças do Jogador de Baixo

                return buscarCoordenadaDireitaNaturalJogadorDeBaixoPecaNormal(pecaAtual, tabuleiro, 0);

            }
        } else { // é rainha
            System.out.println("Está em andamento");

            if (pecaAtual.getId() <= 11) { // Peças do Jogador de Cima
                return buscarCoordenadaDireitaNaturalJogadorDeCimaPecaRainha(pecaAtual, tabuleiro, 0, new Coordenada[6], false);

            } else { // Peças do Jogador de Baixo

                //return buscarCoordenadaDireitaNaturalJogadorDeBaixoPecaRainha(pecaAtual, tabuleiro, 0);

            }
        }

        return null;
    }

    // DIREITA COMEDORA
    /* OBS: A direção é a do XX

        [--] XX
    [--] 08 [--]
        [--]
    [--] 12 [--]
        [--] XX

     */

    private Coordenada[] buscarCoordenadaDireitaComedora(Peca pecaAtual, Jogo tabuleiro) {

        if(!pecaAtual.isRainha()) { // Não é rainha

            if (pecaAtual.getId() <= 11) { // Peças do Jogador de Cima
                return buscarCoordenadaDireitaComedoraJogadorDeCimaPecaNormal(pecaAtual, tabuleiro, 0);

            } else { // Peças do Jogador de Baixo

                return buscarCoordenadaDireitaComedoraJogadorDeBaixoPecaNormal(pecaAtual, tabuleiro, 0);

            }
        } else { // é rainha
            System.out.println("Rainha ainda não está pronta");
        }

        return null;
    }

    // ESQUERDA NATURAL
    /*  OBS: A direção é a do XX

        [--]
    [--] 08 [--]
     XX [--]
    [--] 12 [--]
        [--]

    */
    private Coordenada[] buscarCoordenadaEsquerdaNatural(Peca pecaAtual, Jogo tabuleiro) {

        if(!pecaAtual.isRainha()) { // Não é rainha

            if (pecaAtual.getId() <= 11) { // Pessoas do Jogador de Cima

                return buscarCoordenadaEsquerdaNaturalJogadorDeCimaPecaNormal(pecaAtual, tabuleiro, 0);

            } else { // Pessoas do Jogador de Baixo

                return buscarCoordenadaEsquerdaNaturalJogadorDeBaixo(pecaAtual, tabuleiro, 0);

            }
        } else {
            System.out.println("Rainha ainda precisa ser construída");
        }

        return null;
    }

    // ESQUERDA COMEDORA
    /*  OBS: A direção é a do XX

     XX [--]
    [--] 08 [--]
        [--]
    [--] 12 [--]
     XX [--]

    */

    private Coordenada[] buscarCoordenadaEsquerdaComedora(Peca pecaAtual, Jogo tabuleiro) {

        if(!pecaAtual.isRainha()) { // Não é rainha

            if (pecaAtual.getId() <= 11) { // Pessoas do Jogador de Cima

                return buscarCoordenadaEsquerdaComedoraJogadorDeCimaPecaNormal(pecaAtual, tabuleiro, 0);

            } else { // Pessoas do Jogador de Baixo

                return buscarCoordenadaEsquerdaComedoraJogadorDeBaixoPecaNormal(pecaAtual, tabuleiro, 0);

            }
        } else {
            System.out.println("Rainha ainda precisa ser construída");
        }

        return null;
    }

    private Coordenada[] buscarCoordenadaDireitaNaturalJogadorDeCimaPecaNormal(Peca pecaAtual, Jogo tabuleiro, int buscador){
        Coordenada[] coordenadas = new Coordenada[5];
        int contador = 0;

        int proxLinha = pecaAtual.getCoordenadas().getX() + 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() + 1;

        if (proxLinha > 7 || proxColuna > 7) { // Não sai do tabuleiro
            coordenadas[0] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(new Coordenada(proxLinha, proxColuna));

        if(proxPeca == null) { // Não tem peça na próxima coordenada
            coordenadas[0] = new Coordenada(proxLinha,proxColuna);
            return coordenadas;

        } else if(proxPeca.getId() >= 12){ // A próxima peca é inimiga, talvez tenha como comer ela
            if(buscador > 1) { // A próxima peça é inimiga e tem outra peça protegendo
                coordenadas[0] = null;
                return coordenadas;
            }

            return buscarCoordenadaDireitaNaturalJogadorDeCimaPecaNormal(new Peca(false, pecaAtual.getId(), new Coordenada(proxLinha, proxColuna)), tabuleiro, ++buscador); // fazer varredura

        } else {
            coordenadas[0] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas;
        }
    }

    private Coordenada[] buscarCoordenadaDireitaNaturalJogadorDeCimaPecaRainha(Peca pecaAtual, Jogo tabuleiro, int buscador, Coordenada[] coordenadas, boolean inimigo){
        //Coordenada[] coordenadas = new Coordenada[5];
        //int contador = 0;

        int proxLinha = pecaAtual.getCoordenadas().getX() + 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() + 1;

        if (proxLinha > 7 || proxColuna > 7) { // Não sai do tabuleiro, para quando chega no limite
            coordenadas[buscador] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(new Coordenada(proxLinha, proxColuna));

        if(proxPeca == null) { // Não tem peça na próxima coordenada
            coordenadas[buscador] = new Coordenada(proxLinha,proxColuna);
            buscador++;
            return buscarCoordenadaDireitaNaturalJogadorDeCimaPecaRainha(proxPeca, tabuleiro, buscador, coordenadas, false);

        } else if(proxPeca.getId() >= 12){ // A próxima peca é inimiga, talvez tenha como comer ela
            if(inimigo) { // A próxima peça é inimiga e tem outra peça protegendo
                coordenadas[buscador] = null;
                return coordenadas;
            }

            return buscarCoordenadaDireitaNaturalJogadorDeCimaPecaRainha(proxPeca, tabuleiro, buscador, coordenadas, true);

        } else {
            coordenadas[buscador] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas; // para quando encontra amiga
        }
    }

    private Coordenada[] buscarCoordenadaDireitaComedoraJogadorDeCimaPecaNormal(Peca pecaAtual, Jogo tabuleiro, int buscador){
        Coordenada[] coordenadas = new Coordenada[5];
        int contador = 0;

        int proxLinha = pecaAtual.getCoordenadas().getX() - 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() + 1;

        if (proxLinha < 0 || proxColuna > 7) { // Não sai do tabuleiro
            coordenadas[0] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(new Coordenada(proxLinha, proxColuna));

        if(proxPeca == null) { // Não tem peça na próxima coordenada
            if(buscador > 0) { // A varredura deu certo e pode pular o inimigo
                coordenadas[0] = new Coordenada(proxLinha,proxColuna);
                return coordenadas;
            }
            coordenadas[0] = null;
            return coordenadas;

        } if(proxPeca.getId() >= 12){ // A próxima peca é inimiga, talvez tenha como comer ela
            if(buscador > 1) { // A próxima peça é inimiga e tem outra peça protegendo
                coordenadas[0] = null;
                return coordenadas;
            }

            return buscarCoordenadaDireitaComedoraJogadorDeCimaPecaNormal(new Peca(false, pecaAtual.getId(), new Coordenada(proxLinha, proxColuna)), tabuleiro, ++buscador); // fazer varredura

        } else {
            coordenadas[0] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas;
        }
    }


    private Coordenada[] buscarCoordenadaDireitaNaturalJogadorDeBaixoPecaNormal(Peca pecaAtual, Jogo tabuleiro, int buscador){
        Coordenada[] coordenadas = new Coordenada[5];
        int contador = 0;

        int proxLinha = pecaAtual.getCoordenadas().getX() - 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() + 1;

        if (proxLinha < 0 || proxColuna > 7) { // Não sai do tabuleiro
            coordenadas[contador++] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(new Coordenada(proxLinha, proxColuna));

        if(proxPeca == null) { // Não tem peça na próxima coordenada
            coordenadas[contador++] = new Coordenada(proxLinha,proxColuna);

            return coordenadas;

        } else if(proxPeca.getId() <= 11){ // A próxima peca é inimiga, talvez tenha como comer ela
            if(buscador > 1) { // A próxima peça é inimiga e tem outra peça protegendo
                coordenadas[0] = null;
                return coordenadas;
            }

            return buscarCoordenadaDireitaNaturalJogadorDeBaixoPecaNormal(new Peca(false, pecaAtual.getId(), new Coordenada(proxLinha, proxColuna)), tabuleiro, ++buscador); // fazer varredura

        } else {
            coordenadas[contador++] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas;
        }
    }

    private Coordenada[] buscarCoordenadaDireitaComedoraJogadorDeBaixoPecaNormal(Peca pecaAtual, Jogo tabuleiro, int buscador){
        Coordenada[] coordenadas = new Coordenada[5];

        int proxLinha = pecaAtual.getCoordenadas().getX() + 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() + 1;

        if (proxLinha > 7 || proxColuna > 7) { // Não sai do tabuleiro
            coordenadas[0] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(new Coordenada(proxLinha, proxColuna));

        if(proxPeca == null) { // Não tem peça na próxima coordenada
            if(buscador > 0) { // A varredura deu certo e pode pular o inimigo
                coordenadas[0] = new Coordenada(proxLinha,proxColuna);
                return coordenadas;
            }
            coordenadas[0] = null;
            return coordenadas;

        } if(proxPeca.getId() <= 11){ // A próxima peca é inimiga, talvez tenha como comer ela
            if(buscador > 1) { // A próxima peça é inimiga e tem outra peça protegendo
                coordenadas[0] = null;
                return coordenadas;
            }

            return buscarCoordenadaDireitaComedoraJogadorDeBaixoPecaNormal(new Peca(false, pecaAtual.getId(), new Coordenada(proxLinha, proxColuna)), tabuleiro, ++buscador); // fazer varredura

        } else {
            coordenadas[0] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas;
        }
    }

    private Coordenada[] buscarCoordenadaEsquerdaNaturalJogadorDeCimaPecaNormal(Peca pecaAtual, Jogo tabuleiro, int buscador){
        Coordenada[] coordenadas = new Coordenada[5];
        int contador = 0;

        int proxLinha = pecaAtual.getCoordenadas().getX() + 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() - 1;

        if (proxLinha > 7 || proxColuna < 0) { // Não sai do tabuleiro
            coordenadas[contador++] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(new Coordenada(proxLinha, proxColuna));

        if(proxPeca == null) { // Não tem peça na próxima coordenada
            coordenadas[contador++] = new Coordenada(proxLinha,proxColuna);
            return coordenadas;

        } else if(proxPeca.getId() >= 12){ // A próxima peca é inimiga, talvez tenha como comer ela
            if(buscador > 1) { // A próxima peça é inimiga e tem outra peça protegendo
                coordenadas[0] = null;
                return coordenadas;
            }

            return buscarCoordenadaEsquerdaNaturalJogadorDeCimaPecaNormal(new Peca(false, pecaAtual.getId(), new Coordenada(proxLinha, proxColuna)), tabuleiro, ++buscador); // fazer varredura

        } else {
            coordenadas[contador++] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas;
        }
    }

    private Coordenada[] buscarCoordenadaEsquerdaComedoraJogadorDeCimaPecaNormal(Peca pecaAtual, Jogo tabuleiro, int buscador){
        Coordenada[] coordenadas = new Coordenada[5];

        int proxLinha = pecaAtual.getCoordenadas().getX() - 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() - 1;

        if (proxLinha < 0 || proxColuna < 0) { // Não sai do tabuleiro
            coordenadas[0] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(new Coordenada(proxLinha, proxColuna));

        if(proxPeca == null) { // Não tem peça na próxima coordenada
            if(buscador > 0) { // A varredura deu certo e pode pular o inimigo
                coordenadas[0] = new Coordenada(proxLinha,proxColuna);
                return coordenadas;
            }
            coordenadas[0] = null;
            return coordenadas;

        } if(proxPeca.getId() >= 12){ // A próxima peca é inimiga, talvez tenha como comer ela
            if(buscador > 1) { // A próxima peça é inimiga e tem outra peça protegendo
                coordenadas[0] = null;
                return coordenadas;
            }

            return buscarCoordenadaEsquerdaComedoraJogadorDeCimaPecaNormal(new Peca(false, pecaAtual.getId(), new Coordenada(proxLinha, proxColuna)), tabuleiro, ++buscador); // fazer varredura

        } else {
            coordenadas[0] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas;
        }
    }

    private Coordenada[] buscarCoordenadaEsquerdaNaturalJogadorDeBaixo(Peca pecaAtual, Jogo tabuleiro, int buscador){
        Coordenada[] coordenadas = new Coordenada[5];
        int contador = 0;

        int proxLinha = pecaAtual.getCoordenadas().getX() - 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() - 1;

        if (proxLinha < 0 || proxColuna < 0) { // Não sai do tabuleiro
            coordenadas[contador++] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(new Coordenada(proxLinha, proxColuna));

        if(proxPeca == null) { // Não tem peça na próxima coordenada
            coordenadas[0] = new Coordenada(proxLinha,proxColuna);

            return coordenadas;

        } else if(proxPeca.getId() <= 11){ // A próxima peca é inimiga, talvez tenha como comer ela
            if(buscador > 1) { // A próxima peça é inimiga e tem outra peça protegendo
                coordenadas[0] = null;
                return coordenadas;
            }

            return buscarCoordenadaEsquerdaNaturalJogadorDeBaixo(new Peca(false, pecaAtual.getId(), new Coordenada(proxLinha, proxColuna)), tabuleiro, ++buscador); // fazer varredura

        } else {
            coordenadas[contador++] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas;
        }
    }

    private Coordenada[] buscarCoordenadaEsquerdaComedoraJogadorDeBaixoPecaNormal(Peca pecaAtual, Jogo tabuleiro, int buscador){
        Coordenada[] coordenadas = new Coordenada[5];
        int contador = 0;

        int proxLinha = pecaAtual.getCoordenadas().getX() + 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() - 1;

        if (proxLinha < 0 || proxColuna < 0) { // Não sai do tabuleiro
            coordenadas[contador++] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(new Coordenada(proxLinha, proxColuna));

        if(proxPeca == null) { // Não tem peça na próxima coordenada
            if(buscador > 0) { // A varredura deu certo e pode pular o inimigo
                coordenadas[0] = new Coordenada(proxLinha,proxColuna);
                return coordenadas;
            }
            coordenadas[0] = null;
            return coordenadas;

        } else if(proxPeca.getId() <= 11){ // A próxima peca é inimiga, talvez tenha como comer ela
            if(buscador > 1) { // A próxima peça é inimiga e tem outra peça protegendo
                coordenadas[0] = null;
                return coordenadas;
            }

            return buscarCoordenadaEsquerdaComedoraJogadorDeBaixoPecaNormal(new Peca(false, pecaAtual.getId(), new Coordenada(proxLinha, proxColuna)), tabuleiro, ++buscador); // fazer varredura

        } else {
            coordenadas[contador++] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas;
        }
    }
}