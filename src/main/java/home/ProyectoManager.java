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
        String directoryPath = "src/assets/";
        File directory = new File(directoryPath);

        // Si la carpeta no existe, crearla
        if (!directory.exists()) {
            directory.mkdirs();  // Crea la carpeta y cualquier subcarpeta que falte
        }

        return directoryPath + filename;
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
                if (proyectoValido(p)) {
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
    }

    public void guardarEnXML(String filepath) throws Exception {
        JAXBContext context = JAXBContext.newInstance(ProyectoManager.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        try (FileOutputStream fos = new FileOutputStream(filepath)) {
            marshaller.marshal(this, fos);
        }
    }

    public void guardarEnObjetos(String filepath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            oos.writeObject(proyectos);
        }
    }

    // Método para cargar proyectos desde un archivo CSV con validaciones
    public void cargarDesdeCSV(String filepath) throws IOException {
        proyectos.clear(); // Limpiar la lista actual antes de cargar
        try (CSVReader reader = new CSVReader(new FileReader(filepath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                try {
                    // Validar que todos los valores estén presentes
                    if (line.length < 5 || line[0].isEmpty() || line[1].isEmpty() || line[2].isEmpty() || line[3].isEmpty() || line[4].isEmpty()) {
                        System.err.println("Datos incompletos en el CSV, omitiendo esta línea.");
                        continue;
                    }

                    // Convertir las fechas de String a Date
                    Date fechaInicio = sdf.parse(line[2]);
                    Date fechaFin = sdf.parse(line[3]);

                    Proyecto proyecto = new Proyecto(line[0], line[1], fechaInicio, fechaFin, line[4]);
                    if (proyectoValido(proyecto)) {
                        proyectos.add(proyecto);
                    } else {
                        System.err.println("Proyecto inválido encontrado en el CSV, omitiendo.");
                    }
                } catch (ParseException e) {
                    throw new IOException("Error al convertir las fechas", e);
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para cargar proyectos desde un archivo JSON con validaciones
    public void cargarDesdeJSON(String filepath) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filepath)) {
            Type listType = new TypeToken<ArrayList<Proyecto>>() {}.getType();
            List<Proyecto> loadedProyectos = gson.fromJson(reader, listType);
            for (Proyecto proyecto : loadedProyectos) {
                if (proyectoValido(proyecto)) {
                    proyectos.add(proyecto);
                } else {
                    System.err.println("Proyecto inválido encontrado en JSON, omitiendo.");
                }
            }
        }
    }

    // Método para cargar proyectos desde un archivo XML con validaciones
    public void cargarDesdeXML(String filepath) throws Exception {
        JAXBContext context = JAXBContext.newInstance(ProyectoManager.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (FileInputStream fis = new FileInputStream(filepath)) {
            ProyectoManager pm = (ProyectoManager) unmarshaller.unmarshal(fis);
            for (Proyecto proyecto : pm.getProyectos()) {
                if (proyectoValido(proyecto)) {
                    proyectos.add(proyecto);
                } else {
                    System.err.println("Proyecto inválido encontrado en XML, omitiendo.");
                }
            }
        }
    }

    // Método para cargar proyectos desde un archivo de objetos serializados con validaciones
    public void cargarDesdeObjetos(String filepath) throws IOException, ClassNotFoundException {
        proyectos.clear();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            List<Proyecto> loadedProyectos = (List<Proyecto>) ois.readObject();
            for (Proyecto proyecto : loadedProyectos) {
                if (proyectoValido(proyecto)) {
                    proyectos.add(proyecto);
                } else {
                    System.err.println("Proyecto inválido encontrado en archivo de objetos, omitiendo.");
                }
            }
        }
    }

    // Método para validar que un proyecto tenga todos los campos correctos
    private boolean proyectoValido(Proyecto p) {
        return p != null && p.getNombre() != null && !p.getNombre().isEmpty() &&
                p.getResponsable() != null && !p.getResponsable().isEmpty() &&
                p.getFechaInicio() != null && p.getFechaFin() != null &&
                p.getEstado() != null && !p.getEstado().isEmpty();
    }
}
