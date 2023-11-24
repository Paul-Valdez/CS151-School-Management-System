package schoolmanagementsystem;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;

public class ApplicationWindow extends JFrame {
	
	public Font headerFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);
	
	public ApplicationWindow() {
		this.setSystemLF();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		// getContentPane().setPreferredSize(new Dimension(854, 480));
	}
	
	public void showWindow() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void showWindow(ApplicationWindow newWindow) {
		newWindow.showWindow();
		dispose();
	}
	
	private void setSystemLF() {
		try {
			// Set theme to system theme
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
	    	e.printStackTrace();
	    } catch (IllegalAccessException e) {
	    	e.printStackTrace();
	    }
	}
}