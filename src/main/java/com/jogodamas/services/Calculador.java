package com.jogodamas.services;

import com.jogodamas.domain.Coordenada;
import com.jogodamas.domain.Peca;
import com.jogodamas.domain.Jogo;
import com.jogodamas.services.pecanormal.direita.DireitaComedora;
import com.jogodamas.services.pecanormal.direita.DireitaNatural;
import com.jogodamas.services.pecanormal.esquerda.EsquerdaComedora;
import com.jogodamas.services.pecanormal.esquerda.EsquerdaNatural;

public class Calculador {
    public Coordenada[] calcularPossiveisJogadas(int id, Jogo tabuleiro) {
        Peca pecaAtual = tabuleiro.buscarPecaPorID(id);
        Coordenada coordenadasCalculadas[] = new Coordenada[31];

        coordenadasCalculadas = passarVetorMenorParaMaior(coordenadasCalculadas,
                buscarCoordenadaEsquerdaComedora(pecaAtual, tabuleiro));

        coordenadasCalculadas = passarVetorMenorParaMaior(coordenadasCalculadas,
                buscarCoordenadaDireitaComedora(pecaAtual, tabuleiro));

        coordenadasCalculadas = passarVetorMenorParaMaior(coordenadasCalculadas,
                buscarCoordenadaEsquerdaNatural(pecaAtual, tabuleiro));

        coordenadasCalculadas = passarVetorMenorParaMaior(coordenadasCalculadas,
                buscarCoordenadaDireitaNatural(pecaAtual, tabuleiro));

        if (!pecaAtual.isRainha()) {
            coordenadasCalculadas = obrigarComerPecaNormal(coordenadasCalculadas, pecaAtual.getCoordenadas());
        } else {

        }

        return coordenadasCalculadas;
    }

    private Coordenada[] passarVetorMenorParaMaior(Coordenada maior[], Coordenada menor[]) {
        int contador = 0;
        Coordenada caixaDeSeguranca;

        while (maior[contador] != null) {
            contador++;
        }

        for (int j = 0; j < menor.length; j++) {
            caixaDeSeguranca = menor[j];

            if (caixaDeSeguranca == null) {
                break;
            }

            maior[contador++] = caixaDeSeguranca;
        }

        return maior;
    }

    private Coordenada[] obrigarComerPecaNormal(Coordenada[] vetorCoordenadas, Coordenada coordenadaDaPeca) {
        boolean obrigaComer = false;

        // Varre vetor procurando se há coordenada para comer peça
        for (int i = 0; vetorCoordenadas[i] != null; i++) {
            if (vetorCoordenadas[i].getX() == coordenadaDaPeca.getX() + 2 || vetorCoordenadas[i].getX() == coordenadaDaPeca.getX() - 2) {
                obrigaComer = true;
                break;
            }
        }

        // Caso haja coordenada para comer, eu apago todas as outras que não sejam para comer
        if (obrigaComer) {
            // Transforma aquelas coordenadas não destinadas a comer em null
            for (int i = 0; vetorCoordenadas[i] != null; i++) {
                if (vetorCoordenadas[i].getX() == coordenadaDaPeca.getX() + 2) {
                    continue;
                } else if (vetorCoordenadas[i].getX() == coordenadaDaPeca.getX() - 2) {
                    continue;
                } else {
                    vetorCoordenadas[i] = null;
                }
            }
        }

        return vetorCoordenadas;
    }

    // DIREITA NATURAL
    /* OBS: A direção é a do XX

        [--]
    [--] 08 [--]
        [--] XX
    [--] 12 [--]
        [--]

     */

    private Coordenada[] buscarCoordenadaDireitaNatural(Peca pecaAtual, Jogo tabuleiro) {
        DireitaNatural direita = new DireitaNatural();

        if (!pecaAtual.isRainha()) { // Não é rainha

            if (pecaAtual.getId() <= 11) { // Peças do Jogador de Cima
                return direita.buscarCoordenadaDireitaNaturalJogadorDeCimaPecaNormal(pecaAtual, tabuleiro, 0);

            } else { // Peças do Jogador de Baixo

                return direita.buscarCoordenadaDireitaNaturalJogadorDeBaixoPecaNormal(pecaAtual, tabuleiro, 0);

            }
        } else { // é rainha
            System.out.println("Está em andamento");

            if (pecaAtual.getId() <= 11) { // Peças do Jogador de Cima
                return buscarCoordenadaDireitaNaturalJogadorDeCimaPecaRainha(pecaAtual, tabuleiro, 0, new Coordenada[6], false);

            } else { // Peças do Jogador de Baixo

                //return buscarCoordenadaDireitaNaturalJogadorDeBaixoPecaRainha(pecaAtual, tabuleiro, 0);

            }
        }

        return null;
    }

