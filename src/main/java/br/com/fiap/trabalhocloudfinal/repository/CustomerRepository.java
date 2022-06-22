package br.com.fiap.trabalhocloudfinal.repository;

import br.com.fiap.trabalhocloudfinal.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	Customer findByDocument(String document);

}
