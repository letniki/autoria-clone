package org.example.autoriaclone.service.entityServices;

import lombok.Data;
import org.example.autoriaclone.dto.ProducerDto;
import org.example.autoriaclone.entity.Producer;
import org.example.autoriaclone.mapper.ProducerMapper;
import org.example.autoriaclone.repository.ProducerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class ProducerService {
    private final ProducerRepository producerRepository;
    private final ProducerMapper producerMapper;
    public ProducerDto addProducer(ProducerDto producerDto){
        Producer foundProducer = producerRepository.findProducerByName(producerDto.getName());
        if(foundProducer!=null){
            return ProducerDto.builder().error("Producer with this name already exists").build();
        }
        return producerMapper.toDto(producerRepository.save(producerMapper.toEntity(producerDto)));
    }
    public List<ProducerDto> findAllProducers(){
        return producerRepository.findAll().stream().map(producerMapper::toDto).toList();
    }
}
