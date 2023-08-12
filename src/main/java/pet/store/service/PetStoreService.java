package pet.store.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;
/*The PetStoreService class is a service class responsible for managing 
 * business logic related to pet stores. It interacts with the PetStoreDao 
 * repository to perform CRUD operations on the PetStore entity and uses the PetStoreData 
 * DTO for data transfer between the controller and the service layer.
 * 
 */



@Service
public class PetStoreService {
	@Autowired
	private PetStoreDao petStoreDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private CustomerDao customerDao;
	
	
	@Autowired
	public PetStoreService(PetStoreDao petStoreDao, EmployeeDao employeeDao) {
		this.petStoreDao = petStoreDao;
		this.employeeDao = employeeDao;
		
	}
	@Transactional
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);
		
		copyPetStoreFields(petStore, petStoreData);
		
		
		return new PetStoreData(petStoreDao.save(petStore));
	
	}

	private PetStore findOrCreatePetStore(Long petStoreId) {
		if (Objects.isNull(petStoreId)) {
			return new PetStore();
		} else {
			return findPetStoreById(petStoreId);
        }
	}
		

	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException(
						"Pet store with ID=" + petStoreId + " was not found."));
	}
	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
        petStore.setPetStoreName(petStoreData.getPetStoreName());
        petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
        petStore.setPetStoreCity(petStoreData.getPetStoreCity());
        petStore.setPetStoreState(petStoreData.getPetStoreState());
        petStore.setPetStoreZip(petStoreData.getPetStoreZip());
        petStore.setPetStorePhone(petStoreData.getPetStorePhone());


	}
	
	/*The updates for Week 15 of the PetStoreService.java include the following methods:
	 * the saveEmployee method involve creating or retrieving entities from the database, 
	 * copying relevant fields, and then saving the changes.  The method saveCustomer handles
	 * the relationship between customers and pet stores. The method retrieveAllPetStores
	 * uses the petStoreDao.findAll() method to retrieve all pet store entities from the database
	 * The convertToPetStoreData method is used to convert a PetStore entity into a PetStoreData 
	 * DTO.  The method getPetStoreById retrieves a single pet store by its ID, converts it into 
	 * a PetStoreData DTO(Data Transfer Object), and returns it.  The method deletePetStoreById is 
	 * responsible for deleting a pet store.
	 */
	
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		PetStore petStore = findPetStoreById(petStoreId);
		Employee employee = findOrCreateEmployee(petStoreId, petStoreEmployee.getEmployeeId());
		
		copyEmployeeFields(employee, petStoreEmployee);
		
		employee.setPetStore(petStore);
		petStore.getEmployees().add(employee);
		
		Employee savedEmployee = employeeDao.save(employee);
		
		return new PetStoreEmployee(savedEmployee);
	}
	

	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
        if (employeeId == null) {
            return new Employee();
        } else {
            return findEmployeeById(petStoreId, employeeId);
        }
    }

    private Employee findEmployeeById(Long petStoreId, Long employeeId) {
        Employee employee = employeeDao.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + employeeId));

        if (!employee.getPetStore().getPetStoreId().equals(petStoreId)) {
            throw new IllegalArgumentException("Employee with ID " + employeeId +
                    " does not belong to pet store with ID " + petStoreId);
        }

        return employee;
    }

    private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
        employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
        employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
        employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
        employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
    }
    @Transactional
    public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
        PetStore petStore = findPetStoreById(petStoreId);
        Customer customer = findOrCreateCustomer(petStoreId, petStoreCustomer.getCustomerId());

        copyCustomerFields(customer, petStoreCustomer);

        customer.getPetStores().add(petStore);
        petStore.getCustomers().add(customer);

        Customer savedCustomer = customerDao.save(customer);

        return new PetStoreCustomer(savedCustomer);
    }

    private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
        if (customerId == null) {
            return new Customer();
        } else {
            return findCustomerById(petStoreId, customerId);
        }
    }

    private Customer findCustomerById(Long petStoreId, Long customerId) {
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer not found with ID: " + customerId));

        for (PetStore petStore : customer.getPetStores()) {
            if (petStore.getPetStoreId().equals(petStoreId)) {
                return customer;
            }
        }

        throw new IllegalArgumentException("Customer with ID " + customerId +
                " is not associated with pet store with ID " + petStoreId);
    }

    private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
        customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
        customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
        customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
    }
    @Transactional
    public List<PetStoreData> retrieveAllPetStores() {
        return petStoreDao.findAll().stream()
                .map(this::convertToPetStoreData)
                .collect(Collectors.toList());
	}
    
    private PetStoreData convertToPetStoreData(PetStore petStore) {
        PetStoreData petStoreData = new PetStoreData();
        petStoreData.setPetStoreId(petStore.getPetStoreId());
        petStoreData.setPetStoreName(petStore.getPetStoreName());
        petStoreData.setPetStoreAddress(petStore.getPetStoreAddress());
        petStoreData.setPetStoreCity(petStore.getPetStoreCity());
        petStoreData.setPetStoreState(petStore.getPetStoreState());
        petStoreData.setPetStoreZip(petStore.getPetStoreZip());
        petStoreData.setPetStorePhone(petStore.getPetStorePhone());
        return petStoreData;
    }
    @Transactional
	public PetStoreData getPetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		return convertToPetStoreData(petStore);
	}
    
    @Transactional
	public void deletePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		
		for(Employee employee : petStore.getEmployees()) {
			employeeDao.delete(employee);
		}
		
		for(Customer customer : petStore.getCustomers()) {
			customer.getPetStores().remove(petStore);
		}
		
		petStoreDao.delete(petStore);
		
	}

}
   
