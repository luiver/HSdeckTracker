package com.codecool.hsdecktracker.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="CardServlet", urlPatterns = {"/api/v1/cards"}, loadOnStartup = 2)

public class CardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String linkId = request.getParameter("link_id");
        out.println();
    }
}
