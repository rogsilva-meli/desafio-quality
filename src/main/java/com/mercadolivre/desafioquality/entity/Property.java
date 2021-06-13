package com.mercadolivre.desafioquality.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Property {

    private String prop_name;

    private String prop_district;

    private double totalMeters;

    private List<Room> rooms;

}
