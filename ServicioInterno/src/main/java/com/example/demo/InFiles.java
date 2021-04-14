package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class InFiles {
	
	public void getFiles() throws IOException{
		System.out.println("Empezar");
		String serverFolder = ".//src//main//resources//files//";
		ServerSocket server = new ServerSocket(8080);
		while (true) {
			Socket socket = server.accept();
			
			try {
				InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream();
				ObjectInputStream ois = new ObjectInputStream(in);
				ObjectOutputStream oos = new ObjectOutputStream(out);
				
				System.out.println("Antes de leer boolean");
				boolean modo = ois.readBoolean();//definimos protocolo, si recibe true guarda archivo si recibe false lo envía
				System.out.println("True recibido");
				int id = ois.readInt();
				System.out.println("Id recibido");
				if(modo) {
					MultipartFile file = (MultipartFile) ois.readObject();
					System.out.println("File recibido");
					Path path = Paths.get(serverFolder + id);
					Files.write(path, file.getBytes());
				}else {
					Path path = Paths.get(serverFolder, ""+id);
					Files.copy(path, oos);
				}

				
				ois.close();
				out.close();
				in.close();
				socket.close();
				
			}catch (IOException e){
				System.out.println("Fallo en la conexion.");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
