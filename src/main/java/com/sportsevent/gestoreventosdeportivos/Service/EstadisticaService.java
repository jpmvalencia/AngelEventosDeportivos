package com.sportsevent.gestoreventosdeportivos.Service;

import com.google.gson.Gson;
import com.sportsevent.gestoreventosdeportivos.DAO.EquipoDAO;
import com.sportsevent.gestoreventosdeportivos.DAO.EventoDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstadisticaService {
    private static Gson gson = new Gson();

    // GET /estadisticas: Retorna las estadísticas generales
    public static void obtenerEstadisticas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Obtener todas las estadísticas
        Map<String, Object> estadisticas = calcularEstadisticas();

        // Convertir el mapa de estadísticas a JSON
        String jsonEstadisticas = gson.toJson(estadisticas);

        // Escribir la respuesta
        response.setContentType("application/json");
        response.getWriter().write(jsonEstadisticas);
    }

    // Método auxiliar para calcular todas las estadísticas
    private static Map<String, Object> calcularEstadisticas() {
        // Obtener todos los eventos
        ArrayList<EventoDAO> eventos = EventoDAO.obtenerTodos();

        // 1. Cantidad de eventos por deporte
        Map<String, Integer> eventosPorDeporte = new HashMap<>();
        for (EventoDAO evento : eventos) {
            String deporte = evento.getDeporte();
            eventosPorDeporte.put(deporte, eventosPorDeporte.getOrDefault(deporte, 0) + 1);
        }

        // 2. Promedio de jugadores por equipo
        Map<Integer, Integer> jugadoresPorEquipo = new HashMap<>();
        Map<Integer, Integer> equiposParticipantes = new HashMap<>();

        // Recorrer todos los eventos y equipos para contar los jugadores
        for (EventoDAO evento : eventos) {
            for (Integer equipoId : evento.getEquiposParticipantes()) {
                // Obtener la lista de jugadores de ese equipo
                EquipoDAO equipo = EquipoDAO.buscarPorId(equipoId);
                if (equipo != null) {
                    jugadoresPorEquipo.put(equipoId, jugadoresPorEquipo.getOrDefault(equipoId, 0) + equipo.getJugadores().size());
                    equiposParticipantes.put(equipoId, equiposParticipantes.getOrDefault(equipoId, 0) + 1);
                }
            }
        }

        // Calcular el promedio de jugadores por equipo
        double promedioJugadoresPorEquipo = 0.0;
        for (Integer equipoId : jugadoresPorEquipo.keySet()) {
            promedioJugadoresPorEquipo += jugadoresPorEquipo.get(equipoId);
        }
        if (jugadoresPorEquipo.size() > 0) {
            promedioJugadoresPorEquipo /= jugadoresPorEquipo.size();
        }

        // 3. Equipos con más eventos programados
        Map<Integer, Integer> eventosPorEquipo = new HashMap<>();
        for (EventoDAO evento : eventos) {
            for (Integer equipoId : evento.getEquiposParticipantes()) {
                eventosPorEquipo.put(equipoId, eventosPorEquipo.getOrDefault(equipoId, 0) + 1);
            }
        }

        // Obtener el equipo con más eventos
        Integer maxEventos = eventosPorEquipo.values().stream().max(Integer::compareTo).orElse(0);
        List<Integer> equiposConMasEventos = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : eventosPorEquipo.entrySet()) {
            if (entry.getValue().equals(maxEventos)) {
                equiposConMasEventos.add(entry.getKey());
            }
        }

        // 4. Porcentaje de ocupación de cada evento
        Map<Integer, Double> porcentajeOcupacion = new HashMap<>();
        for (EventoDAO evento : eventos) {
            double ocupacion = (double) evento.getEntradasVendidas() / evento.getCapacidad() * 100;
            porcentajeOcupacion.put(evento.getId(), ocupacion);
        }

        // Crear un mapa con todas las estadísticas
        Map<String, Object> estadisticas = new HashMap<>();
        estadisticas.put("eventosPorDeporte", eventosPorDeporte);
        estadisticas.put("promedioJugadoresPorEquipo", promedioJugadoresPorEquipo);
        estadisticas.put("equiposConMasEventos", equiposConMasEventos);
        estadisticas.put("porcentajeOcupacion", porcentajeOcupacion);

        return estadisticas;
    }
}
