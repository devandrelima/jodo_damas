package com.jogodamas.domain;

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

        for (int id = 0; id <= 11; id++) { // IDs das peças do jogador 2 (bot)
            Peca peca = jogo.buscarPecaPorID(id);
            if (peca == null) continue;
            
            ListaEncadeada<Coordenada> movimentos = calcularMovimentosPossiveis(peca);
            Coordenada[] movimentosArray = movimentos.paraArray(Coordenada.class); 

            for (Coordenada movimento : movimentosArray) {
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

    private ListaEncadeada<Coordenada> calcularMovimentosPossiveis(Peca peca) {
        ListaEncadeada<Coordenada> movimentos = new ListaEncadeada<>();
        int x = peca.getCoordenadas().getX();
        int y = peca.getCoordenadas().getY();

        int[] direcoes = {-1, 1}; // Movimentos diagonais
        for (int dx : direcoes) {
            for (int dy : direcoes) {
                int novoX = x + dx;
                int novoY = y + dy;
                if (ehMovimentoValido(novoX, novoY, peca)) {
                    movimentos.adicionar(new Coordenada(novoX, novoY));
                }

                // Verificar capturas
                int capturarX = x + 2 * dx;
                int capturarY = y + 2 * dy;
                if (ehCapturaValida(x, y, capturarX, capturarY)) {
                    movimentos.adicionar(new Coordenada(capturarX, capturarY));
                }
            }
        }
        return movimentos;
    }

    private boolean ehMovimentoValido(int x, int y, Peca peca) {
        // O movimento precisa estar dentro do tabuleiro
        if (x < 0 || x >= 8 || y < 0 || y >= 8) return false;
    
        // A posição final precisa estar vazia
        if (jogo.buscarPecaPorCoordenada(new Coordenada(x, y)) != null) return false;
    
        // Se a peça for uma dama, pode andar para qualquer lado
        if (peca.isRainha()) return true;
    
        // Peças normais só podem andar para frente
        if ((peca.getId() >= 0 && peca.getId() <= 11 && x <= peca.getCoordenadas().getX())) {
            // O jogador só pode andar para frente (x diminuindo)
            return false;
        }
    
        return true;
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
