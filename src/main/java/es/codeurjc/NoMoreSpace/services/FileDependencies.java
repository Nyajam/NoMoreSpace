package es.codeurjc.NoMoreSpace.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.NoMoreSpace.model.*;
import es.codeurjc.NoMoreSpace.repository.FileRepository;
import es.codeurjc.NoMoreSpace.repository.UserRepository;

@Component
public class FileDependencies
{
	@Autowired
	private UserRepository repo;
	@Autowired
	private FileRepository repoFiles;
	
	//Carga de un fichero
	public boolean uploadFile(User user, MultipartFile newFile, Panel panel)
	{
		File fil =new File(newFile.getOriginalFilename(),false);
		try {
			String host = "localhost";
			int port = 442;
			Socket socket = new Socket(host, port);
			OutputStream out = socket.getOutputStream();
			ObjectOutputStream oout = new ObjectOutputStream(out);
			
			if(!newFile.isEmpty()) {
				oout.writeInt(1);//queremos mandar el archivo por lo que lo indicamos al servicio interno con un 1
				System.out.println("1 enviado");
				oout.writeInt((int)fil.getId());
				System.out.println("Id enviado");
				out.write(newFile.getBytes());
				System.out.println("File enviado");
			}
			oout.close();
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
	
	//Descarga de un fichero
	public MultipartFile donwloadFile(long id) {
		MultipartFile recibido = null;
		try {
			String host = "localhost";
			int port = 442;
			Socket socket = new Socket(host, port);
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			ObjectOutputStream oout = new ObjectOutputStream(out);
			ObjectInputStream ois = new ObjectInputStream(in);
			
			oout.writeInt(2);//queremos que nos devuelva el archivo por lo que se lo indicamos al servicio interno con un 2
			System.out.println("2 enviado");
			oout.writeInt((int) id);
			System.out.println("Id enviado");
			recibido = (MultipartFile) ois.readObject();
			System.out.println("File recibido");
			
			ois.close();
			oout.close();
			in.close();
			out.close();
			socket.close();
			
		}catch (UnknownHostException e) {
			System.err.println("Host desconocido");
		}catch (IOException e) {
			System.err.println("Error I/O");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recibido;
	}
	
	//Borra un file
	public boolean deleteFile(User user, long id)
	{
		File fil=repoFiles.findById(id).get();
		if(fil==null)
			return false;
		if((fil.getBlocks().size()==0||fil.getBlocks()==null)&&user.getPool().equals(fil.getPool()))
		{
			try {
				String host = "localhost";
				int port = 442;
				Socket socket = new Socket(host, port);
				OutputStream out = socket.getOutputStream();
				ObjectOutputStream oout = new ObjectOutputStream(out);
				
				oout.writeInt(3);//queremos borrar el archivo por lo que enviamos un 3
				System.out.println("3 enviado");
				oout.writeInt((int) id);
				System.out.println("Id enviado");
				
				oout.close();
				out.close();
				socket.close();
				
			}catch (UnknownHostException e) {
				System.err.println("Host desconocido");
			}catch (IOException e) {
				System.err.println("Error I/O");
			}
			
			user.getPool().removeFile(fil);
			fil.getPanel().removeFile(fil);
			repo.save(user);
			return true;
		}
		return false;
	}
	
}