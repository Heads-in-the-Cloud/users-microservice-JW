package com.ss.utopia.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Service;

import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.BookingPayment;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.FlightBookings;
import com.ss.utopia.entity.Passenger;
import com.ss.utopia.dao.BookingPaymentRepository;
import com.ss.utopia.dao.BookingRepository;
import com.ss.utopia.dao.FlightBookingsRepository;
import com.ss.utopia.dao.FlightRepository;
import com.ss.utopia.dao.PassengerRepository;

@Service
public class BookingService {
	@Autowired
	BookingRepository bookingRepo;
	@Autowired
	FlightBookingsRepository fbRepo;
	@Autowired
	FlightRepository flightRepo;
	@Autowired
	BookingPaymentRepository bpRepo;
	@Autowired
	PassengerRepository passRepo;
	public Iterable<Booking> getBookings(){
		return bookingRepo.findAll();
	}
	
	public ResponseEntity<Booking> getBookingById(Integer id){
		Optional<Booking> booking = bookingRepo.findById(id);
		if(booking.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).body(booking.get());
	}
	public String confCodeGen() {
		//found code for random string generator online
        String saltChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * saltChars.length());
            salt.append(saltChars.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

	}
	public ResponseEntity<String> createBooking(BookingPayment bp, List<Passenger> passengers, List<Integer> flightIds){
		Booking booking = new Booking();
		booking.setIs_active(1);
		String confCode = confCodeGen();
		booking.setConfirmation_code(confCode);
		bookingRepo.save(booking);
		for(int k = 0; k< passengers.size();k++) {
			//?
			passengers.get(k).setBooking(booking);
			passRepo.save(passengers.get(k));
		}
		bp.setBookingId(booking.getId());
		if(booking.getId() != bp.getBookingId()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid booking_ID for payment");
		}
		bpRepo.save(bp);
		for(int j = 0; j<flightIds.size(); j++) {
			FlightBookings fb = new FlightBookings();
			Optional<Flight> flightExist = flightRepo.findById(flightIds.get(j));
//			System.out.println("------>");
//			System.out.println(flightExist.get());
//			System.out.println("<------");
			if(flightExist.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid flight id for: " + j);
			}
			fb.setBooking(booking);
			fb.setFlight(flightExist.get());//?
			fbRepo.save(fb);
		}
		return ResponseEntity.status(HttpStatus.OK).body("Post Successful");

	}
	
	public ResponseEntity<String> updateBooking(Integer id, Booking booking){
		Optional<Booking>bookingExist = bookingRepo.findById(id);
		if(bookingExist.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking with this id does not exist");
		}
		bookingRepo.save(booking);
		Integer isActive = booking.getId();
		BookingPayment updateBookingPayment = bpRepo.getById(id);
		if(isActive == 0) {
			updateBookingPayment.setRefunded(1);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Update Succeeded");
		
	}
	//Check if delete should be added 
//	public ResponseEntity<String> deleteBooking(Integer id){
//		Optional<Booking>bookingExist = bookingRepo.findById(id);
//		if(bookingExist.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking with this id does not exist");
//		}
//		bookingRepo.deleteById(id);
//		return ResponseEntity.status(HttpStatus.OK).body("Successfully Deleted");
//	}
}
