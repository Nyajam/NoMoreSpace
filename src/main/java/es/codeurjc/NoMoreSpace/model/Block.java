package es.codeurjc.NoMoreSpace.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity
public class Block {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToMany(mappedBy="blocks", cascade = CascadeType.ALL)
	private List<File> files;
	@ManyToMany(mappedBy="blocks", cascade = CascadeType.ALL)
	private List<Pool> pool;
	
	protected Block() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}
	
	public void addFile(File file) {
		getFiles().add(file);
		file.addBlock(this);
	}
	
	public void removeFile(File file) {
		getFiles().remove(file);
		if (file.getBlocks().contains(this)) {
			file.removeBlock(this);
		}
	}

	public List<Pool> getPools() {
		return pool;
	}

	public void setPools(List<Pool> pool) {
		this.pool = pool;
	}
	
	public void addPool(Pool pool) {
		getPools().add(pool);
		pool.addBlock(this);
	}
	
	public void removePool(Pool pool) {
		getPools().remove(pool);
		if (pool.getBlocks().contains(this)) {
			pool.removeBlock(this);
		}
	}

	@Override
	public String toString() {
		return "Block [id=" + id + ", files=" + files + ", pool=" + pool + "]";
	}
	
	
}
