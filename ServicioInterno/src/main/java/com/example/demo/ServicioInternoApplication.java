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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServicioInternoApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ServicioInternoApplication.class, args);
		getFiles();
	}

	public static void getFiles() throws IOException{
		System.out.println("Empezar");
		//String serverFolder = "C:\\Users\\j.lamparero\\git\\NMSFiles";
		String serverFolder = "/opt/DADfiles";
		ServerSocket server = new ServerSocket(443);
		while (true) {
			Socket socket = server.accept();
			
			try {
				InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(out);
				ObjectInputStream ois = new ObjectInputStream(in);
				
				System.out.println("Antes de leer boolean");
				int modo = in.read();//definimos protocolo, si recibe true guarda archivo si recibe false lo envía
				System.out.println("True recibido"+modo);
				int id = in.read();
				System.out.println("Id recibido"+id);
				if(modo!=0) {
					Path path = Paths.get(serverFolder, ""+id);
					Files.write(path, in.readAllBytes());
					System.out.println("File recibido");
				}else if (modo==2) {
					Path path = Paths.get(serverFolder, ""+id);
					Files.copy(path, oos);
				}else {
					Path path = Paths.get(serverFolder, ""+id);
					Files.delete(path);
				}
				
				ois.close();
				oos.close();
				out.close();
				in.close();
				
			}catch (IOException e){
				System.out.println("Fallo en la conexion.");
			}
			socket.close();
			server.close();
		}
	}

}
