package com.exam.catering.repository;

import com.exam.catering.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    @NonNull
    Optional<Orders> findById(@NonNull Integer id);

    @NonNull
    List<Orders> findAll();

    void deleteById(@NonNull Integer id);
}

