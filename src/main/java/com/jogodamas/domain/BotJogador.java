package com.jogodamas.domain;

import com.jogodamas.services.Calculador;

public class BotJogador {
    private Jogo jogo;
    
    public BotJogador(Jogo jogo) {
        this.jogo = jogo;
    }

    public void jogar() {
        if (jogo.getAcabou()) return; 
        
        Peca melhorPeca = null;
        Coordenada melhorMovimento = null;
        int melhorValor = Integer.MIN_VALUE;
        boolean temCaptura = false;

        Calculador calculador = new Calculador();

        for (int id = 0; id <= 11; id++) { // IDs das peças do jogador 2 (bot)
            Peca peca = jogo.buscarPecaPorID(id);
            if (peca == null) continue;
            
            Coordenada[] movimentos = calculador.calcularPossiveisJogadas(peca.getId(), jogo);

            for (Coordenada movimento : movimentos) {
                if (movimento == null) continue;
                int valor = avaliarMovimento(peca, movimento);

                if (valor >= 10) { // Se for captura, o bot deve capturar
                    melhorPeca = peca;
                    melhorMovimento = movimento;
                    temCaptura = true;
                    break; // Para de procurar, pois achou uma captura obrigatória
                }

                if (!temCaptura && valor > melhorValor) {
                    melhorValor = valor;
                    melhorPeca = peca;
                    melhorMovimento = movimento;
                }
            }

            if (temCaptura) break; // Se encontrou captura, para de procurar
        }

        if (!jogo.getAcabou() && melhorPeca != null && melhorMovimento != null) {  //  Evita jogar se o jogo acabou
            try {
                jogo.moverPeca(melhorMovimento, melhorPeca);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean ehCapturaValida(int x, int y, int capturarX, int capturarY) {
        if (capturarX < 0 || capturarX >= 8 || capturarY < 0 || capturarY >= 8) return false;
        if (jogo.buscarPecaPorCoordenada(new Coordenada(capturarX, capturarY)) != null) return false;

        Peca alvo = jogo.buscarPecaPorCoordenada(new Coordenada((x + capturarX) / 2, (y + capturarY) / 2));
        return alvo != null && alvo.getId() > 11; // Deve ser peça do oponente
    }

    private int avaliarMovimento(Peca peca, Coordenada movimento) {
        int valor = 0;
        int x = movimento.getX();
        
        if (peca.isRainha()) {
            valor += 5; // Rainhas são mais valiosas
        }
        
        if (ehCapturaValida(peca.getCoordenadas().getX(), peca.getCoordenadas().getY(), x, movimento.getY())) {
            valor += 10; // Captura tem pontuação alta (deve ser obrigatória)
        }
        
        if (x == 0) {
            valor += 7; //  Chegar ao final do tabuleiro para virar rainha
        }
        
        return valor;
    }
}
