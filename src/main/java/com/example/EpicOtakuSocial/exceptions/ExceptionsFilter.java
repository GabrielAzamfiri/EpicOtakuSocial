package com.example.EpicOtakuSocial.exceptions;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class ExceptionsFilter extends OncePerRequestFilter {
    @Autowired
    @Qualifier("handlerExceptionResolver")
    HandlerExceptionResolver handler;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            this.handler.resolveException(request, response, null, ex);
        }
    }
}
//ExceptionsFilter cattura tutte le eccezioni generate durante l'elaborazione di una richiesta HTTP e le passa a HandlerExceptionResolver,
// il quale si occupa di gestirle in modo controllato, restituendo una risposta HTTP adeguata senza interrompere l'intero flusso dell'applicazione.