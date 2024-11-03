    import java.io.*;
    import java.util.*;
    
    public class HouseCatalogManager {
        static Scanner scanner = new Scanner(System.in);
        static String fileName = "houses.txt";
    
        public static void main(String[] args) {
            int choice;
            do {
                System.out.println("\nHouse Catalog Manager");
                System.out.println("1. Add a House");
                System.out.println("2. View All Houses");
                System.out.println("3. Remove a House");
                System.out.println("4. Modify a House");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scanner.nextLine());
    
                switch (choice) {
                    case 1 -> addHouse();
                    case 2 -> viewHouses();
                    case 3 -> removeHouse();
                    case 4 -> modifyHouse();
                    case 5 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice, please try again.");
                }
            } while (choice != 5);
        }
    
        private static void addHouse() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                System.out.print("Enter house ID: ");
                String id = scanner.nextLine();
                System.out.print("Enter house location: ");
                String location = scanner.nextLine();
                System.out.print("Enter house price: ");
                String price = scanner.nextLine();
    
                writer.write(id + "," + location + "," + price);
                writer.newLine();
                System.out.println("House added successfully!");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }
    
        private static void viewHouses() {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                System.out.println("\nID | Location | Price");
                System.out.println("------------------------");
                while ((line = reader.readLine()) != null) {
                    String[] houseDetails = line.split(",");
                    System.out.println(houseDetails[0] + " | " + houseDetails[1] + " | " + houseDetails[2]);
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }
    
        private static void removeHouse() {
            System.out.print("Enter the ID of the house to remove: ");
            String idToRemove = scanner.nextLine();
            File inputFile = new File(fileName);
            File tempFile = new File("temp.txt");
    
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                String line;
                boolean found = false;
                while ((line = reader.readLine()) != null) {
                    String[] houseDetails = line.split(",");
                    if (!houseDetails[0].equals(idToRemove)) {
                        writer.write(line);
                        writer.newLine();
                    } else {
                        found = true;
                    }
                }
                if (found) {
                    System.out.println("House removed successfully!");
                } else {
                    System.out.println("House not found.");
                }
            } catch (IOException e) {
                System.out.println("Error processing file: " + e.getMessage());
            }
    
            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                System.out.println("Error updating file.");
            }
        }
    
        private static void modifyHouse() {
            System.out.print("Enter the ID of the house to modify: ");
            String idToModify = scanner.nextLine();
            File inputFile = new File(fileName);
            File tempFile = new File("temp.txt");
    
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                String line;
                boolean found = false;
                while ((line = reader.readLine()) != null) {
                    String[] houseDetails = line.split(",");
                    if (houseDetails[0].equals(idToModify)) {
                        System.out.print("Enter new location: ");
                        String newLocation = scanner.nextLine();
                        System.out.print("Enter new price: ");
                        String newPrice = scanner.nextLine();
                        writer.write(houseDetails[0] + "," + newLocation + "," + newPrice);
                        found = true;
                    } else {
                        writer.write(line);
                    }
                    writer.newLine();
                }
                if (found) {
                    System.out.println("House modified successfully!");
                } else {
                    System.out.println("House not found.");
                }
            } catch (IOException e) {
                System.out.println("Error processing file: " + e.getMessage());
            }
    
            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                System.out.println("Error updating file.");
            }
        }
    }
