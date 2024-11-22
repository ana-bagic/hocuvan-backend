package fer.proinz.hocuvan.service;

import java.util.List;
import java.util.Optional;

import fer.proinz.hocuvan.domain.Visitor;

/**
 * The Interface VisitorService is logic between controller and repository for
 * Visitor entity.
 * 
 * @author Nina
 *
 */
public interface VisitorService {

	/**
	 * Lists all visitors from database.
	 *
	 * @return list of all visitors
	 */
	List<Visitor> listAll();

	/**
	 * Creates new visitor.
	 *
	 * @param visitor the visitor to be added to database
	 * @return new visitor
	 */
	Visitor createVisitor(Visitor visitor);



	Optional<Visitor> findById(long id);

	/**
	 * @param username given visitor username
	 * @return visitor associated with given username if it exists, otherwise <code>null</code>
	 */
	Visitor findByUsername(String username);

	Visitor fetch(long id);

	Visitor updateVisitor(Visitor visitor);



	Visitor deleteVisitor(Long id);

	long countAll();

	List<Visitor> filterByUsername(String username);
}
