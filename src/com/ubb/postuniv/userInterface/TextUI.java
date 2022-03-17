package com.ubb.postuniv.userInterface;

import com.ubb.postuniv.domain.*;
import com.ubb.postuniv.service.CarService;
import com.ubb.postuniv.service.ClientCardService;
import com.ubb.postuniv.service.TransactionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class TextUI {
    private Scanner scanner;
    private CarService carService;
    private ClientCardService clientCardService;
    private TransactionService transactionService;
    private CarValidator carValidator;
    private ClientCardValidator clientCardValidator;
    private TransactionValidator transactionValidator;
    private DateTimeFormatter dateFormatter;
    private DateTimeFormatter dateTimeFormatter;

    public TextUI(CarService carService, ClientCardService clientCardService, TransactionService transactionService,
                  CarValidator carValidator, ClientCardValidator clientCardValidator, TransactionValidator transactionValidator,
                  DateTimeFormatter dateFormatter, DateTimeFormatter dateTimeFormatter) {
        scanner = new Scanner(System.in);
        this.carService = carService;
        this.clientCardService = clientCardService;
        this.transactionService = transactionService;
        this.carValidator = carValidator;
        this.clientCardValidator = clientCardValidator;
        this.transactionValidator = transactionValidator;
        this.dateFormatter = dateFormatter;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public void start() {
        boolean mainMenu = true;
        boolean subMenu;
        String input;

        while (mainMenu) {
            printMainMenu();
            System.out.print("~ ");
            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    subMenu = true;
                    while (subMenu) {
                        printCarSubMenu();
                        System.out.print("~ ");
                        input = scanner.nextLine();

                        switch (input) {
                            case "1" -> addCar();
                            case "2" -> printCars();
                            case "3" -> printCar();
                            case "4" -> updateCar();
                            case "5" -> deleteCar();
                            case "0" -> subMenu = false;
                            default -> System.out.println("Error: Please choose a valid option.");
                        }
                    }
                }
                case "2" -> {
                    subMenu = true;
                    while (subMenu) {
                        printClientCardSubMenu();
                        System.out.print("~ ");
                        input = scanner.nextLine();

                        switch (input) {
                            case "1" -> addClientCard();
                            case "2" -> printClientCards();
                            case "3" -> printClientCard();
                            case "4" -> updateClientCard();
                            case "5" -> deleteClientCard();
                            case "0" -> subMenu = false;
                            default -> System.out.println("Error: Please choose a valid option.");
                        }
                    }
                }
                case "3" -> {
                    subMenu = true;
                    while (subMenu) {
                        printTransactionSubMenu();
                        System.out.print("~ ");
                        input = scanner.nextLine();

                        switch (input) {
                            case "1" -> addTransaction();
                            case "2" -> printTransactions();
                            case "3" -> printTransaction();
                            case "4" -> updateTransaction();
                            case "5" -> deleteTransaction();
                            case "0" -> subMenu = false;
                            default -> System.out.println("Error: Please choose a valid option.");
                        }
                    }
                }
                case "4" -> searchCarsAndClients();
                case "5" -> printTransactionsBetweenBounds();
                case "6" -> printCarsOrderedDescendingBySumOfLaborPrice();
                case "7" -> printClientCardsOrderedDescendingBySumOfDiscounts();
                case "8" -> deleteTransactionsBetweenGivenDates();
                case "9" -> updateAllCarWarranties();
                case "0" -> {
                    System.out.println("Goodbye!");
                    mainMenu = false;
                }
                default -> System.out.println("Error: Please choose a valid option.");
            }
        }
    }

    private void updateAllCarWarranties() {
        int count = carService.updateAllCarWarranties();
        System.out.println(count + " Car warranties updated!");
    }

    private void deleteTransactionsBetweenGivenDates() {
        LocalDateTime lowerBoundDate, upperBoundDate;
        String input;

        do {
            System.out.print("Enter the lower bound date and time (\"dd.mm.yyyy HH:mm\"): ");
            input = scanner.nextLine();

            try {
                lowerBoundDate = LocalDateTime.parse(input, dateTimeFormatter);
                break;
            } catch (DateTimeParseException dtpex) {
                System.out.println("Error: Invalid date and time format (\"dd.mm.yyyy HH:mm\"): " + input);
            }
        } while (true);

        do {
            System.out.print("Enter the upper bound date and time (\"dd.mm.yyyy HH:mm\"): ");
            input = scanner.nextLine();

            try {
                upperBoundDate = LocalDateTime.parse(input, dateTimeFormatter);
                break;
            } catch (DateTimeParseException dtpex) {
                System.out.println("Error: Invalid date and time format (\"dd.mm.yyyy HH:mm\"): " + input);
            }
        } while (true);

        int count = transactionService.deleteTransactionsBetweenGivenBounds(lowerBoundDate, upperBoundDate);
        System.out.println(count + " Transactions deleted!");
    }

    private void printClientCardsOrderedDescendingBySumOfDiscounts() {
        System.out.println("""
                ---------------------------------------------------
                | ALL CLIENT CARDS ORD. DESC. BY SUM OF DISCOUNTS |
                ---------------------------------------------------""");

        clientCardService.getClientCardsOrderedDescendingBySumOfDiscounts().forEach(System.out::println);
    }

    private void printCarsOrderedDescendingBySumOfLaborPrice() {
        System.out.println("""
                ---------------------------------------
                | ALL CARS ORD. DESC. BY SUM OF LABOR |
                ---------------------------------------""");

        carService.getCarsOrderedDescendingBySumOfLaborPrice().forEach(System.out::println);
    }

    private void printTransactionsBetweenBounds() {
        double lower, upper;

        do {
            try {
                System.out.print("Enter lower bound (double): ");
                lower = Double.parseDouble(scanner.nextLine());
                System.out.print("Enter upper bound (double): ");
                upper = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NullPointerException npex) {
                System.out.println("Error: input cannot be empty string.");
            } catch (NumberFormatException nfex) {
                System.out.println("Error: input needs to be a number.");
            }
        } while (true);

        System.out.println("""
                -------------------------------------
                |  ALL TRANSACTIONS BETWEEN BOUNDS  |
                -------------------------------------""");

        transactionService.getTransactionsBetweenBounds(lower, upper).forEach(System.out::println);
    }

    private void searchCarsAndClients() {
        String input;

        do {
            System.out.print("Enter search term: ");
            input = scanner.nextLine();

            if (input.isEmpty() || input.isBlank()) {
                System.out.println("Error: search term cannot be null or empty spaces.");
            } else {
                break;
            }

        } while (true);

        System.out.println("""
                -------------------------------------
                |          SEARCH RESULTS           |
                -------------------------------------""");

        AtomicInteger resultCount = new AtomicInteger(0);
        carService.getCarsFullTextSearch(input).forEach(car -> {
            System.out.println(car);
            resultCount.getAndIncrement();
        });

        clientCardService.getClientCardsFullTextSearch(input, dateFormatter).forEach(card -> {
            System.out.println(card);
            resultCount.getAndIncrement();
        });

        if (resultCount.get() == 0) {
            System.out.println("No element found for search term: " + input);
        }
    }

    private void deleteTransaction() {
        String id;

        do {
            System.out.print("Enter transaction id (needs to be unique): ");
            id = scanner.nextLine();

            try {
                transactionValidator.validateIdForAdd(id);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        transactionService.delete(id);
        System.out.println("Deleted!");
    }

    private void updateTransaction() {
        String id, carId, clientCardId, input;
        double partPrice, laborPrice;
        LocalDateTime dateAndTime;

        do {
            System.out.print("Enter transaction id (needs to be unique): ");
            id = scanner.nextLine();

            try {
                transactionValidator.validateIdForUpdate(id);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter car id (needs to be valid): ");
            carId = scanner.nextLine();

            try {
                transactionValidator.validateCarId(id);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter client card id (needs to be blank or valid): ");
            clientCardId = scanner.nextLine();

            try {
                transactionValidator.validateClientCardIdWhichCanBeNull(id);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter the price of parts (needs to be a double): ");
            input = scanner.nextLine();

            try {
                partPrice = Double.parseDouble(input);
                break;
            } catch (NumberFormatException nfex) {
                System.out.println("Error: Invalid price format (needs to be a double): " + input);
            }
        } while (true);

        do {
            System.out.print("Enter the price of labor (needs to be a double): ");
            input = scanner.nextLine();

            try {
                laborPrice = Double.parseDouble(input);
                break;
            } catch (NumberFormatException nfex) {
                System.out.println("Error: Invalid price format (needs to be a double): " + input);
            }
        } while (true);

        do {
            System.out.print("Enter the transaction's date and time (\"dd.mm.yyyy HH:mm\"): ");
            input = scanner.nextLine();

            try {
                dateAndTime = LocalDateTime.parse(input, dateTimeFormatter);
                break;
            } catch (DateTimeParseException dtpex) {
                System.out.println("Error: Invalid date and time format (\"dd.mm.yyyy HH:mm\"): " + input);
            }
        } while (true);

        transactionService.update(id, carId, clientCardId, partPrice, laborPrice, dateAndTime);
        System.out.println("Updated!");

        printInvoice(transactionService.getInvoice(carId, clientCardId, partPrice, laborPrice));
    }

    private void printTransaction() {
        String id;

        do {
            System.out.print("Enter transaction id (needs to be unique): ");
            id = scanner.nextLine();

            try {
                transactionValidator.validateIdForUpdate(id);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        System.out.println(transactionService.get(id));
    }

    private void printTransactions() {
        System.out.println("""
                -------------------------------------
                |   ALL TRANSACTIONS ORDERED BY ID  |
                -------------------------------------""");

        transactionService.getAll().forEach(System.out::println);
    }

    private void addTransaction() {
        String id, carId, clientCardId, input;
        double partPrice, laborPrice;
        LocalDateTime dateAndTime;

        do {
            System.out.print("Enter transaction id (needs to be unique): ");
            id = scanner.nextLine();

            try {
                transactionValidator.validateIdForAdd(id);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter car id (needs to be valid): ");
            carId = scanner.nextLine();

            try {
                transactionValidator.validateCarId(id);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter client card id (needs to be blank or valid): ");
            clientCardId = scanner.nextLine();

            try {
                transactionValidator.validateClientCardIdWhichCanBeNull(clientCardId);
                break;
            } catch (RuntimeException rex) {
                System.out.println(rex.getMessage());
            }
        } while (true);

        do {
            System.out.print("Enter the price of parts (needs to be a double): ");
            input = scanner.nextLine();

            try {
                partPrice = Double.parseDouble(input);
                break;
            } catch (NumberFormatException nfex) {
                System.out.println("Error: Invalid price format (needs to be a double): " + input);
            }
        } while (true);

        do {
            System.out.print("Enter the price of labor (needs to be a double): ");
            input = scanner.nextLine();

            try {
                laborPrice = Double.parseDouble(input);
                break;
            } catch (NumberFormatException nfex) {
                System.out.println("Error: Invalid price format (needs to be a double): " + input);
            }
        } while (true);

        do {
            System.out.print("Enter the transaction's date and time (\"dd.mm.yyyy HH:mm\"): ");
            input = scanner.nextLine();

            try {
                dateAndTime = LocalDateTime.parse(input, dateTimeFormatter);
                break;
            } catch (DateTimeParseException dtpex) {
                System.out.println("Error: Invalid date and time format (\"dd.mm.yyyy HH:mm\"): " + input);
            }
        } while (true);

        transactionService.add(id, carId, clientCardId, partPrice, laborPrice, dateAndTime);
        System.out.println("Added!");

        printInvoice(transactionService.getInvoice(carId, clientCardId, partPrice, laborPrice));
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
                birthDate = LocalDate.parse(input, dateFormatter);
                break;
            } catch (DateTimeParseException dtpex) {
                System.out.println("Error: Invalid year format (needs to \"dd.mm.yyyy\"): " + input);
            }
        } while (true);

        do {
            System.out.print("Enter client's registration date (\"dd.mm.yyyy\"): ");
            input = scanner.nextLine();

            try {
                registrationDate = LocalDate.parse(input, dateFormatter);
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

        clientCardService.getAll().forEach(System.out::println);
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
                birthDate = LocalDate.parse(input, dateFormatter);
                break;
            } catch (DateTimeParseException dtpex) {
                System.out.println("Error: Invalid date format (needs to \"dd.mm.yyyy\"): " + input);
            }
        } while (true);

        do {
            System.out.print("Enter client's registration date (\"dd.mm.yyyy\"): ");
            input = scanner.nextLine();

            try {
                registrationDate = LocalDate.parse(input, dateFormatter);
                break;
            } catch (DateTimeParseException dtpex) {
                System.out.println("Error: Invalid date format (needs to \"dd.mm.yyyy\"): " + input);
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

        carService.getAll().forEach(System.out::println);
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
                -------------------------------------------
                |                MAIN MENU                |
                -------------------------------------------
                | 1. CRUD cars.                           |
                | 2. CRUD client cards.                   |
                | 3. CRUD transactions.                   |
                | 4. Search for cars & clients.           |
                | 5. Print transact. between bounds.      |
                | 6. Print cars ord. by sum of labor.     |
                | 7. Print cards ord. by sum of discounts.|
                | 8. Delete transact. between bounds.     |
                | 9. Update all car warranties.           |
                | 0. Exit.                                |
                -------------------------------------------""");
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
                -------------------------------------""");
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
                -------------------------------------""");
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
                -------------------------------------""");
    }

    private void printInvoice(Invoice invoice) {
        System.out.println("""
                -------------------------------------
                |              INVOICE              |
                -------------------------------------""");

        StringBuilder sb = new StringBuilder();
        double partPrice = invoice.getPartPrice();
        double laborPrice = invoice.getLaborPrice();
        double partDiscount = invoice.getPartDiscount();
        double laborDiscount = invoice.getLaborDiscount();
        double finalPartPrice = invoice.getFinalPartPrice();
        double finalLaborPrice = invoice.getFinalLaborPrice();

        sb.append("Total products and services = $").append(partPrice+laborPrice).append("\n");

        if (partDiscount != 0) {
            sb.append("Discount: Warranty: -$").append(partDiscount).append("\n");
        }

        if (laborDiscount != 0) {
            sb.append("Discount: Client card: -$").append(laborDiscount).append("\n");
        }

        if (partDiscount == 0 && laborDiscount ==0) {
            sb.append("Transaction not eligible for discounts.").append(laborDiscount).append("\n");
        }

        sb.append("Total = $").append(finalPartPrice + finalLaborPrice);

        System.out.println(sb);
    }
}
