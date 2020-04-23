package Tests;

import com.unlam.asw.tp1.Triangulo;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TrianguloTest {

    @Test
    public void errorNegativoTodosLados() {
        double l1 = -3;
        double l2 = -5;
        double l3 = -7;
        Triangulo triangulo = new Triangulo(l1, l2, l3);
        assertEquals(-2, triangulo.obtenerTipoTriangulo());
    }

    @Test
    public void errorUnLadoConCero() {
        double l1 = 2;
        double l2 = 2;
        double l3 = 0;
        Triangulo triangulo = new Triangulo(l1, l2, l3);
        assertEquals(-2, triangulo.obtenerTipoTriangulo());
    }

    @Test
    public void equilateroProximoaCero() {
        double l1 = 0.000001;
        double l2 = 0.000001;
        double l3 = 0.000001;
        Triangulo triangulo = new Triangulo(l1, l2, l3);
        assertEquals(0, triangulo.obtenerTipoTriangulo());
    }

    @Test
    public void isoscelesLosUltimosLados() {
        double l1 = 5;
        double l2 = 3;
        double l3 = 3;
        Triangulo triangulo = new Triangulo(l1, l2, l3);
        assertEquals(1, triangulo.obtenerTipoTriangulo());
    }

    @Test
    public void noEsTriangulo() {
        double l1 = 5;
        double l2 = 8;
        double l3 = 3;
        Triangulo triangulo = new Triangulo(l1, l2, l3);
        assertEquals(-1, triangulo.obtenerTipoTriangulo());
    }

    @Test
    public void escaleno() {
        double l1 = 5;
        double l2 = 6;
        double l3 = 7;
        Triangulo triangulo = new Triangulo(l1, l2, l3);
        assertEquals(2, triangulo.obtenerTipoTriangulo());
    }
}
