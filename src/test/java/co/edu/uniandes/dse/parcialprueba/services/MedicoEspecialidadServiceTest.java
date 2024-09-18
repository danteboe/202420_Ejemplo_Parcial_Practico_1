package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MedicoEspecialidadService.class)
public class MedicoEspecialidadServiceTest {
    
    @Autowired
    private MedicoEspecialidadService medicoEspecialidadService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

   
    @Test
    void testAddEspecialidad() throws EntityNotFoundException, IllegalOperationException{

        MedicoEntity newMedico = factory.manufacturePojo(MedicoEntity.class);
        newMedico.setRegistroMedico("RM"+newMedico.getRegistroMedico());
        entityManager.persist(newMedico);
        
        EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
        entityManager.persist(especialidad);

        medicoEspecialidadService.addEspecialidad(newMedico.getId(), especialidad.getId());
        
        EspecialidadEntity lastEspecialidad = medicoEspecialidadService.getEspecialidad(newMedico.getId(), especialidad.getId());
        assertEquals(especialidad, lastEspecialidad);
        assertEquals(especialidad.getDescripcion(), lastEspecialidad.getDescripcion());
        assertEquals(especialidad.getNombre(), lastEspecialidad.getNombre());

    }

    @Test
	void testAddInvalidEspecialidad() {
		assertThrows(EntityNotFoundException.class, ()->{
			MedicoEntity newmedico = factory.manufacturePojo(MedicoEntity.class);
			entityManager.persist(newmedico);
			medicoEspecialidadService.addEspecialidad(newmedico.getId(), 0L);
		});
	}

    @Test
	void testAddespecialidadInvalidMedico() {
		assertThrows(EntityNotFoundException.class, ()->{
			EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
			entityManager.persist(especialidad);
			medicoEspecialidadService.addEspecialidad(0L, especialidad.getId());
		});
	}
}
