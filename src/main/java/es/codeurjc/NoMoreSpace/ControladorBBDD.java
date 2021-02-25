package es.codeurjc.NoMoreSpace;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorBBDD implements CommandLineRunner {

	@Autowired
	private UserRepository repo;
	
	@Override
	public void run(String [] args) throws Exception{
		
		repo.save(new User());
		repo.save(new User("pepito@hotmail.com","123456","pepipepito"));
		repo.save(new User("juan@hotmail.com","123654","juanijuanito",false,true));
		repo.save(new User("juan@hotmail.com","123654","juanijuanito",false,true,null,null));
		repo.saveAndFlush(new User());
		/*List<User> pepipepito = repo.findByUsername("pepipepito");
		for (User p : pepipepito) {
			System.out.println(p);
		}*/
	}
}
