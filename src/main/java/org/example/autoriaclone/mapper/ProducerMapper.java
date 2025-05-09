package org.example.autoriaclone.mapper;

import org.example.autoriaclone.dto.ProducerDto;
import org.example.autoriaclone.entity.Producer;
import org.springframework.stereotype.Component;

@Component
public class ProducerMapper {
    public ProducerDto toDto(Producer producer){
        return ProducerDto.builder()
                .id(producer.getId())
                .name(producer.getName())
                .build();
    }
    public Producer toEntity(ProducerDto producerDto){
        return new Producer(producerDto.getId(), producerDto.getName());
    }
}
