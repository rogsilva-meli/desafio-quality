package com.mercadolivre.desafioquality.entity.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RoomResponseDTO {

    private String room_name;

    private Double room_width;

    private Double room_length;

    private Double squareMetersRoom;
}
