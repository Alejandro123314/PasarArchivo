package pasar_fichero;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JFileChooser;

public class FileSend {

	private void ready(int port, String host) {
		System.out.println("Choosing file to send");
		try {
			DatagramSocket socket = new DatagramSocket();
			InetAddress address = InetAddress.getByName(host);
			String fileName;

			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (jfc.isMultiSelectionEnabled()) {
				jfc.setMultiSelectionEnabled(false);
			}

			int r = jfc.showOpenDialog(null);
			if (r == JFileChooser.APPROVE_OPTION) {
				File f = jfc.getSelectedFile();
				fileName = f.getName();
				byte[] fileNameBytes = fileName.getBytes();
				DatagramPacket fileStatPacket = new DatagramPacket(fileNameBytes, fileNameBytes.length, address, port);
				socket.send(fileStatPacket);
				byte[] fileByteArray = readFileToByteArray(f);
				
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void sendFile(DatagramSocket socket, byte[] fileByteArray, InetAddress address, int port) throws IOException {
		System.out.println("Sending file");
		int sequencerNumber = 0;
		boolean flag;
		int ackSequence = 0;
		
		for(int i = 0; i < fileByteArray.length; i = i + 2021) {
			sequencerNumber += 1;
			
			byte[] message = new byte[1024];
			message[0] = (byte) (sequencerNumber >> 8);
			message[1] = (byte) (sequencerNumber);
		}
	}

	private static byte[] readFileToByteArray(File file) {

		FileInputStream fis = null;
		byte[] bArray = new byte[(int) file.length()];
		try {
			fis = new FileInputStream(file);
			fis.read(bArray);
			fis.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return bArray;
	}

}
