package org.example.autoriaclone.service.entityServices;

import lombok.Data;
import org.example.autoriaclone.dto.ModelDto;
import org.example.autoriaclone.entity.Model;
import org.example.autoriaclone.entity.Producer;
import org.example.autoriaclone.mapper.ModelMapper;
import org.example.autoriaclone.mapper.ProducerMapper;
import org.example.autoriaclone.repository.ModelRepository;
import org.example.autoriaclone.repository.ProducerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class ModelService {
    public final ModelRepository modelRepository;
    public final ProducerRepository producerRepository;
    private final ModelMapper modelMapper;
    private final ProducerMapper producerMapper;

    public ModelDto addModel(Integer id, ModelDto model){
        Producer producer = producerRepository.findById(id).get();
        List<Model> models = producer.getModels();

        if (models.stream().map(Model::getName).toList().contains(model.getName())){
            return ModelDto.builder().error("This model is already added").build();
        }
        models.add(modelMapper.toEntity(model));
        producer.setModels(models);
        Producer save = producerRepository.save(producer);

        Model addedModel = save.getModels().get(save.getModels().size() - 1);
        addedModel.setProducer(new Producer().setName(save.getName()).setId(save.getId()));
        return modelMapper.toDto(addedModel);
    }

    public List<ModelDto> findAllModels(){
        return modelRepository.findAll().stream().map(modelMapper::toDto).toList();
    }
}
