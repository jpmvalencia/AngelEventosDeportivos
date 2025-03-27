package com.sportsevent.gestoreventosdeportivos.Service;

import com.google.gson.Gson;
import com.sportsevent.gestoreventosdeportivos.DAO.EquipoDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class EquipoService {

    private static Gson gson = new Gson();

    // POST /equipos: Permite registrar un nuevo equipo en el sistema
    public static void registrarEquipo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Leer el JSON del cuerpo de la petición
        String json = request.getReader().lines().collect(Collectors.joining());
        EquipoDAO nuevoEquipo = gson.fromJson(json, EquipoDAO.class);

        // Verificar si ya existe un equipo con el mismo nombre y deporte
        if (existeEquipoConMismoNombreYDeporte(nuevoEquipo.getNombre(), nuevoEquipo.getDeporte())) {
            response.setStatus(HttpServletResponse.SC_CONFLICT); // 409 Conflict
            response.setContentType("application/json");
            String mensajeError = "{\"mensaje\": \"Ya existe un equipo con el mismo nombre y deporte.\"}";
            response.getWriter().write(mensajeError);
            return;
        }

        // Insertar el nuevo equipo
        EquipoDAO.insertar(nuevoEquipo);

        response.setStatus(HttpServletResponse.SC_CREATED); // 201 Created
        response.setContentType("application/json");
        String mensajeExito = "{\"mensaje\": \"Equipo registrado con éxito.\"}";
        response.getWriter().write(mensajeExito);
    }

    // GET /equipos: Retorna la lista de todos los equipos registrados con paginación
    public static void obtenerEquipos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Obtener los parámetros de paginación
        int page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
        int size = Integer.parseInt(request.getParameter("size") != null ? request.getParameter("size") : "10");

        // Obtener la lista de todos los equipos
        List<EquipoDAO> equipos = EquipoDAO.obtenerTodos();

        // Implementar la paginación
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, equipos.size());
        List<EquipoDAO> equiposPaginados = equipos.subList(startIndex, endIndex);

        // Convertir la lista de equipos a JSON
        String jsonEquipos = gson.toJson(equiposPaginados);

        // Escribir la respuesta
        response.setContentType("application/json");
        response.getWriter().write(jsonEquipos);
    }

    // Método auxiliar para verificar si ya existe un equipo con el mismo nombre y deporte
    private static boolean existeEquipoConMismoNombreYDeporte(String nombre, String deporte) {
        for (EquipoDAO equipo : EquipoDAO.obtenerTodos()) {
            if (equipo.getNombre().equalsIgnoreCase(nombre) && equipo.getDeporte().equalsIgnoreCase(deporte)) {
                return true;
            }
        }
        return false;
    }
}