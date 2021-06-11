package com.mercadolivre.desafioquality.entity.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyRequestDTO {

    private String prop_name;

    private String prop_district;
}
