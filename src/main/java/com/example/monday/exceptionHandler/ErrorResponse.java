package com.example.monday.exceptionHandler;

import java.time.Instant;
import java.util.UUID;

public record ErrorResponse(UUID id, Instant timestamp, String message){
}
