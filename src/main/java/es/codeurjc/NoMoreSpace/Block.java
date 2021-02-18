package es.codeurjc.NoMoreSpace;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Block {

	@Id
	@GeneratedValue
	private long block_id;
	
}
