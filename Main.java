import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Item implements Serializable {
    private int id;
    private String name;
    private String description;
    private int quantity;
    private double price;

    // Constructor
    public Item(int id, String name, String description, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Description: " + description + ", Quantity: " + quantity + ", Price: $" + price;
    }
}

class Inventory implements Serializable {
    private ArrayList<Item> items;

    // Constructor
    public Inventory() {
        items = new ArrayList<>();
    }

    // Add item to inventory
    public void addItem(Item item) {
        items.add(item);
    }

    // Display all items in inventory
    public void displayInventory() {
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            for (Item item : items) {
                System.out.println(item);
            }
        }
    }

    // Update item quantity
    public void updateItemQuantity(int itemId, int newQuantity) {
        for (Item item : items) {
            if (item.getId() == itemId) {
                item.setQuantity(newQuantity);
                System.out.println("Quantity updated successfully.");
                return;
            }
        }
        System.out.println("Item not found.");
    }

    // Delete item from inventory
    public void deleteItem(int itemId) {
        for (Item item : items) {
            if (item.getId() == itemId) {
                items.remove(item);
                System.out.println("Item deleted successfully.");
                return;
            }
        }
        System.out.println("Item not found.");
    }

    // Search item by ID
    public void searchItemById(int itemId) {
        for (Item item : items) {
            if (item.getId() == itemId) {
                System.out.println(item);
                return;
            }
        }
        System.out.println("Item not found.");
    }

    // Search item by name
    public void searchItemByName(String itemName) {
        boolean found = false;
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                System.out.println(item);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Item not found.");
        }
    }

    // Save inventory to file
    public void saveInventoryToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(items);
            System.out.println("Inventory saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving inventory to file: " + e.getMessage());
        }
    }

    // Load inventory from file
    @SuppressWarnings("unchecked")
    public void loadInventoryFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            items = (ArrayList<Item>) ois.readObject();
            System.out.println("Inventory loaded from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading inventory from file: " + e.getMessage());
        }
    }
}

public class Main {
    private static final String INVENTORY_FILE = "inventory.dat";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Inventory inventory = new Inventory();

        // Load inventory from file
        inventory.loadInventoryFromFile(INVENTORY_FILE);

        boolean exit = false;
        while (!exit) {
            System.out.println("\n1. Add Item");
            System.out.println("2. Update Item Quantity");
            System.out.println("3. Delete Item");
            System.out.println("4. Search Item by ID");
            System.out.println("5. Search Item by Name");
            System.out.println("6. Display Inventory");
            System.out.println("7. Save Inventory to File");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addItem(scanner, inventory);
                    break;
                case 2:
                    updateItemQuantity(scanner, inventory);
                    break;
                case 3:
                    deleteItem(scanner, inventory);
                    break;
                case 4:
                    searchItemById(scanner, inventory);
                    break;
                case 5:
                    searchItemByName(scanner, inventory);
                    break;
                case 6:
                    inventory.displayInventory();
                    break;
                case 7:
                    inventory.saveInventoryToFile(INVENTORY_FILE);
                    break;
                case 8:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }

        // Save inventory to file before exiting
        inventory.saveInventoryToFile(INVENTORY_FILE);

        scanner.close();
    }

    private static void addItem(Scanner scanner, Inventory inventory) {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        inventory.addItem(new Item(id, name, description, quantity, price));
        System.out.println("Item added successfully.");
    }

    private static void updateItemQuantity(Scanner scanner, Inventory inventory) {
        System.out.print("Enter ID of item to update: ");
        int itemId = scanner.nextInt();
        System.out.print("Enter new quantity: ");
        int newQuantity = scanner.nextInt();
        inventory.updateItemQuantity(itemId, newQuantity);
    }

    private static void deleteItem(Scanner scanner, Inventory inventory) {
        System.out.print("Enter ID of item to delete: ");
        int itemId = scanner.nextInt();
        inventory.deleteItem(itemId);
    }

    private static void searchItemById(Scanner scanner, Inventory inventory) {
        System.out.print("Enter ID of item to search: ");
        int itemId = scanner.nextInt();
        inventory.searchItemById(itemId);
    }

    private static void searchItemByName(Scanner scanner, Inventory inventory) {
        System.out.print("Enter Name of item to search: ");
        String itemName = scanner.nextLine();
        inventory.searchItemByName(itemName);
    }
}
