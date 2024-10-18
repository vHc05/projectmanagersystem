package home;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlRootElement
public class ProjectManager {
    private List<Proyecto> projects = new ArrayList<>();

    public String getFilePath(String filename) {
        return "src/assets/" + filename;
    }


    @XmlElement
    public List<Proyecto> getProjects() {
        return projects;
    }

    public void addProject(Proyecto proyecto) {
        projects.add(proyecto);
    }

    public void deleteProject(String name) {
        projects.removeIf(p -> p.getNombre().equals(name));
    }

    public void modifyProject(String name, Proyecto modifiedProject) {
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getNombre().equals(name)) {
                projects.set(i, modifiedProject);
                break;
            }
        }
    }

    public void saveInJSON(String filepath) throws IOException {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filepath)) {
            gson.toJson(projects, writer);
        }
    }

    public void saveInCSV(String filepath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filepath))) {
            for (Proyecto p : projects) {
                writer.writeNext(new String[]{p.getNombre(), p.getResponsable(), p.getFechaInicio(), p.getFechaFin(), p.getEstado()});
            }
        }
    }

    public void saveInXML(String filepath) throws Exception {
        JAXBContext context = JAXBContext.newInstance(ProjectManager.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        try (FileOutputStream fos = new FileOutputStream(filepath)) {
            marshaller.marshal(this, fos);
        }
    }

    // Método para cargar proyectos desde un archivo CSV
    public void loadFromCSV(String filepath) throws IOException {
        projects.clear(); // Limpiar la lista actual antes de cargar
        try (CSVReader reader = new CSVReader(new FileReader(filepath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                Proyecto project = new Proyecto(line[0], line[1], line[2], line[3], line[4]);
                projects.add(project);
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para cargar proyectos desde un archivo JSON
    public void loadFromJSON(String filepath) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filepath)) {
            Type listType = new TypeToken<ArrayList<Proyecto>>(){}.getType();
            projects = gson.fromJson(reader, listType);
        }
    }

    // Método para cargar proyectos desde un archivo XML
    public void loadFromXML(String filepath) throws Exception {
        JAXBContext context = JAXBContext.newInstance(ProjectManager.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (FileInputStream fis = new FileInputStream(filepath)) {
            ProjectManager pm = (ProjectManager) unmarshaller.unmarshal(fis);
            projects = pm.getProjects();
        }
    }
}
