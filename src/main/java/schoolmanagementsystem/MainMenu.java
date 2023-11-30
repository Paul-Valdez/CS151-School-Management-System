package schoolmanagementsystem;

import schoolmanagementsystem.Faculty.TeacherModule;
import schoolmanagementsystem.Person.PersonModule;
import schoolmanagementsystem.Student.StudentModule;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

public class MainMenu extends ApplicationWindow {
	private JLabel label;
	private JButton personModule, studentModule, teacherModule, logout;
	private JPanel panel;

	public MainMenu() {
		this.initComponents();
	}

	private void initComponents() {
		this.label = new JLabel("Main Menu");
		this.label.setFont(headerFont);
		this.label.setHorizontalAlignment(SwingConstants.CENTER);
		this.personModule = new JButton("Person Module");
		this.studentModule = new JButton("Student Module");
		this.teacherModule = new JButton("Teacher Module");
		this.logout = new JButton("Logout");
		this.panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		this.personModule.addActionListener(event -> showWindowAndDispose(new PersonModule()));
		this.studentModule.addActionListener(event -> showWindowAndDispose(new StudentModule()));
		this.teacherModule.addActionListener(event -> showWindowAndDispose(new TeacherModule()));
		this.logout.addActionListener(event -> showWindowAndDispose(new LoginPage()));

		this.panel.add(this.personModule);
		this.panel.add(this.studentModule);
		this.panel.add(this.teacherModule);
		this.panel.add(this.logout);

		getContentPane().add(this.label, BorderLayout.NORTH);
		getContentPane().add(this.panel, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.pack();
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(() -> new MainMenu().showWindow());
	}
}
