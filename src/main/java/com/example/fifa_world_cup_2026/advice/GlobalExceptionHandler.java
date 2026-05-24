package com.example.fifa_world_cup_2026.advice;

import com.example.fifa_world_cup_2026.exception.MatchNotFoundException;
import com.example.fifa_world_cup_2026.exception.PrognoseClosedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import com.example.fifa_world_cup_2026.exception.TeamNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TeamNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTeamNotFound(TeamNotFoundException ex, Model model) {
        model.addAttribute("errorTitle", "Team niet gevonden");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", "404");
        return "error/404";
    }

    @ExceptionHandler(MatchNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleMatchNotFound(MatchNotFoundException ex, Model model) {
        model.addAttribute("errorTitle", "Wedstrijd niet gevonden");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", "404");
        return "error/404";
    }

    @ExceptionHandler(PrognoseClosedException.class)
    public String handlePrognoseClosed(PrognoseClosedException ex, Model model) {
        model.addAttribute("errorTitle", "Voorspelling gesloten");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", "403");
        return "error/403";
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleSecurity(SecurityException ex, Model model) {
        model.addAttribute("errorTitle", "Geen toegang");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", "403");
        return "error/403";
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(
            org.springframework.security.access.AccessDeniedException ex,
            Model model) {
        model.addAttribute("errorTitle", "Geen toegang");
        model.addAttribute("errorMessage",
                "Je hebt geen toegang tot deze pagina.");
        model.addAttribute("errorCode", "403");
        return "error/403";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoHandler(NoHandlerFoundException ex, Model model) {
        model.addAttribute("errorTitle", "Pagina niet gevonden");
        model.addAttribute("errorMessage",
                "De pagina '" + ex.getRequestURL() + "' bestaat niet.");
        model.addAttribute("errorCode", "404");
        return "error/404";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoResource(NoResourceFoundException ex, Model model) {
        model.addAttribute("errorTitle", "Pagina niet gevonden");
        model.addAttribute("errorMessage", "De gevraagde resource bestaat niet.");
        model.addAttribute("errorCode", "404");
        return "error/404";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorTitle", "Ongeldige invoer");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", "400");
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneral(Exception ex, Model model, HttpServletRequest request) {
        model.addAttribute("errorTitle", "Onverwachte fout");
        model.addAttribute("errorMessage",
                "Er is een onverwachte fout opgetreden. Probeer het later opnieuw.");
        model.addAttribute("errorCode", "500");
        return "error/404";
    }

}
