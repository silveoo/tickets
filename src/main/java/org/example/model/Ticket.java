package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @JsonProperty("origin")
    String origin;

    @JsonProperty("origin_name")
    String originName;

    @JsonProperty("destination")
    String destination;

    @JsonProperty("destination_name")
    String destinationName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yy")
    @JsonProperty("departure_date")
    LocalDate departureDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "H:mm")
    @JsonProperty("departure_time")
    LocalTime departureTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yy")
    @JsonProperty("arrival_date")
    LocalDate arrivalDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "H:mm")
    @JsonProperty("arrival_time")
    LocalTime arrivalTime;

    @JsonProperty("carrier")
    String carrier;

    @JsonProperty("stops")
    int stops;

    @JsonProperty("price")
    int price;
}
