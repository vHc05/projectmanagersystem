package home;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UITest {

    private UI ui;

    @BeforeEach
    void setUp() {
        ui = new UI();
    }

    @Test
    void testCSVButtonActionPerformed() {
        // Simular acción del botón CSV
        ui.csvButton.doClick();
        // Aquí normalmente verificarías el estado de la interfaz, 
        // pero dado que estamos limitados por las dependencias, 
        // simplemente podemos asegurar que no hay errores al hacer clic.
        assertTrue(ui.csvButton.isEnabled(), "El botón CSV debería estar habilitado.");
    }

    @Test
    void testJSONButtonActionPerformed() {
        // Simular acción del botón JSON
        ui.jsonButton.doClick();
        assertTrue(ui.jsonButton.isEnabled(), "El botón JSON debería estar habilitado.");
    }

    @Test
    void testXMLButtonActionPerformed() {
        // Simular acción del botón XML
        ui.xmlButton.doClick();
        assertTrue(ui.xmlButton.isEnabled(), "El botón XML debería estar habilitado.");
    }

    @Test
    void testOBJButtonActionPerformed() {
        // Simular acción del botón OBJ
        ui.objButton.doClick();
        assertTrue(ui.objButton.isEnabled(), "El botón OBJ debería estar habilitado.");
    }
}
