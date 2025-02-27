package com.jogodamas.controller;

import com.jogodamas.domain.*;
import com.jogodamas.dto.Jogada;
import com.jogodamas.dto.PossiveisJogadas;
import com.jogodamas.services.Calculador;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class EndPoints {
    Jogo jogo = new Jogo();
    private Pilha<Jogada> relatorioJogadas;

    @PostMapping("/moverpeca")
    public ResponseEntity<Peca> moverPeca(@RequestBody Jogada jogada) throws Exception {
        Peca peca = jogo.buscarPecaPorID(jogada.id()); // Busca a peça que está sendo movida pelo id
        jogo.moverPeca(jogada.coordenada(), peca); // Move a peça no tabuleiro

        relatorioJogadas.push(jogada); // Guarda a jogada no relatório
        return ResponseEntity.ok(jogo.buscarPecaPorID(jogada.id())); // Retorno a peça com a nova posição
    }

    @GetMapping("/movimentospossiveis")
    public ResponseEntity movimentosPossiveis( @RequestParam int id){
        Calculador calculador = new Calculador();
        PossiveisJogadas possiveisJogadas = new PossiveisJogadas();

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
