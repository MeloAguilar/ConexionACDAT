import java.io.File;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        Gestion gestion = new Gestion();

        System.out.println("Se elminiaron " + gestion.deleteTables() + " tablas");

        gestion.crearTablas();
        gestion.insert(new File("src\\ResourcesSQL\\Alumnos.sql"));
        gestion.insert(new File("src\\ResourcesSQL\\Profesores.sql"));
        gestion.insert(new File("src\\ResourcesSQL\\Matriculas.sql"));

    }
}

