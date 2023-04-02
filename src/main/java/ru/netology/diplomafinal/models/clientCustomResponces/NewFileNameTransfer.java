package ru.netology.diplomafinal.models.clientCustomResponces;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NewFileNameTransfer(@JsonProperty("filename") String name) {
}
