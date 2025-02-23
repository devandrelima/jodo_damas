package com.jogodamas.services;

import com.jogodamas.domain.Coordenada;
import com.jogodamas.domain.Peca;
import com.jogodamas.domain.Tabuleiro;

public class Calculador {
    public Coordenada[] calcularPossiveisJogadas(int id, Tabuleiro tabuleiro) {
        Peca pecaAtual = tabuleiro.buscarPecaPorID(id);
        Coordenada coordenadasCalculadas[] = {};





        return coordenadasCalculadas;
    }

    private Coordenada[] buscarCoordenadaDireita(Peca pecaAtual) {

        if(!pecaAtual.isRainha()) { // Não é rainha

            if (pecaAtual.getId() <= 11) { // Pessoas do Jogador de Cima
                int proxLinha = pecaAtual.getCoordenadas().getX() + 1;
                int proxColuna = pecaAtual.getCoordenadas().getY() + 1;

                if (proxLinha > 7) {
                }
            } else { // Pessoas do Jogador de Baixo

            }
        }

        return null; // muda isso!!!
    }
}
