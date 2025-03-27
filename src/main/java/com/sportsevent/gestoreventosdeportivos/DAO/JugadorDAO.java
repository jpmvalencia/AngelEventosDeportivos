package com.sportsevent.gestoreventosdeportivos.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JugadorDAO {
    private static Map<Integer, JugadorDAO> jugadoresDB = new HashMap<>();

    private int id;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String nacionalidad;
    private String posicion;
    private int numero;
    private int equipoId;
    private boolean estadoActivo;
    private String equipoNombre; // Nombre del equipo (esto es para el GET)

    public JugadorDAO(int id, String nombre, String apellido, String fechaNacimiento, String nacionalidad, String posicion, int numero, int equipoId, boolean estadoActivo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.posicion = posicion;
        this.numero = numero;
        this.equipoId = equipoId;
        this.estadoActivo = estadoActivo;
    }

    // Métodos CRUD (Create, Read, Update, Delete)
    public static void insertar(JugadorDAO jugador) {
        jugadoresDB.put(jugador.getId(), jugador);
    }

    public static JugadorDAO buscarPorId(int id) {
        return jugadoresDB.get(id);
    }

    public static void actualizar(JugadorDAO jugador) {
        jugadoresDB.put(jugador.getId(), jugador);
    }

    public static void eliminar(int id) {
        jugadoresDB.remove(id);
    }

    public static ArrayList<JugadorDAO> obtenerTodos() {
        return new ArrayList<>(jugadoresDB.values());
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(int equipoId) {
        this.equipoId = equipoId;
    }

    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public String getEquipoNombre() {
        return equipoNombre;
    }

    public void setEquipoNombre(String equipoNombre) {
        this.equipoNombre = equipoNombre;
    }
}