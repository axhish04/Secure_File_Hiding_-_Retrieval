package views;

import dao.UserDAO;
import model.User;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Welcome {
    public void welcomeScreen() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Welcome to the app");
            System.out.println("Press 1 to login");
            System.out.println("Press 2 to signup");
            System.out.println("Press 0 to exit");
            int choice = 0;
            try {
                choice = Integer.parseInt(br.readLine());
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            switch (choice) {
                case 1 -> login();
                case 2 -> signUp();
                case 0 -> System.exit(0);
                default -> System.out.println("Invalid choice. Please enter 0, 1, or 2.");
            }
        }
    }

    private void login() {
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter email");
        String email = sc.nextLine();
        System.out.println("Enter password");
        String password = sc.nextLine();
        if (UserService.validateUser(email, password)) {
            new UserView(email).home();
        } else {
            System.out.println("Invalid email or password");
        }
    }

    @SuppressWarnings("resource")
    private void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name");
        String name = sc.nextLine();
        System.out.println("Enter email");
        String email = sc.nextLine();
        System.out.println("Enter password");
        String password = sc.nextLine();
        try {
            if (UserDAO.isExists(email)) {
                System.out.println("User already exists");
                return;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("An error occurred. Please try again.");
            return;
        }
        User user = new User(name, email, password);
        int response = UserService.saveUser(user);
        switch (response) {
            case 0 -> System.out.println("User registered");
            case 1 -> System.out.println("User already exists");
            default -> System.out.println("An unexpected error occurred. Please try again.");
        }
    }
}
