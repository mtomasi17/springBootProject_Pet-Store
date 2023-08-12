package pet.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.service.PetStoreService;

/*The PetStoreController class is a REST controller responsible for handling 
 * HTTP requests related to pet stores. It receives incoming requests, processes 
 * them, and returns appropriate responses. The controller interacts with the 
 * PetStoreService to perform CRUD operations on the pet store data.
 */


/*Changes made for Week 15 pet-store project include addEmployeeToStore Method (POST).
 *  This method is used to add an employee to a pet store.  The saveCustomer Method (POST)
 *  is used to add a customer to a pet store.  The getAllPetStores Method (GET) is used
 *  to retrieve a list of all pet stores' summary data.  The getPetStoreById Method (GET) 
 *  is used to retrieve detailed information about a specific pet store by its ID.  The 
 *  deletePetStore Method (DELETE) is used to delete a pet store by its ID.
 * 
 */
@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
	@Autowired
	private PetStoreService petStoreService;
	
	@Autowired
	public PetStoreController(PetStoreService petStoreService) {
		this.petStoreService = petStoreService;
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertPetStore(@RequestBody PetStoreData petStoreData) {
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
	
	@PostMapping("/{petStoreId}/employee")
	@ResponseStatus(HttpStatus.CREATED)
	public PetStoreEmployee addEmployeeToStore(@PathVariable Long petStoreId, 
			@RequestBody PetStoreEmployee employee) {
		log.info("Adding employee to pet store with ID: {}", petStoreId);
		
		return petStoreService.saveEmployee(petStoreId, employee);
	}
	
	@PostMapping("/{petStoreId}/customer")
	@ResponseStatus(HttpStatus.CREATED)
	public PetStoreCustomer saveCustomer(
			@PathVariable Long petStoreId, 
			@RequestBody PetStoreCustomer petStoreCustomer) {
		log.info("Adding customer to pet store with ID: {}", petStoreId);
		
		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
		
	}
	@GetMapping
	public List<PetStoreData> getAllPetStores(){
		log.info("Retrieving all pet stores");
		return petStoreService.retrieveAllPetStores();
	}
	@GetMapping("/{petStoreId}")
	public PetStoreData getPetStoreById(@PathVariable Long petStoreId) {
		log.info("Retrieving pet store with ID: {}", petStoreId);
		return petStoreService.getPetStoreById(petStoreId);
	}
	
	@DeleteMapping("/{petStoreId}")
	public Map<String, String> deletePetStore(@PathVariable Long petStoreId){
		log.info("Deleting pet store with ID: {}", petStoreId);
		
		petStoreService.deletePetStoreById(petStoreId);
		
		Map<String, String> response = new HashMap<>();
		response.put("Message", "Pet store deleted successfully");
		return response;
	}

}
