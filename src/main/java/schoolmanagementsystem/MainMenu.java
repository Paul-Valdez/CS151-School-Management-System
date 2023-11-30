package schoolmanagementsystem;

import schoolmanagementsystem.Faculty.TeacherModule;
import schoolmanagementsystem.Person.PersonModule;
import schoolmanagementsystem.Student.StudentModule;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

public class MainMenu extends ApplicationWindow {
	public MainMenu() {
		this.initComponents();
	}

	private void initComponents() {
		JLabel label = new JLabel("Main Menu");
		label.setFont(headerFont);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		JButton personModule = new JButton("Person Module");
		JButton studentModule = new JButton("Student Module");
		JButton teacherModule = new JButton("Teacher Module");
		JButton logout = new JButton("Logout");
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		personModule.addActionListener(event -> showWindowAndDispose(new PersonModule()));
		studentModule.addActionListener(event -> showWindowAndDispose(new StudentModule()));
		teacherModule.addActionListener(event -> showWindowAndDispose(new TeacherModule()));
		logout.addActionListener(event -> showWindowAndDispose(new LoginPage()));

		panel.add(personModule);
		panel.add(studentModule);
		panel.add(teacherModule);
		panel.add(logout);

		getContentPane().add(label, BorderLayout.NORTH);
		getContentPane().add(panel, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.pack();
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(() -> new MainMenu().showWindow());
	}
}