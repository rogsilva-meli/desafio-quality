package com.mercadolivre.desafioquality.entity.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RoomRequestDTO {

    private String room_name;
    private Double room_width;
    private Double room_length;
    private String property;
}
