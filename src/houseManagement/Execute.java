package houseManagement;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Execute {
	public static void main(String[] args) { 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setBounds(100, 100, 750, 300);
					frame.setTitle("Архангельское ЖКХ");
//					frame.pack();
					frame.setLocationByPlatform(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
