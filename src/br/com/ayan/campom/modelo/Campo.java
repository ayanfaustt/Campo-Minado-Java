package br.com.ayan.campom.modelo;

import br.com.ayan.campom.excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Campo {

    private final int linha;
    private final int coluna;
    private boolean minado;
    private boolean aberto = false;
    private boolean marcado = false;
    private List<Campo> vizinhos = new ArrayList<>();

    public Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
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
        }
    }

    public boolean abrir(){

        if(!aberto && !marcado){
            aberto = true;

            if(minado){
                throw new ExplosaoException();
            }

            if(segura()){
                vizinhos.forEach(v -> v.abrir());
            }

            return true;

        } else{
            return false;
        }

    }

    boolean segura(){
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

    long minasNaVizinhanca(){
        return vizinhos.stream().filter(v -> v.minado).count();
    }

    void reiniciar(){
        aberto = false;
        minado = false;
        marcado = false;
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
    }
}
