package es.codeurjc.NoMoreSpace.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.NoMoreSpace.model.User;

@CacheConfig(cacheNames="users")
public interface UserRepository extends JpaRepository<User, Long>{
	@Cacheable
	List<User> findByUsername(String username);
	@Cacheable
	List<User> findByPassword(String password);
	@Cacheable
	List<User> findByMail(String mail);
	@Cacheable
	List<User> findByBloqueado(Boolean bloqueado);
	@Cacheable
	List<User> findByAdmin(Boolean admin);

}
