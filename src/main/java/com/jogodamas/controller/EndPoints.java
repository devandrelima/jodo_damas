package com.jogodamas.controller;

import com.jogodamas.domain.*;
import com.jogodamas.dto.Jogada;
import com.jogodamas.dto.PossiveisJogadas;
import com.jogodamas.dto.StatusJogoAtual;
import com.jogodamas.services.Calculador;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class EndPoints {
    Jogo jogo = new Jogo();
    private Pilha<JogadaParaRelatorio> relatorioJogadas = new Pilha<>();
    int jogador;

    @PostMapping("/moverpeca")
    public ResponseEntity<StatusJogoAtual> moverPeca(@RequestBody Jogada jogada) throws Exception {
        Peca peca = jogo.buscarPecaPorID(jogada.id()); // Busca a peça que está sendo movida pelo id
        peca = jogo.moverPeca(jogada.coordenada(), peca); // Move a peça no tabuleiro
        int numeroJogador = 0;

        if(jogador%2==0){
            numeroJogador = 1;
        } else {
            numeroJogador = 2;
        }

        relatorioJogadas.push(new JogadaParaRelatorio(jogada, numeroJogador)); // Guarda a jogada no relatório

        jogo.exibirTabuleiro(); // Usado apenas pelo backend para jogar no console

        jogador++;
        return ResponseEntity.ok(new StatusJogoAtual(peca,
                                                     jogo.getJogador1().getPilhaPecas().getObjetosPilha(),
                                                     jogo.getJogador2().getPilhaPecas().getObjetosPilha(),
                                                     jogo.getAcabou())); // Retorno a peça com a nova posição
    }

    @GetMapping("/movimentospossiveis")
    public ResponseEntity movimentosPossiveis( @RequestParam int id){

        PossiveisJogadas possiveisJogadas = new PossiveisJogadas();
        Calculador calculador = new Calculador();

        // Esse conjunto de ifs é para bloquear as requisições do jogador que não é a vez
        if(jogador%2 == 0 && id <= 11){
            return ResponseEntity.ok(possiveisJogadas.getCoordenadas());
        } else if(jogador%2 == 1 && id >= 12){
            return ResponseEntity.ok(possiveisJogadas.getCoordenadas());
        }

        jogo.exibirTabuleiro(); // Usado apenas pelo backend para jogar no console

        possiveisJogadas.addAllCoordenada(calculador.calcularPossiveisJogadas(id, jogo));

        return ResponseEntity.ok(possiveisJogadas.getCoordenadas());
    }

    @PutMapping("/reset")
    public void reset(){
        System.out.println("reset");
        jogo.resetarTabuleiro();
    }
}
