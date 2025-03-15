package com.jogodamas.services.pecanormal.esquerda;

import com.jogodamas.domain.Coordenada;
import com.jogodamas.domain.Jogo;
import com.jogodamas.domain.Peca;

public class EsquerdaComedora {

    public Coordenada[] buscarCoordenadaEsquerdaComedoraJogadorDeCimaPecaNormal(Peca pecaAtual, Jogo tabuleiro, int buscador){
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
            if(buscador > 0) { // A próxima peça é inimiga e tem outra peça protegendo
                coordenadas[0] = null;
                return coordenadas;
            }

            return buscarCoordenadaEsquerdaComedoraJogadorDeCimaPecaNormal(new Peca(false, pecaAtual.getId(), new Coordenada(proxLinha, proxColuna)), tabuleiro, ++buscador); // fazer varredura

        } else {
            coordenadas[0] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas;
        }
    }

    public Coordenada[] buscarCoordenadaEsquerdaComedoraJogadorDeBaixoPecaNormal(Peca pecaAtual, Jogo tabuleiro, int buscador){
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
            if(buscador > 0) { // A varredura deu certo e pode pular o inimigo
                coordenadas[0] = new Coordenada(proxLinha,proxColuna);
                return coordenadas;
            }
            coordenadas[0] = null;
            return coordenadas;

        } else if(proxPeca.getId() <= 11){ // A próxima peca é inimiga, talvez tenha como comer ela
            if(buscador > 0) { // A próxima peça é inimiga e tem outra peça protegendo
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
