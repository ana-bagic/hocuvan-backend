package fer.proinz.hocuvan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fer.proinz.hocuvan.domain.Organizer;

import java.util.List;

/**
 * Interface which communicates with the database and creates entity Organizer
 * in database.
 * 
 * @author Nina
 *
 */
@Repository
public interface OrganizerRepo extends JpaRepository<Organizer, Long> {

    int countByUsername(String username);

    Organizer findByUsername(String username);

    List<Organizer> findAllByOrgName(String organisationName);

    List<Organizer> findAllByHeadquarters(String headquarter);

    @Modifying
    @Query("update Organizer o set o.orgName = :newOrganisationName where o.id = :id")
    void updateOrganisationName(@Param(value = "id") Long id, @Param(value = "newOrganisationName") String newOrganisationName);

    @Modifying
    @Query("update Organizer o set o.headquarters = :newHeadquarters where o.id = :id")
    void updateHeadquarters(@Param(value = "id") Long id, @Param(value = "newHeadquarters") String newHeadquarters);

    @Modifying
    @Query("update Organizer o set o.username = :newUsername where o.id = :id")
    void updateUsername(@Param(value = "id") Long id, @Param(value = "newUsername") String newUsername);

    @Modifying
    @Query("update Organizer o set o.password = :newPassword where o.id = :id")
    void updatePassword(@Param(value = "id") Long id, @Param(value = "newPassword") String newPassword);

    @Modifying
    @Query("update  Organizer o set o.email = :newEmail where o.id = :id")
    void updateEmail(@Param(value = "id") Long id, @Param(value = "newEmail") String newEmail);

    @Modifying
    @Query("update Organizer o set o.phoneNumber = :newPhoneNumber where o.id = :id")
    void updatePhoneNumber(@Param(value = "id") Long id, @Param(value = "newPhoneNumber") String newPhoneNumber);




    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByUsernameAndIdNot(String username, Long id);

    List<Organizer> findAllByUsername(String username);
}
