package ru.netology.diplomafinal.models.authentification;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginRespond(@JsonProperty("auth-token") String authToken) {
}
