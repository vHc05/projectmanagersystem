package home;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProyectoManagerTest {

    private ProyectoManager proyectoManager;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    void setUp() {
        proyectoManager = new ProyectoManager();
    }

    @Test
    void testAgregarProyecto() {
        Proyecto proyecto = new Proyecto("Proyecto Test", "Responsable Test", new Date(), new Date(), "Estado Test");
        proyectoManager.agregarProyecto(proyecto);
        assertEquals(1, proyectoManager.getProyectos().size(), "La lista de proyectos debería tener un proyecto.");
    }

    @Test
    void testEliminarProyecto() {
        Proyecto proyecto = new Proyecto("Proyecto Test", "Responsable Test", new Date(), new Date(), "Estado Test");
        proyectoManager.agregarProyecto(proyecto);
        proyectoManager.eliminarProyecto("Proyecto Test");
        assertTrue(proyectoManager.getProyectos().isEmpty(), "La lista de proyectos debería estar vacía después de eliminar.");
    }

    @Test
    void testModificarProyecto() {
        Proyecto proyecto = new Proyecto("Proyecto Test", "Responsable Test", new Date(), new Date(), "Estado Test");
        proyectoManager.agregarProyecto(proyecto);
        Proyecto proyectoModificado = new Proyecto("Proyecto Modificado", "Responsable Modificado", new Date(), new Date(), "Estado Modificado");
        proyectoManager.modificarProyecto("Proyecto Test", proyectoModificado);
        assertEquals("Proyecto Modificado", proyectoManager.getProyectos().get(0).getNombre(), "El nombre del proyecto debería ser modificado.");
    }

    @Test
    void testGuardarYcargarDesdeJSON() throws IOException {
        Proyecto proyecto = new Proyecto("Proyecto Test", "Responsable Test", new Date(), new Date(), "Estado Test");
        proyectoManager.agregarProyecto(proyecto);
        String filepath = proyectoManager.getFilePath("proyectos_test.json");
        proyectoManager.guardarEnJSON(filepath);
        ProyectoManager proyectoManagerCargado = new ProyectoManager();
        proyectoManagerCargado.cargarDesdeJSON(filepath);
        assertEquals(1, proyectoManagerCargado.getProyectos().size(), "La lista de proyectos debería tener un proyecto después de cargar desde JSON.");
        new File(filepath).delete();
    }

    @Test
    void testGuardarYcargarDesdeCSV() throws IOException {
        Proyecto proyecto = new Proyecto("Proyecto Test", "Responsable Test", new Date(), new Date(), "Estado Test");
        proyectoManager.agregarProyecto(proyecto);
        String filepath = proyectoManager.getFilePath("proyectos_test.csv");
        proyectoManager.guardarEnCSV(filepath);
        ProyectoManager proyectoManagerCargado = new ProyectoManager();
        proyectoManagerCargado.cargarDesdeCSV(filepath);
        assertEquals(1, proyectoManagerCargado.getProyectos().size(), "La lista de proyectos debería tener un proyecto después de cargar desde CSV.");
        new File(filepath).delete();
    }

    @Test
    void testGuardarYcargarDesdeXML() throws Exception {
        Proyecto proyecto = new Proyecto("Proyecto Test", "Responsable Test", new Date(), new Date(), "Estado Test");
        proyectoManager.agregarProyecto(proyecto);
        String filepath = proyectoManager.getFilePath("proyectos_test.xml");
        proyectoManager.guardarEnXML(filepath);
        ProyectoManager proyectoManagerCargado = new ProyectoManager();
        proyectoManagerCargado.cargarDesdeXML(filepath);
        assertEquals(1, proyectoManagerCargado.getProyectos().size(), "La lista de proyectos debería tener un proyecto después de cargar desde XML.");
        new File(filepath).delete();
    }

    @Test
    void testGuardarYcargarDesdeObjetos() throws IOException, ClassNotFoundException {
        Proyecto proyecto = new Proyecto("Proyecto Test", "Responsable Test", new Date(), new Date(), "Estado Test");
        proyectoManager.agregarProyecto(proyecto);
        String filepath = proyectoManager.getFilePath("proyectos_test.dat");
        proyectoManager.guardarEnObjetos(filepath);
        ProyectoManager proyectoManagerCargado = new ProyectoManager();
        proyectoManagerCargado.cargarDesdeObjetos(filepath);
        assertEquals(1, proyectoManagerCargado.getProyectos().size(), "La lista de proyectos debería tener un proyecto después de cargar desde archivo de objetos.");
        new File(filepath).delete();
    }
}

