package com.jogodamas.dto;

import com.jogodamas.domain.Coordenada;

import java.util.HashSet;
import java.util.Set;

public class PossiveisJogadas {
    private Coordenada coordenadas[];

    public PossiveisJogadas(){
        this.coordenadas = new Coordenada[31];
    }

    public Coordenada[] getCoordenadas() {
        return coordenadas;
    }

    public void addAllCoordenada(Coordenada[] coordenadasCalculadas) {
        for(int i = 0; i < coordenadas.length; i++){
            coordenadas[i] = coordenadasCalculadas[i];
        }
    }
}
