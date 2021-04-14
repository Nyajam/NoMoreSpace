package es.codeurjc.NoMoreSpace.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.NoMoreSpace.model.File;

@Service
public class UpDownFiles {
	
	public void upFile(MultipartFile file, long id) throws IOException {
		try {
			String host = "localhost";
			int port = 8081;
			Socket socket = new Socket(host, port);
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			ObjectOutputStream oout = new ObjectOutputStream(out);
			
			if(!file.isEmpty()) {
				oout.writeBoolean(true);//si manda true escribe archivo, si manda false recibe archivo
				oout.writeInt((int)id);
				oout.writeObject(file);
			}
			
			in.close();
			out.close();
			socket.close();
			
		}catch (UnknownHostException e) {
			System.err.println("Host desconocido");
		}catch (IOException e) {
			System.err.println("Error I/O");
		}
		
	}
}
