package com.company.telegrambot.repository;

import com.company.telegrambot.model.Politician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliticianRepository extends JpaRepository<Politician, Long> {

    Politician getBySurname(String surname);
}
