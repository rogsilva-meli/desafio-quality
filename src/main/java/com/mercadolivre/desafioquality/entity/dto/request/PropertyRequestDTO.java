package com.mercadolivre.desafioquality.entity.dto.request;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyRequestDTO {

    @NotBlank(message = "The field prop_name does not accept empty values.")
    @Size(max = 30, message = "The field prop_name accepts only 30 characters.")
    @Pattern(regexp = "[A-Z].*" , message = "The field prop_name only accepts words with the first capital letter.")
    private String prop_name;

    @NotBlank(message = "The field prop_district does not accept null or blank values.")
    @Size(max = 45, message = "This field accepts only 45 characters.")
    private String prop_district;
}
