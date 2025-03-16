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

    private NoAVL<K> balancear(NoAVL<K> no) {
        if (no == null) return null;

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

    public void put(K chave) {
        raiz = inserir(raiz, chave);
    }

    public void remover(K chave) {
        raiz = removerNo(raiz, chave);
    }

    private NoAVL<K> removerNo(NoAVL<K> no, K chave) {
        if (no == null) return null;

        if (chave.compareTo(no.chave) < 0) {
            no.esquerda = removerNo(no.esquerda, chave);
        } else if (chave.compareTo(no.chave) > 0) {
            no.direita = removerNo(no.direita, chave);
        } else {
            // Caso 1: Nó sem filhos
            if (no.esquerda == null && no.direita == null) {
                return null;
            }

            // Caso 2: Apenas um filho
            if (no.esquerda == null) return no.direita;
            if (no.direita == null) return no.esquerda;

            // Caso 3: Dois filhos (substituir pelo sucessor in-order)
            NoAVL<K> sucessor = encontrarMenorValor(no.direita);
            no.chave = sucessor.chave;
            no.direita = removerNo(no.direita, sucessor.chave);
        }

        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
        return balancear(no);
    }

    private NoAVL<K> encontrarMenorValor(NoAVL<K> no) {
        while (no.esquerda != null) no = no.esquerda;
        return no;
    }

    private void inOrder(NoAVL<K> no, ListaEncadeada<K> lista) {
        if (no != null) {
            inOrder(no.direita, lista); // Ordem decrescente (maior -> menor)
            lista.adicionar(no.chave);
            inOrder(no.esquerda, lista);
        }
    }

    public K[] values(Class<K> clazz) {
        ListaEncadeada<K> lista = new ListaEncadeada<>();
        inOrder(raiz, lista);
        return lista.paraArray(clazz);  
    }
}
