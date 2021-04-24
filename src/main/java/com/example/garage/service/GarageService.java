package com.example.garage.service;

import com.example.garage.model.dto.TicketRequest;
import com.example.garage.model.dto.TicketResponse;
import com.example.garage.model.entity.Ticket;
import com.example.garage.model.enums.Status;
import com.example.garage.model.enums.VehicleType;
import com.example.garage.model.mapper.TicketMapper;
import com.example.garage.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Ilyas Ziyaoglu
 * @date 2021-01-20
 */

@Service
public class GarageService {

	private final TicketRepository ticketRepository;
	private final TicketMapper ticketMapper;

	public GarageService(TicketRepository ticketRepository, TicketMapper ticketMapper) {
		this.ticketRepository = ticketRepository;
		this.ticketMapper = ticketMapper;
	}

	/**
	 * This method creates a ticket for driver and parks vehicle.
	 *
	 * @param ticketRequest - Contains vehicle information.
	 * @return String - Returns information abut parking.
	 */
	public String park(TicketRequest ticketRequest) {

		// get available slots to park
		List<Integer> availableSlots = getAvailableSlotsByVehicleType(ticketRequest.getVehicleType());
		if (availableSlots.isEmpty()) {
			return "Garage is full.";
		}

		// create ticket
		try {
			Ticket ticket = createTicket(ticketRequest, availableSlots);

			// finish and return
			if (ticketRequest.getVehicleType().getWidth() == 1) {
				return "Allocated 1 slot. Ticket number: " + ticket.getId();
			} else {
				return "Allocated " + ticketRequest.getVehicleType().getWidth() + " slots. Ticket number: " + ticket.getId();
			}
		} catch (Exception e) {
			return "Can not create ticket!";
		}
	}

	/**
	 * This method updates ticket as left for vehicle which wants to leave and returns process information.
	 *
	 * @param ticketId - ticket id to leave.
	 * @return String - Returns information abut leave operation.
	 */
	public String leave(Long ticketId) {

		Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
		if (ticketOptional.isEmpty()) {
			return "Ticket not found!";
		}

		Ticket ticket = ticketOptional.get();
		try {
			ticket.setStatus(Status.LEFT);
			ticketRepository.save(ticket);
		} catch (Exception e) {
			return "Leave operation failed!";
		}

		// finish and return
		if (ticket.getVehicleType().getWidth() == 1) {
			return "1 slot is free.";
		} else {
			return ticket.getVehicleType().getWidth() + " slots are free.";
		}
	}

	/**
	 * This method gives information about garage state.
	 *
	 * @return List<TicketResponse> - Returns information abut in garage vehicles and slots.
	 */
	public List<TicketResponse> status() {
		List<Ticket> inGarageTickets = ticketRepository.findAllByStatus(Status.IN_GARAGE);
		return ticketMapper.toResponse(inGarageTickets);
	}

	/**
	 * This method creates a string that demonstrates reserved and unreserved garage slots.
	 *
	 * 0 - unreserved slot
	 * 1 - reserved slot
	 * 2 - unreserved slot next to reserved slot
	 *
	 * @return String - Returns a string that demonstrates reserved and unreserved garage slots.
	 */
	public String loadAndGetSlotString() {
		String slots = "0000000000";
		List<Ticket> ticketsInGarage = ticketRepository.findAllByStatus(Status.IN_GARAGE);

		// fill by 1 reserved slots.
		StringBuilder slotsSB = new StringBuilder(slots);
		ticketsInGarage
				.forEach(ticket -> ticket.getSlots()
					.forEach(slot -> slotsSB.replace(slot-1, slot, "1")));
		slots = slotsSB.toString();

		// fill by 2 unreserved slot next to reserved slot.
		slots = slots.replace("101", "121")
				.replace("100", "120")
				.replace("001", "021")
				.replace("01", "21")
				.replace("10", "12");

		return slots;
	}

	/**
	 * This method finds first available slot list by using vehicle width
	 *
	 * @param vehicleType - Car: 1 slot / Jeep: 2 slot / Truck: 4 slot
	 * @return List<Integer> - Returns first available slot indexes else returns empty list.
	 */
	public List<Integer> getAvailableSlotsByVehicleType(VehicleType vehicleType) {

		// prepare search pattern
		String slots = loadAndGetSlotString();
		String searchPattern = "0".repeat(vehicleType.getWidth());
		List<Integer> reservedSlots;

		// find available slots first index
		int firstSlot = slots.indexOf(searchPattern) + 1;
		if (firstSlot == 0) { // if not found enough slots to park
			reservedSlots = new ArrayList<>();
		} else { // if found enough slots
			int lastSlot = firstSlot + vehicleType.getWidth();
			reservedSlots = IntStream.range(firstSlot, lastSlot).boxed().collect(Collectors.toList());
		}

		return reservedSlots;
	}

	/**
	 * This method creates a ticket for requested slots.
	 *
	 * @param ticketRequest - Contains vehicle information.
	 * @param availableSlots - slots to create ticket for.
	 */
	public Ticket createTicket(TicketRequest ticketRequest, List<Integer> availableSlots) {

		// prepare ticket to save
		Ticket ticket = ticketMapper.toEntity(ticketRequest);
		ticket.setSlots(availableSlots);

		// save ticket
		ticket = ticketRepository.save(ticket);

		return ticket;
	}
}
