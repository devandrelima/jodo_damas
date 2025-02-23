package com.jogodamas.controller;

import com.jogodamas.domain.Coordenada;
import com.jogodamas.domain.Jogada;
import com.jogodamas.domain.Jogo;
import com.jogodamas.domain.Peca;
import com.jogodamas.dto.PossiveisJogadas;
import com.jogodamas.services.Calculador;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class EndPoints {
    Jogo jogo = new Jogo();

    @PostMapping("/moverpeca")
    public ResponseEntity<Peca> moverPeca(@RequestBody Jogada jogada) {
        Peca peca = jogo.buscarPecaPorID(jogada.id());
        jogo.moverPeca(jogada.coordenada(), peca);

        return ResponseEntity.ok(jogo.buscarPecaPorID(jogada.id()));
    }

    @GetMapping("/movimentospossiveis")
    public ResponseEntity movimentosPossiveis( @RequestParam int id){
        jogo.exibirTabuleiro();

        Calculador calculador = new Calculador();
        PossiveisJogadas possiveisJogadas = new PossiveisJogadas();

        possiveisJogadas.addAllCoordenada(calculador.calcularPossiveisJogadas(id, jogo));

        return ResponseEntity.ok(possiveisJogadas.getCoordenadas());
    }

    @PutMapping("/reset")
    public void reset(){
        System.out.println("reset");
        jogo.resetarTabuleiro();
    }
}
