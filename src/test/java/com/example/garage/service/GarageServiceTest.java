package com.example.garage.service;

import com.example.garage.model.dto.TicketRequest;
import com.example.garage.model.dto.TicketResponse;
import com.example.garage.model.entity.Ticket;
import com.example.garage.model.enums.Status;
import com.example.garage.model.enums.VehicleType;
import com.example.garage.model.mapper.TicketMapper;
import com.example.garage.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GarageServiceTest {

	@InjectMocks
	private GarageService garageService;

	@Mock
	private TicketRepository ticketRepository;

	@Mock
	private TicketMapper ticketMapper;

	@BeforeEach
	void setUp() {
	}

	@Test
	void loadAndGetSlotString_One_Jeep_At_Start() {
		List<Ticket> tickets = new ArrayList<>();
		Ticket ticket1 = new Ticket();
		ticket1.setSlots(Arrays.asList(1,2));
		tickets.add(ticket1);
		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);

		String slots = garageService.loadAndGetSlotString();
		assertEquals("1120000000", slots);
	}

	@Test
	void loadAndGetSlotString_One_Truck_In_The_Middle() {
		List<Ticket> tickets = new ArrayList<>();
		Ticket ticket1 = new Ticket();
		ticket1.setSlots(Arrays.asList(3,4,5,6));
		tickets.add(ticket1);
		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);

		String slots = garageService.loadAndGetSlotString();
		assertEquals("0211112000", slots);
	}

	@Test
	void loadAndGetSlotString_One_Car_At_End() {
		List<Ticket> tickets = new ArrayList<>();
		Ticket ticket1 = new Ticket();
		ticket1.setSlots(Collections.singletonList(10));
		tickets.add(ticket1);
		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);

		String slots = garageService.loadAndGetSlotString();
		assertEquals("0000000021", slots);
	}

	@Test
	void loadAndGetSlotString_One_Car_At_Start_One_Truck_After_That() {
		List<Ticket> tickets = new ArrayList<>();

		Ticket ticket1 = new Ticket();
		ticket1.setSlots(Collections.singletonList(1));
		tickets.add(ticket1);

		Ticket ticket2 = new Ticket();
		ticket2.setSlots(Arrays.asList(3,4,5,6));
		tickets.add(ticket2);

		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);

		String slots = garageService.loadAndGetSlotString();
		assertEquals("1211112000", slots);
	}

	@Test
	void loadAndGetSlotString_One_Jeep_At_End_One_Truck_Before_That() {
		List<Ticket> tickets = new ArrayList<>();

		Ticket ticket1 = new Ticket();
		ticket1.setSlots(List.of(9,10));
		tickets.add(ticket1);

		Ticket ticket2 = new Ticket();
		ticket2.setSlots(List.of(4,5,6,7));
		tickets.add(ticket2);

		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);

		String slots = garageService.loadAndGetSlotString();
		assertEquals("0021111211", slots);
	}

	@Test
	void loadAndGetSlotString_Two_Jeep_In_The_Middle() {
		List<Ticket> tickets = new ArrayList<>();

		Ticket ticket1 = new Ticket();
		ticket1.setSlots(List.of(4,5));
		tickets.add(ticket1);

		Ticket ticket2 = new Ticket();
		ticket2.setSlots(List.of(7,8));
		tickets.add(ticket2);

		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);

		String slots = garageService.loadAndGetSlotString();
		assertEquals("0021121120", slots);
	}

	@Test
	void getAvailableSlotsByVehicleType_Truck_When_Jeep_At_Start() {
		List<Ticket> tickets = new ArrayList<>();
		Ticket ticket1 = new Ticket();
		ticket1.setSlots(Arrays.asList(1,2));
		tickets.add(ticket1);
		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);

		List<Integer> availableSlots = 	garageService.getAvailableSlotsByVehicleType(VehicleType.TRUCK);
		assertEquals(List.of(4,5,6,7), availableSlots);
	}

	@Test
	void getAvailableSlotsByVehicleType_Jeep_When_Truck_In_The_Middle() {
		List<Ticket> tickets = new ArrayList<>();
		Ticket ticket1 = new Ticket();
		ticket1.setSlots(Arrays.asList(3,4,5,6));
		tickets.add(ticket1);
		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);

		List<Integer> availableSlots = 	garageService.getAvailableSlotsByVehicleType(VehicleType.JEEP);
		assertEquals(List.of(8,9), availableSlots);
	}

	@Test
	void getAvailableSlotsByVehicleType_Jeep_When_Car_At_End() {
		List<Ticket> tickets = new ArrayList<>();
		Ticket ticket1 = new Ticket();
		ticket1.setSlots(Collections.singletonList(10));
		tickets.add(ticket1);
		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);

		List<Integer> availableSlots = 	garageService.getAvailableSlotsByVehicleType(VehicleType.JEEP);
		assertEquals(List.of(1,2), availableSlots);
	}

	@Test
	void getAvailableSlotsByVehicleType_Jeep_When_Car_At_Start_Truck_After_That() {
		List<Ticket> tickets = new ArrayList<>();

		Ticket ticket1 = new Ticket();
		ticket1.setSlots(Collections.singletonList(1));
		tickets.add(ticket1);

		Ticket ticket2 = new Ticket();
		ticket2.setSlots(Arrays.asList(3,4,5,6));
		tickets.add(ticket2);

		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);

		List<Integer> availableSlots = 	garageService.getAvailableSlotsByVehicleType(VehicleType.JEEP);
		assertEquals(List.of(8,9), availableSlots);
	}

	@Test
	void getAvailableSlotsByVehicleType_Jeep_When_One_Jeep_At_End_One_Truck_Before_That() {
		List<Ticket> tickets = new ArrayList<>();

		Ticket ticket1 = new Ticket();
		ticket1.setSlots(List.of(9,10));
		tickets.add(ticket1);

		Ticket ticket2 = new Ticket();
		ticket2.setSlots(List.of(4,5,6,7));
		tickets.add(ticket2);

		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);

		List<Integer> availableSlots = 	garageService.getAvailableSlotsByVehicleType(VehicleType.JEEP);
		assertEquals(List.of(1,2), availableSlots);
	}

	@Test
	void getAvailableSlotsByVehicleType_Jeep_When_Two_Jeep_At_Start() {
		List<Ticket> tickets = new ArrayList<>();

		Ticket ticket1 = new Ticket();
		ticket1.setSlots(List.of(1,2));
		tickets.add(ticket1);

		Ticket ticket2 = new Ticket();
		ticket2.setSlots(List.of(4,5));
		tickets.add(ticket2);

		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);

		List<Integer> availableSlots = 	garageService.getAvailableSlotsByVehicleType(VehicleType.JEEP);
		assertEquals(List.of(7,8), availableSlots);
	}

	@Test
	void getAvailableSlotsByVehicleType_Truck_When_One_Jeep_End_One_Truck_Before_That() {
		List<Ticket> tickets = new ArrayList<>();

		Ticket ticket1 = new Ticket();
		ticket1.setSlots(List.of(9,10));
		tickets.add(ticket1);

		Ticket ticket2 = new Ticket();
		ticket2.setSlots(List.of(4,5,6,7));
		tickets.add(ticket2);

		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);

		List<Integer> availableSlots = 	garageService.getAvailableSlotsByVehicleType(VehicleType.TRUCK);
		assertEquals(new ArrayList<>(), availableSlots);
	}

	@Test
	void park_When_Truck_Wants_Park_And_Garage_Is_Full() {

		List<Ticket> tickets = new ArrayList<>();
		Ticket ticket1 = new Ticket();
		ticket1.setSlots(List.of(9,10));
		tickets.add(ticket1);
		Ticket ticket2 = new Ticket();
		ticket2.setSlots(List.of(4,5,6,7));
		tickets.add(ticket2);
		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);

		TicketRequest ticketRequest = new TicketRequest();
		ticketRequest.setColor("Black");
		ticketRequest.setPlate("34-HBO-2020");
		ticketRequest.setVehicleType(VehicleType.TRUCK);


		String result = garageService.park(ticketRequest);
		assertEquals("Garage is full.", result);
	}

	@Test
	void park_When_Jeep_Wants_Park_And_Garage_Is_Available() {

		List<Ticket> tickets = new ArrayList<>();
		Ticket ticket1 = new Ticket();
		ticket1.setSlots(List.of(9,10));
		tickets.add(ticket1);
		Ticket ticket2 = new Ticket();
		ticket2.setSlots(List.of(4,5,6,7));
		tickets.add(ticket2);

		TicketRequest ticketRequest = new TicketRequest();
		ticketRequest.setColor("Blue");
		ticketRequest.setPlate("34-VO-2018");
		ticketRequest.setVehicleType(VehicleType.JEEP);

		Ticket ticket = new Ticket();
		ticket.setId(1L);
		ticket.setColor("Blue");
		ticket.setPlate("34-VO-2018");
		ticket.setVehicleType(VehicleType.JEEP);

		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);
		when(ticketRepository.save(ArgumentMatchers.any(Ticket.class))).thenReturn(ticket);
		when(ticketMapper.toEntity(ticketRequest)).thenReturn(ticket);


		String result = garageService.park(ticketRequest);
		assertTrue(result.startsWith("Allocated 2 slots. Ticket number: "));
	}

	@Test
	void park_When_Car_Wants_Park_And_Garage_Is_Available() {

		List<Ticket> tickets = new ArrayList<>();
		Ticket ticket1 = new Ticket();
		ticket1.setSlots(List.of(9,10));
		tickets.add(ticket1);
		Ticket ticket2 = new Ticket();
		ticket2.setSlots(List.of(4,5,6,7));
		tickets.add(ticket2);

		TicketRequest ticketRequest = new TicketRequest();
		ticketRequest.setColor("Blue");
		ticketRequest.setPlate("34-VO-2018");
		ticketRequest.setVehicleType(VehicleType.CAR);

		Ticket ticket = new Ticket();
		ticket.setId(1L);
		ticket.setColor("Blue");
		ticket.setPlate("34-VO-2018");
		ticket.setVehicleType(VehicleType.CAR);

		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);
		when(ticketRepository.save(ArgumentMatchers.any(Ticket.class))).thenReturn(ticket);
		when(ticketMapper.toEntity(ticketRequest)).thenReturn(ticket);


		String result = garageService.park(ticketRequest);
		assertTrue(result.startsWith("Allocated 1 slot. Ticket number: "));
	}

	@Test
	void park_When_Car_Wants_Park_And_Garage_Is_Available_But_Create_Ticket_Fail() {

		List<Ticket> tickets = new ArrayList<>();
		Ticket ticket1 = new Ticket();
		ticket1.setSlots(List.of(9,10));
		tickets.add(ticket1);
		Ticket ticket2 = new Ticket();
		ticket2.setSlots(List.of(4,5,6,7));
		tickets.add(ticket2);

		TicketRequest ticketRequest = new TicketRequest();
		ticketRequest.setColor("Black");
		ticketRequest.setPlate("34-SO-1988");
		ticketRequest.setVehicleType(VehicleType.CAR);

		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);


		String result = garageService.park(ticketRequest);
		assertEquals("Can not create ticket!", result);
	}

	@Test
	void leave_When_Ticket_Not_Found() {
		Optional<Ticket> optionalTicket = Optional.empty();
		when(ticketRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(optionalTicket);

		String result = garageService.leave(5L);
		assertEquals("Ticket not found!", result);
	}

	@Test
	void leave_When_Ticket_Found_For_One_Slot_Leave_Failed() {

		Ticket ticket = new Ticket();
		ticket.setId(1L);
		ticket.setColor("Black");
		ticket.setPlate("34-SO-1988");
		ticket.setVehicleType(VehicleType.CAR);

		Optional<Ticket> optionalTicket = Optional.of(ticket);
		when(ticketRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(optionalTicket);
		when(ticketRepository.save(ArgumentMatchers.any(Ticket.class))).thenThrow(new RuntimeException());

		String result = garageService.leave(5L);
		assertEquals("Leave operation failed!", result);
	}

	@Test
	void leave_When_Ticket_Found_For_One_Slot() {

		Ticket ticket = new Ticket();
		ticket.setId(1L);
		ticket.setColor("Black");
		ticket.setPlate("34-SO-1988");
		ticket.setVehicleType(VehicleType.CAR);

		Optional<Ticket> optionalTicket = Optional.of(ticket);
		when(ticketRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(optionalTicket);

		String result = garageService.leave(5L);
		assertEquals("1 slot is free.", result);
	}

	@Test
	void leave_When_Ticket_Found_For_Two_Slot() {

		Ticket ticket = new Ticket();
		ticket.setId(1L);
		ticket.setColor("Black");
		ticket.setPlate("34-SO-1988");
		ticket.setVehicleType(VehicleType.JEEP);

		Optional<Ticket> optionalTicket = Optional.of(ticket);
		when(ticketRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(optionalTicket);

		String result = garageService.leave(5L);
		assertEquals("2 slots are free.", result);
	}

	@Test
	void createTicket() {

		TicketRequest ticketRequest = new TicketRequest();
		ticketRequest.setColor("Blue");
		ticketRequest.setPlate("34-VO-2018");
		ticketRequest.setVehicleType(VehicleType.JEEP);

		List<Integer> availableSlots = List.of(3,4);

		Ticket ticket = new Ticket();
		ticket.setColor("Blue");
		ticket.setPlate("34-VO-2018");
		ticket.setVehicleType(VehicleType.JEEP);

		when(ticketMapper.toEntity(ticketRequest)).thenReturn(ticket);

		ticket.setId(1L);
		ticket.setSlots(availableSlots);
		when(ticketRepository.save(ArgumentMatchers.any(Ticket.class))).thenReturn(ticket);

		Ticket result = garageService.createTicket(ticketRequest, availableSlots);
		assertEquals(ticket, result);
	}

	@Test
	void status() {

		List<Ticket> tickets = new ArrayList<>();
		Ticket ticket1 = new Ticket();
		ticket1.setId(1L);
		ticket1.setStatus(Status.IN_GARAGE);
		ticket1.setColor("Black");
		ticket1.setPlate("34-VO-2018");
		ticket1.setSlots(List.of(9,10));
		tickets.add(ticket1);
		Ticket ticket2 = new Ticket();
		ticket1.setId(2L);
		ticket2.setStatus(Status.IN_GARAGE);
		ticket2.setColor("Blue");
		ticket2.setPlate("34-VO-2018");
		ticket2.setSlots(List.of(4,5,6,7));
		tickets.add(ticket2);

		List<TicketResponse> ticketResponseListExpected = new ArrayList<>();
		TicketResponse ticketResponse1 = new TicketResponse();
		ticketResponse1.setId(1L);
		ticketResponse1.setColor("Black");
		ticketResponse1.setPlate("34-VO-2018");
		ticketResponse1.setSlots(List.of(9,10));
		ticketResponseListExpected.add(ticketResponse1);
		TicketResponse ticketResponse2 = new TicketResponse();
		ticketResponse2.setId(2L);
		ticketResponse2.setColor("Blue");
		ticketResponse2.setPlate("34-VO-2018");
		ticketResponse2.setSlots(List.of(4,5,6,7));
		ticketResponseListExpected.add(ticketResponse2);

		when(ticketRepository.findAllByStatus(Status.IN_GARAGE)).thenReturn(tickets);
		when(ticketMapper.toResponse(tickets)).thenReturn(ticketResponseListExpected);


		List<TicketResponse> ticketResponseListActual = garageService.status();
		assertEquals(ticketResponseListExpected, ticketResponseListActual);
	}
}