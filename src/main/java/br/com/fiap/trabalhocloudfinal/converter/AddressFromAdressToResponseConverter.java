package br.com.fiap.trabalhocloudfinal.converter;

import br.com.fiap.trabalhocloudfinal.domain.Address;
import br.com.fiap.trabalhocloudfinal.domain.AddressTo;
import br.com.fiap.trabalhocloudfinal.enums.State;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AddressFromAdressToResponseConverter implements Function<AddressTo, Address> {

    @Override
    public Address apply(AddressTo addressTO) {

        Address addressResponseResult = new Address();


        addressResponseResult.setZipcode(addressTO.getCep());
        addressResponseResult.setStreet(addressTO.getLogradouro());
        addressResponseResult.setComplement(addressTO.getComplemento());
        addressResponseResult.setNumber(addressTO.getNumero());
        addressResponseResult.setNeighborhood(addressTO.getBairro());
        addressResponseResult.setCity(addressTO.getLocalidade());
        addressResponseResult.setState(State.valueOf(addressTO.getUf()));

        return addressResponseResult;
    }
}
