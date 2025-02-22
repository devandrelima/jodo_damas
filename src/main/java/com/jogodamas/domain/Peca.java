package com.jogodamas.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Peca {
    private int id;
    private Coordenada coordenadas;

    public void novaCoordenada(Coordenada coordenadasCalculada) {
        this.coordenadas.setX(coordenadasCalculada.getX());
        this.coordenadas.setY(coordenadasCalculada.getY());
    }
}
