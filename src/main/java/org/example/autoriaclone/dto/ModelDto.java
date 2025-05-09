package org.example.autoriaclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.autoriaclone.entity.Producer;
@Data
@Builder
@AllArgsConstructor
public class ModelDto {
    private Integer id;
    private String name;
    private Producer producer;
    private String error;

    public ModelDto setError(String error) {
        this.error = error;
        return this;
    }
    public ModelDto(){}
}
