package br.com.fiap.trabalhocloudfinal.converter;

import br.com.fiap.trabalhocloudfinal.domain.Address;
import br.com.fiap.trabalhocloudfinal.domain.Customer;
import br.com.fiap.trabalhocloudfinal.enums.Country;
import br.com.fiap.trabalhocloudfinal.enums.CustomerStatus;
import br.com.fiap.trabalhocloudfinal.enums.Gender;
import br.com.fiap.trabalhocloudfinal.enums.State;
import br.com.fiap.trabalhocloudfinal.request.CustomerRequest;
import br.com.fiap.trabalhocloudfinal.util.UtilDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class CustomerRequestConverter implements Function<CustomerRequest, Customer> {

    @Autowired
    private UtilDateFormat utilDateFormat;

    @Override
    public Customer apply(CustomerRequest customerRequest) {

        Customer customerNew = new Customer();
        customerNew.setDocument(customerRequest.getDocument());
        customerNew.setGender(Gender.valueOf(customerRequest.getGender().name()));
        customerNew.setEmail(customerRequest.getEmail());
        customerNew.setFirstName(customerRequest.getFirstName());
        customerNew.setLastName(customerRequest.getLastName());
        customerNew.setFullName(customerRequest.getFullName());
        customerNew.setBirthDate(utilDateFormat.dateConverter(customerRequest.getBirthDate()));

        customerNew.setStatus(CustomerStatus.ACTIVE);

        List<Address> addressList = new ArrayList<>();

        Address addressItem = new Address();

        addressItem.setCustomer(customerNew);
        addressItem.setZipcode(customerRequest.getAddress().getZipcode());
        addressItem.setZipcode(customerRequest.getAddress().getZipcode());
        addressItem.setStreet(customerRequest.getAddress().getStreet());
        addressItem.setNumber(customerRequest.getAddress().getNumber());
        addressItem.setComplement(customerRequest.getAddress().getComplement());
        addressItem.setNeighborhood(customerRequest.getAddress().getNeighborhood());
        addressItem.setCity(customerRequest.getAddress().getCity());
        addressItem.setState(State.valueOf(customerRequest.getAddress().getState()));
        addressItem.setCountry(Country.valueOf(customerRequest.getAddress().getCountry()));

        addressList.add(addressItem);
        customerNew.setAddresses(addressList);

        return customerNew;
    }
}