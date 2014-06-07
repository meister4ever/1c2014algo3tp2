package elementosDelJuego;
import geografico.Ciudad;
import geografico.TipoEdificio;
import personajes.Sospechoso;

public class CreadorDePistas {

    private Ciudad ciudadSiguiente;
    private Sospechoso ladron;

    public CreadorDePistas(Ciudad ciudadSiguiente, Sospechoso ladron) {
        this.ciudadSiguiente = ciudadSiguiente;
        this.ladron = ladron;
    }

    public Pista crearNuevaPista(TipoEdificio tipo){
        if(tipo==TipoEdificio.AEROPUERTO)return crearPistaAeropuerto();
        if(tipo==TipoEdificio.PUERTO)return crearPistaPuerto();
        if(tipo==TipoEdificio.BANCO)return crearPistaBanco();
        if(tipo==TipoEdificio.BOLSA)return crearPistaBolsa();
        if(tipo==TipoEdificio.BIBLIOTECA)return crearPistaBiblioteca();
    }

    private PistaBanco crearPistaBanco(){
        return new PistaBanco(ciudadSiguiente,ladron);
    }

    private PistaBolsa crearPistaBolsa(){
        return new PistaBolsa(ciudadSiguiente,ladron);
    }

    private PistaBiblioteca crearPistaBiblioteca(){
        return new PistaBiblioteca(ciudadSiguiente,ladron);
    }

    private PistaPuerto crearPistaPuerto(){
        return new PistaPuerto(ciudadSiguiente,ladron);
    }

    private PistaAeropuerto crearPistaAeropuerto(){
        return new PistaAeropuerto(ciudadSiguiente,ladron);
    }

}