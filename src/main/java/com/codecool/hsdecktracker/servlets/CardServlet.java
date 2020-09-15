package com.codecool.hsdecktracker.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="CardServlet", urlPatterns = {"/cards"}, loadOnStartup = 2)

public class CardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            buffer.append("<div>");
            buffer.append("<a href=\"/another?link_id=" + i + "\">");
            buffer.append("Hello " + i + ". link:");
            buffer.append("</a>");
            buffer.append("</div>");
        }

        String linkId = request.getParameter("link_id");

        out.println(
                "<html>\n" +
                        "<head><title>Another page</title></head>\n" +
                        "<body>\n" +
                        "<h1>Hello CodeCooler!</h1>" +
                        (linkId == null ?
                                "<h3>No link was pressed</h3>" :
                                "<h3>Link " + linkId + " was pressed!</h3>") +
                        "<br/>" +
                        "<div>" + buffer.toString() + "</div>" +
                        "</body></html>"
        );
    }
}
