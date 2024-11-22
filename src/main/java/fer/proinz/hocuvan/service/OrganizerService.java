package fer.proinz.hocuvan.service;

import java.util.List;
import java.util.Optional;

import fer.proinz.hocuvan.domain.Organizer;

/**
 * The Interface OrganizerService is logic between controller and repository for
 * Organizer entity.
 * 
 * @author Nina
 */
public interface OrganizerService {

	/**
	 * Lists all organizers from database.
	 *
	 * @return the list of all organizers
	 */
	List<Organizer> listAll();


	Optional<Organizer> findById(long id);

	/**
	 * Creates new organizer.
	 *
	 * @param organizer the organizer to be added to database
	 * @return new organizer
	 */
	Organizer createOrganizer(Organizer organizer);

	/**
	 * @param username given organizer username
	 * @return organizer associated with given username if it exists, otherwise <code>null</code>
	 */
	Organizer findByUsername(String username);

	Organizer fetch(long id);

	Organizer updateOrganizer(Organizer organizer);

	Organizer deleteOrganizer(Long id);

	long countAll();

	List<Organizer> filterByUsername(String username);

	List<String> organisationHeadquarters();
}
