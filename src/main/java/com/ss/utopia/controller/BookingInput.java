package com.ss.utopia.controller;

import java.util.List;

import com.ss.utopia.entity.BookingPayment;
import com.ss.utopia.entity.Passenger;

public class BookingInput {
	private BookingPayment payment;
	private List<Integer> flightIds;
	private List<Passenger> passengers;
	public BookingPayment getPayment() {
		return payment;
	}
	public void setPayment(BookingPayment payment) {
		this.payment = payment;
	}
	public List<Integer> getFlightIds() {
		return flightIds;
	}
	public void setFlightIds(List<Integer> flightIds) {
		this.flightIds = flightIds;
	}
	public List<Passenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

} 
