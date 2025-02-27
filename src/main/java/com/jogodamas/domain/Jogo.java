package com.jogodamas.domain;

public class Jogo {
    private boolean acabou;
    private Peca tabuleiro[][];
    private Jogador jogador1;
    private Jogador jogador2;

    public Jogo(){
        acabou = false;
        jogador1 = new Jogador();
        jogador2 = new Jogador();
        tabuleiro = new Peca[8][8];

        resetarTabuleiro();
    }

    public Peca moverPeca(Coordenada proxCoordenada, Peca peca) {
        int novoX = proxCoordenada.getX();
        int novoY = proxCoordenada.getY();

        tabuleiro[peca.getCoordenadas().getX()][peca.getCoordenadas().getY()] = null;
        tabuleiro[novoX][novoY] = peca;

        peca.getCoordenadas().setX(novoX);
        peca.getCoordenadas().setY(novoY);

        return peca;
    }

    public Peca buscarPecaPorID(int id){
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                try {
                    if(tabuleiro[i][j].getId() == id){
                            return tabuleiro[i][j];
                    }
                } catch (NullPointerException e) {
                    continue;
                }
            }
        }

        return null;
    }

    public Peca buscarPecaPorCoordenada(Coordenada coordenada){

        Peca peca = tabuleiro[coordenada.getX()][coordenada.getY()];

        if(peca == null) return null;

        return peca;
    }


    public void exibirTabuleiro(){
        System.out.println("\n\n-------------------------------\n\n");

        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                try {
                    int id = tabuleiro[i][j].getId();

                    if(id < 10) {System.out.print(" 0" + id + " ");}
                    else {System.out.print(" " + id + " ");}

                } catch (NullPointerException e) {
                    if(i % 2 == 0 && j % 2 == 1){
                        System.out.print( "    ");
                    } else if(i % 2 == 0 && j % 2 == 0) {
                       System.out.print( "[--]");
                    }else if(i % 2 == 1 && j % 2 == 0) {
                        System.out.print( "    ");
                    } else {
                        System.out.print( "[--]");
                    }
                }
            }
            System.out.println();
        }
    }

    public void resetarTabuleiro(){
        int contador = 0;

        // Preenche tabuleiro com as peças
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){

                if(i == 3 || i == 4){
                    tabuleiro[i][j] = null;
                    continue;
                }

                if(i % 2 == 0 && j % 2 == 1){
                    tabuleiro[i][j] = new Peca(false, contador++, new Coordenada(i,j));
                } else if(i % 2 == 1 && j % 2 == 0) {
                    tabuleiro[i][j] = new Peca(false, contador++, new Coordenada(i,j));
                }
            }
        }
    }
}
