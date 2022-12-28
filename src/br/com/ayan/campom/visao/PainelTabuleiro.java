package br.com.ayan.campom.visao;

import br.com.ayan.campom.modelo.Tabuleiro;

import javax.swing.*;
import java.awt.*;

public class PainelTabuleiro extends JPanel {
    public PainelTabuleiro(Tabuleiro tabuleiro) {
        setLayout(new GridLayout(tabuleiro.getLinhas(), tabuleiro.getColunas()));

        int total = tabuleiro.getLinhas() * tabuleiro.getColunas();

        tabuleiro.paracCada(c -> add(new BotaoCampo(c)));

        tabuleiro.setObservador(e -> {

            SwingUtilities.invokeLater(() -> {
                if(e.isGanhou()){
                    JOptionPane.showMessageDialog(this, "Ganhou");
                }else{
                    JOptionPane.showMessageDialog(this, "Perdeu");
                }

                tabuleiro.reiniciar();
            });

        });
    }
}
