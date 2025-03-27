package com.sportsevent.gestoreventosdeportivos.Service;

import com.google.gson.Gson;
import com.sportsevent.gestoreventosdeportivos.DAO.EquipoDAO;
import com.sportsevent.gestoreventosdeportivos.DAO.JugadorDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JugadorService {

    private static Gson gson = new Gson();

    // POST /jugadores: Permite registrar un nuevo jugador en el sistema
    public static void registrarJugador(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Leer el JSON del cuerpo de la petición
        String json = request.getReader().lines().collect(Collectors.joining());
        JugadorDAO nuevoJugador = gson.fromJson(json, JugadorDAO.class);

        // Verificar si ya existe un jugador con el mismo número en el mismo equipo
        if (existeJugadorConMismoNumero(nuevoJugador.getNumero(), nuevoJugador.getEquipoId())) {
            response.setStatus(HttpServletResponse.SC_CONFLICT); // 409 Conflict
            response.setContentType("application/json");
            String mensajeError = "{\"mensaje\": \"Ya existe un jugador con el mismo número en el mismo equipo.\"}";
            response.getWriter().write(mensajeError);
            return;
        }

        // Insertar el nuevo jugador
        JugadorDAO.insertar(nuevoJugador);

        // Actualizar la lista de jugadores del equipo correspondiente
        EquipoDAO equipo = EquipoDAO.buscarPorId(nuevoJugador.getEquipoId());
        if (equipo != null) {
            equipo.getJugadores().add(nuevoJugador.getId());
            EquipoDAO.actualizar(equipo);
        }

        response.setStatus(HttpServletResponse.SC_CREATED); // 201 Created
        response.setContentType("application/json");
        String mensajeExito = "{\"mensaje\": \"Jugador registrado con éxito.\"}";
        response.getWriter().write(mensajeExito);
    }

    // GET /jugadores: Retorna la lista de todos los jugadores registrados
    public static void obtenerJugadores(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Obtener la lista de todos los jugadores
        List<JugadorDAO> jugadores = JugadorDAO.obtenerTodos();

        // Añadir el nombre del equipo a cada jugador
        for (JugadorDAO jugador : jugadores) {
            EquipoDAO equipo = EquipoDAO.buscarPorId(jugador.getEquipoId());
            if (equipo != null) {
                jugador.setEquipoNombre(equipo.getNombre());
            }
        }

        // Convertir la lista de jugadores a JSON
        String jsonJugadores = gson.toJson(jugadores);

        // Escribir la respuesta
        response.setContentType("application/json");
        response.getWriter().write(jsonJugadores);
    }

    // Método auxiliar para verificar si ya existe un jugador con el mismo número en el mismo equipo
    private static boolean existeJugadorConMismoNumero(int numero, int equipoId) {
        for (JugadorDAO jugador : JugadorDAO.obtenerTodos()) {
            if (jugador.getNumero() == numero && jugador.getEquipoId() == equipoId) {
                return true;
            }
        }
        return false;
    }
}