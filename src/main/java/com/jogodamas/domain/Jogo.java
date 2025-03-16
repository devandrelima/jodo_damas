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

    public Jogador getJogador1() {
        return jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public boolean getAcabou(){
        return acabou;
    }

    public void setAcabou(boolean acabou){
        this.acabou = acabou;
    }

    public Peca moverPeca(Coordenada proxCoordenada, Peca peca) throws Exception {
        int novoX = proxCoordenada.getX();
        int novoY = proxCoordenada.getY();

        // Lógica para eliminar peça do jogo
        tabuleiro[peca.getCoordenadas().getX()][peca.getCoordenadas().getY()] = null;

        if(!peca.isRainha()){
            if((novoX == peca.getCoordenadas().getX() + 2) || (novoX == peca.getCoordenadas().getX() - 2)){
                if((novoY == peca.getCoordenadas().getY() + 2) || (novoY == peca.getCoordenadas().getY() - 2)){
                    int coordenadaXPontoMedio = (novoX + peca.getCoordenadas().getX()) / 2;
                    int coordenadaYPontoMedio = (novoY + peca.getCoordenadas().getY()) / 2;

                    Peca PecaEliminada = tabuleiro[coordenadaXPontoMedio][coordenadaYPontoMedio];

                    if(PecaEliminada.getId() <= 11){
                        jogador1.getPilhaPecas().push(PecaEliminada);
                    } else {
                        jogador2.getPilhaPecas().push(PecaEliminada);
                    }

                    tabuleiro[coordenadaXPontoMedio][coordenadaYPontoMedio] = null;
                }
            }
        } else {

            int pecaX = peca.getCoordenadas().getX();
            int pecaY = peca.getCoordenadas().getY();

            if(pecaX < novoX){
                if(pecaY < novoY){
                    for(int i = pecaX, j = pecaY; i < novoX && j < novoY; i++, j++){
                        if(tabuleiro[i][j] != null){

                            Peca PecaEliminada = tabuleiro[i][j];

                            if(PecaEliminada.getId() <= 11){
                                jogador1.getPilhaPecas().push(PecaEliminada);
                            } else {
                                jogador2.getPilhaPecas().push(PecaEliminada);
                            }

                            tabuleiro[i][j] = null;
                        }
                    }
                } else {
                    for(int i = pecaX, j = pecaY; i < novoX && j > novoY; i++, j--){
                        if(tabuleiro[i][j] != null){

                            Peca PecaEliminada = tabuleiro[i][j];

                            if(PecaEliminada.getId() <= 11){
                                jogador1.getPilhaPecas().push(PecaEliminada);
                            } else {
                                jogador2.getPilhaPecas().push(PecaEliminada);
                            }

                            tabuleiro[i][j] = null;
                        }
                    }
                }
            } else {
                if(pecaY < novoY){
                    for(int i = pecaX, j = pecaY; i > novoX && j < novoY; i--, j++){
                        if(tabuleiro[i][j] != null){

                            Peca PecaEliminada = tabuleiro[i][j];

                            if(PecaEliminada.getId() <= 11){
                                jogador1.getPilhaPecas().push(PecaEliminada);
                            } else {
                                jogador2.getPilhaPecas().push(PecaEliminada);
                            }

                            tabuleiro[i][j] = null;
                        }
                    }
                } else {
                    for(int i = pecaX, j = pecaY; i > novoX && j > novoY; i--, j--){
                        if(tabuleiro[i][j] != null){

                            Peca PecaEliminada = tabuleiro[i][j];

                            if(PecaEliminada.getId() <= 11){
                                jogador1.getPilhaPecas().push(PecaEliminada);
                            } else {
                                jogador2.getPilhaPecas().push(PecaEliminada);
                            }

                            tabuleiro[i][j] = null;
                        }
                    }
                }
            }
        }

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
        this.jogador1 = new Jogador();
        this.jogador2 = new Jogador();

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
