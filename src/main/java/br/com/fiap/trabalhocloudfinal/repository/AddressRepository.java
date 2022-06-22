package br.com.fiap.trabalhocloudfinal.repository;

import br.com.fiap.trabalhocloudfinal.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByZipcode(String zipcode);
}
