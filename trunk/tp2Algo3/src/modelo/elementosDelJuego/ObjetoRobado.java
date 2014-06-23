package modelo.elementosDelJuego;

import modelo.geografico.Ciudad;

public class ObjetoRobado {

    private Valor valor;
	private Ciudad ciudadOrigen;
	private String descripcionObjeto;

    public ObjetoRobado(Valor valordelobjeto, Ciudad ciudadOrigen, String descripcion) {
        this.valor = valordelobjeto;
        this.ciudadOrigen = ciudadOrigen;
        this.descripcionObjeto = descripcion;
	}

	public String contenidoComoString(){

		return this.descripcionObjeto;
	
	}

    public Valor obtenerValor() {
        return this.valor;
    }
    
    public int obtenerCantidadciudades() {
        if (this.valor == Valor.COMUN) return 4;
        if (this.valor == Valor.VALIOSO) return 5;
        if (this.valor == Valor.MUY_VALIOSO) return 7;
		return 0;
    }

	public Ciudad obtenerCiudadOrigen() {
		return this.ciudadOrigen;
	}
}