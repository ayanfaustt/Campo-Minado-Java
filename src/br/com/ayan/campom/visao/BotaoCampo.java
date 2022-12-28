package br.com.ayan.campom.visao;

import br.com.ayan.campom.modelo.Campo;
import br.com.ayan.campom.modelo.CampoEvento;
import br.com.ayan.campom.modelo.CampoObservador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BotaoCampo extends JButton implements CampoObservador, MouseListener {

    private final Color BG_PADRAO = new Color(184, 184, 184);
    private final Color BG_MARCAR = new Color(8, 179, 247);
    private final Color BG_EXPLODIR = new Color(189, 66, 68);
    private final Color TEXTO_VERDE = new Color(0, 100, 0);
    private Campo campo;

    public BotaoCampo(Campo campo) {
        this.campo = campo;
        setBorder(BorderFactory.createBevelBorder(0));
        setBackground(BG_PADRAO);

        addMouseListener(this);
        campo.setObservadores(this);
    }

    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento) {
        switch (evento){
            case ABRIR :
                aplicarEstiloAbrir();
                break;
            case MARCAR:
                aplicarEstiloMarcar();
                break;
            case EXPLODIR:
                aplicarEstiloExplodir();
                break;
            case DESMARCAR:
                aplicarEstiloDesmarcar();
                break;
            default:
                aplicarEstiloPadrao();
        }
    }

    private void aplicarEstiloPadrao() {
        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createBevelBorder(0));
        setText("");
    }

    private void aplicarEstiloDesmarcar() {
        setBackground(BG_PADRAO);
        setText("");
    }

    private void aplicarEstiloExplodir() {
        setBackground(BG_EXPLODIR);
        setText("X");
        setForeground(Color.white);
    }

    private void aplicarEstiloMarcar() {
        setBackground(BG_MARCAR);
        setForeground(Color.black);
        setText("M");
//        setIcon();

    }

    private void aplicarEstiloAbrir() {

        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        if (campo.isMinado()){
            setBackground(BG_EXPLODIR);
            return;
        }
        setBackground(BG_PADRAO);

        switch (campo.minasNaVizinhanca()){
            case 1:
                setForeground(TEXTO_VERDE);
                break;
            case 2:
                setForeground(Color.blue);
                break;
            case 3:
                setForeground(Color.yellow);
                break;
            case 4:
            case 5:
            case 6:
                setForeground(Color.red);
                break;
            default:
                setForeground(Color.PINK);
        }

        String valor = !campo.segura() ? campo.minasNaVizinhanca() + "" : "";

        setText(valor);
    }

    @Override
    public void mousePressed(MouseEvent e){
        if(e.getButton() == 1){
            campo.abrir();
        }else{
            campo.alternarMarcacao();
        }
    }

    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
}
