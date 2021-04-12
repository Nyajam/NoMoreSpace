package es.codeurjc.NoMoreSpace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.NoMoreSpace.model.Panel;


public interface PanelRepository extends JpaRepository<Panel, Long>{
	
}
