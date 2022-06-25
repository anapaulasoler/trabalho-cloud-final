package br.com.fiap.trabalhocloudfinal.converter;

import br.com.fiap.trabalhocloudfinal.domain.Address;
import br.com.fiap.trabalhocloudfinal.response.AddressResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class AddressResponseConverter implements Function<Address, AddressResponse> {

    @Override
    public AddressResponse apply(Address address) {

        AddressResponse addressResponseResult = new AddressResponse();

        addressResponseResult.withNumber(address.getNumber());
        addressResponseResult.withZipcode(address.getZipcode());
        addressResponseResult.withStreet(address.getStreet());

        return addressResponseResult;
    }
}
