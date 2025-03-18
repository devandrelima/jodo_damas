package com.jogodamas.domain;


class NoLista<V> {
  V valor;
  NoLista<V> proximo;

  public NoLista(V valor) {
      this.valor = valor;
      this.proximo = null;
  }
}

class ListaEncadeada<V> {
    private NoLista<V> cabeca;
    private NoLista<V> cauda;
    private int tamanho;
  
    public ListaEncadeada() {
        this.cabeca = null;
        this.cauda = null;
        this.tamanho = 0;
    }
  
    public void adicionar(V valor) {
        NoLista<V> novoNo = new NoLista<>(valor);
        if (cabeca == null) {
            cabeca = novoNo;
            cauda = novoNo;
        } else {
            cauda.proximo = novoNo;
            cauda = novoNo;
        }
        tamanho++;
    }
  
    public int tamanho() {
        return tamanho;
    }
  
    public V obter(int index) {
        if (index < 0 || index >= tamanho) {
            throw new IndexOutOfBoundsException("Índice fora dos limites");
        }
        NoLista<V> atual = cabeca;
        for (int i = 0; i < index; i++) {
            atual = atual.proximo;
        }
        return atual.valor;
    }
  
    public void definir(int index, V valor) {
        if (index < 0 || index >= tamanho) {
            throw new IndexOutOfBoundsException("Índice fora dos limites");
        }
        NoLista<V> atual = cabeca;
        for (int i = 0; i < index; i++) {
            atual = atual.proximo;
        }
        atual.valor = valor;
    }

    public V[] paraArray(Class<V> clazz) {
        @SuppressWarnings("unchecked")
        V[] array = (V[]) java.lang.reflect.Array.newInstance(clazz, tamanho);
        NoLista<V> atual = cabeca;
        int i = 0;
        while (atual != null) {
            array[i++] = atual.valor;
            atual = atual.proximo;
        }
        return array;
    }
  }
  
