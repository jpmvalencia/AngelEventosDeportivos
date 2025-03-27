package com.sportsevent.gestoreventosdeportivos.Servlet;

import com.sportsevent.gestoreventosdeportivos.Service.EventoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "EventoServlet", urlPatterns = "/eventos")
public class EventoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EventoService.registrarEvento(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EventoService.obtenerEventos(request, response);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("vender-entradas".equals(accion)) {
            EventoService.venderEntradas(request, response);
        } else if ("actualizar-estado".equals(accion)) {
            EventoService.actualizarEstado(request, response);
        }
    }
}