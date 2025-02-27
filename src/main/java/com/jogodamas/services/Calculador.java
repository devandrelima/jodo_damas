package com.jogodamas.services;

import com.jogodamas.domain.Coordenada;
import com.jogodamas.domain.Peca;
import com.jogodamas.domain.Jogo;

public class Calculador {
    public Coordenada[] calcularPossiveisJogadas(int id, Jogo tabuleiro) {
        Peca pecaAtual = tabuleiro.buscarPecaPorID(id);
        Coordenada coordenadasCalculadas[] = new Coordenada[31];

        coordenadasCalculadas = passarVetorMenorParaMaior(coordenadasCalculadas,
                                buscarCoordenadaDireita(pecaAtual, tabuleiro));

        coordenadasCalculadas = passarVetorMenorParaMaior(coordenadasCalculadas,
                                buscarCoordenadaEsquerda(pecaAtual, tabuleiro));

        return coordenadasCalculadas;
    }

    private Coordenada[] passarVetorMenorParaMaior(Coordenada maior[], Coordenada menor[]){
        int contador = 0;
        Coordenada caixaDeSeguranca;

        while(maior[contador] != null){
            contador++;
        }

        for(int j = 0; j < menor.length; j++){
            caixaDeSeguranca = menor[j];

            if(caixaDeSeguranca == null){
                break;
            }

            maior[contador] = caixaDeSeguranca;
        }

        return maior;
    }

    private Coordenada[] buscarCoordenadaDireita(Peca pecaAtual, Jogo tabuleiro) {

        if(!pecaAtual.isRainha()) { // Não é rainha

            if (pecaAtual.getId() <= 11) { // Peças do Jogador de Cima
                return buscarCoordenadaDireitaJogadorDeCima(pecaAtual, tabuleiro, 0);

            } else { // Peças do Jogador de Baixo

                return buscarCoordenadaDireitaJogadorDeBaixo(pecaAtual, tabuleiro, 0);

            }
        } else { // é rainha
            System.out.println("Rainha ainda não está pronta");
        }

        return null;
    }

    private Coordenada[] buscarCoordenadaDireitaJogadorDeCima(Peca pecaAtual, Jogo tabuleiro, int buscador){
        Coordenada[] coordenadas = new Coordenada[5];
        int contador = 0;

        int proxLinha = pecaAtual.getCoordenadas().getX() + 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() + 1;

        if (proxLinha > 7 || proxColuna > 7) { // Não sai do tabuleiro
            coordenadas[0] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(new Coordenada(proxLinha, proxColuna));

        if(proxPeca == null) { // Não tem peça na próxima coordenada
            coordenadas[0] = new Coordenada(proxLinha,proxColuna);
            return coordenadas;

        } else if(proxPeca.getId() >= 12){ // A próxima peca é inimiga, talvez tenha como comer ela
            if(buscador > 1) { // A próxima peça é inimiga e tem outra peça protegendo
                coordenadas[0] = null;
                return coordenadas;
            }

            return buscarCoordenadaDireitaJogadorDeCima(new Peca(false, pecaAtual.getId(), new Coordenada(proxLinha, proxColuna)), tabuleiro, ++buscador); // fazer varredura

        } else {
            coordenadas[0] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas;
        }
    }

    private Coordenada[] buscarCoordenadaDireitaJogadorDeBaixo(Peca pecaAtual, Jogo tabuleiro, int buscador){
        Coordenada[] coordenadas = new Coordenada[5];
        int contador = 0;

        int proxLinha = pecaAtual.getCoordenadas().getX() - 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() + 1;

        if (proxLinha < 0 || proxColuna > 7) { // Não sai do tabuleiro
            coordenadas[contador++] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(new Coordenada(proxLinha, proxColuna));

        if(proxPeca == null) { // Não tem peça na próxima coordenada
            coordenadas[contador++] = new Coordenada(proxLinha,proxColuna);

            return coordenadas;

        } else if(proxPeca.getId() <= 11){ // A próxima peca é inimiga, talvez tenha como comer ela
            if(buscador > 1) { // A próxima peça é inimiga e tem outra peça protegendo
                coordenadas[0] = null;
                return coordenadas;
            }

            return buscarCoordenadaDireitaJogadorDeBaixo(new Peca(false, pecaAtual.getId(), new Coordenada(proxLinha, proxColuna)), tabuleiro, ++buscador); // fazer varredura

        } else {
            coordenadas[contador++] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas;
        }
    }

    private Coordenada[] buscarCoordenadaEsquerda(Peca pecaAtual, Jogo tabuleiro) {

        if(!pecaAtual.isRainha()) { // Não é rainha

            if (pecaAtual.getId() <= 11) { // Pessoas do Jogador de Cima

                return buscarCoordenadaEsquerdaJogadorDeCima(pecaAtual, tabuleiro, 0);

            } else { // Pessoas do Jogador de Baixo

                return buscarCoordenadaEsquerdaJogadorDeBaixo(pecaAtual, tabuleiro, 0);

            }
        } else {
            System.out.println("Rainha ainda precisa ser construída");
        }

        return null;
    }

    private Coordenada[] buscarCoordenadaEsquerdaJogadorDeCima(Peca pecaAtual, Jogo tabuleiro, int buscador){
        Coordenada[] coordenadas = new Coordenada[5];
        int contador = 0;

        int proxLinha = pecaAtual.getCoordenadas().getX() + 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() - 1;

        if (proxLinha > 7 || proxColuna < 0) { // Não sai do tabuleiro
            coordenadas[contador++] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(new Coordenada(proxLinha, proxColuna));

        if(proxPeca == null) { // Não tem peça na próxima coordenada
            coordenadas[contador++] = new Coordenada(proxLinha,proxColuna);
            return coordenadas;

        } else if(proxPeca.getId() >= 12){ // A próxima peca é inimiga, talvez tenha como comer ela
            if(buscador > 1) { // A próxima peça é inimiga e tem outra peça protegendo
                coordenadas[0] = null;
                return coordenadas;
            }

            return buscarCoordenadaEsquerdaJogadorDeCima(new Peca(false, pecaAtual.getId(), new Coordenada(proxLinha, proxColuna)), tabuleiro, ++buscador); // fazer varredura

        } else {
            coordenadas[contador++] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas;
        }
    }


    private Coordenada[] buscarCoordenadaEsquerdaJogadorDeBaixo(Peca pecaAtual, Jogo tabuleiro, int buscador){
        Coordenada[] coordenadas = new Coordenada[5];
        int contador = 0;

        int proxLinha = pecaAtual.getCoordenadas().getX() - 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() - 1;

        if (proxLinha < 0 || proxColuna < 0) { // Não sai do tabuleiro
            coordenadas[contador++] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(new Coordenada(proxLinha, proxColuna));

        if(proxPeca == null) { // Não tem peça na próxima coordenada
            coordenadas[contador++] = new Coordenada(proxLinha,proxColuna);

            return coordenadas;

        } else if(proxPeca.getId() <= 11){ // A próxima peca é inimiga, talvez tenha como comer ela
            if(buscador > 1) { // A próxima peça é inimiga e tem outra peça protegendo
                coordenadas[0] = null;
                return coordenadas;
            }

            return buscarCoordenadaEsquerdaJogadorDeCima(new Peca(false, pecaAtual.getId(), new Coordenada(proxLinha, proxColuna)), tabuleiro, ++buscador); // fazer varredura

        } else {
            coordenadas[contador++] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas;
        }
    }
}
