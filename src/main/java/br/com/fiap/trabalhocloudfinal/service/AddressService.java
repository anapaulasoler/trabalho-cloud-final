package br.com.fiap.trabalhocloudfinal.service;


import br.com.fiap.trabalhocloudfinal.domain.Address;
import br.com.fiap.trabalhocloudfinal.request.AddressRequest;
import br.com.fiap.trabalhocloudfinal.response.AddressResponse;

import java.io.IOException;

public interface AddressService {

    Address findByZipcodeByViaCep(String zipcode) throws IOException;


}
