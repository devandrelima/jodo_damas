package com.jogodamas.services.pecarainha.esquerda;

import com.jogodamas.domain.Coordenada;
import com.jogodamas.domain.Jogo;
import com.jogodamas.domain.Peca;

public class EsquerdaComedoraPecaRainha {
    public Coordenada[] buscarCoordenadaJogadorDeCima(Peca pecaAtual, Jogo tabuleiro, int buscador, Coordenada[] coordenadas, boolean inimigo) {
        //Coordenada[] coordenadas = new Coordenada[5];
        //int contador = 0;

        int proxLinha = pecaAtual.getCoordenadas().getX() - 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() - 1;

        if (proxLinha < 0 || proxColuna < 0) { // Não sai do tabuleiro, para quando chega no limite
            coordenadas[buscador] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(new Coordenada(proxLinha, proxColuna));

        if (proxPeca == null) { // Não tem peça na próxima coordenada
            coordenadas[buscador] = new Coordenada(proxLinha, proxColuna);
            buscador++;
            return buscarCoordenadaJogadorDeCima(new Peca(false, pecaAtual.getId(), new Coordenada(proxLinha, proxColuna)), tabuleiro, buscador, coordenadas, false);

        } else if (proxPeca.getId() >= 12) { // A próxima peca é inimiga, talvez tenha como comer ela
            if (inimigo) { // A próxima peça é inimiga e tem outra peça protegendo
                coordenadas[buscador] = null;
                return coordenadas;
            }

            return buscarCoordenadaJogadorDeCima(proxPeca, tabuleiro, buscador, coordenadas, true);

        } else {
            coordenadas[buscador] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas; // para quando encontra amiga
        }
    }
}
