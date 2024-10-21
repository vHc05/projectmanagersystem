package home;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ObjectFileInterface extends JFrame {
    private ProyectoManager proyectoManager;
    private JList<String> projectList;
    private DefaultListModel<String> listModel;
    private String filename = "proyectos.obj";
    private JButton deleteButton;
    private JButton modifyButton;
    private JButton downloadButton;

    public ObjectFileInterface() {
        proyectoManager = new ProyectoManager();
        setTitle("Proyectos en Fichero de Objetos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicializar el modelo de lista y la lista de proyectos
        listModel = new DefaultListModel<>();
        projectList = new JList<>(listModel);
        add(new JScrollPane(projectList), BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Agregar Proyecto");
        addButton.addActionListener(e -> addProject());
        buttonPanel.add(addButton);

        // Botones Eliminar y Modificar, inicialmente deshabilitados
        deleteButton = new JButton("Eliminar Proyecto");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(e -> deleteProject());
        buttonPanel.add(deleteButton);

        modifyButton = new JButton("Modificar Proyecto");
        modifyButton.setEnabled(false);
        modifyButton.addActionListener(e -> modifyProject());
        buttonPanel.add(modifyButton);

        // Botón para descargar el archivo
        downloadButton = new JButton("Descargar Archivo");
        downloadButton.addActionListener(e -> downloadFile());
        buttonPanel.add(downloadButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Listener para habilitar botones cuando se selecciona un proyecto
        projectList.addListSelectionListener(e -> {
            boolean seleccion = !projectList.isSelectionEmpty();
            deleteButton.setEnabled(seleccion);
            modifyButton.setEnabled(seleccion);
        });

        // Cargar proyectos al iniciar
        loadObjectFile();
    }

    private void loadObjectFile() {
        try {
            proyectoManager.cargarDesdeObjetos(proyectoManager.getFilePath(filename));
            reloadDisplay();
            if (proyectoManager.getProyectos().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay proyectos disponibles.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el fichero de objetos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reloadDisplay() {
        listModel.clear();
        for (Proyecto p : proyectoManager.getProyectos()) {
            listModel.addElement(p.getNombre() + " - " + p.getResponsable() + " - " + p.getFechaInicio());
        }
    }

    private void addProject() {
        ProyectoForm form = new ProyectoForm();
        int result = JOptionPane.showConfirmDialog(this, form, "Agregar Proyecto",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Proyecto newProject = form.getProyecto();
            proyectoManager.agregarProyecto(newProject);
            try {
                proyectoManager.guardarEnObjetos(proyectoManager.getFilePath(filename));
                reloadDisplay();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al guardar el fichero de objetos: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteProject() {
        int index = projectList.getSelectedIndex();
        if (index >= 0) {
            Proyecto proyecto = proyectoManager.getProyectos().get(index);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar el proyecto \"" + proyecto.getNombre() + "\"?",
                    "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                proyectoManager.eliminarProyecto(proyecto.getNombre());
                try {
                    proyectoManager.guardarEnObjetos(proyectoManager.getFilePath(filename));
                    reloadDisplay();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al guardar el fichero de objetos: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void modifyProject() {
        int index = projectList.getSelectedIndex();
        if (index >= 0) {
            Proyecto actualProject = proyectoManager.getProyectos().get(index);
            ProyectoForm form = new ProyectoForm();
            form.setProyecto(actualProject);

            int result = JOptionPane.showConfirmDialog(this, form, "Modificar Proyecto",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                Proyecto modifiedProject = form.getProyecto();
                proyectoManager.modificarProyecto(actualProject.getNombre(), modifiedProject);
                try {
                    proyectoManager.guardarEnObjetos(proyectoManager.getFilePath(filename));
                    reloadDisplay();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al guardar el fichero de objetos: " + e.getMessage(),
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
            File destination = fileChooser.getSelectedFile();
            String originPath = proyectoManager.getFilePath(filename);
            File origin = new File(originPath);
            try {
                Files.copy(origin.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(this, "Archivo descargado exitosamente.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al descargar el archivo: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
