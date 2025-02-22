package com.jogodamas.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Jogador {
    private int id;
    private String nome;
    private Pilha<Peca> pilhaPecas;
}
