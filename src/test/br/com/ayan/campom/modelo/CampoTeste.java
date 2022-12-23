package test.br.com.ayan.campom.modelo;
import br.com.ayan.campom.excecao.ExplosaoException;
import br.com.ayan.campom.modelo.Campo;
import org.junit.jupiter.api.BeforeEach;
import  org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.assertTrue;

public class CampoTeste {
    private Campo campo;

    @BeforeEach
    void iniciarCampo(){
        campo = new Campo(3,3);
    }
    @Test
    void testeVizinhoReal(){
        Campo vizinho = new Campo(3,2);

        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);

    }
    @Test
    void testeNaoVizinho(){
        Campo vizinho = new Campo(1,2);

        boolean resultado = campo.adicionarVizinho(vizinho);

        assertFalse(resultado);

    }

    @Test
    void testeValorPadraoAtributoMarcado(){
        assertFalse(campo.isMarcado());
    }

    @Test
    void testeAlternarMarcacao(){
        campo.alternarMarcacao();
        assertTrue(campo.isMarcado());
    }

    @Test
    void testeAlternarMarcacaoDuasChamadas(){
        campo.alternarMarcacao();
        campo.alternarMarcacao();
        assertFalse(campo.isMarcado());
    }

    @Test
    void testeArurNaoMinadoNaoMarcado(){

        assertTrue(campo.abrir());
    }

    @Test
    void testeArurNaoMinadoMarcado(){
        campo.alternarMarcacao();
        assertFalse(campo.abrir());
    }

    @Test
    void testeArurMinadoMarcado(){
        campo.alternarMarcacao();
        campo.minar();
        assertFalse(campo.abrir());
    }

    @Test
    void testeArurMinadoNaoMarcado(){
        campo.minar();

        assertThrows(ExplosaoException.class, () -> {
            campo.abrir();
        });
    }

    @Test
    void testeAbrirComVizinhos(){
        Campo vi1 = new Campo(2, 2);
        Campo vi2 = new Campo(2, 3);
        Campo vi3 = new Campo(1, 1);

        vi1.adicionarVizinho(vi3);

        campo.adicionarVizinho(vi1);
        campo.adicionarVizinho(vi2);

        campo.abrir();

        assertTrue(vi3.isAberto());
    }


    @Test
    void testeAbrirComVizinhos2(){
        Campo vi1 = new Campo(2, 2);
        Campo vi2 = new Campo(2, 3);
        Campo vi3 = new Campo(1, 1);

        vi3.minar();

        vi1.adicionarVizinho(vi3);

        campo.adicionarVizinho(vi1);
        campo.adicionarVizinho(vi2);

        campo.abrir();

        assertTrue(vi3.isFechado());
    }

}
