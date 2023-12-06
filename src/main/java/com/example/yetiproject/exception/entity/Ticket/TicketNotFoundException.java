package com.example.yetiproject.exception.entity.Ticket;

public class TicketNotFoundException extends RuntimeException{
	public TicketNotFoundException(String message) {
		super(message);
	}
}