    // DIREITA COMEDORA
    /* OBS: A direção é a do XX

        [--] XX
    [--] 08 [--]
        [--]
    [--] 12 [--]
        [--] XX

     */

    private Coordenada[] buscarCoordenadaDireitaComedora(Peca pecaAtual, Jogo tabuleiro) {
        DireitaComedora direita = new DireitaComedora();

        if (!pecaAtual.isRainha()) { // Não é rainha

            if (pecaAtual.getId() <= 11) { // Peças do Jogador de Cima
                return direita.buscarCoordenadaDireitaComedoraJogadorDeCimaPecaNormal(pecaAtual, tabuleiro, 0);

            } else { // Peças do Jogador de Baixo

                return direita.buscarCoordenadaDireitaComedoraJogadorDeBaixoPecaNormal(pecaAtual, tabuleiro, 0);

            }
        } else { // é rainha
            System.out.println("Rainha ainda não está pronta");

        }

        return null;
    }

    // ESQUERDA NATURAL
    /*  OBS: A direção é a do XX

        [--]
    [--] 08 [--]
     XX [--]
    [--] 12 [--]
        [--]

    */
    private Coordenada[] buscarCoordenadaEsquerdaNatural(Peca pecaAtual, Jogo tabuleiro) {
        EsquerdaNatural esquerda = new EsquerdaNatural();

        if (!pecaAtual.isRainha()) { // Não é rainha

            if (pecaAtual.getId() <= 11) { // Pessoas do Jogador de Cima

                return esquerda.buscarCoordenadaEsquerdaNaturalJogadorDeCimaPecaNormal(pecaAtual, tabuleiro, 0);

            } else { // Pessoas do Jogador de Baixo

                return esquerda.buscarCoordenadaEsquerdaNaturalJogadorDeBaixoPecaNormal(pecaAtual, tabuleiro, 0);

            }
        } else {
            System.out.println("Rainha ainda precisa ser construída");
        }

        return null;
    }

    // ESQUERDA COMEDORA
    /*  OBS: A direção é a do XX

     XX [--]
    [--] 08 [--]
        [--]
    [--] 12 [--]
     XX [--]

    */
    private Coordenada[] buscarCoordenadaEsquerdaComedora(Peca pecaAtual, Jogo tabuleiro) {
        EsquerdaComedora esquerda = new EsquerdaComedora();

        if (!pecaAtual.isRainha()) { // Não é rainha

            if (pecaAtual.getId() <= 11) { // Pessoas do Jogador de Cima

                return esquerda.buscarCoordenadaEsquerdaComedoraJogadorDeCimaPecaNormal(pecaAtual, tabuleiro, 0);

            } else { // Pessoas do Jogador de Baixo

                return esquerda.buscarCoordenadaEsquerdaComedoraJogadorDeBaixoPecaNormal(pecaAtual, tabuleiro, 0);

            }
        } else {
            System.out.println("Rainha ainda precisa ser construída");
        }

        return null;
    }


    private Coordenada[] buscarCoordenadaDireitaNaturalJogadorDeCimaPecaRainha(Peca pecaAtual, Jogo tabuleiro, int buscador, Coordenada[] coordenadas, boolean inimigo) {
        //Coordenada[] coordenadas = new Coordenada[5];
        //int contador = 0;

        int proxLinha = pecaAtual.getCoordenadas().getX() + 1;
        int proxColuna = pecaAtual.getCoordenadas().getY() + 1;

        if (proxLinha > 7 || proxColuna > 7) { // Não sai do tabuleiro, para quando chega no limite
            coordenadas[buscador] = null;

            return coordenadas;
        }

        Peca proxPeca = tabuleiro.buscarPecaPorCoordenada(new Coordenada(proxLinha, proxColuna));

        if (proxPeca == null) { // Não tem peça na próxima coordenada
            coordenadas[buscador] = new Coordenada(proxLinha, proxColuna);
            buscador++;
            return buscarCoordenadaDireitaNaturalJogadorDeCimaPecaRainha(proxPeca, tabuleiro, buscador, coordenadas, false);

        } else if (proxPeca.getId() >= 12) { // A próxima peca é inimiga, talvez tenha como comer ela
            if (inimigo) { // A próxima peça é inimiga e tem outra peça protegendo
                coordenadas[buscador] = null;
                return coordenadas;
            }

            return buscarCoordenadaDireitaNaturalJogadorDeCimaPecaRainha(proxPeca, tabuleiro, buscador, coordenadas, true);

        } else {
            coordenadas[buscador] = null; // A próxima peça é amiga, não tem como pular ela
            return coordenadas; // para quando encontra amiga
        }
    }
}