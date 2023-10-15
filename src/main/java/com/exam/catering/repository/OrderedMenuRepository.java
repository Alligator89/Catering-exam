package com.exam.catering.repository;

import com.exam.catering.domain.OrderedMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderedMenuRepository extends JpaRepository<OrderedMenu, Integer> {

    @NonNull
    Optional<OrderedMenu> findById(@NonNull Integer id);

    void deleteById(@NonNull Integer id);
}
