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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@XmlRootElement
public class ProyectoManager {
    private List<Proyecto> proyectos = new ArrayList<>();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public String getFilePath(String filename) {
        return "src/assets/" + filename;
    }

    @XmlElement
    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void agregarProyecto(Proyecto proyecto) {
        proyectos.add(proyecto);
    }

    public void eliminarProyecto(String nombre) {
        proyectos.removeIf(p -> p.getNombre().equals(nombre));
    }

    public void modificarProyecto(String nombre, Proyecto proyectoModificado) {
        for (int i = 0; i < proyectos.size(); i++) {
            if (proyectos.get(i).getNombre().equals(nombre)) {
                proyectos.set(i, proyectoModificado);
                break;
            }
        }
    }

    public void guardarEnJSON(String filepath) throws IOException {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filepath)) {
            gson.toJson(proyectos, writer);
        }
    }

    public void guardarEnCSV(String filepath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filepath))) {
            for (Proyecto p : proyectos) {
                writer.writeNext(new String[]{
                        p.getNombre(),
                        p.getResponsable(),
                        sdf.format(p.getFechaInicio()),  // Convertir Date a String
                        sdf.format(p.getFechaFin()),     // Convertir Date a String
                        p.getEstado()
                });
            }
        }
    }

    public void guardarEnXML(String filepath) throws Exception {
        JAXBContext context = JAXBContext.newInstance(ProyectoManager.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Optional.of(true));
        try (FileOutputStream fos = new FileOutputStream(filepath)) {
            marshaller.marshal(this, fos);
        }
    }

    public void guardarEnObjetos(String filepath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            oos.writeObject(proyectos);
        }
    }

    // Método para cargar proyectos desde un archivo CSV
    public void cargarDesdeCSV(String filepath) throws IOException {
        proyectos.clear(); // Limpiar la lista actual antes de cargar
        try (CSVReader reader = new CSVReader(new FileReader(filepath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                try {
                    // Convertir las fechas de String a Date
                    Date fechaInicio = sdf.parse(line[2]);
                    Date fechaFin = sdf.parse(line[3]);

                    Proyecto proyecto = new Proyecto(line[0], line[1], fechaInicio, fechaFin, line[4]);
                    proyectos.add(proyecto);
                } catch (ParseException e) {
                    throw new IOException("Error al convertir las fechas", e);
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para cargar proyectos desde un archivo JSON
    public void cargarDesdeJSON(String filepath) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filepath)) {
            Type listType = new TypeToken<ArrayList<Proyecto>>() {}.getType();
            proyectos = gson.fromJson(reader, listType);
        }
    }

    // Método para cargar proyectos desde un archivo XML
    public void cargarDesdeXML(String filepath) throws Exception {
        JAXBContext context = JAXBContext.newInstance(ProyectoManager.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (FileInputStream fis = new FileInputStream(filepath)) {
            ProyectoManager pm = (ProyectoManager) unmarshaller.unmarshal(fis);
            proyectos = pm.getProyectos();
        }
    }

    public void cargarDesdeObjetos(String filepath) throws IOException, ClassNotFoundException {
        proyectos.clear();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            proyectos = (List<Proyecto>) ois.readObject();
        }
    }
}
