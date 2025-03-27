package com.sportsevent.gestoreventosdeportivos.Servlet;

import com.sportsevent.gestoreventosdeportivos.Service.JugadorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "JugadorServlet", urlPatterns = "/jugadores")
public class JugadorServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JugadorService.registrarJugador(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JugadorService.obtenerJugadores(request, response);
    }
}