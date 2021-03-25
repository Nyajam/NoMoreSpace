package es.codeurjc.NoMoreSpace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.codeurjc.NoMoreSpace.repository.UserRepository;

@Component
public class BlockDependencies
{
	@Autowired
	private UserRepository repo;
}
