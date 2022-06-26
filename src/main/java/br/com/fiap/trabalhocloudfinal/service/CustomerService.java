package br.com.fiap.trabalhocloudfinal.service;


import br.com.fiap.trabalhocloudfinal.domain.Address;
import br.com.fiap.trabalhocloudfinal.domain.Customer;
import br.com.fiap.trabalhocloudfinal.request.CustomerRequest;
import br.com.fiap.trabalhocloudfinal.response.CustomerResponse;

import java.io.IOException;

public interface CustomerService {

    CustomerResponse create(CustomerRequest request ) throws IOException;

    Customer findByDocument(String document);
}
