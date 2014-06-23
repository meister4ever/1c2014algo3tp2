package modelo.juego;

import jdk.nashorn.internal.objects.NativeArray;
import modelo.personajes.*;
import modelo.geografico.*;
import modelo.elementosDelJuego.*;
import modelo.descripciones.*;

import java.util.ArrayList;

import static jdk.nashorn.internal.objects.NativeArray.forEach;

public class Caso {

	private Ciudad ciudadDelRobo;
	private Ciudad ciudadActual;
	private ObjetoRobado objetoRobado;
	private Sospechoso ladron;
    private Tiempo tiempoFinal;
    private Tiempo tiempoInicial;
    private Tiempo tiempoActual;
    private Lugar ubicacionLadron;
    private RecorridoLadron recorridoLadron;
	private Policia policia;

    public Caso(ArrayList<Ciudad> ciudades, Grafo grafo, Valor valor, Policia policia, ArrayList<Sospechoso> sospechosos) {
        
    	// Agregar XML para objetosRobados
    	this.objetoRobado = new ObjetoRobado(valor, ciudades.get(0),"Descripcion objeto");
        this.policia = policia;
        this.ladron=sospechosos.get((int)(Math.random()*sospechosos.size()+0));//Elige un ladron al azar entre los sospechosos.
        this.recorridoLadron = new RecorridoLadron(ciudades,this.obtenerCantidadCiudades(valor),grafo);
    }
    
	public Caso(Policia poli, ObjetoRobado bolaDeOro) {
		
		Tiempo.iniciar();
		
		this.policia = poli;
		
		poli.asignarNuevoCasoEn(bolaDeOro.obtenerCiudadOrigen());
		
		
	}
	
    public int obtenerCantidadCiudades(Valor valor ) {
        if (valor == Valor.COMUN) return 4;
        if (valor == Valor.VALIOSO) return 5;
        if (valor == Valor.MUY_VALIOSO) return 7;
		return 0;
    }
    
    public Ciudad obtenerCiudadRobo(){
		return this.ciudadDelRobo;
	}
	
	public ObjetoRobado obtenerObjetoRobado(){
		return this.objetoRobado;
	}
	
	public Sexo obtenerSexoLadron(){
		return this.ladron.obtenerDescripcion().getSexo();
	}

    private void plantarPistas() {
        for (Ciudad ciudad : recorridoLadron.obtenerCiudades()) {
            //Para que esto quede mejor tendriamos que crear un iterador para recorridoLadro
            Ciudad ciudadSiguiente = recorridoLadron.obtenerCiudadSiguiente(ciudad);
            CreadorDePistas creadorDePistas = new CreadorDePistas(ciudadSiguiente, ladron);
            ArrayList<Lugar> lugares = ciudad.obtenerLugaresDisponibles();
            for (Lugar lugar : lugares)
                lugar.plantarPista(creadorDePistas.crearNuevaPista(lugar.obtenerTipo()));
        }
    }

	public int obtenerTiempoTranscurridoEnHs() {
		return (Tiempo.getTiempo());
	}

}
