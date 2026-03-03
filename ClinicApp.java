/*
 * Clinic Management System
 * Features:
 * - Add Patient
 * - Search Patient
 * - Delete Patient
 * - Clear All
 * - Count Patients
 * - File Persistence
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Patient {
    private int id;
    private String name;
    private int age;

    public Patient(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }
    public String getName() {
    return name;
}

public int getAge() {
    return age;
}

    public void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }
}

public class ClinicApp {
    // Display menu options to the user
    public static void showMenu() { 
            System.out.println("\n1.Add patient details");
            System.out.println("2.View all patients");
            System.out.println("3.Search patient by ID");
            System.out.println("4.Delete patient by ID");
            System.out.println("5.Show total number of patients");
            System.out.println("6.Clear all patients");
            System.out.println("7.Exit");

}
// check if patient ID already exists to prevent duplicates
public static boolean isDuplicateId(ArrayList<Patient> patients, int id) {
        for (Patient p : patients) {
            if (p.getId() == id) {
                return true;
            }
        }
        return false;
    }
    // save patient data into a text file in append mode to ensure data is not overwritten and can be retrieved later ans csv format
    //  is used for easy parsing when loading data back into the application(id,name,age)
    public static void savePatientsToFile(ArrayList<Patient> patients) {
        try (FileWriter writer = new FileWriter("patients.txt")) {
            for (Patient p : patients) {
                writer.write(p.getId() + "," + p.getName() + "," + p.getAge() + "\n");
            }
            System.out.println("Patients saved to file successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while saving patients to file.");
            e.printStackTrace();
        }
    }
// Loads patient data from file when program starts
public static void loadPatientsFromFile(ArrayList<Patient> patients) {
        try (BufferedReader br = new BufferedReader(new FileReader("patients.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");
                if (data.length == 3) {
                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    int age = Integer.parseInt(data[2]);

                    Patient p = new Patient(id, name, age);
                    patients.add(p);
                }
                
            }
            br.close();
        } catch (IOException e) {
            System.out.println("No existing patient data found.");
        }
    }
    public static void main(String[] args) {

        ArrayList<Patient> patients = new ArrayList<>();
        loadPatientsFromFile(patients);
        Scanner sc = new Scanner(System.in);

        while (true) {
            showMenu();

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {

                System.out.print("Enter patient ID: ");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter patient name: ");
                String name = sc.nextLine();

                System.out.print("Enter patient age: ");
                int age = sc.nextInt();
                sc.nextLine();

               if (isDuplicateId(patients, id)) {
                System.out.println("Patient ID already exists! Try different ID.");
                } else {
                    Patient p = new Patient(id, name, age);
                    patients.add(p);
                    savePatientsToFile(patients);
                    System.out.println("Patient added successfully!");
                }

            } else if (choice == 2) {

                if (patients.isEmpty()) {
                    System.out.println("No patients found.");
                } else {
                    for (Patient p : patients) {
                        System.out.println("\n------- Patient Found -------");
                        p.display();
                        System.out.println("-----");
                    }
                }

            } 
            else if (choice == 3){
                System.out.print("Enter patient ID to search: ");
                int searchId = sc.nextInt();
                sc.nextLine();

                boolean found = false;

                for (Patient p : patients) {
                    if (p.getId() == searchId) {
                        System.out.println("\n------- Patient Found -------");
                        p.display();
                        System.out.println("-----");
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("No patient found with ID " + searchId);
                }

            } else if (choice == 4) {

                if (patients.isEmpty()) {
                    System.out.println("No patients to delete.");
                } else {

                    System.out.print("Enter patient ID to delete: ");
                    int deleteId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Are you sure you want to delete patient with ID " + deleteId + "? (Y/N): ");
                    String confirmation = sc.nextLine();

                    if(confirmation.equalsIgnoreCase("Y")) {

                    boolean deleted = false;

                    for (int i = 0; i < patients.size(); i++) {
                        if (patients.get(i).getId() == deleteId) {
                            patients.remove(i);
                            deleted = true;
                            System.out.println("Patient deleted successfully!");
                            break;
                        }
                    }

                    if (!deleted) {
                        System.out.println("Patient not found.");
                    }
                }else {
                    System.out.println("Deletion cancelled.");
                }
                

            }
        } else if (choice == 5) {
    System.out.println("Total Patients: " + patients.size());

} else if (choice == 6){
    if (patients.isEmpty()) {
        System.out.println("No patients to clear.");
    } else {
        System.out.print("Are you sure you want to delete all patients? (Y/N): ");
        String confirmation = sc.nextLine();

        if(confirmation.equalsIgnoreCase("Y")) {
            patients.clear();
            savePatientsToFile(patients);
            System.out.println("All patients deleted successfully!");
        } else {
            System.out.println("Delete operation cancelled.");
        }
    }
}
 else if (choice == 7) {
                break;

            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        sc.close();
    }
}