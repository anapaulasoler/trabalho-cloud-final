package br.com.fiap.trabalhocloudfinal.converter;

import br.com.fiap.trabalhocloudfinal.domain.Address;
import br.com.fiap.trabalhocloudfinal.domain.Customer;
import br.com.fiap.trabalhocloudfinal.enums.Country;
import br.com.fiap.trabalhocloudfinal.enums.State;
import br.com.fiap.trabalhocloudfinal.request.AddressRequest;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class AddressRequestConverter implements BiFunction<AddressRequest, Customer, Address> {
	@Override
	public Address apply(AddressRequest address, Customer customer) {

		Address result = new Address();

		result.setZipcode(address.getZipcode());
		result.setStreet(address.getStreet());
		result.setNumber(address.getNumber());
		result.setComplement(address.getComplement());
		result.setNeighborhood(address.getNeighborhood());
		result.setCity(address.getCity());
		result.setState(State.valueOf(address.getState()));
		result.setCountry(Country.valueOf(address.getCountry()));
		result.setCustomer(customer);

		return result;

	}

}
