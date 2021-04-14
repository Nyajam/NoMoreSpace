package es.codeurjc.NoMoreSpace.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.NoMoreSpace.model.*;
import es.codeurjc.NoMoreSpace.repository.UserRepository;

@Component
public class FileDependencies
{
	@Autowired
	private UserRepository repo;
	
	//Carga de un fichero
	public boolean uploadFile(User user, MultipartFile newFile, Panel panel)
	{
		File fil =new File(newFile.getOriginalFilename(),false);
		try {
			String host = "localhost";
			int port = 8080;
			Socket socket = new Socket(host, port);
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			ObjectOutputStream oout = new ObjectOutputStream(out);
			
			if(!newFile.isEmpty()) {
				oout.writeBoolean(true);//si manda true escribe archivo, si manda false recibe archivo
				System.out.println("True enviado");
				oout.writeInt((int)fil.getId());
				System.out.println("Id enviado");
				oout.writeObject(newFile.getBytes());
				System.out.println("File enviado");
			}
			
			in.close();
			out.close();
			socket.close();
			
		}catch (UnknownHostException e) {
			System.err.println("Host desconocido");
		}catch (IOException e) {
			System.err.println("Error I/O");
		}
		user.getPool().addFile(fil);
		panel.addFile(fil);
		repo.save(user);
		return true;
	}
	
	/*public void upFile(MultipartFile file, long id) throws IOException {
		try {
			String host = "localhost";
			int port = 8081;
			Socket socket = new Socket(host, port);
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			ObjectOutputStream oout = new ObjectOutputStream(out);
			
			if(!file.isEmpty()) {
				oout.writeBoolean(true);//si manda true escribe archivo, si manda false recibe archivo
				System.out.println("True enviado");
				oout.writeInt((int)id);
				System.out.println("Id enviado");
				oout.writeObject(file);
				System.out.println("File enviado");
			}
			
			in.close();
			out.close();
			socket.close();
			
		}catch (UnknownHostException e) {
			System.err.println("Host desconocido");
		}catch (IOException e) {
			System.err.println("Error I/O");
		}
		
	}*/
	
	
}
