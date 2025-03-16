package com.jogodamas.dto;

import com.jogodamas.domain.Coordenada;
import com.jogodamas.domain.Jogador;
import com.jogodamas.domain.Peca;
import com.jogodamas.domain.Pilha;

public record StatusJogoAtual(Peca peca, Object[] jogador1, Object[] jogador2, boolean acabou) {}
