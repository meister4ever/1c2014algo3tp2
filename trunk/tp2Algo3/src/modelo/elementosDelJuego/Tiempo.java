package modelo.elementosDelJuego;

//Estaria bueno hacer que Tiempo sea unico (singleton), asi cuando viajamos podemos 'manejarlo' desde ahi,
// cuando emitimos una orden de arresto, etc.
public class Tiempo {



	private static int horas;
	private static int horaSue�o;

	private Tiempo(){}
	
	public static void aumentarHoras(int cantidadHoras){
		
		horas += cantidadHoras;
		
		//LA CONDICION EXTRA ASEGURA QUE PUEDA DORMIR EN VIAJES NOCTURNOS LARGOS
		if( horas >= horaSue�o && (horas-horaSue�o) < 8 )
			{
				//IMPRIMIR "DURMIENDO......"
				horas += 8;
				horaSue�o += 24;
			}
		else if( horas > horaSue�o )
			horaSue�o += 24;
		
	}
	public static int getTiempo(){
		
		return horas;
	}
	public String tiempoComoSring(){
		//deberiamos imprimir dia y hora segun el valor del atributo tiempo
		String fecha = "";
		//TODO
		return fecha;
	}
	public static void iniciar() {

		horas = 0;
		horaSue�o = 15;
		
	}
}