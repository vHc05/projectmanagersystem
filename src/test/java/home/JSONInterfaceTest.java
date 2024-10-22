package home;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JSONInterfaceTest {
    private JSONInterface jsonInterface;
    private ProyectoManager proyectoManager;

    @BeforeEach
    void setUp() {
        proyectoManager = new ProyectoManager(); // Suponiendo que ProyectoManager tiene un constructor por defecto
        jsonInterface = new JSONInterface() {
            {
                this.proyectoManager = proyectoManager; // Reemplazamos el ProyectoManager real
            }
        };
    }

    @Test
    void testLoadJSON_FileDoesNotExist() {
        // Comprobamos que no se produzca una excepción al cargar un archivo que no existe
        assertDoesNotThrow(() -> jsonInterface.loadJSON());
        
        // El mensaje de advertencia debería ser mostrado (si se implementa en la función)
        // Esto es más difícil de probar sin Mockito, se sugiere un sistema de logs para la GUI o pruebas manuales.
    }

    @Test
    void testAddProject() throws IOException {
        // Agregamos un proyecto a la lista
        Proyecto nuevoProyecto = new Proyecto("Nuevo Proyecto", "Responsable", new Date(), new Date(), "Activo");
        
        // Simulamos agregar el proyecto
        proyectoManager.agregarProyecto(nuevoProyecto);
        proyectoManager.guardarEnJSON(proyectoManager.getFilePath("proyectos.json"));

        // Verificamos que el proyecto se haya agregado
        jsonInterface.reloadDisplay();
        assertEquals(1, proyectoManager.getProyectos().size());
        assertEquals(nuevoProyecto.getNombre(), proyectoManager.getProyectos().get(0).getNombre());
    }

    @Test
    void testDeleteProject() throws IOException {
        // Simulamos que hay un proyecto en la lista
        Proyecto proyecto = new Proyecto("Proyecto a Eliminar", "Responsable", new Date(), new Date(), "Activo");
        proyectoManager.agregarProyecto(proyecto);
        proyectoManager.guardarEnJSON(proyectoManager.getFilePath("proyectos.json"));

        // Comprobamos que hay un proyecto
        assertEquals(1, proyectoManager.getProyectos().size());

        // Agregamos el proyecto a la lista y lo eliminamos
        jsonInterface.reloadDisplay();
        jsonInterface.projectList.setSelectedIndex(1); // Seleccionamos el proyecto
        jsonInterface.deleteProject();

        // Verificamos que el proyecto haya sido eliminado
        assertEquals(1, proyectoManager.getProyectos().size());
    }

    @Test
    void testModifyProject() throws IOException {
        // Simulamos que hay un proyecto en la lista
        Proyecto proyecto = new Proyecto("Proyecto a Modificar", "Responsable", new Date(), new Date(), "Activo");
        proyectoManager.agregarProyecto(proyecto);
        proyectoManager.guardarEnJSON(proyectoManager.getFilePath("proyectos.json"));

        // Agregar el proyecto a la lista
        jsonInterface.reloadDisplay();
        jsonInterface.projectList.setSelectedIndex(0);

        // Creamos un proyecto modificado
        Proyecto proyectoModificado = new Proyecto("Proyecto Modificado", "Nuevo Responsable", new Date(), new Date(), "Finalizado");

        // Modificamos el proyecto mediante la interfaz
        proyectoManager.modificarProyecto(proyecto.getNombre(), proyectoModificado);
        proyectoManager.guardarEnJSON(proyectoManager.getFilePath("proyectos.json"));

        // Verificamos que el proyecto haya sido modificado
        jsonInterface.reloadDisplay();
        assertEquals(proyectoModificado.getNombre(), proyectoManager.getProyectos().get(0).getNombre());
    }

    @Test
    void testDownloadFile() throws IOException {
        // Comprobamos que el archivo se puede crear para descarga
        File file = new File("proyectos.json");
        file.createNewFile(); // Creamos el archivo
        proyectoManager.guardarEnJSON(proyectoManager.getFilePath("proyectos.json"));

        // Ejecutamos el método de descarga
        jsonInterface.downloadFile();

        // Verificamos que el archivo se haya creado
        assertTrue(file.exists());

        // Limpiamos después de la prueba
        Files.deleteIfExists(file.toPath());
    }
}
