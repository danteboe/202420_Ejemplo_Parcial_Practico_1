package co.edu.uniandes.dse.parcialprueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EspecialidadService {
    
    @Autowired
    EspecialidadRepository especialidadRepository;

    @Transactional
    public EspecialidadEntity createEspecialidad(EspecialidadEntity especialidadEntity)throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia proceso de creación de especialidad");

        if(especialidadEntity.getNombre()==null){
            throw new IllegalOperationException("El nombre de la especialidad no puede ser nulo");
        }

        if(!validDescripcion(especialidadEntity.getDescripcion())){
            throw new IllegalOperationException("La descripción no es válida");
        }

        log.info("Termina proceso de creación de especialidad");
        return especialidadRepository.save(especialidadEntity);

    }

    public boolean validDescripcion(String descripString){
        if(descripString==null){
            return false;
        }
        if(descripString.length()<10){
            return false;
        }
        return true;
    }
}
