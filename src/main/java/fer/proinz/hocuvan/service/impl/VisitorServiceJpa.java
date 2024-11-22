package fer.proinz.hocuvan.service.impl;

import java.util.List;
import java.util.Optional;

import fer.proinz.hocuvan.repo.OrganizerRepo;
import fer.proinz.hocuvan.service.EntityMissingException;
import fer.proinz.hocuvan.service.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fer.proinz.hocuvan.domain.Visitor;
import fer.proinz.hocuvan.repo.VisitorRepo;
import fer.proinz.hocuvan.service.VisitorService;
import org.springframework.util.Assert;

/**
 * The Class VisitorServiceJpa is an Implementation of VisitorService which
 * communicates with repository.
 * 
 * @author Nina
 */
@Service
public class VisitorServiceJpa implements VisitorService {

	/** Repository of visitor service. */
	@Autowired
	private VisitorRepo visitorRepo;
	@Autowired
	private OrganizerRepo organizerRepo;

	@Override
	public List<Visitor> listAll() {
		return visitorRepo.findAll();
	}


	@Override
	public Optional<Visitor> findById(long id) {
		return visitorRepo.findById(id);
	}

	@Override
	public Visitor createVisitor(Visitor visitor) {
		validate(visitor);

		if(visitor.getPassword().length()<5 || visitor.getPassword().length()>30){
			throw new RequestDeniedException("Unešeni podatci su neispravni");
		}

		// provjeri da ne postoji niti organizator niti visitor sa istim kor. imenom
		if(visitorRepo.countByUsername(visitor.getUsername()) > 0
				|| organizerRepo.countByUsername(visitor.getUsername()) > 0 )
			throw new RequestDeniedException("Korisnik s tim korisničkim imenom već postoji");

		visitor.setPassword(new BCryptPasswordEncoder().encode(visitor.getPassword()));
		return visitorRepo.save(visitor);

	}


	@Override
	public Visitor findByUsername(String username) {
		Assert.notNull(username, "Korisničko ime je prazno");
		return visitorRepo.findByUsername(username);
	}

	@Override
	public Visitor fetch(long id) {
		return findById(id).orElseThrow(
				() -> new EntityMissingException(Visitor.class)
		);
	}

	/*
	* TODO: provjera formata podataka
	*  -> dodati regularne izraze za email, phoneNumber, password
	* */
	@Override
	public Visitor updateVisitor(Visitor visitor) {
		Long id = visitor.getId();
		if (!visitorRepo.existsById(id)){
			throw new EntityMissingException(Visitor.class);
		}

		if(visitor.getUsername()!=null){
			if (visitorRepo.existsByUsernameAndIdNot(visitor.getUsername(),id))
				throw new RequestDeniedException(
						"Visitor with username " + visitor.getUsername() + " already exists"
				);
			visitorRepo.updateUsername(id,visitor.getUsername());
		}
		if(visitor.getPassword()!=null){
			visitorRepo.updatePassword(id,new BCryptPasswordEncoder().encode(visitor.getPassword()));
		}
		if(visitor.getEmail()!=null){
			if (visitorRepo.existsByEmailAndIdNot(visitor.getEmail(),id))
				throw new RequestDeniedException(
						"Visitor with email " + visitor.getUsername() + " already exists"
				);
			visitorRepo.updateEmail(id,visitor.getEmail());
		}
		if(visitor.getFirstName()!=null){
			visitorRepo.updateFirstName(id,visitor.getFirstName());
		}
		if(visitor.getLastName()!=null){
			visitorRepo.updateLastName(id,visitor.getLastName());
		}
		if(visitor.getPhoneNumber()!=null){
			visitorRepo.updatePhoneNumber(id,visitor.getPhoneNumber());
		}

		return fetch(id);
	}

	@Override
	public Visitor deleteVisitor(Long id) {
		Visitor visitor = fetch(id);
		visitorRepo.delete(visitor);
		return visitor;
	}

	@Override
	public long countAll() {
		return visitorRepo.count();
	}

	@Override
	public List<Visitor> filterByUsername(String username) {
		if(username=="" || username==null){
			return visitorRepo.findAll();
		}else{
			return visitorRepo.findAllByUsername(username);
		}
	}

	private void validate(Visitor visitor) {
		Assert.notNull(visitor, "Visitor object must be given");
		Assert.notNull(visitor.getUsername(), "Username must be given");
		Assert.notNull(visitor.getPassword(), "Password must be given");
	}
}
