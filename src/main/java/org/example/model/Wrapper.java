package org.example.model;

import java.util.List;

public class Wrapper {
    private List<Ticket> tickets;
    public List<Ticket> getTickets(){
        return tickets;
    }

    public void setTickets(List<Ticket> tickets){
        this.tickets = tickets;
    }
}
