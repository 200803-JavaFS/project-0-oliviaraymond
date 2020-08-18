package com.revature.util;

import java.util.Scanner;

import com.revature.daos.UserDAO;
import com.revature.models.User;
import com.revature.services.AccountService;

public class ConsoleUtility {

	private static final Scanner scan = new Scanner(System.in);
	private AccountService as = new AccountService();
	private UserDAO ud = new UserDAO();
	private boolean exit = false;
	private User loggedInUser;

	public void beginApp() {

		System.out.println(
				"  /$$$$$$  /$$ /$$            /$$          /$$              /$$$$$$$                      /$$       ");
		System.out.println(
				" /$$__  $$| $$|__/           |__/         | $/             | $$__  $$                    | $$       ");
		System.out.println(
				"| $$  \\ $$| $$ /$$ /$$    /$$ /$$  /$$$$$$|_//$$$$$$$     | $$  \\ $$  /$$$$$$  /$$$$$$$ | $$   /$$ ");
		System.out.println(
				"| $$  | $$| $$| $$|  $$  /$$/| $$ |____  $$ /$$_____/      | $$$$$$$  |____  $$| $$__  $$| $$  /$$/ ");
		System.out.println(
				"| $$  | $$| $$| $$ \\  $$/$$/ | $$  /$$$$$$$|  $$$$$$      | $$__  $$  /$$$$$$$| $$  \\ $$| $$$$$$/  ");
		System.out.println(
				"| $$  | $$| $$| $$  \\  $$$/  | $$ /$$__  $$ \\____  $$    | $$  \\ $$ /$$__  $$| $$  | $$| $$_  $$  ");
		System.out.println(
				"|  $$$$$$/| $$| $$   \\  $/   | $$|  $$$$$$$ /$$$$$$$/     | $$$$$$$/|  $$$$$$$| $$  | $$| $$ \\  $$ ");
		System.out.println(
				" \\______/ |__/|__/    \\_/    |__/ \\_______/|_______/    |_______/  \\_______/|__/  |__/|__/  \\__/ ");

		while (!exit) {
			if (loggedInUser != null) {
				operationMenu();
			} else {
				mainMenu();
			}
		}
	}

	private void operationMenu() {
		System.out.println("Welcome to Olivia's Bank! \n" + "What would you like to do? \n" + "[W]ithdraw \n"
				+ "[D]eposit \n" + "[T]ransfer \n" + "[U]pdate \n" + "[L]ogout");
		String answer = scan.nextLine();
		operationSwitch(answer);

	}

	private void operationSwitch(String answer) {
		answer = answer.toLowerCase();

		switch (answer) {
		case "w":
			System.out.println("How much would you like to withdraw?");
			double amount = scan.nextDouble();
			as.withdraw(amount, loggedInUser.getId());
			
			
			break;
		case "d":
			newCustomerMenu();
			break;
		case "t":
			employeeMenu();
			break;
		case "u":
			adminMenu();
			break;
		case "l":
			loggedInUser = null;
			System.out.println("You are now logged out.");
			break;
		default:
			System.out.println("Error. Please type one of the three options listed. ");
			break;
		}
		
	}

	public void mainMenu() {
		System.out.println("Welcome to Olivia's Bank! \n" + "Who are you? \n" + "I am a [C]urrent Customer \n"
				+ "I am a [N]ew Customer \n" + "I am an [E]mployee \n" + "I am an [A]dmin \n" + "[X]");
		String answer = scan.nextLine();
		answerSwitch(answer);
	}

	private void answerSwitch(String answer) {
		answer = answer.toLowerCase();

		switch (answer) {
		case "c":
			currentCustomerMenu();
			break;
		case "n":
			newCustomerMenu();
			break;
		case "e":
			employeeMenu();
			break;
		case "a":
			adminMenu();
			break;
		case "x":
			exit = true;
			break;
		default:
			System.out.println("Error. Please type one of the three options listed. ");
			break;
		}
	}

	private User newCustomerMenu() {
		System.out.println(
				"Welcome to your new bank! \n" + "Please follow the steps to create an account. \n" + "[Exit]");
		System.out.println("What is your first name? ");
		String firstName = scan.nextLine();
		System.out.println("What is your last name? ");
		String lastName = scan.nextLine();
		System.out.println("What is your email address? ");
		String email = scan.nextLine();
		System.out.println("What type of user are you? A customer, employee, or admin. ");
		String type = scan.nextLine();
		System.out.println("Create a password.");
		String password = scan.nextLine();
		User u = new User(firstName, lastName, email, password, type);
		as.createUser(u);
		return u;

	}

	private void currentCustomerMenu() {
		System.out.println("Welcome back loyal customer! \n" + "Please enter your login information \n");
		System.out.println("What is your email? ");
		String email = scan.nextLine();
		System.out.println("What is your password? ");
		String password = scan.nextLine();

		User u = as.login(email, password);
		if (u == null) {
			System.out.println("Not a current customer or invalid credentials");
		} else {
			loggedInUser = u;
			System.out.println("You are now logged in");

		}

	}

	private void adminMenu() {
		System.out.println("Admin Portal \n" + "Enter login information: \n"

				+ "[Exit]");

	}

	private void employeeMenu() {
		System.out.println("Employee Portal \n" + "Enter login information: \n"

				+ "[Exit]");

	}

}
