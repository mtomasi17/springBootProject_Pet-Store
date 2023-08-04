package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.store.entity.PetStore;

/*The PetStoreDao interface is a repository interface provided by 
 * Spring JPA. It extends the JpaRepository interface, allowing easy 
 * interaction with the database for the PetStore entity.
 * 
 */

@Repository
public interface PetStoreDao extends JpaRepository<PetStore, Long> {

}
