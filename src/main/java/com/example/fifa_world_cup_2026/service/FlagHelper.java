package com.example.fifa_world_cup_2026.service;

import org.springframework.stereotype.Component;
import java.util.Map;

@Component("flagHelper")
public class FlagHelper {

    private static final Map<String, String> COUNTRY_CODES = Map.ofEntries(
            Map.entry("Belgium",      "be"),
            Map.entry("France",       "fr"),
            Map.entry("Brazil",       "br"),
            Map.entry("Argentina",    "ar"),
            Map.entry("Spain",        "es"),
            Map.entry("Germany",      "de"),
            Map.entry("England",      "gb-eng"),
            Map.entry("Portugal",     "pt"),
            Map.entry("Netherlands",  "nl"),
            Map.entry("Italy",        "it"),
            Map.entry("Croatia",      "hr"),
            Map.entry("Morocco",      "ma"),
            Map.entry("Japan",        "jp"),
            Map.entry("South Korea",  "kr"),
            Map.entry("USA",          "us"),
            Map.entry("Mexico",       "mx"),
            Map.entry("Canada",       "ca"),
            Map.entry("Poland",       "pl"),
            Map.entry("Colombia",     "co"),
            Map.entry("Uruguay",      "uy"),
            Map.entry("Ecuador",      "ec"),
            Map.entry("Senegal",      "sn"),
            Map.entry("Ghana",        "gh"),
            Map.entry("Australia",    "au"),
            Map.entry("Switzerland",  "ch"),
            Map.entry("Denmark",      "dk"),
            Map.entry("Serbia",       "rs"),
            Map.entry("Ukraine",      "ua"),
            Map.entry("Turkey",       "tr"),
            Map.entry("Iran",         "ir")
    );

    public String getCode(String countryName) {
        if (countryName == null) return "un";
        return COUNTRY_CODES.getOrDefault(countryName, "un");
    }

    public String getFlagUrl(String countryName) {
        return "https://flagcdn.com/w40/" + getCode(countryName) + ".png";
    }

}
