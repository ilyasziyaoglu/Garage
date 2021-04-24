package com.example.garage.controller;

import com.example.garage.model.dto.TicketRequest;
import com.example.garage.model.dto.TicketResponse;
import com.example.garage.service.GarageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2021-04-23
 */

@RestController(value = "garage")
public class GarageController {

	private final GarageService garageService;

	public GarageController(GarageService garageService) {
		this.garageService = garageService;
	}

	@PostMapping(value = "park")
	public String park(@RequestBody TicketRequest request) {
		return garageService.park(request);
	}

	@PostMapping(value = "leave")
	public String leave(@RequestBody Long ticketId) {
		return garageService.leave(ticketId);
	}

	@GetMapping(value = "status")
	public List<TicketResponse> status() {
		return garageService.status();
	}
}
