package es.codeurjc.NoMoreSpace;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Block {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long block_id;
	
	@ManyToMany(mappedBy="blocks")
	private List<File> files;
	@OneToMany(mappedBy="block")
	private Pool pool;
}
