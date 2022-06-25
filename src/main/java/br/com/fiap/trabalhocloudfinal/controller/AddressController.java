package br.com.fiap.trabalhocloudfinal.controller;

import br.com.fiap.trabalhocloudfinal.domain.Address;
import br.com.fiap.trabalhocloudfinal.request.AddressRequest;
import br.com.fiap.trabalhocloudfinal.response.AddressResponse;
import br.com.fiap.trabalhocloudfinal.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/address")
public class AddressController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddressController.class);
	
	private final AddressService addressService;

	@Autowired
	public AddressController(final AddressService addressService) {
		this.addressService = addressService;
	}

	@GetMapping(path = "/{zipcode}")
	public ResponseEntity<?> find(@PathVariable("zipcode") String zipcode) {

    	LOGGER.info("stage=init method=AddressController.find "
				+ "Message=Start Find Address request: {} ", zipcode);
    	
		Address address = addressService.findByZipcode(zipcode);

    	LOGGER.info("stage=end method=AddressController.find "
				+ "Message=Finish Find Address response: {} ", address);
    	
		return ResponseEntity.ok().body(address);
		
	}

	@PostMapping(path = "/{document}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@PathVariable(value = "document", required = true) String document,
			@Valid @RequestBody AddressRequest request) {

    	LOGGER.info("stage=init method=AddressController.create "
				+ "Message=Start Create Address request:{} document:{}", request, document);
    	
		AddressResponse addressResponse = addressService.createAddress(request, document);

		LOGGER.info("stage=init method=AddressController.create "
				+ "Message=Start Create Address response:{}", addressResponse);
		
		return new ResponseEntity<>(addressResponse, (HttpStatus.CREATED));
	}
}
