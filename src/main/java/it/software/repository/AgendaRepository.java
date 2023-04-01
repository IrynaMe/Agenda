package it.software.repository;


import it.software.model.Agenda;
import org.springframework.data.repository.CrudRepository;

public interface AgendaRepository extends CrudRepository<Agenda, Integer> {
}
