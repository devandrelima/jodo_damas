package com.jogodamas.services;

import com.jogodamas.domain.Coordenada;
import com.jogodamas.domain.Peca;
import com.jogodamas.domain.Jogo;
import com.jogodamas.services.pecanormal.direita.DireitaComedoraPecaNormal;
import com.jogodamas.services.pecanormal.direita.DireitaNaturalPecaNormal;
import com.jogodamas.services.pecanormal.esquerda.EsquerdaComedoraPecaNormal;
import com.jogodamas.services.pecanormal.esquerda.EsquerdaNaturalPecaNormal;
import com.jogodamas.services.pecarainha.direita.DireitaComedoraRainha;
import com.jogodamas.services.pecarainha.direita.DireitaNaturalPecaRainha;
import com.jogodamas.services.pecarainha.esquerda.EsquerdaComedoraPecaRainha;
import com.jogodamas.services.pecarainha.esquerda.EsquerdaNaturalPecaRainha;

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
        DireitaNaturalPecaNormal direitaPecaNormal = new DireitaNaturalPecaNormal();
        DireitaNaturalPecaRainha direitaRainha = new DireitaNaturalPecaRainha();

        if (!pecaAtual.isRainha()) { // Não é rainha

            if (pecaAtual.getId() <= 11) { // Peças do Jogador de Cima
                return direitaPecaNormal.buscarCoordenadaDireitaNaturalJogadorDeCimaPecaNormal(pecaAtual, tabuleiro, 0);

            } else { // Peças do Jogador de Baixo

                return direitaPecaNormal.buscarCoordenadaDireitaNaturalJogadorDeBaixoPecaNormal(pecaAtual, tabuleiro, 0);

            }
        } else { // é rainha
            System.out.println("Está em andamento");

            if (pecaAtual.getId() <= 11) { // Peças do Jogador de Cima
               return direitaRainha.buscarCoordenadaJogadorDeCima(pecaAtual, tabuleiro, 0, new Coordenada[8], false);

            } else { // Peças do Jogador de Baixo
                return direitaRainha.buscarCoordenadaJogadorDeBaixo(pecaAtual, tabuleiro, 0, new Coordenada[8], false);

            }
        }
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
        DireitaComedoraPecaNormal direitaPecaNormal = new DireitaComedoraPecaNormal();
        DireitaComedoraRainha direitaRainha = new DireitaComedoraRainha();

        if (!pecaAtual.isRainha()) { // Não é rainha

            if (pecaAtual.getId() <= 11) { // Peças do Jogador de Cima
                return direitaPecaNormal.buscarCoordenadaDireitaComedoraJogadorDeCimaPecaNormal(pecaAtual, tabuleiro, 0);

            } else { // Peças do Jogador de Baixo

                return direitaPecaNormal.buscarCoordenadaDireitaComedoraJogadorDeBaixoPecaNormal(pecaAtual, tabuleiro, 0);

            }
        } else { // é rainha
            System.out.println("Está em andamento");

            if (pecaAtual.getId() <= 11) { // Peças do Jogador de Cima
                return direitaRainha.buscarCoordenadaJogadorDeCima(pecaAtual, tabuleiro, 0, new Coordenada[8], false);

            } else { // Peças do Jogador de Baixo

                return direitaRainha.buscarCoordenadaJogadorDeBaixo(pecaAtual, tabuleiro, 0, new Coordenada[8], false);
            }
        }
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
        EsquerdaNaturalPecaNormal esquerdaPecaNormal = new EsquerdaNaturalPecaNormal();
        EsquerdaNaturalPecaRainha esquerdaRainha = new EsquerdaNaturalPecaRainha();

        if (!pecaAtual.isRainha()) { // Não é rainha

            if (pecaAtual.getId() <= 11) { // Pessoas do Jogador de Cima

                return esquerdaPecaNormal.buscarCoordenadaEsquerdaNaturalJogadorDeCimaPecaNormal(pecaAtual, tabuleiro, 0);

            } else { // Pessoas do Jogador de Baixo

                return esquerdaPecaNormal.buscarCoordenadaEsquerdaNaturalJogadorDeBaixoPecaNormal(pecaAtual, tabuleiro, 0);

            }
        } else {
            System.out.println("Está em andamento");

            if (pecaAtual.getId() <= 11) { // Peças do Jogador de Cima
                return esquerdaRainha.buscarCoordenadaJogadorDeCima(pecaAtual, tabuleiro, 0, new Coordenada[8], false);

            } else { // Peças do Jogador de Baixo

                return esquerdaRainha.buscarCoordenadaJogadorDeBaixo(pecaAtual, tabuleiro, 0, new Coordenada[8], false);

            }
        }
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
        EsquerdaComedoraPecaNormal esquerdaPecaNormal = new EsquerdaComedoraPecaNormal();
        EsquerdaComedoraPecaRainha esquerdaRainha = new EsquerdaComedoraPecaRainha();

        if (!pecaAtual.isRainha()) { // Não é rainha

            if (pecaAtual.getId() <= 11) { // Pessoas do Jogador de Cima

                return esquerdaPecaNormal.buscarCoordenadaEsquerdaComedoraJogadorDeCimaPecaNormal(pecaAtual, tabuleiro, 0);

            } else { // Pessoas do Jogador de Baixo

                return esquerdaPecaNormal.buscarCoordenadaEsquerdaComedoraJogadorDeBaixoPecaNormal(pecaAtual, tabuleiro, 0);

            }
        } else {
            System.out.println("Está em andamento");

            if (pecaAtual.getId() <= 11) { // Peças do Jogador de Cima
                return esquerdaRainha.buscarCoordenadaJogadorDeCima(pecaAtual, tabuleiro, 0, new Coordenada[8], false);

            } else { // Peças do Jogador de Baixo
                return esquerdaRainha.buscarCoordenadaJogadorDeBaixo(pecaAtual, tabuleiro, 0, new Coordenada[8], false);
            }
        }
    }
}

