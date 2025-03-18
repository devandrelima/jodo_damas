package com.jogodamas.domain;

public class Ranking {
    private ListaEncadeada<JogadorRanking> jogadores;

    public Ranking() {
        jogadores = new ListaEncadeada<>();
    }

    public void adicionarJogador(String nome) {
        if (buscarJogadorObj(nome) == null) {
            jogadores.adicionar(new JogadorRanking(nome));
        }
    }

    public JogadorRanking buscarJogadorObj(String nome) {
        for (int i = 0; i < jogadores.tamanho(); i++) {
            if (jogadores.obter(i).getNome().equals(nome)) {
                return jogadores.obter(i);
            }
        }
        return null;
    }

    public void registrarVitoria(String nome) {
        JogadorRanking jogador = buscarJogadorObj(nome);
        if (jogador == null) {
            adicionarJogador(nome);
            jogador = buscarJogadorObj(nome);
        }
        jogador.registrarVitoria();
        ordenarRanking();
    }

    public void registrarDerrota(String nome) {
        JogadorRanking jogador = buscarJogadorObj(nome);
        if (jogador == null) {
            adicionarJogador(nome);
            jogador = buscarJogadorObj(nome);
        }
        jogador.registrarDerrota();
        ordenarRanking();
    }

    public void registrarEmpate(String nome1, String nome2) {
        JogadorRanking jogador1 = buscarJogadorObj(nome1);
        JogadorRanking jogador2 = buscarJogadorObj(nome2);
        
        if (jogador1 == null) {
            adicionarJogador(nome1);
            jogador1 = buscarJogadorObj(nome1);
        }
        if (jogador2 == null) {
            adicionarJogador(nome2);
            jogador2 = buscarJogadorObj(nome2);
        }
        
        jogador1.registrarEmpate();
        jogador2.registrarEmpate();
        ordenarRanking();
    }

    private void ordenarRanking() {
        quickSort(jogadores, 0, jogadores.tamanho() - 1);
    }

    private void quickSort(ListaEncadeada<JogadorRanking> lista, int baixo, int alto) {
        if (baixo < alto) {
            int p = particionar(lista, baixo, alto);
            quickSort(lista, baixo, p - 1);
            quickSort(lista, p + 1, alto);
        }
    }

    private int particionar(ListaEncadeada<JogadorRanking> lista, int baixo, int alto) {
        JogadorRanking pivo = lista.obter(alto);
        int i = baixo - 1;
        for (int j = baixo; j < alto; j++) {
            if (lista.obter(j).compareTo(pivo) < 0) {
                i++;
                trocar(lista, i, j);
            }
        }
        trocar(lista, i + 1, alto);
        return i + 1;
    }

    private void trocar(ListaEncadeada<JogadorRanking> lista, int i, int j) {
        JogadorRanking temp = lista.obter(i);
        lista.definir(i, lista.obter(j));
        lista.definir(j, temp);
    }

    public JogadorRanking[] gerarRanking() {
        return paraArray();
    }

    private JogadorRanking[] paraArray() {
        JogadorRanking[] array = new JogadorRanking[jogadores.tamanho()];
        for (int i = 0; i < jogadores.tamanho(); i++) {
            array[i] = jogadores.obter(i);
        }
        return array;
    }
}
