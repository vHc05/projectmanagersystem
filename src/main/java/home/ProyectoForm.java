package home;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProyectoForm extends JPanel {
    private JTextField nombreField;
    private JTextField responsableField;
    private JTextField fechaInicioField;
    private JTextField fechaFinField;
    private JTextField estadoField;

    // Formato de fecha que vamos a utilizar (DD-MM-YYYY)
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

        try {
            // Convertir las fechas ingresadas a objetos Date
            fechaInicio = dateFormat.parse(fechaInicioField.getText());
            fechaFin = dateFormat.parse(fechaFinField.getText());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Retornar un nuevo objeto Proyecto
        return new Proyecto(nombre, responsable, fechaInicio, fechaFin, estado);
    }

    // Método para cargar un proyecto existente en los campos
    public void setProyecto(Proyecto proyecto) {
        nombreField.setText(proyecto.getNombre());
        responsableField.setText(proyecto.getResponsable());

        // Convertir Date a String usando SimpleDateFormat antes de mostrarlo
        fechaInicioField.setText(dateFormat.format(proyecto.getFechaInicio()));
        fechaFinField.setText(dateFormat.format(proyecto.getFechaFin()));

        estadoField.setText(proyecto.getEstado());
    }
}
