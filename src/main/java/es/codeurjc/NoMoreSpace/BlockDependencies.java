package es.codeurjc.NoMoreSpace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlockDependencies
{
	@Autowired
	private UserRepository repo;
}
