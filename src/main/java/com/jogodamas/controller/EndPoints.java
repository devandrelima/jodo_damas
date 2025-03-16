package com.jogodamas.controller;
import com.jogodamas.domain.*;
import com.jogodamas.dto.Jogada;
import com.jogodamas.dto.NomesJogadoresRequest;
import com.jogodamas.dto.PossiveisJogadas;
import com.jogodamas.dto.StatusJogoAtual;
import com.jogodamas.services.Calculador;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

@CrossOrigin
@RestController
@RequestMapping
public class EndPoints {
    Jogo jogo = new Jogo();
    private Pilha<JogadaParaRelatorio> relatorioJogadas = new Pilha<>();
    private Ranking ranking = new Ranking();
    int jogador;

    @PutMapping("/ativarbot")
    public ResponseEntity<String> ativarBot() {
        jogo.ativarBot();
        return ResponseEntity.ok("");
    }

    @PutMapping("/desativarbot")
    public ResponseEntity<String> desativarBot() {
        jogo.desativarBot();
        return ResponseEntity.ok("");
    }

    @PostMapping("/moverpeca")
    public ResponseEntity<StatusJogoAtual> moverPeca(@RequestBody Jogada jogada) throws Exception {
        Peca peca = jogo.buscarPecaPorID(jogada.id());
        peca = jogo.moverPeca(jogada.coordenada(), peca);
        int numeroJogador = (jogador % 2 == 0) ? 1 : 2;

        // Quando a peça de cima chegar na última linha, vira dama
        if((peca.getId() >= 0 && peca.getId() <= 11) && peca.getCoordenadas().getX() == 7){
            peca.setRainha(true);
        }

        // Quando a peça de baixo chegar na primeira linha, vira dama
        if((peca.getId() >= 12 && peca.getId() <= 23) && peca.getCoordenadas().getX() == 0){
            peca.setRainha(true);
        }

        // Quando a peça de baixo chegar na primeira linha, vira dama
        if((peca.getId() >= 12 && peca.getId() <= 23) && peca.getCoordenadas().getX() == 0){
            peca.setRainha(true);
        }

        if(numeroJogador == 1) {
            relatorioJogadas.push(new JogadaParaRelatorio(jogada, jogo.getJogador1().getNome())); // Guarda a jogada no relatório
        } else {
            relatorioJogadas.push(new JogadaParaRelatorio(jogada, jogo.getJogador2().getNome())); // Guarda a jogada no relatório
        }

        jogo.exibirTabuleiro(); // Usado apenas pelo backend para jogar no console

        jogador++;

        // quando uma das pilha encher, quer dizer que o jogo acabou
        if(jogo.getJogador1().getPilhaPecas().getTam() == 12) {
            ranking.registrarVitoria(jogo.getJogador2().getNome());
            ranking.registrarDerrota(jogo.getJogador1().getNome());

            jogo.getJogador1().setNome(jogo.getJogador1().getNome());
            jogo.getJogador2().setNome(jogo.getJogador2().getNome());

            jogo.setAcabou(true);

        } else if (jogo.getJogador2().getPilhaPecas().getTam() == 12){
            ranking.registrarVitoria(jogo.getJogador1().getNome());
            ranking.registrarDerrota(jogo.getJogador2().getNome());

            jogo.getJogador1().setNome(jogo.getJogador1().getNome());
            jogo.getJogador2().setNome(jogo.getJogador2().getNome());

            jogo.setAcabou(true);
        

        } 


        return ResponseEntity.ok(new StatusJogoAtual(peca,
                                                     jogo.getJogador1().getPilhaPecas().getObjetosPilha(),
                                                     jogo.getJogador2().getPilhaPecas().getObjetosPilha(),
                                                     jogo.getAcabou())); // Retorno a peça com a nova posição
    }

        @GetMapping("/relatorio")
    public ResponseEntity<String> gerarRelatorioJogadas() {
        Object[] jogadasArray = relatorioJogadas.getObjetosPilha();
        System.out.println("Quantidade de jogadas registradas: " + jogadasArray.length);
        System.out.println("Conteúdo da pilha: " + Arrays.toString(jogadasArray));
        
        if (jogadasArray.length == 0 || jogadasArray[0] == null) {
            return ResponseEntity.status(400).body("Nenhuma jogada registrada.");
        }
        
        java.io.File file = new java.io.File("relatorio_jogadas.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Relatório de Jogadas:\n");
            for (Object obj : jogadasArray) {
                if (obj instanceof JogadaParaRelatorio jogada) {
                    writer.write("Jogador " + jogada.getNomeJogador() + ": (" + jogada.getJogada().coordenada().getX() + ", " + jogada.getJogada().coordenada().getY() + ")" + "\n");
                }
            }
            return ResponseEntity.ok("Relatório gerado com sucesso: " + file.getAbsolutePath());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao gerar relatório de jogadas: " + e.getMessage());
        }
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

    @PutMapping("/definirnomes")
    public void definirNomeJogador(@RequestBody NomesJogadoresRequest request) {
        jogo.getJogador1().setNome(request.nome1());
        jogo.getJogador2().setNome(request.nome2());
    }

    @PutMapping("/reset")
    public void reset(){
        this.jogador = 0;
        this.relatorioJogadas = new Pilha<>(); // Resetar jogadas ao reiniciar
        System.out.println("reset");
        jogo.resetarTabuleiro();
        jogo.setAcabou(false);
    }

    @PutMapping("/empate")
    public void empate(){
        ranking.registrarEmpate(jogo.getJogador1().getNome());
        ranking.registrarEmpate(jogo.getJogador2().getNome());
        jogo.setAcabou(true);
        jogo.resetarTabuleiro();
    }

    @GetMapping("/ranking")
    public ResponseEntity<JogadorRanking[]> obterRanking() {
        return ResponseEntity.ok(ranking.gerarRanking()); // ⬅ Retorna ranking atualizado
    }

    @PutMapping("/moverbot")
public ResponseEntity<StatusJogoAtual> moverBot() {
    if (!jogo.isBotAtivo()) {
        return ResponseEntity.badRequest().body(null);
    }
    
    if (jogo.getAcabou()) {
        return ResponseEntity.badRequest().body(null);
    }

    jogo.jogarBot(); 

    // Busca a última peça movida
    Peca ultimaPecaMovida = jogo.getUltimaPecaMovida();

    if ((ultimaPecaMovida.getId() >= 0 && ultimaPecaMovida.getId() <= 11) && 
         ultimaPecaMovida.getCoordenadas().getX() == 0) {
        ultimaPecaMovida.setRainha(true);
    }

    if (jogo.getJogador1().getPilhaPecas().getTam() == 12) {
        ranking.registrarVitoria(jogo.getJogador2().getNome());
        ranking.registrarDerrota(jogo.getJogador1().getNome());
        jogo.setAcabou(true);
    } else if (jogo.getJogador2().getPilhaPecas().getTam() == 12) {
        ranking.registrarVitoria(jogo.getJogador1().getNome());
        ranking.registrarDerrota(jogo.getJogador2().getNome());
        jogo.setAcabou(true);
    }

    return ResponseEntity.ok(new StatusJogoAtual(
        ultimaPecaMovida,
        jogo.getJogador1().getPilhaPecas().getObjetosPilha(),
        jogo.getJogador2().getPilhaPecas().getObjetosPilha(),
        jogo.getAcabou()
    ));
}


}
