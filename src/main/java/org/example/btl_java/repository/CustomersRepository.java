package org.example.btl_java.repository;

import org.example.btl_java.model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Integer> {
    Customers findByAccount_Email(String email);
}
