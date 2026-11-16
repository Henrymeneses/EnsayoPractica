import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class principal {
    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
        Utilidades util = new Utilidades();

        // Estructuras de datos dinámicas principales (HU6)
        LinkedList<Cliente> listaClientes = new LinkedList<>();
        LinkedList<Vehiculo> listaVehiculos = new LinkedList<>();
        Queue<ContratoRenting> contratosActivos = new LinkedList<>(); // Cola para alquileres activos
        Stack<ContratoRenting> contratosFinalizados = new Stack<>();    // Pila para historial

        boolean sistemaActivo = true;

        while (sistemaActivo) {
            try {
                int opcion = util.mostrarMenuPrincipal(sc);
                switch (opcion) {
                    case 1:
                        util.gestionarClientesMenu(listaClientes, contratosActivos, listaVehiculos, sc);
                        break;
                    case 2:
                        util.gestionarVehiculosMenu(listaVehiculos, sc);
                        break;
                    case 3:
                        util.gestionarContratosMenu(contratosActivos, contratosFinalizados, listaClientes, listaVehiculos, sc);
                        break;
                    case 4:
                        util.imprimirInformeGeneral(listaClientes, listaVehiculos, contratosActivos, contratosFinalizados);
                        break;
                    case 5:
                        sistemaActivo = false;
                        System.out.println("Saliendo del sistema de renting. ¡Hasta pronto!");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, seleccione un número del 1 al 5.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado en la ejecución: " + e.getMessage());
                sc.nextLine(); // Limpiar buffer de entrada en caso de excepción
            }
        }
        sc.close();
    }
}