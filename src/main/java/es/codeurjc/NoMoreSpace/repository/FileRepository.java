package es.codeurjc.NoMoreSpace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.NoMoreSpace.model.File;

public interface FileRepository extends JpaRepository<File, Long>{

	List<File> findByFilename(String filename);

}
