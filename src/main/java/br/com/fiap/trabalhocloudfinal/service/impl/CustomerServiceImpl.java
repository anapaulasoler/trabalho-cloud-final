package br.com.fiap.trabalhocloudfinal.service.impl;

import br.com.fiap.trabalhocloudfinal.converter.CustomerRequestConverter;
import br.com.fiap.trabalhocloudfinal.converter.CustomerResponseConverter;
import br.com.fiap.trabalhocloudfinal.domain.Address;
import br.com.fiap.trabalhocloudfinal.domain.Customer;
import br.com.fiap.trabalhocloudfinal.enums.Country;
import br.com.fiap.trabalhocloudfinal.enums.State;
import br.com.fiap.trabalhocloudfinal.repository.CustomerRepository;
import br.com.fiap.trabalhocloudfinal.request.AddressRequest;
import br.com.fiap.trabalhocloudfinal.request.CustomerRequest;
import br.com.fiap.trabalhocloudfinal.response.AddressResponse;
import br.com.fiap.trabalhocloudfinal.response.CustomerResponse;
import br.com.fiap.trabalhocloudfinal.service.AddressService;
import br.com.fiap.trabalhocloudfinal.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;
    private final CustomerResponseConverter customerResponseConverter;
    private final CustomerRequestConverter customerRequestConverter;
    private final AddressService addressService;

    @Autowired
    public CustomerServiceImpl(final CustomerRepository customerRepository,
                               final CustomerResponseConverter customerResponseConverter,
                               final CustomerRequestConverter customerRequestConverter, AddressService addressService) {
        this.customerRepository = customerRepository;
        this.customerResponseConverter = customerResponseConverter;
        this.customerRequestConverter = customerRequestConverter;
        this.addressService = addressService;
    }


    @Override
    public CustomerResponse create(CustomerRequest request) throws IOException {

        findAddressByZipcode(request);
        Customer customer = customerRequestConverter.apply(request);
        customerRepository.save(customer);

        CustomerResponse response = customerResponseConverter.apply(customer);

        return response;
    }

    @Override
    public Customer findByDocument(String document) {
        return null;
    }

    private CustomerRequest findAddressByZipcode(CustomerRequest request) throws IOException {

       Address adr = addressService.findByZipcodeByViaCep(request.getAddress().getZipcode());

            AddressRequest addressItem = new AddressRequest();

            addressItem.setZipcode(adr.getZipcode());
            addressItem.setStreet(adr.getStreet());
            addressItem.setNumber(adr.getNumber());
            addressItem.setComplement(adr.getComplement());
            addressItem.setNeighborhood(adr.getNeighborhood());
            addressItem.setCity(adr.getCity());
            addressItem.setState(adr.getState().name());
            addressItem.setCountry(String.valueOf(Country.BR));

        request.setAddress(addressItem);

        return request;
    }

}
