package com.ss.utopia.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.BookingPayment;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.FlightBookings;
import com.ss.utopia.entity.Passenger;
import com.ss.utopia.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {
	@Autowired
	BookingService bookingService;
	
	@GetMapping
	public @ResponseBody Iterable<Booking> getBookings() throws ClassNotFoundException, SQLException{
		return bookingService.getBookings();
	}
	
	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<Booking> getBookingById(@PathVariable Integer id){
		return bookingService.getBookingById(id);
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<String> addBooking(@RequestBody BookingInput input){
		System.out.println(input.getPayment());
		System.out.println(input.getPassengers());
		System.out.println(input.getFlightIds());
		return bookingService.createBooking(input.getPayment(),input.getPassengers(),input.getFlightIds());
	}
	
	@PutMapping
	public @ResponseBody ResponseEntity<String> updateBooking(@RequestParam Integer id, @RequestBody Booking booking){
		return bookingService.updateBooking(id, booking);
	}
//	
//	@DeleteMapping("/{id}")
//	public @ResponseBody ResponseEntity<String> deleteBooking(@PathVariable Integer id) {
//		return bookingService.deleteBooking(id);
//	}
}
