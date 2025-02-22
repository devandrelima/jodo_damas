package com.jogodamas.domain;

public class Tabuleiro {
    private boolean acabou;
    private Peca tabuleiro[][];
    private Jogador jogador1;
    private Jogador jogador2;

    public Tabuleiro(){
        acabou = false;
        jogador1 = new Jogador();
        jogador2 = new Jogador();
        tabuleiro = new Peca[8][8];

        int contador = 0;

        // Preenche tabuleiro com as peças
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){

                if(i == 3 || i == 4){
                    continue;
                }

                if(i % 2 == 0 && j % 2 == 1){
                    tabuleiro[i][j] = new Peca(contador++, new Coordenada(i,j));
                } else if(i % 2 == 1 && j % 2 == 0) {
                    tabuleiro[i][j] = new Peca(contador++, new Coordenada(i,j));
                }
            }
        }
    }

    public Coordenada buscarCordenadasPecaPorID(int id){
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                try {
                    if(tabuleiro[i][j].getId() == id){
                            return tabuleiro[i][j].getCoordenadas();
                    }
                } catch (NullPointerException e) {
                    continue;
                }
            }
        }

        return null;
    }

    public void exibirTabuleiro(){
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                try {
                    System.out.print(tabuleiro[i][j].getId() + " ");
                } catch (NullPointerException e) {
                    System.out.print( "[]");
                }
            }
            System.out.println();
        }
    }
}
