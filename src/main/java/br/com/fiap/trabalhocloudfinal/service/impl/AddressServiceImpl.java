package br.com.fiap.trabalhocloudfinal.service.impl;

import br.com.fiap.trabalhocloudfinal.converter.AddressRequestConverter;
import br.com.fiap.trabalhocloudfinal.converter.AddressResponseConverter;
import br.com.fiap.trabalhocloudfinal.domain.Address;
import br.com.fiap.trabalhocloudfinal.domain.Customer;
import br.com.fiap.trabalhocloudfinal.exceptions.ObjectNotFoundException;
import br.com.fiap.trabalhocloudfinal.repository.AddressRepository;
import br.com.fiap.trabalhocloudfinal.repository.CustomerRepository;
import br.com.fiap.trabalhocloudfinal.request.AddressRequest;
import br.com.fiap.trabalhocloudfinal.response.AddressResponse;
import br.com.fiap.trabalhocloudfinal.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class AddressServiceImpl implements AddressService {

	private AddressRepository addressRepository;
	private AddressResponseConverter addressResponseConverter;
	private AddressRequestConverter addressRequestConverter;
	private CustomerRepository customerRepository;

	@Autowired
	public AddressServiceImpl(AddressRepository addressRepository, AddressResponseConverter addressResponseConverter,
			AddressRequestConverter addressRequestConverter, CustomerRepository customerRepository) {
		this.addressRepository = addressRepository;
		this.addressResponseConverter = addressResponseConverter;
		this.addressRequestConverter = addressRequestConverter;
		this.customerRepository = customerRepository;
	}

	@Override
	public Address findByZipcode(String zipcode) {
		Optional<Address> address = addressRepository.findByZipcode(zipcode);
		return address.orElseThrow(() -> new ObjectNotFoundException("Endereço não localizado!"));
	}

	@Override
	public AddressResponse createAddress(AddressRequest request, String document) {

		Customer customer = customerRepository.findByDocument(document);

		if (isNull(customer)) {
			throw new ObjectNotFoundException("Cliente não localizado!");
		}

		Address address = addressRequestConverter.apply(request, customer);
		addressRepository.save(address);
		return addressResponseConverter.apply(address);
	}
}
