package br.com.ayan.campom.visao;

import br.com.ayan.campom.excecao.ExplosaoException;
import br.com.ayan.campom.excecao.SairException;
import br.com.ayan.campom.modelo.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class TabuleiroConsole {
    private Tabuleiro tabuleiro;
    private Scanner entrada = new Scanner(System.in);

    public TabuleiroConsole(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;

        executarJogo();
    }

    private void executarJogo() {
        try {
            boolean continuar = true;
            while (continuar){
                turnoDojogo();
                
                System.out.println("Jogar novamente ? (S/n)");
                String resposta = entrada.nextLine();
                if("n".equalsIgnoreCase(resposta)){
                    continuar = false;
                }else {
                    tabuleiro.reiniciar();
                }
            }
        } catch (SairException e){
            System.out.println("Tchau !");
        } finally {
            entrada.close();
        }
    }

    private void turnoDojogo() {
        try {

            while (!tabuleiro.objetivoAlcancado()){
                System.out.println(tabuleiro);
                String digitado = capturarInteracao("Digite (x,y): ");

                Iterator<Integer> xy = Arrays.stream(digitado.split(",")).map(e -> Integer.parseInt(e.trim())).iterator();

                digitado = capturarInteracao("1 - Abrir ou 2 - (Des)Marcar: ");

                if("1".equals(digitado)){
                    tabuleiro.abrir(xy.next(), xy.next());
                } else if ("2".equals(digitado)) {
                    tabuleiro.marcar(xy.next(), xy.next());
                    
                }

            }
            System.out.println(tabuleiro);
            System.out.println("Vencedor");
        } catch (ExplosaoException e){
            System.out.println(tabuleiro);
            System.out.println("booooom!");
        }
    }

    private String capturarInteracao(String texto){
        System.out.print(texto);
        String digitado = entrada.nextLine();

        if("sair".equalsIgnoreCase(digitado)){
            throw new SairException();
        }

        return digitado;
    }
}
