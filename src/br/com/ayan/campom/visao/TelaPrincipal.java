package br.com.ayan.campom.visao;

import br.com.ayan.campom.modelo.Tabuleiro;

import javax.swing.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal(){
        Tabuleiro tabuleiro = new Tabuleiro(16, 30, 5);
        PainelTabuleiro painel = new PainelTabuleiro(tabuleiro);

        add(painel);
        setTitle("Campo Minado");
        setSize(690, 438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        new TelaPrincipal();
    }
}
