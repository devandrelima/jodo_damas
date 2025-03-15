package com.jogodamas.services.pecarainha.direita;

import com.jogodamas.domain.Coordenada;
import com.jogodamas.domain.Jogo;
import com.jogodamas.domain.Peca;

public class DireitaNaturalPecaRainha {

    public Coordenada[] buscarCoordenadaDireitaNaturalJogadorDeCimaPecaRainha(Peca pecaAtual, Jogo tabuleiro, int buscador, Coordenada[] coordenadas, boolean inimigo) {
        //Coordenada[] coordenadas = new Coordenada[5];
        //int contador = 0;

        int proxLinha = pecaAtual.getCoordenadas().getX() + 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() + 1;

        if (proxLinha > 7 || proxColuna > 7) { // Não sai do tabuleiro, para quando chega no limite
            coordenadas[buscador] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(new Coordenada(proxLinha, proxColuna));

        if (proxPeca == null) { // Não tem peça na próxima coordenada
            coordenadas[buscador] = new Coordenada(proxLinha, proxColuna);
            buscador++;
            return buscarCoordenadaDireitaNaturalJogadorDeCimaPecaRainha(proxPeca, tabuleiro, buscador, coordenadas, false);

        } else if (proxPeca.getId() >= 12) { // A próxima peca é inimiga, talvez tenha como comer ela
            if (inimigo) { // A próxima peça é inimiga e tem outra peça protegendo
                coordenadas[buscador] = null;
                return coordenadas;
            }

            return buscarCoordenadaDireitaNaturalJogadorDeCimaPecaRainha(proxPeca, tabuleiro, buscador, coordenadas, true);

        } else {
            coordenadas[buscador] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas; // para quando encontra amiga
        }
    }
}
