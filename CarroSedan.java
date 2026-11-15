public class CarroSedan extends Vehiculo {
    private String tipoCombustible; // gasolina, diésel o eléctrico
    private String transmision;     // automática o manual

    public CarroSedan(String placa, String marca, int modelo, float precioDiario, String estado, 
                      String tipoCombustible, String transmision) {
        super(placa, marca, modelo, precioDiario, estado);
        this.tipoCombustible = tipoCombustible;
        this.transmision = transmision;
    }

    public CarroSedan() {
        super();
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }
}