package security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import security.enity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>{

	Optional<AppUser> findByUsername(String username);
}
