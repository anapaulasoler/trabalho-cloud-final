package br.com.fiap.trabalhocloudfinal.service.impl;

import br.com.fiap.trabalhocloudfinal.converter.CustomerRequestConverter;
import br.com.fiap.trabalhocloudfinal.converter.CustomerResponseConverter;
import br.com.fiap.trabalhocloudfinal.converter.CustomerUpdateRequestConverter;
import br.com.fiap.trabalhocloudfinal.domain.Address;
import br.com.fiap.trabalhocloudfinal.domain.Customer;
import br.com.fiap.trabalhocloudfinal.enums.Country;
import br.com.fiap.trabalhocloudfinal.exceptions.ObjectNotFoundException;
import br.com.fiap.trabalhocloudfinal.repository.CustomerRepository;
import br.com.fiap.trabalhocloudfinal.request.AddressRequest;
import br.com.fiap.trabalhocloudfinal.request.CustomerRequest;
import br.com.fiap.trabalhocloudfinal.request.CustomerUpdateRequest;
import br.com.fiap.trabalhocloudfinal.response.CustomerResponse;
import br.com.fiap.trabalhocloudfinal.service.AddressService;
import br.com.fiap.trabalhocloudfinal.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;
    private final CustomerResponseConverter customerResponseConverter;
    private final CustomerRequestConverter customerRequestConverter;
    private final AddressService addressService;
    private final CustomerUpdateRequestConverter customerUpdateRequestConverter;


    @Autowired
    public CustomerServiceImpl(final CustomerRepository customerRepository,
                               final CustomerResponseConverter customerResponseConverter,
                               final CustomerRequestConverter customerRequestConverter, AddressService addressService,
                               final CustomerUpdateRequestConverter customerUpdateRequestConverter) {
        this.customerRepository = customerRepository;
        this.customerResponseConverter = customerResponseConverter;
        this.customerRequestConverter = customerRequestConverter;
        this.addressService = addressService;
        this.customerUpdateRequestConverter = customerUpdateRequestConverter;
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
    public CustomerResponse findByDocument(String document) {

        Customer customer = customerRepository.findByDocument(document);

        if (Objects.isNull(customer)) {
            throw new ObjectNotFoundException("Cliente não encontrado!");
        }

        CustomerResponse response = customerResponseConverter.apply(customer);
        return response;
    }

    public void deleteByDocument(String document) {

        Customer customer = customerRepository.findByDocument(document);

        if (Objects.isNull(customer)) {
            throw new ObjectNotFoundException("Cliente não encontrado!");
        }
        customerRepository.deleteAll();
    }

    @Override
    public CustomerResponse update(String document, CustomerUpdateRequest request) {

        Customer result = customerRepository.findByDocument(document);

        Customer customer = customerUpdateRequestConverter.apply(request,result);

        customerRepository.save(customer);

        CustomerResponse response = customerResponseConverter.apply(customer);

        return response;

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
