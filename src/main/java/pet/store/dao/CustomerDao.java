package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pet.store.entity.Customer;

/*The CustomerDao interface is responsible for interacting with the underlying 
 * database to perform CRUD operations on the Customer entity.
 * 
 */

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
