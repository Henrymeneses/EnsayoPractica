import java.util.Scanner;

public class Validaciones {

    // Valida que un número entero sea ingresado correctamente usando recursividad
    public int validaEntero(Scanner sc) {
        if (!sc.hasNextInt()) {
            System.out.println("Por favor, ingrese un dígito numérico válido:");
            sc.next(); // Limpiar buffer
            return validaEntero(sc); // Llamada recursiva
        }
        int valor = sc.nextInt();
        sc.nextLine(); // Limpiar salto de línea
        return valor;
    }

    // Valida que un número decimal (float/double) sea ingresado correctamente de forma recursiva
    public float validaDecimal(Scanner sc) {
        if (!sc.hasNextFloat()) {
            System.out.println("Por favor, ingrese un valor decimal numérico válido:");
            sc.next();
            return validaDecimal(sc); // Llamada recursiva
        }
        float valor = sc.nextFloat();
        sc.nextLine();
        return valor;
    }

    // Valida texto general sin que esté vacío, de forma recursiva
    public String validaTextoNoVacio(Scanner sc, String mensaje) {
        System.out.println(mensaje);
        String texto = sc.nextLine().trim();
        if (texto.isEmpty()) {
            System.out.println("El campo no puede estar vacío. Intente de nuevo.");
            return validaTextoNoVacio(sc, mensaje); // Llamada recursiva
        }
        return texto;
    }

    // Valida cédula o textos alfanuméricos sin caracteres especiales usando recursividad
    public String validaCedulaOCodigo(Scanner sc, String mensaje) {
        System.out.println(mensaje);
        String texto = sc.nextLine().trim();
        // Verifica que solo tenga letras y números (sin símbolos ni espacios raros)
        if (texto.isEmpty() || !texto.matches("^[a-zA-Z0-9]+$")) {
            System.out.println("Error: No se permiten espacios ni caracteres especiales. Intente de nuevo.");
            return validaCedulaOCodigo(sc, mensaje); // Llamada recursiva
        }
        SoloTexto(texto); // Referencia o validación auxiliar si se requiere
        return texto;
    }
    
    public String SoloTexto(String texto) {
        return texto;
    }

    // Valida nombres y apellidos (solo letras, sin números ni caracteres especiales)
    public String validaSoloLetras(Scanner sc, String mensaje) {
        System.out.println(mensaje);
        String texto = sc.nextLine().trim();
        if (texto.isEmpty() || !texto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            System.out.println("Error: Solo se permiten letras (sin números ni símbolos). Intente de nuevo.");
            return validaSoloLetras(sc, mensaje); // Llamada recursiva
        }
        return texto;
    }

    // Valida que el teléfono contenga estrictamente solo números
    public String validaSoloNumerosTelefono(Scanner sc, String mensaje) {
        System.out.println(mensaje);
        String texto = sc.nextLine().trim();
        if (texto.isEmpty() || !texto.matches("^[0-9]+$")) {
            System.out.println("Error: El teléfono solo debe contener números. Intente de nuevo.");
            return validaSoloNumerosTelefono(sc, mensaje); // Llamada recursiva
        }
        return texto;
    }
}