package re.st.animalshelter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import re.st.animalshelter.entity.Volunteer;
import re.st.animalshelter.entity.animal.Animal;
import re.st.animalshelter.repository.VolunteerRepository;

import java.util.LinkedList;

@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final AnimalService animalService;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository, AnimalService animalService) {
        this.volunteerRepository = volunteerRepository;
        this.animalService = animalService;
    }

    public LinkedList<Volunteer> getAllVolunteers() {
        return new LinkedList<>(volunteerRepository.findAll());
    }

    public Volunteer getById(long id) {
       return volunteerRepository.findById(id).orElseThrow(RuntimeException::new); //TODO
    }

    public Volunteer getByChatId(long chatId) {
        return volunteerRepository.findByChatId(chatId).orElseThrow(RuntimeException::new); //TODO
    }

    public void save(Volunteer volunteer) {
        volunteerRepository.save(volunteer);
    }

    public boolean isExist(long id, String userName) {
        return volunteerRepository.findByIdOrUserName(id, userName).isPresent();
    }

    public void remove(Volunteer volunteer) {
        volunteerRepository.delete(volunteer);
    }

    public void attachAnimal(long volunteerId, long animalId) {
        Volunteer volunteer = getById(volunteerId);
        Animal animal = animalService.getById(animalId);
        volunteer.addAnimal(animal);
        save(volunteer);
    }

    public void removeAnimal(long id, long animalId) {
        Volunteer volunteer = getById(id);
        Animal animal = animalService.getById(animalId);
        volunteer.removeAnimal(animal);
        save(volunteer);
    }
}
