// Importación de las clases necesarias para la conexión a la base de datos
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

// Clase principal que maneja la conexión a la base de datos MySQL
public class Conexion {
    // Método principal que se ejecuta al iniciar el programa
    public static void main(String[] args) {
        // Configuración de los parámetros de conexión
        String url = "jdbc:mysql://localhost:3306/broker_online"; // URL de la base de datos
        String usuario = "root"; // Usuario de la base de datos
        String contraseñaDB = "T3quierovid@mia"; // Contraseña de la base de datos

        try {
            System.out.println("Intentando cargar el driver...");
            // Registro explícito del driver de MySQL (necesario para versiones 9.x del conector)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver cargado exitosamente");

            System.out.println("Intentando conectar a la base de datos...");
            System.out.println("URL: " + url);
            System.out.println("Usuario: " + usuario);
            
            // Establecimiento de la conexión con la base de datos
            Connection conexion = DriverManager.getConnection(url, usuario, contraseñaDB);
            System.out.println("✅ Conexión exitosa a la base de datos.");
            
            // Crear la tabla de usuarios
            Statement statement = conexion.createStatement();
            
            // Primero, eliminar la tabla si existe para evitar errores
            String dropTable = "DROP TABLE IF EXISTS usuarios";
            statement.executeUpdate(dropTable);
            System.out.println("Tabla usuarios eliminada si existía");
            
            // Crear la tabla de usuarios
            String createTable = "CREATE TABLE usuarios (" +
                               "id INT PRIMARY KEY, " +
                               "nombre VARCHAR(100) NOT NULL, " +
                               "telefono VARCHAR(20) NOT NULL, " +
                               "correo VARCHAR(100) NOT NULL, " +
                               "contraseña VARCHAR(50) NOT NULL, " +
                               "saldo DECIMAL(10,2) NOT NULL)";
            statement.executeUpdate(createTable);
            System.out.println("✅ Tabla usuarios creada exitosamente");
            
            // Insertar los datos de usuarios
            String insertData = "INSERT INTO usuarios (id, nombre, telefono, correo, contraseña, saldo) VALUES " +
                              "(1, 'Laura Martínez', '3001234567', 'laura.martinez@email.com', 'clave123', 150000.50), " +
                              "(2, 'Carlos Pérez', '3119876543', 'carlos.perez@email.com', 'carlospass', 25000.00), " +
                              "(3, 'Ana Gómez', '3012345678', 'ana.gomez@email.com', 'anita321', 550000.75), " +
                              "(4, 'Juan Rodríguez', '3123456789', 'juan.rodriguez@email.com', 'juan1234', 10000.00), " +
                              "(5, 'Sofía Torres', '3209876543', 'sofia.torres@email.com', 'sofiapass', 305000.00)";
            statement.executeUpdate(insertData);
            System.out.println("✅ Datos de usuarios insertados exitosamente");
            
            // Verificar y mostrar los datos de la tabla
            System.out.println("\n=== Verificación de datos en la tabla usuarios ===");
            String consulta = "SELECT * FROM usuarios";
            ResultSet resultado = statement.executeQuery(consulta);
            
            // Mostrar los datos en formato de tabla
            System.out.println("+----+----------------+-------------+--------------------------------+------------+------------+");
            System.out.println("| ID |     Nombre     |  Teléfono   |             Correo             | Contraseña |   Saldo    |");
            System.out.println("+----+----------------+-------------+--------------------------------+------------+------------+");
            
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String telefono = resultado.getString("telefono");
                String correo = resultado.getString("correo");
                String pass = resultado.getString("contraseña");
                double saldo = resultado.getDouble("saldo");
                
                System.out.printf("| %-2d | %-14s | %-11s | %-30s | %-10s | %10.2f |\n", 
                                id, nombre, telefono, correo, pass, saldo);
            }
            System.out.println("+----+----------------+-------------+--------------------------------+------------+------------+");
            
            // Cerrar el ResultSet
            resultado.close();
            
            // Cerrar el statement
            statement.close();
            
            // Cierre de la conexión para liberar recursos
            conexion.close();
            System.out.println("\nConexión cerrada correctamente");
        } catch (ClassNotFoundException e) {
            // Manejo de error cuando no se encuentra el driver de MySQL
            System.out.println("❌ Driver no encontrado: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            // Manejo de error cuando hay problemas al conectar con la base de datos
            System.out.println("❌ Error al conectar: " + e.getMessage());
            System.out.println("Código de error SQL: " + e.getErrorCode());
            System.out.println("Estado SQL: " + e.getSQLState());
            e.printStackTrace();
        }
    }
}
