package es.codeurjc.NoMoreSpace;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControladorTest
{
	//Pagina del Index
	@GetMapping("/")
	public String indexPage(Model model)
	{
		model.addAttribute("panelCSS",true);
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		return "index";
	}
	
	//Pagina de apertura de login
	@GetMapping("/login")
	public String loginPage(Model model)
	{
		model.addAttribute("panelCSS",true);
		model.addAttribute("expulse",false);
		model.addAttribute("access",false);
		model.addAttribute("titleApp","NoMereSpacePlease!");
		model.addAttribute("titlePage","Login");
		return "login";
	}
	
	//Pagina gestion de login
	@RequestMapping("/login")
	public String loginPageProcess(Model model, @RequestParam String user)
	{
		model.addAttribute("panelCSS",true);
		model.addAttribute("expulse",user.equals("nobody"));
		model.addAttribute("access",!user.equals("nobody"));
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","Login");
		return "login";
	}
	
	//Pagina del home del usuario
	@GetMapping("/home")
	public String homePage(Model model)
	{
		List<FileTest> filesUser=new LinkedList();
		for(int i=0;i<20;i++)
			filesUser.add(new FileTest("file_"+i,i));
		model.addAttribute("userName","test");
		model.addAttribute("filesUser",filesUser);
		model.addAttribute("panelCSS",false);
		model.addAttribute("admin",true); //SI EL USUARIO ES ADMINISTRADOR
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","Home");
		return "home";
	}
	
	//Pagina de gestion del usuario
	@GetMapping("/myuser")
	public String myuserPage(Model model)
	{
		int size=0;
		LinkedList<FileTest> filesUser=new LinkedList();
		for(int i=0;i<20;i++)
		{
			filesUser.add(new FileTest("file_"+i,i));
			size=size+i*1000;
		}
		model.addAttribute("userName","test");
		model.addAttribute("filesUser",filesUser);
		
		model.addAttribute("userSizePoolMax","10GB");
		model.addAttribute("userSizePool",size/1024+"MB");
		model.addAttribute("userSizePoolFiles",filesUser.size());
		model.addAttribute("userSizePoolPercent",(size/1024.0)/10240.0);
		model.addAttribute("userMail","test@urjc.es");
		
		model.addAttribute("panelCSS",false);
		model.addAttribute("admin",true); //SI EL USUARIO ES ADMINISTRADOR
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","My User");
		return "myuser";
	}
	
	//Pagina de administracion
	@GetMapping("/adm")
	public String admPage(Model model)
	{
		List<FileTest> filesUser=new LinkedList();
		for(int i=0;i<20;i++)
			filesUser.add(new FileTest("file_"+i,i));
		model.addAttribute("userName","test");
		model.addAttribute("filesUser",filesUser);
		model.addAttribute("panelCSS",false);
		model.addAttribute("admin",true); //SI EL USUARIO ES ADMINISTRADOR
		model.addAttribute("titleApp","NoMoreSpacePlease!");
		model.addAttribute("titlePage","Home");
		return "administration";
	}
}
