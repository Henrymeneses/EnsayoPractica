public class CamionetaSUV extends Vehiculo {
    private String traccion;          // 4x2 o 4x4
    private float capacidadMaletero;  // en litros

    public CamionetaSUV(String placa, String marca, int modelo, float precioDiario, String estado, 
                        String traccion, float capacidadMaletero) {
        super(placa, marca, modelo, precioDiario, estado);
        this.traccion = traccion;
        this.capacidadMaletero = capacidadMaletero;
    }

    public CamionetaSUV() {
        super();
    }

    public String getTraccion() {
        return traccion;
    }

    public void setTraccion(String traccion) {
        this.traccion = traccion;
    }

    public float getCapacidadMaletero() {
        return capacidadMaletero;
    }

    public void setCapacidadMaletero(float capacidadMaletero) {
        this.capacidadMaletero = capacidadMaletero;
    }
}   