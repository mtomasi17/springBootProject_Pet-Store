package pet.store.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

/*The PetStoreService class is a service class responsible for managing 
 * business logic related to pet stores. It interacts with the PetStoreDao 
 * repository to perform CRUD operations on the PetStore entity and uses the PetStoreData 
 * DTO for data transfer between the controller and the service layer.
 * 
 */

@Service
public class PetStoreService {
	private final PetStoreDao petStoreDao;
	
	@Autowired
	public PetStoreService(PetStoreDao petStoreDao) {
		this.petStoreDao = petStoreDao;
	}

	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);
		
		copyPetStoreFields(petStore, petStoreData);
		
		PetStore savedPetStore = petStoreDao.save(petStore);
		
		return new PetStoreData(savedPetStore);
	
	}

	private PetStore findOrCreatePetStore(Long petStoreId) {
		if (petStoreId == null) {
			return new PetStore();
		} else {
			return petStoreDao.findById(petStoreId)
                    .orElseThrow(() -> new NoSuchElementException("Pet store not found with ID: " + petStoreId));
        }
	}
		

	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
        petStore.setPetStoreName(petStoreData.getPetStoreName());
        petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
        petStore.setPetStoreCity(petStoreData.getPetStoreCity());
        petStore.setPetStoreState(petStoreData.getPetStoreState());
        petStore.setPetStoreZip(petStoreData.getPetStoreZip());
        petStore.setPetStorePhone(petStoreData.getPetStorePhone());


	}
	

}
