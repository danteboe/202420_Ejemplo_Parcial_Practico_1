package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoEspecialidadService {
    
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Transactional
    public MedicoEntity addEspecialidad(Long medicoId, Long especialidadId) throws EntityNotFoundException{
        log.info("Inicia proceso de agregar especialidad a medico");

        Optional<MedicoEntity> medicoEntity = medicoRepository.findById(medicoId);
        if(medicoEntity.isEmpty()){
            throw new EntityNotFoundException("No se encontró el medico con id: "+medicoId);
        }

        Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(especialidadId);
        if(especialidadEntity.isEmpty()){
            throw new EntityNotFoundException("No se encontró la especialidad con id: "+especialidadId);
        }

        medicoEntity.get().getEspecialidades().add(especialidadEntity.get() );
        log.info("Termina proceso de agregar especialidad a medico");
        return medicoEntity.get();
    }

    @Transactional
	public EspecialidadEntity getEspecialidad(Long medicoId, Long especialidadId)
			throws EntityNotFoundException, IllegalOperationException {
		Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(especialidadId);
		Optional<MedicoEntity> medicoEntity = medicoRepository.findById(medicoId);

		if (especialidadEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la esp");

		if (medicoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el med");
		if (!medicoEntity.get().getEspecialidades().contains(especialidadEntity.get()))
			throw new IllegalOperationException("The especialidad is not associated to the medico");
		
		return especialidadEntity.get();
	}
}
