package com.ubb.postuniv.userInterface;

import com.ubb.postuniv.domain.*;
import com.ubb.postuniv.service.CarService;
import com.ubb.postuniv.service.ClientCardService;
import com.ubb.postuniv.service.TransactionService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class TextUI {
    private Scanner scanner;
    private CarService carService;
    private ClientCardService clientCardService;
    private TransactionService transactionService;
    private CarValidator carValidator;
    private ClientCardValidator clientCardValidator;
    private TransactionValidator transactionValidator;
    private DateTimeFormatter formatter;

    public TextUI(CarService carService, ClientCardService clientCardService, TransactionService transactionService,
                  CarValidator carValidator, ClientCardValidator clientCardValidator, TransactionValidator transactionValidator,
                  DateTimeFormatter formatter) {
        scanner = new Scanner(System.in);
        this.carService = carService;
        this.clientCardService = clientCardService;
        this.transactionService = transactionService;
        this.carValidator = carValidator;
        this.clientCardValidator = clientCardValidator;
        this.transactionValidator = transactionValidator;
        this.formatter = formatter;
    }

    public void start() {
        boolean mainMenu = true;
        boolean subMenu = false;
        String input;

        while (mainMenu) {
            printMainMenu();
            System.out.print("~ Please choose an option: ");
            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    subMenu = true;
                    while (subMenu) {
                        printCarSubMenu();
                        System.out.print("~ Please choose an option: ");
                        input = scanner.nextLine();

                        switch (input) {
                            case "1" -> addCar();
                            case "2" -> printCars();
                            case "3" -> printCar();
                            case "4" -> updateCar();
                            case "5" -> deleteCar();
                            case "0" -> subMenu = false;
                            default -> System.out.println("Please choose a valid option.");
                        }
                    }
                }
                case "2" -> {
                    subMenu = true;
                    while (subMenu) {
                        printClientCardSubMenu();
                        System.out.print("~ Please choose an option: ");
                        input = scanner.nextLine();

                        switch (input) {
                            case "1" -> addClientCard();
                            case "2" -> printClientCards();
                            case "3" -> printClientCard();
                            case "4" -> updateClientCard();
                            case "5" -> deleteClientCard();
                            case "0" -> subMenu = false;
                            default -> System.out.println("Please choose a valid option.");
                        }
                    }
                }
                /*case "3" -> {
                    subMenu = true;
                    while (subMenu) {
                        printTransactionSubMenu();
                        System.out.print("~ Please choose an option: ");
                        input = scanner.nextLine();

                        switch (input) {
                            case "1" -> addTransaction();
                            case "2" -> printTransactions();
                            case "3" -> printTransaction();
                            case "4" -> updateTransaction();
                            case "5" -> deleteTransaction();
                            case "0" -> subMenu = false;
                            default -> System.out.println("Please choose a valid option.");
                        }
                    }
                }*/
                case "0" -> {
                    System.out.println("Goodbye!");
                    mainMenu = false;
                }
                default -> System.out.println("Please choose a valid option.");
            }
        }
    }

    private void deleteClientCard() {
        String id;

        do {
            System.out.print("Enter existing client card's id: ");
            id = scanner.nextLine();

            try {
                clientCardValidator.validateIdForUpdate(id);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        clientCardService.delete(id);
        System.out.println("Deleted!");
    }

    private void updateClientCard() {
        String id, firstName, lastName, cnp, input;
        LocalDate birthDate, registrationDate;

        do {
            System.out.print("Enter existing client's card id (needs to be unique): ");
            id = scanner.nextLine();

            try {
                clientCardValidator.validateIdForUpdate(id);
                clientCardService.resetCnpInCaseItDoesNotChangeAtUpdate(id); // if we update a client card but want to keep its own cnp
                break;                                                       // we need to reset the cnp so the old one passes validator
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter client's first name (needs to be a string): ");
            firstName = scanner.nextLine();

            try {
                clientCardValidator.validateNameFormat(firstName);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter client's last name (needs to be a string): ");
            lastName = scanner.nextLine();

            try {
                clientCardValidator.validateNameFormat(lastName);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter client's CNP (needs to be unique): ");
            cnp = scanner.nextLine();

            try {
                clientCardValidator.validateCnp(cnp);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter client's birth date (\"dd.mm.yyyy\"): ");
            input = scanner.nextLine();

            try {
                birthDate = LocalDate.parse(input, formatter);
                break;
            } catch (DateTimeParseException dtpex) {
                System.out.println("Error: Invalid year format (needs to \"dd.mm.yyyy\"): " + input);
            }
        } while (true);

        do {
            System.out.print("Enter client's registration date (\"dd.mm.yyyy\"): ");
            input = scanner.nextLine();

            try {
                registrationDate = LocalDate.parse(input, formatter);
                break;
            } catch (DateTimeParseException dtpex) {
                System.out.println("Error: Invalid year format (needs to \"dd.mm.yyyy\"): " + input);
            }
        } while (true);

        clientCardService.update(id, firstName, lastName, cnp, birthDate, registrationDate);
        System.out.println("Added!");
    }

    private void printClientCard() {
        String id;

        do {
            System.out.print("Enter existing client card's id: ");
            id = scanner.nextLine();

            try {
                clientCardValidator.validateIdForUpdate(id);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        System.out.println("""
                -------------------------------------
                |     CLIENT CARD FOUND BY ID       |
                -------------------------------------""");
        System.out.println(clientCardService.get(id));
    }

    private void printClientCards() {
        System.out.println("""
                -------------------------------------
                |   ALL CLIENT CARDS ORDERED BY ID  |
                -------------------------------------""");
        for (ClientCard clientCard : clientCardService.getAll()) {
            System.out.println(clientCard);
        }
    }

    private void addClientCard() {
        String id, firstName, lastName, cnp, input;
        LocalDate birthDate, registrationDate;

        do {
            System.out.print("Enter client card id (needs to be unique): ");
            id = scanner.nextLine();

            try {
                clientCardValidator.validateIdForAdd(id);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter client's first name (needs to be a string): ");
            firstName = scanner.nextLine();

            try {
                clientCardValidator.validateNameFormat(firstName);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter client's last name (needs to be a string): ");
            lastName = scanner.nextLine();

            try {
                clientCardValidator.validateNameFormat(lastName);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter client's CNP (needs to be unique): ");
            cnp = scanner.nextLine();

            try {
                clientCardValidator.validateCnp(cnp);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter client's birth date (\"dd.mm.yyyy\"): ");
            input = scanner.nextLine();

            try {
                birthDate = LocalDate.parse(input, formatter);
                break;
            } catch (DateTimeParseException dtpex) {
                System.out.println("Error: Invalid year format (needs to \"dd.mm.yyyy\"): " + input);
            }
        } while (true);

        do {
            System.out.print("Enter client's registration date (\"dd.mm.yyyy\"): ");
            input = scanner.nextLine();

            try {
                registrationDate = LocalDate.parse(input, formatter);
                break;
            } catch (DateTimeParseException dtpex) {
                System.out.println("Error: Invalid year format (needs to \"dd.mm.yyyy\"): " + input);
            }
        } while (true);

        clientCardService.add(id, firstName, lastName, cnp, birthDate, registrationDate);
        System.out.println("Added!");
    }

    private void deleteCar() {
        String id;

        do {
            System.out.print("Enter existing car's id: ");
            id = scanner.nextLine();

            try {
                carValidator.validateIdForUpdate(id);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        carService.delete(id);
        System.out.println("Deleted!");
    }

    private void updateCar() {
        String id, model, input;
        int yearOfPurchase, km;
        boolean warranty;

        do {
            System.out.print("Enter existing car's id: ");
            id = scanner.nextLine();

            try {
                carValidator.validateIdForUpdate(id);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter car model (needs to be a string): ");
            model = scanner.nextLine();

            try {
                carValidator.validateModelFormat(model);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter car's year of purchase (needs to be a positive integer): ");
            input = scanner.nextLine();

            try {
                yearOfPurchase = Integer.parseInt(input);
                carValidator.validateYearOfPurchase(yearOfPurchase);
                break;
            } catch (NumberFormatException nfex) {
                System.out.println("Error: Invalid year format (needs to be a positive integer): " + input);
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter km's (needs to be a positive integer): ");
            input = scanner.nextLine();

            try {
                km = Integer.parseInt(input);
                carValidator.validateKm(km);
                break;
            } catch (NumberFormatException nfex) {
                System.out.println("Error: Invalid number format (needs to be a positive integer): " + input);
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        System.out.print("Enter warranty (True / any other value = False): ");
        input = scanner.nextLine();
        warranty = Boolean.parseBoolean(input);

        carService.update(id, model, yearOfPurchase, km, warranty);
        System.out.println("Updated!");
    }

    private void printCar() {
        String id;

        do {
            System.out.print("Enter existing car's id: ");
            id = scanner.nextLine();

            try {
                carValidator.validateIdForUpdate(id);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        System.out.println("""
                -------------------------------------
                |          CAR FOUND BY ID          |
                -------------------------------------""");
        System.out.println(carService.get(id));
    }

    private void printCars() {
        System.out.println("""
                -------------------------------------
                |        ALL CARS ORDERED BY ID      |
                -------------------------------------""");
        for (Car car : carService.getAll()) {
            System.out.println(car);
        }
    }

    private void addCar() {
        String id, model, input;
        int yearOfPurchase, km;
        boolean warranty;

        do {
            System.out.print("Enter car id (needs to be unique): ");
            id = scanner.nextLine();

            try {
                carValidator.validateIdForAdd(id);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter car model (needs to be a string): ");
            model = scanner.nextLine();

            try {
                carValidator.validateModelFormat(model);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter car's year of purchase (needs to be a positive integer): ");
            input = scanner.nextLine();

            try {
                yearOfPurchase = Integer.parseInt(input);
                carValidator.validateYearOfPurchase(yearOfPurchase);
                break;
            } catch (NumberFormatException nfex) {
                System.out.println("Error: Invalid year format (needs to be a positive integer): " + input);
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter km's (needs to be a positive integer): ");
            input = scanner.nextLine();

            try {
                km = Integer.parseInt(input);
                carValidator.validateKm(km);
                break;
            } catch (NumberFormatException nfex) {
                System.out.println("Error: Invalid number format (needs to be a positive integer): " + input);
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        System.out.print("Enter warranty (True / any other value = False): ");
        input = scanner.nextLine();
        warranty = Boolean.parseBoolean(input);

        carService.add(id, model, yearOfPurchase, km, warranty);
        System.out.println("Added!");
    }

    public void printMainMenu() {
        System.out.println("""
                -------------------------------------
                |             MAIN MENU             |
                -------------------------------------
                | 1. CRUD cars.                     |
                | 2. CRUD client cards.             |
                | 3. CRUD transactions.             |
                | 4.      -----------               |
                | 5.   UNDER CONSTRUCTION           |
                | 6.      -----------               |
                | 0. Exit.                          |
                -------------------------------------
                """);
    }

    public void printCarSubMenu() {
        System.out.println("""
                -------------------------------------
                |             CAR MENU              |
                -------------------------------------
                | 1. Add a new car.                 |
                | 2. Print all cars.                |
                | 3. Print car by id.               |
                | 4. Update a car's info.           |
                | 5. Delete a car.                  |
                | 0. Back.                          |
                -------------------------------------
               """);
    }

    private void printClientCardSubMenu() {
        System.out.println("""
                -------------------------------------
                |         CLIENT CARD MENU          |
                -------------------------------------
                | 1. Add a new client card.         |
                | 2. Print all client cards.        |
                | 3. Print client card by id.       |
                | 4. Update a client card's info.   |
                | 5. Delete a client card.          |
                | 0. Back.                          |
                -------------------------------------
               """);
    }

    private void printTransactionSubMenu() {
        System.out.println("""
                -------------------------------------
                |         TRANSACTION MENU          |
                -------------------------------------
                | 1. Add a new transaction.         |
                | 2. Print all transactions.        |
                | 3. Print transaction by id.       |
                | 4. Update a transaction's info.   |
                | 5. Delete a transaction.          |
                | 0. Back.                          |
                -------------------------------------
               """);
    }
}
