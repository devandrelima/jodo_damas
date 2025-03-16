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
    if (jogador == null) {
        adicionarJogador(nome);
        jogador = buscarJogadorObj(nome);
    }

    jogadores.remover(jogador);  // REMOVE antes de atualizar
    jogador.registrarVitoria();
    jogadores.put(jogador);
}

    public void registrarEmpate(String nome) {
        JogadorRanking jogador = buscarJogadorObj(nome);
        if (jogador == null) {
            adicionarJogador(nome);
            jogador = buscarJogadorObj(nome);
        }

        jogadores.remover(jogador); // REMOVE antes de atualizar
        jogador.registrarEmpate();
        jogadores.put(jogador);
    }

    public void registrarDerrota(String nome) {
        JogadorRanking jogador = buscarJogadorObj(nome);
        if (jogador == null) {
            adicionarJogador(nome);
            jogador = buscarJogadorObj(nome);
        }

        jogadores.remover(jogador);  // REMOVE antes de atualizar
        jogador.registrarDerrota();
        jogadores.put(jogador);
    }

  public JogadorRanking[] gerarRanking() {
      return jogadores.values(JogadorRanking.class);
  }

  private JogadorRanking buscarJogadorObj(String nome) {
      for (JogadorRanking j : jogadores.values(JogadorRanking.class)) {
          if (j.nome.equals(nome)) return j;
      }
      return null;
  }
}
