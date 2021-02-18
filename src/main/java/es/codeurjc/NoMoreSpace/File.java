package es.codeurjc.NoMoreSpace;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class File {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long file_id;
	
	
	private String filename;
	
	@ManyToMany
	private List<Block> blocks;
	@ManyToOne
	private Panel panel;
	@OneToOne(mappedBy="file")
	private Pool pool;
	
}
