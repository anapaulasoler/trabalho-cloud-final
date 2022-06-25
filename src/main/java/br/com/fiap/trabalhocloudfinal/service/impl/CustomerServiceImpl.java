package br.com.fiap.trabalhocloudfinal.service.impl;

import br.com.fiap.trabalhocloudfinal.converter.CustomerRequestConverter;
import br.com.fiap.trabalhocloudfinal.converter.CustomerResponseConverter;
import br.com.fiap.trabalhocloudfinal.domain.Customer;
import br.com.fiap.trabalhocloudfinal.repository.CustomerRepository;
import br.com.fiap.trabalhocloudfinal.request.CustomerRequest;
import br.com.fiap.trabalhocloudfinal.response.CustomerResponse;
import br.com.fiap.trabalhocloudfinal.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;
    private final CustomerResponseConverter customerResponseConverter;
    private final CustomerRequestConverter customerRequestConverter;

    @Autowired
    public CustomerServiceImpl(final CustomerRepository customerRepository,
                               final CustomerResponseConverter customerResponseConverter,
                               final CustomerRequestConverter customerRequestConverter) {
        this.customerRepository = customerRepository;
        this.customerResponseConverter = customerResponseConverter;
        this.customerRequestConverter = customerRequestConverter;
    }


    @Override
    public CustomerResponse create(CustomerRequest request) {

        Customer customer = customerRequestConverter.apply(request);
        customerRepository.save(customer);
        CustomerResponse response = customerResponseConverter.apply(customer);

        return response;
    }

    @Override
    public Customer findByDocument(String document) {
        return null;
    }


}
