package br.com.ayan.campom.modelo;


import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Campo {

    private final int linha;
    private final int coluna;
    private boolean minado;
    private boolean aberto = false;
    private boolean marcado = false;
    private List<Campo> vizinhos = new ArrayList<>();

    //Ambos fazem a mesma coisa, porém o primeiro usa uma interface funcional do próprio java
    private List<CampoObservador> observadores = new ArrayList<>();
    private List<BiConsumer<Campo, CampoEvento>> observers = new ArrayList<>();

    public Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public void setObservadores(CampoObservador observador){
        observadores.add(observador);
    }

    private void notificarObservadores(CampoEvento evento){
        observadores.stream().forEach(ob -> ob.eventoOcorreu(this, evento));
    }

     public boolean adicionarVizinho(Campo vizinho){
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int delta = deltaColuna + deltaLinha;

        if(delta == 1 && !diagonal){
            vizinhos.add(vizinho);
            return true;
        } else if(delta == 2 && diagonal){
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }

    }

    public void alternarMarcacao(){
        if(!aberto){
            marcado = !marcado;

            if(marcado){
                notificarObservadores(CampoEvento.MARCAR);
            }else {
                notificarObservadores(CampoEvento.DESMARCAR);
            }
        }
    }

    public boolean abrir(){

        if(!aberto && !marcado){
            if(minado){
                //TODO implementar nova versão
                 notificarObservadores(CampoEvento.EXPLODIR);
                 return true;
            }

            setAberto(true);

            notificarObservadores(CampoEvento.ABRIR);
            if(segura()){
                vizinhos.forEach(v -> v.abrir());
            }

            return true;

        } else{
            return false;
        }

    }

    public boolean segura(){
        return vizinhos.stream().noneMatch(v -> v.minado);
    }

    public void minar(){
        minado = true;
    }

    public boolean isMinado(){
        return minado;
    }

    public boolean isMarcado(){
        return marcado;
    }

    public boolean isAberto(){
        return aberto;
    }

    public boolean isFechado(){
        return !isAberto();
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    boolean objetivoAlcancado(){
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;

        return desvendado || protegido;
    }

    public int minasNaVizinhanca(){
        return (int) vizinhos.stream().filter(v -> v.minado).count();
    }

    void reiniciar(){
        aberto = false;
        minado = false;
        marcado = false;
        notificarObservadores(CampoEvento.REINICIAR);
    }

    public String toString(){
        if(marcado){
            return "x";
        } else if(aberto && minado){
            return "*";
        }else if(aberto && minasNaVizinhanca() > 0){
            return Long.toString(minasNaVizinhanca());
        }else if(aberto){
            return " ";
        }else{
            return "?";
        }
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;

        if(aberto){
            notificarObservadores(CampoEvento.ABRIR);
        }
    }
}
