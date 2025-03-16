package com.jogodamas.domain;

import com.jogodamas.dto.Jogada;

public class JogadaParaRelatorio {
    Jogada jogada;
    String jogador;

    public JogadaParaRelatorio(Jogada jogada, String jogador) {
        this.jogada = jogada;
        this.jogador = jogador;
    }
    
    public Jogada getJogada() {
        return jogada;
    }
    
    public String getNomeJogador() {
        return jogador;
    }
}


