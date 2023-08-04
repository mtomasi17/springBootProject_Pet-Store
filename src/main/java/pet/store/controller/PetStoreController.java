package pet.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.service.PetStoreService;

/*The PetStoreController class is a REST controller responsible for handling 
 * HTTP requests related to pet stores. It receives incoming requests, processes 
 * them, and returns appropriate responses. The controller interacts with the 
 * PetStoreService to perform CRUD operations on the pet store data.
 */

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
	
	private final PetStoreService petStoreService;
	
	@Autowired
	public PetStoreController(PetStoreService petStoreService) {
		this.petStoreService = petStoreService;
	}
	
	@PostMapping
	public PetStoreData createPetStore(@RequestBody PetStoreData petStoreData) {
		log.info("Received request to create a pet store: {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	@PutMapping("/{petStoreId}")
	public PetStoreData updatePetStore(@PathVariable Long petStoreId, 
			@RequestBody PetStoreData petStoreData) {
		log.info("Updating pet store with ID: {}", petStoreId);
		
		petStoreData.setPetStoreId(petStoreId);
		
		return petStoreService.savePetStore(petStoreData);
	}

}
