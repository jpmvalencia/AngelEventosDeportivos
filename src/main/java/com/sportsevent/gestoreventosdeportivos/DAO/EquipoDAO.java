package com.sportsevent.gestoreventosdeportivos.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EquipoDAO {
    private static Map<Integer, EquipoDAO> equiposDB = new HashMap<>();

    private int id;
    private String nombre;
    private String deporte;
    private String ciudad;
    private String fechaFundacion;
    private String logo;
    private ArrayList<Integer> jugadores;

    public EquipoDAO(int id, String nombre, String deporte, String ciudad, String fechaFundacion, String logo, ArrayList<Integer> jugadores) {
        this.id = id;
        this.nombre = nombre;
        this.deporte = deporte;
        this.ciudad = ciudad;
        this.fechaFundacion = fechaFundacion;
        this.logo = logo;
        this.jugadores = jugadores;
    }

    // Métodos CRUD (Create, Read, Update, Delete)
    public static void insertar(EquipoDAO equipo) {
        equiposDB.put(equipo.getId(), equipo);
    }

    public static EquipoDAO buscarPorId(int id) {
        return equiposDB.get(id);
    }

    public static void actualizar(EquipoDAO equipo) {
        equiposDB.put(equipo.getId(), equipo);
    }

    public static void eliminar(int id) {
        equiposDB.remove(id);
    }

    public static ArrayList<EquipoDAO> obtenerTodos() {
        return new ArrayList<>(equiposDB.values());
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(String fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public ArrayList<Integer> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Integer> jugadores) {
        this.jugadores = jugadores;
    }
}
