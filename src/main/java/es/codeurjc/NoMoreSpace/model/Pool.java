package es.codeurjc.NoMoreSpace.model;

import java.util.List;

import javax.persistence.CascadeType;
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
	private long poolId;
	
	@OneToOne
	private User user;
	@ManyToMany
	private List<Block> blocks;
	@OneToMany
	private List<File> file;
	
	
	public Pool() {}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}
	
	public void addBlock(Block block) {
		getBlocks().add(block);
		block.addPool(this);
	}
	
	public void removeBlock(Block block) {
		getBlocks().remove(block);
		if (block.getPools().contains(this)) {
			block.removePool(this);
		}
	}


	public List<File> getFile() {
		return this.file;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}
	
	public void addFile(File file) {
		getFile().add(file);
		file.setPool(this);
	}
	
	public void removeFile(File file) {
		getFile().remove(file);
		file.setPool(null);
	}


	@Override
	public String toString() {
		return "Pool [id=" + poolId + ", user=" + user + ", blocks=" + blocks + ", file=" + file + "]";
	}


	
	
}
