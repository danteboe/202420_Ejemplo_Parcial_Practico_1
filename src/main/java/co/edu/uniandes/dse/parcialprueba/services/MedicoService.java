package co.edu.uniandes.dse.parcialprueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoService {
    
    @Autowired
    MedicoRepository medicoRepository;

    @Transactional
    public MedicoEntity createMedicos(MedicoEntity medicoEntity)throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia proceso de creación de medico");

        if(medicoEntity.getNombre()==null){
            throw new IllegalOperationException("El nombre del medico no puede ser nulo");
        }

        if(medicoEntity.getApellido()==null){
            throw new IllegalOperationException("El apellido del medico no puede ser nulo");
        }

        if(!validRegistroMedico(medicoEntity.getRegistroMedico())){
            throw new IllegalOperationException("El registro medico no es valido");
        }

        log.info("Termina proceso de creación de medico");
        return medicoRepository.save(medicoEntity);

    }

    public boolean validRegistroMedico(String registroMedico){
        if(registroMedico==null){
            return false;
        }
        if(!registroMedico.startsWith("RM")){
            return false;
        }
        return true;
    }
}
