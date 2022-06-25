package br.com.fiap.trabalhocloudfinal.controller;

import br.com.fiap.trabalhocloudfinal.domain.Address;
import br.com.fiap.trabalhocloudfinal.domain.Customer;
import br.com.fiap.trabalhocloudfinal.request.CustomerRequest;
import br.com.fiap.trabalhocloudfinal.response.CustomerResponse;
import br.com.fiap.trabalhocloudfinal.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customer")
@Validated
public class CustomerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	
    private final CustomerService customerService;

    @Autowired
    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody CustomerRequest request) {

    	LOGGER.info("stage=init method=CustomerController.create "
				+ "Message=Start Create Customer request: {} ", request);
    	
        CustomerResponse customerResponse = customerService.create(request);

    	LOGGER.info("stage=end method=CustomerController.create "
				+ "Message=Finish Create Customer response: {} ", customerResponse);
    	
        return new ResponseEntity<>(customerResponse, (HttpStatus.CREATED));
    }

    @GetMapping(path = "/find/{document}")
    public ResponseEntity<?> find(@PathVariable("document") String document) {

        LOGGER.info("stage=init method=AddressController.find "
                + "Message=Start Find Address request: {} ", document);

        Customer customer = customerService.findByDocument(document);

        LOGGER.info("stage=end method=AddressController.find "
                + "Message=Finish Find Address response: {} ", document);

        return ResponseEntity.ok().body(customer);

    }

}
