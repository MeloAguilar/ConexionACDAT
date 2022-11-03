import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Gestion {
    private MiConexion conexion;
    public static final String BBDD = "jdbc:mysql://dns11036.phdns11.es";
    public static final String USER = "caguilar";
    public static final String PASS = "caguilar";

    public Gestion(String nameBBDD, String user, String password)
    {
        conexion = new MiConexion(nameBBDD, user, password);
    }

    public Gestion(){
        conexion = new MiConexion(BBDD, USER, PASS);

    }
    /**
     * Método que se encarga de crear una tabla dentro de la base de datos
     */
    public void crearTabla(String nombreTabla, String[] tabla){

        StringBuilder peticion = new StringBuilder("CREATE TABLE ad2223_caguilar." + nombreTabla + " (");

        for (int i = 0; i < tabla.length; i++) {
            peticion.append(tabla[i]);
            if(i< tabla.length-1){
                peticion.append(",");
            }
        }
        peticion.append(");");
        try {
            conexion.abrirConexion();
            Statement sttmt = this.conexion.getConexion().createStatement();
            sttmt.executeUpdate(peticion.toString());
            System.out.println("Se generó la tabla "+ nombreTabla);
            System.out.println("Se creó la tabla "+nombreTabla+" con los datos " + tabla.toString());
        } catch (SQLException e) {
            System.out.println("No se pudo insertar la tabla");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            conexion.cerrarConexion();
        }


    }

    public int crearTablas()
    {
        int filasAfectadas = 0;
        crearTabla("Profesores", new String[]{"id int Primary Key AUTO_INCREMENT", "nombre varChar(50)", "apellidos varChar(50)", "fechaNacimiento Date", "antiguedad int"});
        crearTabla("Alumnos", new String[]{"id int Primary Key AUTO_INCREMENT", "nombre varChar(50)", "apellidos varChar(50)", "fechaNacimiento Date"});
        crearTabla("Matriculas", new String[]{"id int Primary Key AUTO_INCREMENT", "idProfesor int", "idAlumno int", "asignatura varChar(50)", "curso int", "Foreign Key idProfesor References Profesores(id) On delete Cascade on Update Cascade", " Foreign Key idAlumno References Alumnos(id) On Delete Cascade on Update Cascade"});
        return filasAfectadas;
    }

    /**
     * Método que se encarga de enviar una instrucción select al servidor de la base de datos
     * y devuelve un ResultSet
     *
     * <pre>la petición debe ser una cadena con estilo sql que haga referencia a una tabla de la base de datos
     * que exista</pre>
     * <post>Devolverá un Array de Strings que contendrán todos los datos recibidos de la BBDD</post>
     * @param peticion
     * @return ResultSet -> result
     */
    public ResultSet select(String peticion){
        ResultSet result = null;

            //result = this.conexion.executeQuery(peticion);


        return result;
    }


    public int insert(File archivo){
        String linea;
        BufferedReader br;
        try {
            Statement statement = conexion.abrirConexion().createStatement();
            br = new BufferedReader(new FileReader(archivo));

            while((linea = br.readLine())!=null){
                statement.executeUpdate(linea);

            }

            System.out.println("datos insertados correctamente");
        } catch (SQLException e) {
            System.out.println("No se puede acceder a la base de datos");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no se encontró");
        } catch (IOException e) {
            System.out.println("Errror de entrada o salida de datos");
        }
        finally {
            conexion.cerrarConexion();
        }
        return 0;
    }


    public int update(String peticion, int id){
        return 0;
    }

    public void delete(String sql){
        try {
            var stmnt = conexion.abrirConexion().createStatement();
           stmnt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("No se pudo eliminar el dato");
        } catch (ClassNotFoundException e) {
            System.out.println("No se pudo accede a la base de datos");
        }

    }

    public void deleteTable(String tabla){
        String tablaString = "DROP TABLE ad2223_caguilar." + tabla;
        delete(tablaString);
    }

    public int deleteTables(){
        int filasAfectadas = 0;
        deleteTable("Matriculas");
        filasAfectadas += 1;
        deleteTable("Profesores");
        filasAfectadas += 1;
        deleteTable("Alumnos");
        filasAfectadas += 1;
        return filasAfectadas;
    }


}
