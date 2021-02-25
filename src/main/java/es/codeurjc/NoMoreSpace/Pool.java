package es.codeurjc.NoMoreSpace;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;

@Entity
public class Pool {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long pool_id;
	
	@OneToOne(mappedBy="pool")
	private User user;
	@ManyToMany
	private List<Block> blocks;
	@OneToMany
	private List<File> file;
	
	
	protected Pool() {}


	@Override
	public String toString() {
		return "Pool [pool_id=" + pool_id + ", user=" + user + ", blocks=" + blocks + ", file=" + file + "]";
	}


	
	
}
