package es.codeurjc.NoMoreSpace;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

	List<User> findByUsername(String username);
	List<User> findByPassword(String password);
	List<User> findByMail(String mail);
	List<User> findByBloqueado(Boolean bloqueado);
	List<User> findByAdmin(Boolean admin);

}
