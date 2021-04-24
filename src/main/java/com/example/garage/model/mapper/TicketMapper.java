package com.example.garage.model.mapper;

import com.example.garage.model.dto.TicketRequest;
import com.example.garage.model.dto.TicketResponse;
import com.example.garage.model.entity.Ticket;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2021-04-23
 */

@Mapper(componentModel = "spring")
public interface TicketMapper {
	Ticket toEntity(TicketRequest request);
	TicketResponse toResponse(Ticket entity);
	List<TicketResponse> toResponse(List<Ticket> tickets);
}
