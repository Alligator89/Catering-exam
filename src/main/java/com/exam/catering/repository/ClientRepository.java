package com.exam.catering.repository;

import com.exam.catering.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findClientByLastName(String lastName);

    @NonNull
    Optional<Client> findById(@NonNull Integer id);

    @NonNull
    List<Client> findAll();

    void deleteById(@NonNull Integer id);
}
