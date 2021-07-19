package br.dev.universos.mcb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.universos.mcb.dto.MessageResponseDTO;
import br.dev.universos.mcb.dto.PersonDTO;
import br.dev.universos.mcb.exception.PersonNotFoundException;
import br.dev.universos.mcb.mappers.PersonMapper;
import br.dev.universos.mcb.models.Person;
import br.dev.universos.mcb.repositories.PersonRepository;
import java.util.stream.Collectors;

@Service
public class PersonService {
    
    @Autowired
    private PersonRepository personRepository;
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public MessageResponseDTO savePerson(PersonDTO personDTO) {
        Person persontoSave = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(persontoSave);
        return createMessageResponse(savedPerson.getId(), "Contato criado com o ID: ");
    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfExists(id);

        return personMapper.toDTO(person);
    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);

        Person personToUpdate = personMapper.toModel(personDTO);
        Person updatedPerson  = personRepository.save(personToUpdate);

        return createMessageResponse(updatedPerson.getId(), "Contato atualizado com o ID: ");
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }    

}
