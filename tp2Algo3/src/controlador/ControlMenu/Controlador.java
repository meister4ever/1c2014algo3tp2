package controlador.ControlMenu;

import Vista.Vista;
import controlador.ControlXML.CargadorXML;
import controlador.ControlXML.GuardadorXML;
import modelo.descripciones.*;
import modelo.elementosDelJuego.CuartelGeneral;
import modelo.elementosDelJuego.SeAcaboElTiempoDelCasoExcepcion;
import modelo.geografico.Ciudad;
import modelo.geografico.Lugar;
import modelo.juego.MenuBase;
import modelo.personajes.Sospechoso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class Controlador {

    private MenuBase menuBase;
    private Sexo sexoElegido;
    private Hobby hobbyElegido;
    private Pelo peloElegido;
    private Senia seniaElegida;
    private Vehiculo vehiculoElegido;

    private char comilla = (char) 34;

    public Controlador(MenuBase unMenuBase) {
        menuBase = unMenuBase;
        menuBase = unMenuBase;
        sexoElegido = null;
        hobbyElegido = null;
        peloElegido = null;
        seniaElegida = null;
        vehiculoElegido = null;
    }

    public MenuBase getMenuBase() {
        return menuBase;
    }


    private class EscuchaBotonNuevo implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.out.println("El juego se crea");
            //menuBase.setOpcionMenuBase(1);
            menuBase.nuevoJuego();
        }

        public EscuchaBotonNuevo() {
        }
    }


    public ActionListener getListenerNuevo() {
        return new EscuchaBotonNuevo();
    }

    //-------------------------------------------------------------
    private class EscuchaBotonGuardar implements ActionListener {
        private Vista vista;

        public EscuchaBotonGuardar(Vista unaVista) {
            this.vista = unaVista;
        }

        public void actionPerformed(ActionEvent e) {
            GuardadorXML guardadorXML = new GuardadorXML(menuBase.getJuego());
            guardadorXML.guardar();

            JLabel label = new JLabel();
            label.setSize(this.vista.getSize());
            String texto = "<html><body color = " + comilla + "red" + comilla + " size = 4>Partida Guardada" +
                    "</body></html>";
            label.setText(texto);
            this.vista.add("North", label);
            this.vista.setVisible(true);  //mostramos el marco
            System.out.println("El juego se guarda");
        }
    }

    public ActionListener getListenerGuardar(Vista unaVista) {
        return new EscuchaBotonGuardar(unaVista);
    }

    private class EscuchaBotonSalir implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public ActionListener getListenerSalir() {
        return new EscuchaBotonSalir();
    }


    private class EscuchaBotonViajar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //VIAJAR
            System.out.println("Viajando...");
            menuBase.menuViajar();
        }
    }

    public ActionListener getListenerViajar() {
        return new EscuchaBotonViajar();
    }

    private class EscuchaBotonViajarACiudad implements ActionListener {
        private ArrayList<Ciudad> ciudadesDisponibles;
        private int pos;
        private Vista vista;

        public EscuchaBotonViajarACiudad(int posicion, ArrayList<Ciudad> ciudades, Vista unaVista) {
            this.ciudadesDisponibles = ciudades;
            this.pos = posicion;
            this.vista = unaVista;
        }

        public void actionPerformed(ActionEvent arg0) {
            try {
                menuBase.getPolicia().viajarA(ciudadesDisponibles.get(this.pos));
                menuBase.menuPrincipal();
                vista.setVisible(true);
            } catch (SeAcaboElTiempoDelCasoExcepcion i) {
                System.out.println(i.AvisoAlJugador());
                vista.vistaGameOver();
                vista.setVisible(true);
            }
        }
    }

    public ActionListener getListenerViajarACiudad(int pos, ArrayList<Ciudad> ciudadesDisponibles, Vista vista) {
        return new EscuchaBotonViajarACiudad(pos, ciudadesDisponibles, vista);
    }

    private class EscuchaBotonPistas implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //PISTAS
            System.out.println("Dando Pistas...");
            menuBase.menuVisitar();
        }
    }

    public ActionListener getListenerPistas() {
        return new EscuchaBotonPistas();
    }

    private class EscucharBotonVisitarLugar implements ActionListener {
        private ArrayList<Lugar> lugaresDisponibles;
        private Vista vista;
        private MenuBase menu;

        public EscucharBotonVisitarLugar(MenuBase unMenuBase, Vista unaVista, ArrayList<Lugar> lugares) {
            this.lugaresDisponibles = lugares;
            this.vista = unaVista;
            this.menu = unMenuBase;
        }

        public void actionPerformed(ActionEvent arg0) {
            JPanel panel = new JPanel();
            int i = 0;

            for (Lugar unLugar : lugaresDisponibles) {
                String nombre = unLugar.obtenerTipo().toString();
                Button unBoton = new Button(nombre);
                EscucharBotonLugar botonLugar = new EscucharBotonLugar(menu, i, lugaresDisponibles, vista);
                unBoton.addActionListener(botonLugar.getListenerLugar(menu, i, lugaresDisponibles, vista));
                panel.add(unBoton);
                i++;
            }

            this.vista.add("North", panel);
            panel.setVisible(true);
            vista.setVisible(true);
        }
    }

    public ActionListener getListenerVisitarLugar(MenuBase unMenuBase, Vista unaVista, ArrayList<Lugar> lugaresDisponibles) {
        return new EscucharBotonVisitarLugar(unMenuBase, unaVista, lugaresDisponibles);
    }

    private class EscucharBotonLugar implements ActionListener {
        private ArrayList<Lugar> lugares;
        private int posicion;
        private Vista vista;
        private MenuBase menu;

        public EscucharBotonLugar(MenuBase unMenuBase, int pos, ArrayList<Lugar> lugaresDisponibles, Vista unaVista) {
            this.lugares = lugaresDisponibles;
            this.posicion = pos;
            this.vista = unaVista;
            this.menu = unMenuBase;
        }

        public void actionPerformed(ActionEvent arg0) {
            try {
                Lugar unLugar = lugares.get(posicion);
                menuBase.getPolicia().visitarLugar(unLugar);
                String pista = menuBase.getPolicia().obtenerUltimaPista();
                vista.vistaLugar(unLugar, pista);
            } catch (SeAcaboElTiempoDelCasoExcepcion i) {
                System.out.println(i.AvisoAlJugador());
                vista.vistaGameOver();
                vista.setVisible(true);
            }
        }

        public ActionListener getListenerLugar(MenuBase unMenuBase, int pos, ArrayList<Lugar> lugares, Vista unaVista) {
            return new EscucharBotonLugar(unMenuBase, pos, lugares, unaVista);
        }

    }

    private class EscuchaBotonOrdenArresto implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //ORDEN ARRESTO
            System.out.println("Generando Orden de Arresto...");
            sexoElegido = null;
            hobbyElegido = null;
            peloElegido = null;
            seniaElegida = null;
            vehiculoElegido = null;
            menuBase.menuOrdenDeArresto();
        }
    }

    public ActionListener getListenerOrdenArresto() {
        return new EscuchaBotonOrdenArresto();
    }

    private class EscuchaBotonComenzarInvestigacion implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            menuBase.menuPrincipal();
        }
    }

    public ActionListener getListenerComenzarInvestigacion() {
        return new EscuchaBotonComenzarInvestigacion();
    }

    private class EscuchaBotonEmitirOrden implements ActionListener {
        private ArrayList<Sospechoso> sospechosos;

        public void actionPerformed(ActionEvent arg0) {
            CuartelGeneral cuartelGeneral = CuartelGeneral.getInstance();
            sospechosos = cuartelGeneral.buscarSospechoso(new Descripcion(sexoElegido, hobbyElegido, peloElegido, seniaElegida, vehiculoElegido));

            JFrame sospechososVentana = new JFrame();
            sospechososVentana.setDefaultCloseOperation(sospechososVentana.DISPOSE_ON_CLOSE);
            sospechososVentana.setSize(350, 400);
            JLabel mensajeLabel = new JLabel();
            mensajeLabel.setSize(sospechososVentana.getSize());
            String texto = "<html><body><font size = 6>Coincidencias:<br>";
            int i = 1;
            for (Sospechoso s : sospechosos) {
                texto += "<br>";
                texto += i;
                texto += ". ";
                texto += s.getNombre();
                i++;
            }

            if (sospechosos.size() == 0) {
                texto += "<br><br>No se encontro ningun sospechoso que concuerde con la descripcion seleccionada";
            }
            if (sospechosos.size() == 1) {
                menuBase.getPolicia().emitirOrdenDeArrestoPara(sospechosos.get(0));
                texto += "<br><br>Emitida la orden de arresto para " + sospechosos.get(0).getNombre();
            }

            texto += "</body></html>";
            mensajeLabel.setText(texto);
            sospechososVentana.add("North", mensajeLabel);
            sospechososVentana.setVisible(true);
        }
    }

    public ActionListener getListenerEmitirOrden() {
        return new EscuchaBotonEmitirOrden();
    }


    private class EscuchaSeleccionSexo implements ItemListener {

        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (e.getItem() == "") {
                    sexoElegido = null;
                } else {
                    sexoElegido = (Sexo) e.getItem();
                }
            }
        }
    }

    public ItemListener getListenerSeleccionSexo() {
        return new EscuchaSeleccionSexo();
    }

    private class EscuchaSeleccionHobby implements ItemListener {

        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (e.getItem() == "") {
                    hobbyElegido = null;
                } else {
                    hobbyElegido = (Hobby) e.getItem();
                }
            }
        }
    }

    public ItemListener getListenerSeleccionHobby() {
        return new EscuchaSeleccionHobby();
    }

    private class EscuchaSeleccionPelo implements ItemListener {

        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (e.getItem() == "") {
                    peloElegido = null;
                } else {
                    peloElegido = (Pelo) e.getItem();
                }
            }
        }
    }

    public ItemListener getListenerSeleccionPelo() {
        return new EscuchaSeleccionPelo();
    }

    private class EscuchaSeleccionSenia implements ItemListener {

        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (e.getItem() == "") {
                    seniaElegida = null;
                } else {
                    seniaElegida = (Senia) e.getItem();
                }
            }
        }
    }

    public ItemListener getListenerSeleccionSenia() {
        return new EscuchaSeleccionSenia();
    }

    private class EscuchaSeleccionVehiculo implements ItemListener {

        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (e.getItem() == "") {
                    vehiculoElegido = null;
                } else {
                    vehiculoElegido = (Vehiculo) e.getItem();
                }
            }
        }
    }

    public ItemListener getListenerSeleccionVehiculo() {
        return new EscuchaSeleccionVehiculo();
    }

    private class EscucharBotonVolver implements ActionListener {
        private Vista vista;
        private Vista pista;

        public EscucharBotonVolver(Vista unaVista, Vista unaPista) {
            this.vista = unaVista;
            this.pista = unaPista;
        }

        public void actionPerformed(ActionEvent arg0) {
            try {
                if (this.pista != null) this.pista.setVisible(false);
                menuBase.menuPrincipal();
            } catch (SeAcaboElTiempoDelCasoExcepcion i) {
                System.out.println(i.AvisoAlJugador());
                vista.vistaGameOver();
                vista.setVisible(true);
            }
        }
    }

    public ActionListener getListenerVolver(Vista unaVista, Vista unaPista) {
        return new EscucharBotonVolver(unaVista, unaPista);
    }

    private class EscucharBotonCargarPartida implements ActionListener {
        private Vista vista;

        public void actionPerformed(ActionEvent arg0) {
            CargadorXML cargadorXML = new CargadorXML();
            menuBase.setJuego(cargadorXML.cargar());
            menuBase.setPolicia(menuBase.getJuego().obtenerPolicia());

            vista.vistaCiudad(menuBase, menuBase.getPolicia().obtenerCiudadActual());
        }

        public EscucharBotonCargarPartida(Vista unaVista) {
            this.vista = unaVista;
        }
    }

    public ActionListener getListenerCargarPartida(Vista unaVista) {
        return new EscucharBotonCargarPartida(unaVista);
    }
}

	
