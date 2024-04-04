package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.Ticket;
import org.example.model.Wrapper;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    Wrapper ticketsWrapper = objectMapper.readValue(new File("tickets.json"), Wrapper.class);
    List<Ticket> tickets = ticketsWrapper.getTickets();

    public Main() throws IOException {}

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.minTimeForEveryCarrier();
        main.priceDifference();
    }
    private Duration estimateFlightTime(Ticket ticket){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime departureDateTime = LocalDateTime.parse(ticket.getDepartureDate() + " " + ticket.getDepartureTime(), formatter);
        LocalDateTime arrivalDateTime = LocalDateTime.parse(ticket.getArrivalDate() + " " + ticket.getArrivalTime(), formatter);

        return Duration.between(departureDateTime, arrivalDateTime);
    }

    private void minTimeForEveryCarrier() throws IOException{
        Map<String, Duration> minFlightTimes = new HashMap<>();

        for(Ticket ticket : tickets){
            if (("VVO".equals(ticket.getOrigin()) && "TLV".equals(ticket.getDestination()))) {
                Duration flightTime = estimateFlightTime(ticket);
                Duration currentMin = minFlightTimes.get(ticket.getCarrier());
                if (currentMin == null || flightTime.compareTo(currentMin) < 0) {
                    minFlightTimes.put(ticket.getCarrier(), flightTime);
                }
            }
        }

        for(Map.Entry<String, Duration> entry : minFlightTimes.entrySet()){
            System.out.println("Carrier: " + entry.getKey() + ", shortest flight time: " + entry.getValue().toHours()
            + " hours and " + entry.getValue().toMinutes() % 60 + " minutes");
        }
    }

    private void priceDifference() throws IOException{
        List<Integer> prices = tickets.stream()
                .filter(ticket -> "VVO".equals(ticket.getOrigin()) && "TLV".equals(ticket.getDestination()))
                .map(Ticket::getPrice)
                .collect(Collectors.toList());

        double avgPrice = prices.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(Double.NaN);

        Collections.sort(prices);
        double medianPrice;
        int size = prices.size();
        if(size % 2 == 0){
            medianPrice = ((double) prices.get(size / 2) + (double) prices.get(size/ 2 - 1)) / 2;
        } else { medianPrice = (double) prices.get(size / 2); }

        double difference = avgPrice - medianPrice;

        System.out.println("Average price: " + avgPrice);
        System.out.println("Median price: " + medianPrice);
        System.out.println("Difference: " + difference);
    }
}