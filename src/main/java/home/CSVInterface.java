package home;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;

public class CSVInterface extends JFrame {
    private ProyectoManager proyectoManager;
    private JList<String> projectList;
    private DefaultListModel<String> listModel;
    private String filename = "proyectos.csv";
    private JButton deleteButton;
    private JButton modifyButton;
    private JButton downloadButton;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Cambiar a "dd-MM-yyyy"

    public CSVInterface() {
        proyectoManager = new ProyectoManager();
        setTitle("Proyectos en CSV");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        projectList = new JList<>(listModel);
        add(new JScrollPane(projectList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Agregar Proyecto");
        addButton.addActionListener(e -> addProyect());
        buttonPanel.add(addButton);

        deleteButton = new JButton("Eliminar Proyecto");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(e -> deleteProyect());
        buttonPanel.add(deleteButton);

        modifyButton = new JButton("Modificar Proyecto");
        modifyButton.setEnabled(false);
        modifyButton.addActionListener(e -> modifyProyect());
        buttonPanel.add(modifyButton);

        downloadButton = new JButton("Descargar Archivo");
        downloadButton.addActionListener(e -> downloadFile());
        buttonPanel.add(downloadButton);

        add(buttonPanel, BorderLayout.SOUTH);

        projectList.addListSelectionListener(e -> {
            boolean seleccion = !projectList.isSelectionEmpty();
            deleteButton.setEnabled(seleccion);
            modifyButton.setEnabled(seleccion);
        });

        loadCSV();
    }

    private void loadCSV() {
        try {
            proyectoManager.cargarDesdeCSV(proyectoManager.getFilePath(filename));
            reloadDisplay();
            if (proyectoManager.getProyectos().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay proyectos disponibles.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar CSV: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reloadDisplay() {
        listModel.clear();
        for (Proyecto p : proyectoManager.getProyectos()) {
            String fechaInicioStr = sdf.format(p.getFechaInicio());
            listModel.addElement(p.getNombre() + " - " + p.getResponsable() + " - " + fechaInicioStr);
        }
    }

    private void addProyect() {
        ProyectoForm form = new ProyectoForm();
        int result = JOptionPane.showConfirmDialog(this, form, "Agregar Proyecto",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Proyecto nuevoProyecto = form.getProyecto();
            proyectoManager.agregarProyecto(nuevoProyecto);
            try {
                proyectoManager.guardarEnCSV(proyectoManager.getFilePath(filename));
                reloadDisplay();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar CSV: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteProyect() {
        int index = projectList.getSelectedIndex();
        if (index >= 0) {
            Proyecto proyecto = proyectoManager.getProyectos().get(index);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar el proyecto \"" + proyecto.getNombre() + "\"?",
                    "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                proyectoManager.eliminarProyecto(proyecto.getNombre());
                try {
                    proyectoManager.guardarEnCSV(proyectoManager.getFilePath(filename));
                    reloadDisplay();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error al guardar CSV: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void modifyProyect() {
        int index = projectList.getSelectedIndex();
        if (index >= 0) {
            Proyecto proyectoActual = proyectoManager.getProyectos().get(index);
            ProyectoForm form = new ProyectoForm();
            form.setProyecto(proyectoActual);

            int result = JOptionPane.showConfirmDialog(this, form, "Modificar Proyecto",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                Proyecto proyectoModificado = form.getProyecto();
                proyectoManager.modificarProyecto(proyectoActual.getNombre(), proyectoModificado);
                try {
                    proyectoManager.guardarEnCSV(proyectoManager.getFilePath(filename));
                    reloadDisplay();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error al guardar CSV: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void downloadFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(filename));
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File destino = fileChooser.getSelectedFile();
            String origenPath = proyectoManager.getFilePath(filename);
            File origen = new File(origenPath);
            try {
                Files.copy(origen.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(this, "Archivo descargado exitosamente.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al descargar el archivo: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
