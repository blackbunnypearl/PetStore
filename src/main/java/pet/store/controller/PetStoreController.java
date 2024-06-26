package pet.store.controller;

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

@RestController // this annotation tells Spring that this class is a REST controller. This means
				// that the class will handle HTTP requests and return JSON responses. The
				// default
				// response status code is 200 (OK), but we can specify a different status code
				// if needed. The @RestController annotation also tells Spring to take care of
				// mapping the requests to the appropriate methods in this class and returning
				// JSON responses.
@RequestMapping("/") // this annotation tells Spring that the URI for every HTTP request that is
								// mapped to a method in this controller class must start with "/pet_store".
@Slf4j // This is a Lombok annotation that creates an SLF4J logger. This logger is
		// named "log" and can be used in the class to print log messages easily.
public class PetStoreController {

	@Autowired
	private PetStoreService petStoreService;

	/*
	 * Create a public method that maps an HTTP POST request to "/pet_store". The
	 * response status should be 201 (Created). Pass the contents of the request
	 * body as a parameter (type PetStoreData) to the method. (Use @RequestBody.)
	 * The method should return a PetStoreData object. Log the request. Call a
	 * method in the service class (savePetStore) that will insert or modify the pet
	 * store data.
	 */
	@PostMapping("/pet_store")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertPetStore(@RequestBody PetStoreData petStoreData) {
		log.info("Create Pet Store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	//modify the pet store object
	@PutMapping("/pet_store/{petStoreId}")
	public PetStoreData updatePetStore(@PathVariable Long petStoreId,
			@RequestBody PetStoreData petStoreData) {
		//Set the pet store ID in the pet store data from the ID parameter.
		petStoreData.setPetStoreId(petStoreId);
		log.info("Updating pet store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	//add a employee to an existing pet store
	@PostMapping("/pet_store/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)//return a 201 (Created) status code
	public PetStoreEmployee addEmployee(@PathVariable Long petStoreId,@RequestBody PetStoreEmployee petStoreEmployee) {
		log.info("Add Employee {} to the pet store with Id {}", petStoreEmployee,petStoreId);
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
	}
	
	//add a customer to an existing pet store
	@PostMapping("/pet_store/{petStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)//return a 201 (Created) status code
	public PetStoreCustomer addCustomer(@PathVariable Long petStoreId,@RequestBody PetStoreCustomer petStoreCustomer) {
		log.info("Add Customer {} to the pet store with Id {}", petStoreCustomer, petStoreId);
		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	}

	//Get list of pet stores
	@GetMapping("/pet_store")
	public List<PetStoreData> getAllPetStores(){
		return petStoreService.retrieveAllPetStores();
	}
	
	//Get pet store with specific id
	@GetMapping("/pet_store/{petStoreId}")
	public PetStoreData retrievePetStoreWithId(@PathVariable Long petStoreId){
		return petStoreService.retrievePetStoreWithID(petStoreId);
	}
	
	//Delete Request by petStoreId
	@DeleteMapping("/pet_store/{petStoreId}")
	public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
		log.info("Deleting pet store with Id={}", petStoreId);

		petStoreService.deletePetStoreById(petStoreId);

		return Map.of("message", "Deletion of pet store with Id=" + petStoreId + " was successful.");

	}
	
	
}