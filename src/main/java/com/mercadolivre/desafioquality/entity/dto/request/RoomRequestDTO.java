package com.mercadolivre.desafioquality.entity.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RoomRequestDTO {

    @NotBlank(message = "The field room_name does not accept empty values.")
    @Size(max = 30, message = "The field room_name accepts only 30 characters.")
    @Pattern(regexp = "([A-Z][a-z]+)", message = "The field room_name only accepts words with the first capital letter.")
    private String room_name;

    @NotNull(message = "The field room_width does not accept empty values.")
    @Positive(message = "The field room_width does not accept negative values.")
    @Range(max = 25, message = "The field room_width does not accept values over 25.")
    private Double room_width;

    private String property;

    @NotNull(message = "The field room_width does not accept empty values.")
    @Positive(message = "The field room_width does not accept negative values.")
    @Range(max = 33, message = "The field room_width does not accept values over 33.")
    private Double room_length;
}
