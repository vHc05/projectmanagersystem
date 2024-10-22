package home;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CSVInterfaceTest {
    private CSVInterface csvInterface;
    private ProyectoManager proyectoManager;

    @BeforeEach
    void setUp() {
        // Inicializa el ProyectoManager
        proyectoManager = new ProyectoManager();
        csvInterface = new CSVInterface();

        // Asegúrate de que el archivo CSV esté limpio antes de cada prueba
        String filename = "proyectos.csv";
        File file = new File(proyectoManager.getFilePath(filename));
        if (file.exists()) {
            file.delete(); // Eliminar cualquier archivo previo
        }
    }

    @Test
    void testLoadCSV_FileDoesNotExist() {
        // Intenta cargar el CSV cuando no existe
        csvInterface.loadCSV();

        // Verifica que la lista de proyectos esté vacía
        assertTrue(proyectoManager.getProyectos().isEmpty());
        // Aquí podrías agregar verificación de mensajes de diálogo si es necesario
    }

    @Test
    void testAddProject() {
        // Crea un nuevo proyecto
        Proyecto nuevoProyecto = new Proyecto("Proyecto Test", "Responsable Test", new Date(), new Date(), "Activo");
        
        // Agrega el proyecto
        proyectoManager.agregarProyecto(nuevoProyecto);
        
        // Verifica que el proyecto se haya agregado
        assertEquals(1, proyectoManager.getProyectos().size());
        assertEquals(nuevoProyecto.getNombre(), proyectoManager.getProyectos().get(0).getNombre());
    }

    @Test
    void testDeleteProject() throws IOException {
        // Crea y agrega un proyecto
        Proyecto proyecto = new Proyecto("Proyecto Test", "Responsable Test", new Date(), new Date(), "Activo");
        proyectoManager.agregarProyecto(proyecto);
        
        // Verifica que se haya agregado
        assertEquals(1, proyectoManager.getProyectos().size());

        // Elimina el proyecto
        proyectoManager.eliminarProyecto("Proyecto Test");

        // Verifica que la lista de proyectos esté vacía
        assertTrue(proyectoManager.getProyectos().isEmpty());
    }

    @Test
    void testModifyProject() throws IOException {
        // Crea y agrega un proyecto
        Proyecto proyectoOriginal = new Proyecto("Proyecto Test", "Responsable Test", new Date(), new Date(), "Activo");
        proyectoManager.agregarProyecto(proyectoOriginal);

        // Modifica el proyecto
        Proyecto proyectoModificado = new Proyecto("Proyecto Modificado", "Responsable Modificado", new Date(), new Date(), "Finalizado");
        proyectoManager.modificarProyecto("Proyecto Test", proyectoModificado);

        // Verifica que el proyecto haya sido modificado
        assertEquals(1, proyectoManager.getProyectos().size());
        assertEquals(proyectoModificado.getNombre(), proyectoManager.getProyectos().get(0).getNombre());
        assertEquals(proyectoModificado.getEstado(), proyectoManager.getProyectos().get(0).getEstado());
    }

    @Test
    void testDownloadFile() throws IOException {
        // Crea y agrega un proyecto
        Proyecto proyecto = new Proyecto("Proyecto Test", "Responsable Test", new Date(), new Date(), "Activo");
        proyectoManager.agregarProyecto(proyecto);
        
        // Guarda el proyecto en el archivo CSV
        proyectoManager.guardarEnCSV(proyectoManager.getFilePath("proyectos.csv"));

        // Verifica que el archivo se haya creado
        File file = new File(proyectoManager.getFilePath("proyectos.csv"));
        assertTrue(file.exists());
    }
}


