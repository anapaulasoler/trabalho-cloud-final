package br.com.fiap.trabalhocloudfinal.converter;

import br.com.fiap.trabalhocloudfinal.domain.Address;
import br.com.fiap.trabalhocloudfinal.domain.Customer;
import br.com.fiap.trabalhocloudfinal.response.AddressResponse;
import br.com.fiap.trabalhocloudfinal.response.CustomerResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class CustomerResponseConverter implements Function<Customer, CustomerResponse> {

    @Override
    public CustomerResponse apply(Customer customer) {

        CustomerResponse result = new CustomerResponse();

        result.withDocument(customer.getDocument());
        result.withFirstName(customer.getFirstName());
        result.withLastName(customer.getLastName());
        result.withAddressResponseList(generateAddressList(customer.getAddresses()));

        return result;
    }

    private List<AddressResponse> generateAddressList(List<Address> addressList) {


        List<AddressResponse> addressResponseList = new ArrayList<>();

        for (Address address : addressList) {

            AddressResponse response = new AddressResponse();

            response.setNumber(address.getNumber());
            response.setStreet(address.getStreet());
            response.setZipcode(address.getZipcode());
            addressResponseList.add(response);
        }
        return addressResponseList;
    }
}
