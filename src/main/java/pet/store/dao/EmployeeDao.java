package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.store.entity.Employee;


/*This interface is responsible for interacting
 * with the underlying database to perform CRUD 
 * (Create, Read, Update, Delete) operations on 
 * the Employee entity
 */

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
