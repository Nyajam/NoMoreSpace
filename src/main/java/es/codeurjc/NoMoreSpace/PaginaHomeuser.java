package es.codeurjc.NoMoreSpace;

import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PaginaHomeuser
{
	@Autowired
	private UserRepository repo;
	@Autowired
	private UserDependencies userOP;
	@Autowired
	private PanelDependencies panelOP;
	@Autowired
	private PoolDependencies poolOP;
	@Autowired
	private FileDependencies fileOP;
	@Autowired
	private BlockDependencies blockOP;

	//Parte comun del home del usuario
	private String homeESTC(Model model, Optional <User> usuario)
	{
		model.addAttribute("userName",usuario.get().getUsername());
		model.addAttribute("panelCSS",false);
		model.addAttribute("admin",usuario.get().isAdmin()); //SI EL USUARIO ES ADMINISTRADOR
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","Home");
		model.addAttribute("home",true);
		return "home";
	}

	//Pagina del home del usuario - Privada
	@GetMapping("/home")
	public String homePage(Model model, HttpSession sesion)
	{
		Optional <User> usuario;
		usuario=userOP.chkSession(sesion);
		if(usuario==null)
		{
			return userOP.noSession(model, sesion);
		}
		model.addAttribute("actualPanel",usuario.get().getPanel().get(0).getName());
		model.addAttribute("panels",usuario.get().getPanel());
		return homeESTC(model,usuario);
	}
	
	//Pagina del home del usuario - Privada
	@RequestMapping("/home")
	public String homePageProcess(Model model, HttpSession sesion, @RequestParam String actualPanel, @RequestParam String goToPanel)
	{
		Optional <User> usuario;
		usuario=userOP.chkSession(sesion);
		if(usuario==null)
		{
			return userOP.noSession(model, sesion);
		}
		//model.addAttribute("actualPanel",actualPanel+"/"+goToPanel);
		//model.addAttribute("panels", getPanelByPath(usuario.get(),actualPanel+"/"+goToPanel).getPanel() );
		
		model.addAttribute("actualPanel",goToPanel);
		model.addAttribute("panels",usuario.get().getPanel());
		
		return homeESTC(model,usuario);
	}
	
	//Pagina del home del usuario, creacion de panel - Privada
	@RequestMapping("/home/newpanel")
	public String homePageNewPanel(Model model, HttpSession sesion, @RequestParam String nameNewPanel, @RequestParam String actualPanel)
	{
		Optional <User> usuario;
		usuario=userOP.chkSession(sesion);
		if(usuario==null)
		{
			return userOP.noSession(model, sesion);
		}
		model.addAttribute("actualPanel",actualPanel);
		Panel workdir, nuevo;
		workdir=panelOP.getPanelByPath(usuario.get(),actualPanel);
		for(int i=0;i<workdir.getPanel().size();i++)
			if(workdir.getPanel().get(i).getName().equals(nameNewPanel))
			{
				model.addAttribute("panels",usuario.get().getPanel());
				return homeESTC(model,usuario);
			}
		if(workdir!=null)
		{
			nuevo=new Panel(usuario.get(),nameNewPanel);
			workdir.getPanel().add(nuevo);
			//usuario.get().addPanel(workdir);
			repo.save(usuario.get());
		}
		model.addAttribute("panels",usuario.get().getPanel());
		return homeESTC(model,usuario);
	}
	
	//Pagina del home del usuario, borrar panel - Privada
	@RequestMapping("/home/deletepanel")
	public String homePageSharePanel(Model model, HttpSession sesion, @RequestParam String actualPanel)
	{
		Optional <User> usuario;
		usuario=userOP.chkSession(sesion);
		if(usuario==null)
		{
			return userOP.noSession(model, sesion);
		}
		for(int i=0;i<usuario.get().getPanel().size();i++)
			if(usuario.get().getPanel().get(i).getName().equals(actualPanel))
			{
				//usuario.get().getPanel().get(i).setUser(null);
				//usuario.get().getPanel().remove(i);
				usuario.get().removePanel(usuario.get().getPanel().get(i));
				repo.save(usuario.get());
				break;
			}
		
		model.addAttribute("panels",usuario.get().getPanel());
		model.addAttribute("actualPanel",usuario.get().getPanel().get(0).getName());
		return homeESTC(model,usuario);
	}
	
	//Pagina del home del usuario, gestion de ficheros - Privada
	@PostMapping("/home/upfiles")
	public String homePageMyFiles(Model model, HttpSession sesion, @RequestParam("filesUpload") MultipartFile filesUpload, @RequestParam String actualPanel)
	{
		Optional <User> usuario;
		usuario=userOP.chkSession(sesion);
		if(usuario==null)
		{
			return userOP.noSession(model, sesion);
		}
		for(int i=0;i<usuario.get().getPanel().size();i++)
			if(usuario.get().getPanel().get(i).getName().equals(actualPanel))
			{
				if(usuario.get().getPool()==null)
				{
					Pool pol = new Pool();
					pol.setUser(usuario.get());
					usuario.get().setPool(pol);
				}
				File file = new File();
				file.setFilename(filesUpload.getName());
				//file.setFilename("fichero");
				usuario.get().getPanel().get(i).addFile(file);
				if(usuario.get().getPool().getFile()==null)
					usuario.get().getPool().setFile(new ArrayList());
				usuario.get().getPool().getFile().add(file);
				repo.save(usuario.get());
				model.addAttribute("filesUser",usuario.get().getPanel().get(i).getFile());
				break;
			}
		model.addAttribute("panels",usuario.get().getPanel());
		model.addAttribute("actualPanel",usuario.get().getPanel().get(0).getName());
		return homeESTC(model,usuario);
	}
	
	//Pagina del home del usuario, descarga de ficheros - Privada
	@RequestMapping("/home/downfiles")
	public String homePageDownFiles(Model model, HttpSession sesion)
	{
		Optional <User> usuario;
		usuario=userOP.chkSession(sesion);
		if(usuario==null)
		{
			return userOP.noSession(model, sesion);
		}
		model.addAttribute("actualPanel",usuario.get().getPanel().get(0).getName());
		model.addAttribute("panels",usuario.get().getPanel());
		return homeESTC(model,usuario);
	}
	
}
