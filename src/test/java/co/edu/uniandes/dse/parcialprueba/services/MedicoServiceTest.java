package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.*;


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
@Import(MedicoService.class)
public class MedicoServiceTest {
    
    @Autowired
    private MedicoService medicoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData(){
        entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EspecialidadEntity").executeUpdate();

    }

    private void insertData(){

        EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
        entityManager.persist(especialidadEntity);
        
    }

    @Test
    void testCreateMedico() throws EntityNotFoundException, IllegalOperationException{
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        newEntity.setRegistroMedico("RM"+newEntity.getRegistroMedico());
        MedicoEntity result = medicoService.createMedicos(newEntity);    
        assertNotNull(result);
        MedicoEntity entity = entityManager.find(MedicoEntity.class, result.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getApellido(), entity.getApellido());
        assertEquals(newEntity.getRegistroMedico(), entity.getRegistroMedico());
    }

    @Test
    void testCreateMedicoInvalidRegistroMedico() throws EntityNotFoundException, IllegalOperationException{
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        newEntity.setRegistroMedico("1234");
        assertThrows(IllegalOperationException.class, ()->medicoService.createMedicos(newEntity));
    }

    
}
