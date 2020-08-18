package com.revature.util;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.revature.daos.UserDAO;
import com.revature.models.Account;
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
				operationMenu(loggedInUser);
			} else {
				mainMenu();
			}
		}
	}

	private void operationMenu(User u) {
		System.out.println("Welcome to Olivia's Bank! \n" + "What would you like to do? \n" + "[W]ithdraw \n"
				+ "[D]eposit \n" + "[T]ransfer \n" + "[U]pdate \n" + "[O]pen an account \n" + "[L]ogout");
		String answer = scan.nextLine();
		operationSwitch(answer, u);

	}

	private void operationSwitch(String answer, User u) {
		answer = answer.toLowerCase();

		switch (answer) {
		case "w":
			System.out.println("How much would you like to withdraw?");
			double amountW = scan.nextDouble();
			as.withdraw(amountW, loggedInUser.getId());
			
			break;
		case "d":
			System.out.println("How much would you like to deposit?");
			double amountD = scan.nextDouble();
			as.deposit(amountD, loggedInUser.getId());
			break;
		case "t":
			System.out.println("How much would you like to transfer?");
			double amountT = scan.nextDouble();
			scan.nextLine();
			System.out.println("Which account would you like to transfer to? Enter account id. ");
			int accountIdT = scan.nextInt();
			scan.nextLine();
			as.transfer(amountT, accountIdT);
			break;
		case "u":
			System.out.println("What would you like to update? ");
			//updateUserAccount(u);
			break;
		case "o":
			openAccount(u);
		case "l":
			loggedInUser = null;
			System.out.println("You are now logged out.");
			break;
		default:
			System.out.println("Error. Please type one of the three options listed. ");
			break;
		}
		
	}

	private void viewAllUserAccounts(User u) {
		System.out.println("You are viewing all of your accounts.");
		List<Account> list = as.findUserAccounts(u);
		for(Account a:list) {
			System.out.println(a);
		}	
		try {			
			operationMenu(u);
		} catch (InputMismatchException e) {
			System.out.println("catching InputMismatchException here");
			beginApp();
		}
	}

	private void openAccount(User u) {
		System.out.println("Do you want to open a [C]hecking or [S]avings?");
		String accountType = scan.nextLine();
		accountType = accountType.toLowerCase();
		Account a = null;
		
		switch(accountType) {
		case "c":
			a = new Account(1,"CHECKING", 0.0, u.getId(),u);
			as.insertAccount(a);
			viewAllUserAccounts(u);
			break;
		case "s":
			a = new Account(1, "SAVINGS", 0.0, u.getId(), u);
			as.insertAccount(a);
			viewAllUserAccounts(u);
			break;
		default:
			System.out.println("System error.");
			operationMenu(u);
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
		as.insertUser(u);
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
		System.out.println("Admin Portal \n" + "Enter login information: \n");
		System.out.println("Welcome admin, what would you like to do?");
		System.out.println("Here are all of the accounts");
		viewAccountsAsEmployeeAdmin();
		System.out.println("Update account Status including cancelling (s), Update account Balance (b), or Exit (e)");
		String rep = scan.nextLine();
		rep = rep.toLowerCase();
		switch(rep) {
		case "s":
			System.out.println("Which account do you want to update the status of?");
			int id = scan.nextInt();
			scan.nextLine();
			Account a = as.findAccountById(id);
			updateStatusAdmin(a);
			break;
		case "b":
			System.out.println("You are going to update the account balance of a user, admin.");
			adminUpdateAccount();
			break;
		case "e":
			System.out.println("Goodbye admin.");
			break;
		default:
			System.out.println("System error.");
			adminMenu();
			break;
		}

	}

	private void adminUpdateAccount() {
		System.out.println("Which account would you update?");
		int id = scan.nextInt();
		scan.nextLine();
		Account a = as.findAccountById(id);
		if (a.getStatus()==2) {			
			System.out.println("Do you want make a WITHDRAW (w), TRANSFER (t), or DEPOSIT (d), or Exit (e)?");
			String resp = scan.nextLine();
			resp = resp.toLowerCase();
			switch(resp) {
			case "w":
				System.out.println("What is the amount you want to withdraw?");
				double amountW = scan.nextDouble();
				scan.nextLine();
				a = new Account(a.getAccountId(),  a.getAccountType(), a.getBalance() - amountW, a.getStatus(), a.getUser());
				as.updateAccount(a);
				System.out.println(a);
				adminMenu();
				break;
			case "t":
				System.out.println("What is the amount you want to transfer?");
				double amountT = scan.nextDouble();
				scan.nextLine();
				System.out.println("What is the account id you want to transfer to?");
				int accountIDToTranfersTo = scan.nextInt();
				scan.nextLine();
				Account accountT = as.findAccountById(accountIDToTranfersTo);
				System.out.println("accountToTransferTo"+ accountT.getStatus());
				System.out.println("accountToTransferTo"+ accountT);
				if (accountT.getStatus()==2 && accountT != null) {
					
					a = new Account(a.getAccountId(),  a.getAccountType(), a.getBalance() - amountT, a.getStatus(), a.getUser());
					as.updateAccount(a);
					accountT = new Account(accountT.getAccountId(), accountT.getStatus(), accountT.getAccountType(),  accountT.getBalance() + amountT, accountT.getUser());
					as.updateAccount(accountT);
					System.out.println(a);
					adminMenu();
				} else {
					System.out.println("That's not an account available account");
					viewAccountsAsEmployeeAdmin();
				}
				break;
			case "d":
				System.out.println("What is the amount you want to deposit?");
				double amountD = scan.nextDouble();
				scan.nextLine();
				a = new Account(a.getAccountId(),  a.getStatus(), a.getAccountType(), a.getBalance() + amountD, a.getUser());
				as.updateAccount(a);
				System.out.println(a);
				adminMenu();
				break;
			case "e":
				System.out.println("Goodbye admin.");
				break;
			default:
				System.out.println("System error.");
				adminMenu();
				break;
			}
		} else {
			System.out.println("You cannot access this account because the status is not open.");
			adminMenu();
		}
	}

	private void updateStatusAdmin(Account a) {
		System.out.println("What do you want to change the status to be? Pending (p), Open (o), Cancel (c).");
		String resp = scan.nextLine();
		resp = resp.toLowerCase();
		switch(resp) {
		case "p":
			System.out.println("The account status will be pending.");
			Account acc = new Account(a.getAccountId(),  1, a.getAccountType(), a.getBalance(), a.getUser());
			as.updateAccount(acc);
			employeeLogoutMenu();
			break;
		case "o":
			System.out.println("The account status will be open.");
			Account acco = new Account(a.getAccountId(), 2, a.getAccountType(),a.getBalance(), a.getUser());
			as.updateAccount(acco);
			employeeLogoutMenu();
			break;
		case "c":
			System.out.println("The account status will be closed.");
			Account accC = new Account(a.getAccountId(),  3, a.getAccountType(), a.getBalance(), a.getUser());
			as.updateAccount(accC);
			employeeLogoutMenu();
			break;
		default:
			System.out.println("System error.");
			try {			
				adminMenu();
			} catch (InputMismatchException e) {
				System.out.println("catching InputMismatchException here");
				beginApp();
			}
			
			break;
		}
		
	}

	private void employeeMenu() {
		System.out.println("Employee Portal \n" + "Enter login information: \n" );
		System.out.println("What is your email? ");
		String email = scan.nextLine();
		System.out.println("What is your password? ");
		String password = scan.nextLine();

		User u = as.login(email, password);
		if (u == null) {
			System.out.println("Not a current employee or invalid credentials");
		} else {
			loggedInUser = u;
			System.out.println("You are now logged in");

		}
		System.out.println("As an employee, you are able to view account and user information.");
		System.out.println("Here are all of the account information.");
		viewAccountsAsEmployeeAdmin();
//		List<Account> list = ac.findAll();
//		for(Account a:list) {
//			System.out.println(a);
//		}
		try {
			employeeViewAccount();
		} catch (InputMismatchException e) {
			System.out.println("catching InputMismatchException here");
			beginApp();
		}
	}

	private void employeeViewAccount() {
		System.out.println("Do you want to view an account? Yes (y), No (n) means exit.");
		String in = scan.nextLine();
		in = in.toLowerCase();
		if (in.equals("y")) {
			System.out.println("Please enter the account id of the account you would like to view.");
			int id = scan.nextInt();
			scan.nextLine();
			Account a = as.findAccountById(id);
			System.out.println(a);
			employeeChangeStatusOfAccount(a);
		} else if (in.equals("n")){
			System.out.println("See ya later employee.");
		} else {
			System.out.println("Didn't get that.");
			employeeMenu();
		}
		
	}

	private void employeeChangeStatusOfAccount(Account a) {
		System.out.println("Do you want to change the status of this account? Yes (y), No (n).");
		String answer = scan.nextLine();
		answer = answer.toLowerCase();
		switch(answer) {
		case "y":
			updateStatusEmployee(a);
		case "n":
			System.out.println("Bye, employee");
			break;
		default:
			System.out.println("System error.");
			employeeMenu();
			break;
		}
		
	}

	private void updateStatusEmployee(Account a) {
		System.out.println("What do you want to change the status to be? Pending (p), Open (o).");
		String answer = scan.nextLine();
		answer = answer.toLowerCase();
		switch(answer) {
		case "p":
			System.out.println("The account status will be pending.");
			Account acc = new Account(a.getAccountId(),  1, a.getAccountType(),a.getBalance(), a.getUser());
			as.updateAccount(acc);
			employeeLogoutMenu();
			break;
		case "o":
			System.out.println("The account status will be open.");
			Account acco = new Account(a.getAccountId(), 2, a.getAccountType(), a.getBalance(),a.getUser());
			as.updateAccount(acco);
			employeeLogoutMenu();
			break;
		default:
			System.out.println("System error.");
			employeeMenu();
			break;
		}
		
		
	}

	private void employeeLogoutMenu() {
		System.out.println("Do you want to look another account? Yes (y), No (n).");
		String answer = scan.nextLine();
		answer = answer.toLowerCase();
		switch(answer) {
		case "y":
			employeeMenu();
			break;
		case "n":
			System.out.println("Goodbye, employee.");
			loggedInUser = null;
			System.out.println("You are now logged out.");
			break;
		default:
			System.out.println("System error.");
			employeeMenu();
			break;
		}
		
		
	}

	private void viewAccountsAsEmployeeAdmin() {
		List<Account> list = as.findAllAccounts();
		for(Account a:list) {
			System.out.println(a);
		}
		
	}
	
	}

