package fer.proinz.hocuvan.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fer.proinz.hocuvan.repo.VisitorRepo;
import fer.proinz.hocuvan.service.EntityMissingException;
import fer.proinz.hocuvan.service.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fer.proinz.hocuvan.domain.Organizer;
import fer.proinz.hocuvan.repo.OrganizerRepo;
import fer.proinz.hocuvan.service.OrganizerService;
import org.springframework.util.Assert;

/**
 * The Class OrganizerServiceJpa is an Implementation of OrganizerService which
 * communicates with repository.
 * 
 * @author Nina
 */
@Service
public class OrganizerServiceJpa implements OrganizerService {

	/** Repository of organizer service. */
	@Autowired
	private OrganizerRepo organizerRepo;
	@Autowired
	private VisitorRepo visitorRepo;

	@Override
	public List<Organizer> listAll() {
		return organizerRepo.findAll();
	}

	@Override
	public Optional<Organizer> findById(long id) {
		return organizerRepo.findById(id);
	}

	@Override
	public Organizer createOrganizer(Organizer organizer) {
		validate(organizer);

		if(organizer.getPassword().length()<5 || organizer.getPassword().length()>30){
			throw new RequestDeniedException("Neispravna lozinka.");
		}

		// provjeri da ne postoji niti organizator niti visitor sa istim kor. imenom
		if(organizerRepo.countByUsername(organizer.getUsername()) > 0
		   || visitorRepo.countByUsername(organizer.getUsername()) > 0 )
			throw new RequestDeniedException("Korisnik s tim korisničkim imenom već postoji");

		organizer.setPassword(new BCryptPasswordEncoder().encode(organizer.getPassword()));
		return organizerRepo.save(organizer);
	}


	@Override
	public Organizer findByUsername(String username) {
		Assert.notNull(username, "Korisničko ime ne smije biti prazno");
		return organizerRepo.findByUsername(username);
	}

	@Override
	public Organizer fetch(long id) {
		return findById(id).orElseThrow(
				() -> new EntityMissingException(Organizer.class)
		);
	}

	/*
	 * TODO: provjera formata podataka
	 *  -> dodati regularne izraze za email, phoneNumber, password
	 * */
	@Override
	public Organizer updateOrganizer(Organizer organizer) {
		Long id = organizer.getId();
		if (!organizerRepo.existsById(id)){
			throw new EntityMissingException(Organizer.class);
		}

		if(organizer.getUsername()!=null){
			if (organizerRepo.existsByUsernameAndIdNot(organizer.getUsername(),id))
				throw new RequestDeniedException(
						"Organizer with username " + organizer.getUsername() + " already exists"
				);
			organizerRepo.updateUsername(id,organizer.getUsername());
		}
		if(organizer.getPassword()!=null){
			organizerRepo.updatePassword(id,new BCryptPasswordEncoder().encode(organizer.getPassword()));
		}
		if(organizer.getEmail()!=null){
			if (organizerRepo.existsByEmailAndIdNot(organizer.getEmail(),id))
				throw new RequestDeniedException(
						"Organizer with email " + organizer.getUsername() + " already exists"
				);
			organizerRepo.updateEmail(id,organizer.getEmail());
		}
		if(organizer.getOrgName()!=null){
			organizerRepo.updateOrganisationName(id,organizer.getOrgName());
		}
		if(organizer.getHeadquarters()!=null){
			organizerRepo.updateHeadquarters(id,organizer.getHeadquarters());
		}
		if(organizer.getPhoneNumber()!=null){
			organizerRepo.updatePhoneNumber(id,organizer.getPhoneNumber());
		}

		return fetch(id);
	}

	@Override
	public Organizer deleteOrganizer(Long id) {
		Organizer organizer = fetch(id);
		organizerRepo.delete(organizer);
		return organizer;
	}

	@Override
	public List<Organizer> filterByUsername(String username) {
		if(username=="" || username==null){
			return organizerRepo.findAll();
		}else{
			return organizerRepo.findAllByUsername(username);
		}
	}

	@Override
	public List<String> organisationHeadquarters() {
		List<Organizer> organizers=organizerRepo.findAll();
		List<String> headquarters=new ArrayList<>();
		for(Organizer o : organizers){
			if(!headquarters.contains(o.getHeadquarters())){
				headquarters.add(o.getHeadquarters());
			}
		}
		return  headquarters;
	}

	@Override
	public long countAll() {
		return organizerRepo.count();
	}

	private void validate(Organizer organizer) {
		Assert.notNull(organizer, "Organizer object must be given");
		Assert.notNull(organizer.getUsername(), "Username must be given");
		Assert.notNull(organizer.getPassword(), "Password must be given");
	}
}
