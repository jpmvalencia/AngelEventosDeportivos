package com.sportsevent.gestoreventosdeportivos.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventoDAO {
    private static Map<Integer, EventoDAO> eventosDB = new HashMap<>();

    private int id;
    private String nombre;
    private String fecha;
    private String lugar;
    private String deporte;
    private ArrayList<Integer> equiposParticipantes; // Lista de IDs de equipos
    private ArrayList<String> equiposParticipantesNombres; // Lista de nombres de equipos para respuesta
    private int capacidad;
    private int entradasVendidas;
    private String estado; // "Programado", "En curso", "Finalizado", "Cancelado"

    public EventoDAO(int id, String nombre, String fecha, String lugar, String deporte, ArrayList<Integer> equiposParticipantes, int capacidad, int entradasVendidas, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.lugar = lugar;
        this.deporte = deporte;
        this.equiposParticipantes = equiposParticipantes;
        this.capacidad = capacidad;
        this.entradasVendidas = entradasVendidas;
        this.estado = estado;
    }

    // Métodos CRUD
    public static void insertar(EventoDAO evento) {
        eventosDB.put(evento.getId(), evento);
    }

    public static EventoDAO buscarPorId(int id) {
        return eventosDB.get(id);
    }

    public static void actualizar(EventoDAO evento) {
        eventosDB.put(evento.getId(), evento);
    }

    public static void eliminar(int id) {
        eventosDB.remove(id);
    }

    public static ArrayList<EventoDAO> obtenerTodos() {
        return new ArrayList<>(eventosDB.values());
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public ArrayList<Integer> getEquiposParticipantes() {
        return equiposParticipantes;
    }

    public void setEquiposParticipantes(ArrayList<Integer> equiposParticipantes) {
        this.equiposParticipantes = equiposParticipantes;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getEntradasVendidas() {
        return entradasVendidas;
    }

    public void setEntradasVendidas(int entradasVendidas) {
        this.entradasVendidas = entradasVendidas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ArrayList<String> getEquiposParticipantesNombres() {
        return equiposParticipantesNombres;
    }

    public void setEquiposParticipantesNombres(ArrayList<String> equiposParticipantesNombres) {
        this.equiposParticipantesNombres = equiposParticipantesNombres;
    }
}
