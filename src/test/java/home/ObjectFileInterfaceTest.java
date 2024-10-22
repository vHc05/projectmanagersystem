package home;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class ObjectFileInterfaceTest {

    private ObjectFileInterface objectFileInterface;
    
    @BeforeEach
    void setUp() {
        objectFileInterface = new ObjectFileInterface();
        // Limpia los proyectos entre pruebas para evitar solapamientos
        objectFileInterface.proyectoManager.getProyectos().clear();
    }
    
    @Test
    void testAddProject() {
        // Simular agregar un proyecto
        objectFileInterface.addProject();
        assertEquals(1, objectFileInterface.proyectoManager.getProyectos().size(), "La lista de proyectos debería tener un proyecto.");
    }

    @Test
    void testDeleteProject() {
        // Simular agregar y luego eliminar un proyecto
        objectFileInterface.addProject();
        objectFileInterface.projectList.setSelectedIndex(0);
        objectFileInterface.deleteProject();
        assertEquals(0, objectFileInterface.proyectoManager.getProyectos().size(), "La lista de proyectos debería estar vacía después de eliminar.");
    }

    @Test
    void testModifyProject() {
        // Simular agregar un proyecto y luego modificarlo
        objectFileInterface.addProject();
        objectFileInterface.projectList.setSelectedIndex(0);
        objectFileInterface.modifyProject();
        assertEquals("Proyecto Modificado", objectFileInterface.proyectoManager.getProyectos().get(0).getNombre(), "El proyecto debería ser modificado.");
    }

    @Test
    void testDownloadFile() throws IOException {
        // Crear un archivo temporal para probar la descarga
        File tempFile = File.createTempFile("test", ".dat");
        objectFileInterface.proyectoManager.guardarEnObjetos(tempFile.getAbsolutePath());
        objectFileInterface.downloadFile();
        File downloadedFile = new File(objectFileInterface.proyectoManager.getFilePath("proyectos.dat"));
        assertTrue(Files.exists(downloadedFile.toPath()), "El archivo debería existir después de la descarga.");
    }

    @Test
    void testLoadObjectFile() {
        // Simular la carga del archivo de objetos
        objectFileInterface.loadObjectFile();
        assertFalse(objectFileInterface.proyectoManager.getProyectos().isEmpty(), "La lista de proyectos no debería estar vacía después de cargar.");
    }
}

