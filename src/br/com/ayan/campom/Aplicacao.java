package br.com.ayan.campom;

import br.com.ayan.campom.modelo.Tabuleiro;
import br.com.ayan.campom.visao.TabuleiroConsole;

public class Aplicacao {
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(6,6,6);

        new TabuleiroConsole(tabuleiro);
    }
}
