package home;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class XMLInterfaceTest {

    private XMLInterface xmlInterface;

    @BeforeEach
    void setUp() {
        xmlInterface = new XMLInterface();
        // Limpiar los proyectos entre pruebas para evitar solapamientos
        xmlInterface.proyectoManager.getProyectos().clear();
    }

    @Test
    void testAddProject() {
        // Simular agregar un proyecto
        xmlInterface.addProject();
        assertEquals(1, xmlInterface.proyectoManager.getProyectos().size(), "La lista de proyectos debería tener un proyecto.");
    }

    @Test
    void testDeleteProject() {
        // Simular agregar y luego eliminar un proyecto
        xmlInterface.addProject();
        xmlInterface.projectList.setSelectedIndex(0);
        xmlInterface.deleteProject();
        assertEquals(0, xmlInterface.proyectoManager.getProyectos().size(), "La lista de proyectos debería estar vacía después de eliminar.");
    }

    @Test
    void testModifyProject() {
        // Simular agregar un proyecto y luego modificarlo
        xmlInterface.addProject();
        xmlInterface.projectList.setSelectedIndex(0);
        xmlInterface.modifyProject();
        assertEquals("Proyecto Modificado", xmlInterface.proyectoManager.getProyectos().get(0).getNombre(), "El proyecto debería ser modificado.");
    }

    @Test
    void testDownloadFile() throws IOException, Exception {
        // Crear un archivo temporal para probar la descarga
        File tempFile = File.createTempFile("test", ".xml");
        xmlInterface.proyectoManager.guardarEnXML(tempFile.getAbsolutePath());
        xmlInterface.downloadFile();
        File downloadedFile = new File(xmlInterface.proyectoManager.getFilePath("proyectos.xml"));
        assertTrue(Files.exists(downloadedFile.toPath()), "El archivo debería existir después de la descarga.");
    }

    @Test
    void testLoadXML() {
        // Simular la carga del archivo XML
        xmlInterface.loadXML();
        assertFalse(xmlInterface.proyectoManager.getProyectos().isEmpty(), "La lista de proyectos no debería estar vacía después de cargar.");
    }
}

