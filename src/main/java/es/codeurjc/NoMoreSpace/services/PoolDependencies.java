package es.codeurjc.NoMoreSpace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.codeurjc.NoMoreSpace.model.User;
import es.codeurjc.NoMoreSpace.repository.UserRepository;

@Component
public class PoolDependencies
{
	@Autowired
	private UserRepository repo;
	

	//Retorna el peso en bytes de los files del usuario
	public int sizeOfPool(User usuario)
	{
		int size=0;
		//for(int i=0;i<usuario.getPool().getFile().size();i++)
		//{
		//	size=usuario.getPool().getFile().get(i);
		//}
		if(usuario.getPool()==null)
			return 0;
		size=usuario.getPool().getFile().size();
		return size;
	}
}
