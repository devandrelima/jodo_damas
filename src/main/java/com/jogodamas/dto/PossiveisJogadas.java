package com.jogodamas.dto;

import com.jogodamas.domain.Coordenada;

public class PossiveisJogadas {
    private Coordenada coordenadas[];

    public PossiveisJogadas () {
        coordenadas = new Coordenada[31];

        for(int i = 0; i < 31; i++){
            coordenadas[i] = null;
        }

    }

    public Coordenada[] getCoordenadas() {
        return coordenadas;
    }

    public void addAllCoordenada(Coordenada[] coordenadasCalculadas) {
        int tamVetorCoordenadas = coordenadasCalculadas.length;

        this.coordenadas = new Coordenada[tamVetorCoordenadas];

        for(int i = 0; i < tamVetorCoordenadas; i++){
            coordenadas[i] = coordenadasCalculadas[i];
        }
    }
}
