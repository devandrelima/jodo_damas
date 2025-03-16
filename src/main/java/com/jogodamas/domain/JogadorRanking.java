package com.jogodamas.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JogadorRanking implements Comparable<JogadorRanking> {
  String nome;
  int vitorias;
  int empates;
  int partidas;
  int derrotas;

  public JogadorRanking(String nome) {
      this.nome = nome;
      this.vitorias = 0;
      this.empates = 0;
      this.partidas = 0;
      this.derrotas = 0;
  }

  public void registrarVitoria() {
      vitorias++;
      partidas++;
  }

  public void registrarEmpate() {
      empates++;
      partidas++;
  }

  public void registrarDerrota() {
    derrotas++;
    partidas++;
}

  @Override
  public int compareTo(JogadorRanking outro) {
      // Ordenação decrescente: mais vitórias primeiro
      if (this.vitorias != outro.vitorias) return Integer.compare(outro.vitorias, this.vitorias);
      if (this.empates != outro.empates) return Integer.compare(outro.empates, this.empates);
      return Integer.compare(outro.partidas, this.partidas);
  }

  @Override
  public String toString() {
      return "Jogador: " + nome + ", Vitórias: " + vitorias + ", Empates: " + empates + ", Partidas: " + partidas;
  }
}

