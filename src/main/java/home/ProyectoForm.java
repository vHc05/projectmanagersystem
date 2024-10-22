package home;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProyectoForm extends JPanel {
    public JTextField nombreField;
    public JTextField responsableField;
    public JTextField fechaInicioField;
    public JTextField fechaFinField;
    public JTextField estadoField;

    // Formato de fecha que vamos a utilizar (DD-MM-YYYY)
    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ProyectoForm() {
        setLayout(new GridLayout(5, 2));

        // Crear etiquetas y campos de texto
        add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        add(nombreField);

        add(new JLabel("Responsable:"));
        responsableField = new JTextField();
        add(responsableField);

        add(new JLabel("Fecha Inicio (YYYY-MM-DD):"));
        fechaInicioField = new JTextField();
        add(fechaInicioField);

        add(new JLabel("Fecha Fin (YYYY-MM-DD):"));
        fechaFinField = new JTextField();
        add(fechaFinField);

        add(new JLabel("Estado:"));
        estadoField = new JTextField();
        add(estadoField);
    }

    // Método para obtener un objeto Proyecto a partir de los campos
    public Proyecto getProyecto() {
        String nombre = nombreField.getText();
        String responsable = responsableField.getText();
        Date fechaInicio = null;
        Date fechaFin = null;
        String estado = estadoField.getText();

        // Expresión regular para validar el formato yyyy-MM-dd
        String datePattern = "\\d{4}-\\d{2}-\\d{2}";

        // Validar el formato de fecha de inicio
        if (!fechaInicioField.getText().matches(datePattern)) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use el formato YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return null; // No crear el proyecto si la fecha no es válida
        }

        // Validar el formato de fecha de fin
        if (!fechaFinField.getText().matches(datePattern)) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use el formato YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return null; // No crear el proyecto si la fecha no es válida
        }

        try {
            // Parsear las fechas si cumplen con el formato correcto
            fechaInicio = dateFormat.parse(fechaInicioField.getText());
            fechaFin = dateFormat.parse(fechaFinField.getText());
        } catch (ParseException e) {
            // En el caso extremadamente raro de que falle el parseo (pese a pasar la validación), mostramos error
            JOptionPane.showMessageDialog(this, "Error al parsear la fecha. Asegúrese de que esté en formato YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Retornar un nuevo objeto Proyecto solo si las fechas son válidas
        return new Proyecto(nombre, responsable, fechaInicio, fechaFin, estado);
    }

    // Método para cargar un proyecto existente en los campos
    public void setProyecto(Proyecto proyecto) {

        System.out.print(proyecto);
        nombreField.setText(proyecto.getNombre());
        responsableField.setText(proyecto.getResponsable());

        // Convertir Date a String usando SimpleDateFormat antes de mostrarlo
        fechaInicioField.setText(dateFormat.format(proyecto.getFechaInicio()));
        fechaFinField.setText(dateFormat.format(proyecto.getFechaFin()));

        estadoField.setText(proyecto.getEstado());
    }
}
