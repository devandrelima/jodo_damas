package com.jogodamas.controller;


import com.jogodamas.domain.Tabuleiro;
import com.jogodamas.dto.PossiveisJogadas;
import com.jogodamas.domain.Coordenada;
import com.jogodamas.services.Calculador;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class Jogo {
    Tabuleiro tabuleiro = new Tabuleiro();

    @PostMapping("/moverpeca")
    public ResponseEntity<String>moverPeca(){
        return ResponseEntity.ok("Peca dama!");
    }

    @GetMapping("/movimentospossiveis")
    public ResponseEntity movimentosPossiveis( @RequestParam int id){
        tabuleiro.exibirTabuleiro();



        //Calculador calculador = new Calculador();
        //PossiveisJogadas possiveisJogadas = new PossiveisJogadas();

        //possiveisJogadas.addAllCoordenada(calculador.calcularPossiveisJogadas(tabuleiro.buscarCordenadasPecaPorID(id)));

        //return ResponseEntity.ok(possiveisJogadas.getCoordenadas());

        return ResponseEntity.ok(tabuleiro.buscarCordenadasPecaPorID(id));

    }

    @PutMapping("/reset")
    public void reset(){

    }
}
