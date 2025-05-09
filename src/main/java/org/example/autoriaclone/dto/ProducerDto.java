package org.example.autoriaclone.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProducerDto {
    private Integer id;
    @NotBlank(message = "name is required")
    private String name;
    private String error;

    public ProducerDto setError(String error) {
        this.error = error;
        return this;
    }
    public ProducerDto(){}

}