package com.jogodamas.domain;

public class Ranking {
  private TreeMap<JogadorRanking> jogadores;

  public Ranking() {
      jogadores = new TreeMap<>();
  }

  public void adicionarJogador(String nome) {
      JogadorRanking novoJogador = new JogadorRanking(nome);
      if (buscarJogadorObj(nome) == null) {
          jogadores.put(novoJogador);
      }
  }

  public void registrarVitoria(String nome) {
      JogadorRanking jogador = buscarJogadorObj(nome);
      if (jogador != null) {
          jogadores.put(jogador);  // Remove e adiciona para reordenar
          jogador.registrarVitoria();
          jogadores.put(jogador);
      }
  }

  public void registrarEmpate(String nome) {
      JogadorRanking jogador = buscarJogadorObj(nome);
      if (jogador != null) {
          jogadores.put(jogador);  // Remove e adiciona para reordenar
          jogador.registrarEmpate();
          jogadores.put(jogador);
      }
  }

  public JogadorRanking[] gerarRanking() {
      return jogadores.values();
  }

  private JogadorRanking buscarJogadorObj(String nome) {
      for (JogadorRanking j : jogadores.values()) {
          if (j.nome.equals(nome)) return j;
      }
      return null;
  }
}
