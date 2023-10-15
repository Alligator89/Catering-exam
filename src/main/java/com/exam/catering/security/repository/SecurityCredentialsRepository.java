package com.exam.catering.security.repository;

import com.exam.catering.security.domain.SecurityCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SecurityCredentialsRepository extends JpaRepository<SecurityCredentials, Integer> {

    Optional<SecurityCredentials> findByClientLogin(String login);
}
