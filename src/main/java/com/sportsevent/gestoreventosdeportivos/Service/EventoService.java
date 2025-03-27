package com.sportsevent.gestoreventosdeportivos.Service;

import com.google.gson.Gson;
import com.sportsevent.gestoreventosdeportivos.DAO.EquipoDAO;
import com.sportsevent.gestoreventosdeportivos.DAO.EventoDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class EventoService {

    private static Gson gson = new Gson();

    public static void actualizarEstado(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int eventoId = Integer.parseInt(request.getParameter("eventoId"));
        String nuevoEstado = request.getParameter("estado");

        EventoDAO evento = EventoDAO.buscarPorId(eventoId);
        if (evento == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.getWriter().write("{\"mensaje\": \"Evento no encontrado.\"}");
            return;
        }

        evento.setEstado(nuevoEstado);
        EventoDAO.actualizar(evento);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write("{\"mensaje\": \"Estado del evento actualizado con éxito.\"}");
    }
    public static void venderEntradas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int eventoId = Integer.parseInt(request.getParameter("eventoId"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));

        EventoDAO evento = EventoDAO.buscarPorId(eventoId);
        if (evento == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.getWriter().write("{\"mensaje\": \"Evento no encontrado.\"}");
            return;
        }

        if (evento.getEntradasVendidas() + cantidad > evento.getCapacidad()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"mensaje\": \"No hay suficiente capacidad en el evento.\"}");
            return;
        }

        evento.setEntradasVendidas(evento.getEntradasVendidas() + cantidad);
        EventoDAO.actualizar(evento);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write("{\"mensaje\": \"Entradas vendidas con éxito.\"}");
    }
    public static void obtenerEventos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<EventoDAO> eventos = EventoDAO.obtenerTodos();

        // Obtener nombres de equipos para cada evento
        for (EventoDAO evento : eventos) {
            ArrayList<String> nombresEquipos = new ArrayList<>();
            for (Integer equipoId : evento.getEquiposParticipantes()) {
                EquipoDAO equipo = EquipoDAO.buscarPorId(equipoId);
                if (equipo != null) {
                    nombresEquipos.add(equipo.getNombre());
                }
            }
            evento.setEquiposParticipantesNombres(nombresEquipos);
        }

        // Convertir la lista a JSON y devolverla
        String jsonEventos = gson.toJson(eventos);
        response.setContentType("application/json");
        response.getWriter().write(jsonEventos);
    }
    public static void registrarEvento(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Leer los parámetros de la solicitud
        String json = request.getReader().lines().collect(Collectors.joining());
        EventoDAO nuevoEvento = gson.fromJson(json, EventoDAO.class);

        // Verificar que al menos haya dos equipos
        ArrayList<Integer> equiposParticipantes = nuevoEvento.getEquiposParticipantes();
        if (equiposParticipantes.size() < 2) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"mensaje\": \"El evento debe tener al menos dos equipos.\"}");
            return;
        }

        // Verificar que todos los equipos pertenezcan al mismo deporte
        String deporteEquipo = null;
        for (Integer equipoId : equiposParticipantes) {
            EquipoDAO equipo = EquipoDAO.buscarPorId(equipoId);
            if (equipo == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json");
                response.getWriter().write("{\"mensaje\": \"Uno de los equipos no existe.\"}");
                return;
            }

            // Si es el primer equipo, guardamos su deporte
            if (deporteEquipo == null) {
                deporteEquipo = equipo.getDeporte();
            }

            // Verificar que el deporte coincida
            if (!equipo.getDeporte().equalsIgnoreCase(deporteEquipo)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json");
                response.getWriter().write("{\"mensaje\": \"Todos los equipos deben pertenecer al mismo deporte.\"}");
                return;
            }
        }

        // Si todo es correcto, insertar el evento en la base de datos
        EventoDAO.insertar(nuevoEvento);

        // Respuesta exitosa
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("application/json");
        response.getWriter().write("{\"mensaje\": \"Evento registrado con éxito.\"}");
    }
}
