package home;

import home.Proyecto;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ProyectoTest {

    @Test
    void testConstructorConParametros() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date fechaInicio = sdf.parse("2023-01-01");
            Date fechaFin = sdf.parse("2023-12-31");
            Proyecto proyecto = new Proyecto("Proyecto Test", "Responsable Test", fechaInicio, fechaFin, "En Progreso");

            assertEquals("Proyecto Test", proyecto.getNombre());
            assertEquals("Responsable Test", proyecto.getResponsable());
            assertEquals(fechaInicio, proyecto.getFechaInicio());
            assertEquals(fechaFin, proyecto.getFechaFin());
            assertEquals("En Progreso", proyecto.getEstado());
        } catch (Exception e) {
            fail("Se produjo una excepción al crear el proyecto: " + e.getMessage());
        }
    }

    @Test
    void testConstructorPorDefecto() {
        Proyecto proyecto = new Proyecto();
        assertNull(proyecto.getNombre());
        assertNull(proyecto.getResponsable());
        assertNull(proyecto.getFechaInicio());
        assertNull(proyecto.getFechaFin());
        assertNull(proyecto.getEstado());
    }

    @Test
    void testSettersYGetters() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date fechaInicio = sdf.parse("2023-01-01");
            Date fechaFin = sdf.parse("2023-12-31");
            Proyecto proyecto = new Proyecto();

            proyecto.setNombre("Proyecto Test");
            proyecto.setResponsable("Responsable Test");
            proyecto.setFechaInicio(fechaInicio);
            proyecto.setFechaFin(fechaFin);
            proyecto.setEstado("En Progreso");

            assertEquals("Proyecto Test", proyecto.getNombre());
            assertEquals("Responsable Test", proyecto.getResponsable());
            assertEquals(fechaInicio, proyecto.getFechaInicio());
            assertEquals(fechaFin, proyecto.getFechaFin());
            assertEquals("En Progreso", proyecto.getEstado());
        } catch (Exception e) {
            fail("Se produjo una excepción al establecer los valores del proyecto: " + e.getMessage());
        }
    }
}
