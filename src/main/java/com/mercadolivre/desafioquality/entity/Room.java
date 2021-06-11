package com.mercadolivre.desafioquality.entity;

import lombok.*;

import javax.validation.constraints.Size;

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
