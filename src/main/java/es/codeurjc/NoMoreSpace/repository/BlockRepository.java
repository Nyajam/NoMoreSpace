package es.codeurjc.NoMoreSpace.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.NoMoreSpace.model.Block;

public interface BlockRepository extends JpaRepository<Block, Long>{

	Optional<Block> findById(Long id);


}
