package com.jogodamas.domain;

import com.jogodamas.dto.Jogada;

public class JogadaParaRelatorio {
    Jogada jogada;
    int jogador;

    public JogadaParaRelatorio(Jogada jogada, int jogador) {
        this.jogada = jogada;
        this.jogador = jogador;
    }
}
