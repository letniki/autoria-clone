package org.example.autoriaclone.mapper;

import org.example.autoriaclone.dto.ModelDto;
import org.example.autoriaclone.entity.Model;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {
    public ModelDto toDto(Model model){
        return ModelDto.builder()
                .id(model.getId())
                .name(model.getName())
                .producer(model.getProducer())
                .build();
    }
    public Model toEntity(ModelDto modelDto){
        return new Model(modelDto.getId(), modelDto.getName(), modelDto.getProducer());
    }
}
