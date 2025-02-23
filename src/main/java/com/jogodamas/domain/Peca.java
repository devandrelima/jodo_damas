package com.jogodamas.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Peca {
    private boolean rainha;
    private int id;
    private Coordenada coordenadas;

    public void novaCoordenada(Coordenada coordenadasCalculada) {
        this.coordenadas.setX(coordenadasCalculada.getX());
        this.coordenadas.setY(coordenadasCalculada.getY());
    }

    public boolean isRainha() {
        return rainha;
    }
    public void setRainha(boolean rainha) {
        this.rainha = rainha;
    }
}
