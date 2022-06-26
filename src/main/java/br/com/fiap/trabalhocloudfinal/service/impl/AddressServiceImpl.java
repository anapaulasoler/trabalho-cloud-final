package br.com.fiap.trabalhocloudfinal.service.impl;

import br.com.fiap.trabalhocloudfinal.converter.AddressFromAdressToResponseConverter;
import br.com.fiap.trabalhocloudfinal.converter.AddressRequestConverter;
import br.com.fiap.trabalhocloudfinal.converter.AddressResponseConverter;
import br.com.fiap.trabalhocloudfinal.domain.Address;
import br.com.fiap.trabalhocloudfinal.domain.AddressTo;
import br.com.fiap.trabalhocloudfinal.domain.Customer;
import br.com.fiap.trabalhocloudfinal.exceptions.ObjectNotFoundException;
import br.com.fiap.trabalhocloudfinal.repository.AddressRepository;
import br.com.fiap.trabalhocloudfinal.repository.CustomerRepository;
import br.com.fiap.trabalhocloudfinal.request.AddressRequest;
import br.com.fiap.trabalhocloudfinal.response.AddressResponse;
import br.com.fiap.trabalhocloudfinal.service.AddressService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static java.util.Objects.isNull;

@Service
public class AddressServiceImpl implements AddressService {

	private AddressRepository addressRepository;
	private AddressResponseConverter addressResponseConverter;
	private AddressRequestConverter addressRequestConverter;
	private CustomerRepository customerRepository;

	private AddressFromAdressToResponseConverter addressFromAdressToResponseConverter;

	@Autowired
	public AddressServiceImpl(AddressRepository addressRepository, AddressResponseConverter addressResponseConverter,
			AddressRequestConverter addressRequestConverter, CustomerRepository customerRepository,AddressFromAdressToResponseConverter addressFromAdressToResponseConverter) {
		this.addressRepository = addressRepository;
		this.addressResponseConverter = addressResponseConverter;
		this.addressRequestConverter = addressRequestConverter;
		this.customerRepository = customerRepository;
		this.addressFromAdressToResponseConverter = addressFromAdressToResponseConverter;
	}

	@Override
	public Address findByZipcodeByViaCep(String zipcode) throws IOException {

		URL url = new URL("https://viacep.com.br/ws/" + zipcode + "/json");
		URLConnection connection = url.openConnection();
		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String cep;
		StringBuilder jsCep = new StringBuilder();

		while ((cep = br.readLine()) != null) {
			jsCep.append(cep);
		}

		AddressTo at = new Gson().fromJson(jsCep.toString(),AddressTo.class);

		Address address = addressFromAdressToResponseConverter.apply(at);

		return address;
	}

//
//	@Override
//	public AddressResponse createAddress(AddressRequest request, String document) {
//
//		Customer customer = customerRepository.findByDocument(document);
//
//		if (isNull(customer)) {
//			throw new ObjectNotFoundException("Cliente não localizado!");
//		}
//
//		Address address = addressRequestConverter.apply(request, customer);
//		addressRepository.save(address);
//		return addressResponseConverter.apply(address);
//	}
}
