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
                return buscarCoordenadaDireitaJogadorDeCima(pecaAtual, tabuleiro);

            } else { // Pessoas do Jogador de Baixo

                return buscarCoordenadaDireitaJogadorDeBaixo(pecaAtual, tabuleiro);

            }
        } else { // é rainha
            System.out.println("Rainha ainda não está pronta");
        }

        return null;
    }

    private Coordenada[] buscarCoordenadaDireitaJogadorDeCima(Peca pecaAtual, Jogo tabuleiro){
        Coordenada[] coordenadas = new Coordenada[5];
        int contador = 0;

        int proxLinha = pecaAtual.getCoordenadas().getX() + 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() + 1;

        if (proxLinha > 7 || proxColuna > 7) { // Não sai do tabuleiro
            coordenadas[contador++] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(proxLinha, proxColuna);

        if(proxPeca == null) { // Não tem peça na próxima coordenada
            coordenadas[contador++] = new Coordenada(proxLinha,proxColuna);
            return coordenadas;

        } else if(proxPeca.getId() >= 12 && proxPeca.getId() <= 23){ // A próxima peca é inimiga, talvez tenha como comer ela
            return buscarCoordenadaDireita(proxPeca, tabuleiro);
        } else {
            coordenadas[contador++] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas;
        }
    }

    private Coordenada[] buscarCoordenadaDireitaJogadorDeBaixo(Peca pecaAtual, Jogo tabuleiro){
        Coordenada[] coordenadas = new Coordenada[5];
        int contador = 0;

        int proxLinha = pecaAtual.getCoordenadas().getX() - 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() + 1;

        if (proxLinha < 0 || proxColuna > 7) { // Não sai do tabuleiro
            coordenadas[contador++] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(proxLinha, proxColuna);

        if(proxPeca == null) { // Não tem peça na próxima coordenada
            coordenadas[contador++] = new Coordenada(proxLinha,proxColuna);

            return coordenadas;

        } else if(proxPeca.getId() <= 11){ // A próxima peca é inimiga, talvez tenha como comer ela
            return buscarCoordenadaDireita(proxPeca, tabuleiro);
        } else {
            coordenadas[contador++] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas;
        }
    }


    private Coordenada[] buscarCoordenadaEsquerda(Peca pecaAtual, Jogo tabuleiro) {
        Coordenada[] coordenadas = new Coordenada[5];
        int contador = 0;

        if(!pecaAtual.isRainha()) { // Não é rainha

            if (pecaAtual.getId() <= 11) { // Pessoas do Jogador de Cima
                int proxLinha = pecaAtual.getCoordenadas().getX() + 1;
                int proxColuna = pecaAtual.getCoordenadas().getY() - 1;

                if (proxLinha > 7 || proxColuna < 0) { // Não sai do tabuleiro
                    coordenadas[contador++] = null;

                    return coordenadas;
                }

                Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(proxLinha, proxColuna);

                if(proxPeca == null) { // Não tem peça na próxima coordenada
                    coordenadas[contador++] = new Coordenada(proxLinha,proxColuna);
                    return coordenadas;

                } else if(proxPeca.getId() >= 12 && proxPeca.getId() <= 23){ // A próxima peca é inimiga, talvez tenha como comer ela
                    return buscarCoordenadaDireita(proxPeca, tabuleiro);
                } else {
                    coordenadas[contador++] = null; // A próxima peça é amiga, não tem como pular ela
                    return coordenadas;
                }

            } else { // Pessoas do Jogador de Baixo

                int proxLinha = pecaAtual.getCoordenadas().getX() - 1;
                int proxColuna = pecaAtual.getCoordenadas().getY() - 1;

                if (proxLinha < 0 || proxColuna < 0) { // Não sai do tabuleiro
                    coordenadas[contador++] = null;

                    return coordenadas;
                }

                Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(proxLinha, proxColuna);

                if(proxPeca == null) { // Não tem peça na próxima coordenada
                    coordenadas[contador++] = new Coordenada(proxLinha,proxColuna);

                    return coordenadas;

                } else if(proxPeca.getId() <= 11){ // A próxima peca é inimiga, talvez tenha como comer ela
                    return buscarCoordenadaDireita(proxPeca, tabuleiro);
                } else {
                    coordenadas[contador++] = null; // A próxima peça é amiga, não tem como pular ela
                    return coordenadas;
                }
            }
        }

        coordenadas[contador++] = null;
        return coordenadas;
    }
}
