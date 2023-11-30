package schoolmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class ApplicationWindow extends JFrame {
	private static Map<Class<? extends ApplicationWindow>, ApplicationWindow> openWindows = new HashMap<>();
	public Font headerFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);
//	public Font regularFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);

	public ApplicationWindow() {
		this.setSystemLF();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				openWindows.remove(ApplicationWindow.this.getClass());
			}
		});
	}

	public void showWindow() {
		Class<? extends ApplicationWindow> windowClass = this.getClass();
		if (openWindows.containsKey(windowClass)) {
			ApplicationWindow existingWindow = openWindows.get(windowClass);
			existingWindow.toFront();
			existingWindow.repaint();
		} else {
			setLocationRelativeTo(null);
			setVisible(true);
			openWindows.put(windowClass, this);
		}
	}

	/** Opens new window without closing previous window. */
	public void showWindow(ApplicationWindow newWindow) {
		Class<? extends ApplicationWindow> windowClass = newWindow.getClass();
		if (openWindows.containsKey(windowClass)) {
			ApplicationWindow existingWindow = openWindows.get(windowClass);
			existingWindow.toFront();
			existingWindow.repaint();
		} else {
			newWindow.showWindow();
		}
	}

	/** Opens new window and closes previous window. */
	public void showWindowAndDispose(ApplicationWindow newWindow) {
		showWindow(newWindow);
		if (this != newWindow) {
			dispose();
		}
	}

	private void setSystemLF() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}
