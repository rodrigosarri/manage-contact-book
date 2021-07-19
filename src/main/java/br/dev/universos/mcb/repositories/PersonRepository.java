package br.dev.universos.mcb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.universos.mcb.models.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    
}
