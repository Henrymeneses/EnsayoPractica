import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Utilidades {
    Validaciones v = new Validaciones();

    // ==========================================
    // GESTIÓN DE CLIENTES
    // ==========================================

    public LinkedList<Cliente> registrarCliente(LinkedList<Cliente> listaClientes, Scanner sc) {
        String cedula = v.validaCedulaOCodigo(sc, "Ingrese la cédula del cliente (sin caracteres especiales):");
        if (buscarClientePorCedula(listaClientes, cedula) != null) {
            System.out.println("Error: Ya existe un cliente registrado con esa cédula.");
            return listaClientes;
        }
        String nombre = v.validaSoloLetras(sc, "Ingrese el nombre del cliente:");
        String apellido = v.validaSoloLetras(sc, "Ingrese el apellido del cliente:");
        String telefono = v.validaSoloNumerosTelefono(sc, "Ingrese el teléfono del cliente (solo números):");
        String direccion = v.validaTextoNoVacio(sc, "Ingrese la dirección del cliente:");
        String licencia = v.validaCedulaOCodigo(sc, "Ingrese el número de licencia de conducción:");

        Cliente nuevo = new Cliente(cedula, nombre, apellido, telefono, direccion, licencia);
        listaClientes.add(nuevo);
        System.out.println("Cliente registrado exitosamente.");
        return listaClientes;
    }

    public Cliente buscarClientePorCedula(LinkedList<Cliente> listaClientes, String cedula) {
        for (Cliente c : listaClientes) {
            if (c.getCedula().equalsIgnoreCase(cedula)) {
                return c;
            }
        }
        return null;
    }

    public LinkedList<Cliente> modificarCliente(LinkedList<Cliente> listaClientes, Scanner sc) {
        String cedula = v.validaCedulaOCodigo(sc, "Ingrese la cédula del cliente a modificar:");
        Cliente c = buscarClientePorCedula(listaClientes, cedula);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return listaClientes;
        }
        c.setNombre(v.validaSoloLetras(sc, "Ingrese el nuevo nombre:"));
        c.setApellido(v.validaSoloLetras(sc, "Ingrese el nuevo apellido:"));
        c.setTelefono(v.validaSoloNumerosTelefono(sc, "Ingrese el nuevo teléfono:"));
        c.setDireccion(v.validaTextoNoVacio(sc, "Ingrese la nueva dirección:"));
        c.setLicenciaConduccion(v.validaCedulaOCodigo(sc, "Ingrese la nueva licencia de conducción:"));
        System.out.println("Cliente modificado correctamente.");
        return listaClientes;
    }

    // Sincronización HU6: al eliminar cliente, se elimina su contrato activo y se libera el vehículo
    public LinkedList<Cliente> eliminarCliente(LinkedList<Cliente> listaClientes,
                                               Queue<ContratoRenting> contratosActivos,
                                               LinkedList<Vehiculo> listaVehiculos, Scanner sc) {
        String cedula = v.validaCedulaOCodigo(sc, "Ingrese la cédula del cliente a eliminar:");
        Cliente c = buscarClientePorCedula(listaClientes, cedula);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return listaClientes;
        }

        // Sincronizar contratos activos
        Iterator<ContratoRenting> it = contratosActivos.iterator();
        while (it.hasNext()) {
            ContratoRenting cr = it.next();
            if (cr.getCedulaCliente().equalsIgnoreCase(cedula)) {
                Vehiculo veh = buscarVehiculoPorPlaca(listaVehiculos, cr.getPlacaVehiculo());
                if (veh != null) {
                    veh.setEstado("disponible");
                }
                it.remove();
                System.out.println("Contrato asociado " + cr.getIdContrato() + " eliminado y vehículo liberado.");
            }
        }

        listaClientes.remove(c);
        System.out.println("Cliente eliminado del sistema.");
        return listaClientes;
    }

    // ==========================================
    // GESTIÓN DE VEHÍCULOS (Polimorfismo HU3)
    // ==========================================

    public LinkedList<Vehiculo> registrarVehiculo(LinkedList<Vehiculo> listaVehiculos, Scanner sc) {
        System.out.println("Seleccione el tipo de vehículo: 1. Carro Sedán | 2. Camioneta SUV");
        int tipo = v.validaEntero(sc);
        if (tipo != 1 && tipo != 2) {
            System.out.println("Opción de tipo de vehículo inválida.");
            return listaVehiculos;
        }

        String placa = v.validaCedulaOCodigo(sc, "Ingrese la placa del vehículo (sin caracteres especiales):");
        if (buscarVehiculoPorPlaca(listaVehiculos, placa) != null) {
            System.out.println("Error: Ya existe un vehículo con esa placa.");
            return listaVehiculos;
        }

        String marca = v.validaSoloLetras(sc, "Ingrese la marca del vehículo (solo letras):");
        System.out.println("Ingrese el año/modelo del vehículo:");
        int modelo = validaEnteroPositivo(sc);
        System.out.println("Ingrese el precio diario de alquiler:");
        float precioDiario = validaDecimalPositivo(sc);

        if (tipo == 1) {
            String combustible = validaCombustible(sc);
            String transmision = validaTransmision(sc);
            listaVehiculos.add(new CarroSedan(placa, marca, modelo, precioDiario, "disponible", combustible, transmision));
        } else {
            String traccion = validaTraccion(sc);
            System.out.println("Ingrese la capacidad del maletero (en litros):");
            float maletero = validaDecimalPositivo(sc);
            listaVehiculos.add(new CamionetaSUV(placa, marca, modelo, precioDiario, "disponible", traccion, maletero));
        }

        System.out.println("Vehículo registrado correctamente.");
        return listaVehiculos;
    }

    public Vehiculo buscarVehiculoPorPlaca(LinkedList<Vehiculo> listaVehiculos, String placa) {
        for (Vehiculo veh : listaVehiculos) {
            if (veh.getPlaca().equalsIgnoreCase(placa)) {
                return veh;
            }
        }
        return null;
    }

    public LinkedList<Vehiculo> modificarVehiculo(LinkedList<Vehiculo> listaVehiculos, Scanner sc) {
        String placa = v.validaCedulaOCodigo(sc, "Ingrese la placa del vehículo a modificar:");
        Vehiculo veh = buscarVehiculoPorPlaca(listaVehiculos, placa);
        if (veh == null) {
            System.out.println("Vehículo no encontrado.");
            return listaVehiculos;
        }
        veh.setMarca(v.validaSoloLetras(sc, "Ingrese la nueva marca:"));
        System.out.println("Ingrese el nuevo modelo (año):");
        veh.setModelo(validaEnteroPositivo(sc));
        System.out.println("Ingrese el nuevo precio diario:");
        veh.setPrecioDiario(validaDecimalPositivo(sc));

        if (veh instanceof CarroSedan) {
            CarroSedan sedan = (CarroSedan) veh;
            sedan.setTipoCombustible(validaCombustible(sc));
            sedan.setTransmision(validaTransmision(sc));
        } else if (veh instanceof CamionetaSUV) {
            CamionetaSUV suv = (CamionetaSUV) veh;
            suv.setTraccion(validaTraccion(sc));
            System.out.println("Ingrese la nueva capacidad del maletero (litros):");
            suv.setCapacidadMaletero(validaDecimalPositivo(sc));
        }
        System.out.println("Vehículo modificado exitosamente.");
        return listaVehiculos;
    }

    public LinkedList<Vehiculo> eliminarVehiculo(LinkedList<Vehiculo> listaVehiculos, Scanner sc) {
        String placa = v.validaCedulaOCodigo(sc, "Ingrese la placa del vehículo a eliminar:");
        Vehiculo veh = buscarVehiculoPorPlaca(listaVehiculos, placa);
        if (veh == null) {
            System.out.println("Vehículo no encontrado.");
            return listaVehiculos;
        }
        if (veh.getEstado().equalsIgnoreCase("alquilado")) {
            System.out.println("No se puede eliminar un vehículo que actualmente está alquilado.");
            return listaVehiculos;
        }
        listaVehiculos.remove(veh);
        System.out.println("Vehículo eliminado correctamente.");
        return listaVehiculos;
    }

    // ==========================================
    // GESTIÓN DE CONTRATOS DE RENTING (Queue y Stack)
    // ==========================================

    public Queue<ContratoRenting> registrarContrato(Queue<ContratoRenting> contratosActivos,
                                                    Stack<ContratoRenting> contratosFinalizados,
                                                    LinkedList<Cliente> listaClientes,
                                                    LinkedList<Vehiculo> listaVehiculos, Scanner sc) {
        String cedula = v.validaCedulaOCodigo(sc, "Ingrese la cédula del cliente:");
        Cliente cliente = buscarClientePorCedula(listaClientes, cedula);
        if (cliente == null) {
            System.out.println("Error: El cliente no está registrado.");
            return contratosActivos;
        }

        // Regla HU4: No permitir más de un vehículo alquilado por cliente al mismo tiempo
        if (tieneContratoActivo(contratosActivos, cedula)) {
            System.out.println("Error: Este cliente ya tiene un contrato de renting activo.");
            return contratosActivos;
        }

        String placa = v.validaCedulaOCodigo(sc, "Ingrese la placa del vehículo a alquilar:");
        Vehiculo vehiculo = buscarVehiculoPorPlaca(listaVehiculos, placa);
        if (vehiculo == null) {
            System.out.println("Error: El vehículo no existe.");
            return contratosActivos;
        }

        // Regla HU4: No permitir alquilar vehículo en estado "alquilado"
        if (vehiculo.getEstado().equalsIgnoreCase("alquilado")) {
            System.out.println("Error: El vehículo se encuentra actualmente alquilado.");
            return contratosActivos;
        }

        String idContrato = "CTR-" + (contratosActivos.size() + contratosFinalizados.size() + 1);
        String fechaInicio = v.validaTextoNoVacio(sc, "Ingrese la fecha de inicio (DD/MM/AAAA):");
        String fechaFin = v.validaTextoNoVacio(sc, "Ingrese la fecha de fin (DD/MM/AAAA):");
        System.out.println("Ingrese el total de días de alquiler:");
        int totalDias = validaEnteroPositivo(sc);
        float valorTotal = totalDias * vehiculo.getPrecioDiario();

        ContratoRenting contrato = new ContratoRenting(idContrato, cedula, placa, fechaInicio, fechaFin, totalDias, valorTotal);
        contratosActivos.offer(contrato); // Se encola el contrato activo
        vehiculo.setEstado("alquilado");

        System.out.println("Contrato " + idContrato + " registrado con éxito. Valor total: $" + valorTotal);
        return contratosActivos;
    }

    public boolean tieneContratoActivo(Queue<ContratoRenting> contratosActivos, String cedula) {
        for (ContratoRenting c : contratosActivos) {
            if (c.getCedulaCliente().equalsIgnoreCase(cedula)) {
                return true;
            }
        }
        return false;
    }

    public ContratoRenting buscarContrato(Queue<ContratoRenting> activos, Stack<ContratoRenting> finalizados, String id) {
        for (ContratoRenting c : activos) {
            if (c.getIdContrato().equalsIgnoreCase(id)) {
                return c;
            }
        }
        for (ContratoRenting c : finalizados) {
            if (c.getIdContrato().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    // Regla HU4: Solo se permite modificar fecha o duración; ID, cédula y placa son inmutables
    public Queue<ContratoRenting> modificarContrato(Queue<ContratoRenting> contratosActivos,
                                                    LinkedList<Vehiculo> listaVehiculos, Scanner sc) {
        String id = v.validaTextoNoVacio(sc, "Ingrese el ID del contrato activo a modificar (ej. CTR-1):");
        ContratoRenting encontrado = null;
        for (ContratoRenting c : contratosActivos) {
            if (c.getIdContrato().equalsIgnoreCase(id)) {
                encontrado = c;
                break;
            }
        }

        if (encontrado == null) {
            System.out.println("Contrato activo no encontrado (recuerde que los finalizados no se modifican).");
            return contratosActivos;
        }

        encontrado.setFechaInicio(v.validaTextoNoVacio(sc, "Ingrese la nueva fecha de inicio (DD/MM/AAAA):"));
        encontrado.setFechaFin(v.validaTextoNoVacio(sc, "Ingrese la nueva fecha de fin (DD/MM/AAAA):"));
        System.out.println("Ingrese la nueva duración en días:");
        int nuevosDias = validaEnteroPositivo(sc);
        encontrado.setTotalDias(nuevosDias);

        Vehiculo veh = buscarVehiculoPorPlaca(listaVehiculos, encontrado.getPlacaVehiculo());
        if (veh != null) {
            encontrado.setValorTotal(nuevosDias * veh.getPrecioDiario());
        }

        System.out.println("Contrato modificado exitosamente. Nuevo valor total: $" + encontrado.getValorTotal());
        return contratosActivos;
    }

    // Devolución: saca el contrato de la Cola de activos y lo apila en el historial (Stack)
    public Stack<ContratoRenting> finalizarContrato(Queue<ContratoRenting> contratosActivos,
                                                    Stack<ContratoRenting> contratosFinalizados,
                                                    LinkedList<Vehiculo> listaVehiculos, Scanner sc) {
        String id = v.validaTextoNoVacio(sc, "Ingrese el ID del contrato a finalizar/devolver:");
        ContratoRenting aFinalizar = null;

        Iterator<ContratoRenting> it = contratosActivos.iterator();
        while (it.hasNext()) {
            ContratoRenting c = it.next();
            if (c.getIdContrato().equalsIgnoreCase(id)) {
                aFinalizar = c;
                it.remove();
                break;
            }
        }

        if (aFinalizar == null) {
            System.out.println("No se encontró un contrato activo con ese ID.");
            return contratosFinalizados;
        }

        Vehiculo veh = buscarVehiculoPorPlaca(listaVehiculos, aFinalizar.getPlacaVehiculo());
        if (veh != null) {
            veh.setEstado("disponible");
        }

        contratosFinalizados.push(aFinalizar); // Se apila en el historial
        System.out.println("Devolución procesada. Contrato " + id + " finalizado y vehículo disponible nuevamente.");
        return contratosFinalizados;
    }

    // ==========================================
    // MÉTODOS AUXILIARES RECURSIVOS DE APOYO
    // ==========================================

    public int validaEnteroPositivo(Scanner sc) {
        int num = v.validaEntero(sc);
        if (num <= 0) {
            System.out.println("El valor debe ser un número positivo mayor a 0. Intente de nuevo:");
            return validaEnteroPositivo(sc);
        }
        return num;
    }

    public float validaDecimalPositivo(Scanner sc) {
        float num = v.validaDecimal(sc);
        if (num <= 0) {
            System.out.println("El valor debe ser positivo mayor a 0. Intente de nuevo:");
            return validaDecimalPositivo(sc);
        }
        return num;
    }

    public String validaCombustible(Scanner sc) {
        String comb = v.validaSoloLetras(sc, "Ingrese tipo de combustible (gasolina, diesel, electrico):").toLowerCase();
        if (!comb.equals("gasolina") && !comb.equals("diesel") && !comb.equals("electrico")) {
            System.out.println("Opción no válida. Debe ser gasolina, diesel o electrico.");
            return validaCombustible(sc);
        }
        return comb;
    }

    public String validaTransmision(Scanner sc) {
        String trans = v.validaSoloLetras(sc, "Ingrese transmisión (automatica, manual):").toLowerCase();
        if (!trans.equals("automatica") && !trans.equals("manual")) {
            System.out.println("Opción no válida. Debe ser automatica o manual.");
            return validaTransmision(sc);
        }
        return trans;
    }

    public String validaTraccion(Scanner sc) {
        String trac = v.validaCedulaOCodigo(sc, "Ingrese tracción (4x2, 4x4):").toLowerCase();
        if (!trac.equals("4x2") && !trac.equals("4x4")) {
            System.out.println("Opción no válida. Debe ser 4x2 o 4x4.");
            return validaTraccion(sc);
        }
        return trac;
    }
}