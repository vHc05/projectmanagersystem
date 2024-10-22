package home;

import home.Proyecto;
import home.ProyectoForm;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ProyectoFormTest {
    private ProyectoForm proyectoForm;

    @BeforeEach
    void setUp() {
        proyectoForm = new ProyectoForm();
    }

    @Test
    void testGetProyecto() throws ParseException {
        // Configura los campos del formulario
        proyectoForm.nombreField.setText("Proyecto Test");
        proyectoForm.responsableField.setText("Responsable Test");
        proyectoForm.fechaInicioField.setText("2024-10-20");
        proyectoForm.fechaFinField.setText("2024-10-30");
        proyectoForm.estadoField.setText("En Progreso");

        // Obtén el proyecto
        Proyecto proyecto = proyectoForm.getProyecto();

        // Verifica que se obtengan los datos correctos
        assertEquals("Proyecto Test", proyecto.getNombre());
        assertEquals("Responsable Test", proyecto.getResponsable());
        assertEquals("En Progreso", proyecto.getEstado());

        // Verifica que las fechas se hayan establecido correctamente
        Date expectedFechaInicio = proyectoForm.dateFormat.parse("2024-10-20");
        Date expectedFechaFin = proyectoForm.dateFormat.parse("2024-10-30");
        assertEquals(expectedFechaInicio, proyecto.getFechaInicio());
        assertEquals(expectedFechaFin, proyecto.getFechaFin());
    }

    @Test
    void testGetProyecto_InvalidDateFormat() {
        // Establecer valores de prueba con un formato de fecha incorrecto
        proyectoForm.nombreField.setText("Proyecto Test");
        proyectoForm.responsableField.setText("Responsable Test");
        proyectoForm.fechaInicioField.setText("20-10-2024"); // Formato incorrecto
        proyectoForm.fechaFinField.setText("2024-10-30");
        proyectoForm.estadoField.setText("En Progreso");

        // Captura el diálogo de advertencia
        // Almacena el sistema de salida actual para restaurarlo después
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Proyecto proyecto = proyectoForm.getProyecto();

        // Verifica que el campo nombre siga estando lleno
        assertEquals("Proyecto Test", proyectoForm.nombreField.getText());
        
        // Verifica que la fecha de inicio esté como null
        assertNull(proyecto.getFechaInicio());
        
        // Restaura la salida del sistema
        System.setOut(originalOut);
    }

    @Test
    void testSetProyecto() {
        // Crear un proyecto para cargar
        Proyecto proyecto = new Proyecto("Proyecto Test", "Responsable Test", new Date(), new Date(), "En Progreso");

        // Cargar el proyecto en el formulario
        proyectoForm.setProyecto(proyecto);

        // Verificar que los campos del formulario contengan la información del proyecto
        assertEquals("Proyecto Test", proyectoForm.nombreField.getText());
        assertEquals("Responsable Test", proyectoForm.responsableField.getText());
        assertEquals("En Progreso", proyectoForm.estadoField.getText());
    }
}
