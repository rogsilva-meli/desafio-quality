package com.mercadolivre.desafioquality.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mercadolivre.desafioquality.entity.Room;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PropertyResponseDTO {

    private String prop_name;

    private String prop_district;

    private double totalMeters;

    private double valueProperty;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RoomResponseDTO biggestRoom;

    private List<RoomResponseDTO> rooms;
}
