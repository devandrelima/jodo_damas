package com.jogodamas.domain;

import lombok.Getter;

@Getter
public class Pilha <T>{
    class No {
        T dado;
        No prox;

        public No(T dado) {
            this.dado = dado;
            this.prox = null;
        }
    }

    private No cabeca;
    private No calda;
    private int tam;
    private int capacidade;

    public Pilha(){
        this.calda = null;
        this.cabeca = null;
        this.tam = 0;
        this.capacidade = -1;
    }

    public Pilha(int capacidade){
        this.calda = null;
        this.cabeca = null;
        this.tam = 0;
        this.capacidade = capacidade;
    }

    public boolean isEmpty(){
        if(cabeca == null){
            return true;
        }
        return false;
    }

    public boolean isFull(){
        if(capacidade == -1){
            return false;
        } else {
            if(tam == capacidade){
                return true;
            } else {
                return false;
            }
        }
    }

    public T peek(){
        if(isEmpty()){
            return null;
        } else {
            return cabeca.dado;
        }
    }

    public void push(T dado) throws Exception{
        if(isFull()){
             throw new Exception("Pilha Cheia");
        }

        No novo = new No(dado);

        if(cabeca == null){
            cabeca = novo;
            calda = novo;
        } else {
            calda.prox = novo;
            calda = novo;
        }

        tam++;
    }


}
