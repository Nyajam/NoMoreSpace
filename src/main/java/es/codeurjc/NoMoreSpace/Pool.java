package es.codeurjc.NoMoreSpace;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;

@Entity
public class Pool {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long pool_id;
	
	private long name;
	
	
	
	@OneToOne(mappedBy="pool")
	private User user;
	@OneToMany
	private List<Block> block;
	@OneToMany
	private File file;
	
	
	
}
