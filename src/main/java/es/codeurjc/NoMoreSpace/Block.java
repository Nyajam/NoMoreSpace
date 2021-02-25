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
public class Block {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long block_id;
	
	@ManyToMany(mappedBy="blocks")
	private List<File> files;
	@ManyToMany(mappedBy="blocks")
	private List<Pool> pool;
	
	protected Block() {}

	@Override
	public String toString() {
		return "Block [block_id=" + block_id + ", files=" + files + ", pool=" + pool + "]";
	}
	
	
}
