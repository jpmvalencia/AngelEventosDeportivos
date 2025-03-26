package com.sportsevent.gestoreventosdeportivos.DAO;

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

    // Métodos CRUD
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
}
