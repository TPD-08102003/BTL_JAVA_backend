package org.example.btl_java.repository;

import org.example.btl_java.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Integer> {
        boolean existsByEmail(String email);
        Optional<Accounts> findByEmail(String email);

}
