package com.mercadolivre.desafioquality.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Room {
    private String room_name;
    private Double room_width;
    private Double room_length;
    private String property;
}
