package com.jogodamas.domain;

class NoAVL<K> {
    K chave;
    NoAVL<K> esquerda, direita;
    int altura;

    public NoAVL(K chave) {
        this.chave = chave;
        this.altura = 1;
    }
}

class TreeMap<K extends Comparable<K>> {
    private NoAVL<K> raiz;

    public TreeMap() {
        this.raiz = null;
    }

    private int altura(NoAVL<K> no) {
        return (no == null) ? 0 : no.altura;
    }

    private int fatorBalanceamento(NoAVL<K> no) {
        return (no == null) ? 0 : altura(no.esquerda) - altura(no.direita);
    }

    private NoAVL<K> rotacaoDireita(NoAVL<K> y) {
        NoAVL<K> x = y.esquerda;
        NoAVL<K> temp = x.direita;
        x.direita = y;
        y.esquerda = temp;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        return x;
    }

    private NoAVL<K> rotacaoEsquerda(NoAVL<K> x) {
        NoAVL<K> y = x.direita;
        NoAVL<K> temp = y.esquerda;
        y.esquerda = x;
        x.direita = temp;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        return y;
    }

    private NoAVL<K> inserir(NoAVL<K> no, K chave) {
        if (no == null) return new NoAVL<>(chave);
        
        if (chave.compareTo(no.chave) < 0) {
            no.esquerda = inserir(no.esquerda, chave);
        } else if (chave.compareTo(no.chave) > 0) {
            no.direita = inserir(no.direita, chave);
        } else {
            return no;  // Não insere duplicado
        }

        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
        return balancear(no);
    }

    private NoAVL<K> balancear(NoAVL<K> no) {
        int fator = fatorBalanceamento(no);

        if (fator > 1 && fatorBalanceamento(no.esquerda) >= 0) {
            return rotacaoDireita(no);
        }
        if (fator < -1 && fatorBalanceamento(no.direita) <= 0) {
            return rotacaoEsquerda(no);
        }
        if (fator > 1 && fatorBalanceamento(no.esquerda) < 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }
        if (fator < -1 && fatorBalanceamento(no.direita) > 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public void put(K chave) {
        raiz = inserir(raiz, chave);
    }

    private void inOrder(NoAVL<K> no, ListaEncadeada<K> lista) {
        if (no != null) {
            inOrder(no.direita, lista); // Ordem decrescente (maior -> menor)
            lista.adicionar(no.chave);
            inOrder(no.esquerda, lista);
        }
    }

    public K[] values() {
        ListaEncadeada<K> lista = new ListaEncadeada<>();
        inOrder(raiz, lista);
        return lista.paraArray();
    }
}
