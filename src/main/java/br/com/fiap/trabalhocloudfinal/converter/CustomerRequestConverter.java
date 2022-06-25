package br.com.fiap.trabalhocloudfinal.converter;

import br.com.fiap.trabalhocloudfinal.domain.Address;
import br.com.fiap.trabalhocloudfinal.domain.Customer;
import br.com.fiap.trabalhocloudfinal.enums.Country;
import br.com.fiap.trabalhocloudfinal.enums.CustomerStatus;
import br.com.fiap.trabalhocloudfinal.enums.Gender;
import br.com.fiap.trabalhocloudfinal.enums.State;
import br.com.fiap.trabalhocloudfinal.request.AddressRequest;
import br.com.fiap.trabalhocloudfinal.request.CustomerRequest;
import br.com.fiap.trabalhocloudfinal.util.UtilDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CustomerRequestConverter  implements Function<CustomerRequest, Customer> {

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

        List<AddressRequest> addressRequest = customerRequest.getAddress().stream().distinct().collect(Collectors.toList());

        for (AddressRequest addresses: addressRequest) {

            Address addressItem = new Address();

            addressItem.setCustomer(customerNew);
            addressItem.setZipcode(addresses.getZipcode());
            addressItem.setStreet(addresses.getStreet());
            addressItem.setNumber(addresses.getNumber());
            addressItem.setComplement(addresses.getComplement());
            addressItem.setNeighborhood(addresses.getNeighborhood());
            addressItem.setCity(addresses.getCity());
            addressItem.setState(State.valueOf(addresses.getState()));
            addressItem.setCountry(Country.valueOf(addresses.getCountry()));

            addressList.add(addressItem);

        }
        customerNew.setAddresses(addressList);

        return customerNew;
    }
}