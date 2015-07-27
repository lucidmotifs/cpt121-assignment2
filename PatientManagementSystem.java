import java.util.Scanner;
import java.lang.Math;

public class PatientManagementSystem {
	private static Patient[] patients;
	 
    public static void main(String[] args) {
		
		// Variable to hold patient name from user input
		String patientNameInput;
		String patientNumberInput;
		
		// Varible to hold menu input selection
		char menuSelection;
		
		// Generic count varible used to track loops
		int count = 0;
		
		// Create Scanner instance to read user input
        Scanner in = new Scanner(System.in);
		
		// Create array of patients
		patients = new Patient[5];
		
		// Add patient records to array
		patients[0] = new Patient(	"14-0910", 
									"Macaffer, B.", 
									"06/09/14", 
									2, 
									"Knee Injury",
									"J. Feller");
									
		patients[1] = new Patient(	"15-0609", 
									"Judd, c.", 
									"12/06/15", 
									1, 
									"Knee Injury",
									"J. Feller");
									
		patients[2] = new Patient(	"15-0575", 
									"Macaffer, B.", 
									"22/05/15", 
									3, 
									"Knee Soreness",
									"J. Feller");
									
		patients[3] = new Patient(	"15-0613", 
									"Buckley, N.", 
									"22/05/15", 
									2, 
									"Hamstring Rupture",
									"B. Reid");
									
		patients[4] = new Patient(	"15-0592", 
									"Jaensch, M.", 
									"06/09/14", 
									2, 
									"Knee Injury",
									"R. Baird");
		
		System.out.println();
		System.out.println("Current Patient List:");
		System.out.println();						
		// Display basic patients details
		for (Patient p : patients) {			
		
			// Print out patient details
			System.out.println();
			System.out.printf("%-25s %20s %n", "Patient No.:", p.getNumber());
        	System.out.printf("%-25s %20s %n", "Patient Name:", p.getName());
			System.out.printf(
				"%-25s %20s %n", "Procedure Date:", p.getProcedureDate());
			System.out.printf("%-25s %20s %n", "Doctor Assigned:", 
				p.getDoctor());
			
		}
		
		// Display full history of a specific patient
		System.out.println();
        System.out.print("Enter Patient Name: ");
        patientNameInput = in.nextLine();
		System.out.println();
		
		System.out.printf("Patient History for %s %n", patientNameInput);
		System.out.println("------------------------------------");
		
		// reset count
		count = 0;	
		
		// Iterate though patients array and match patients by name.
		for (Patient p : patients) {
								
			// Print out procedure details for patient if name matches.
			if (p.getName().compareTo(patientNameInput) == 0) {
				// Increment count
				count++;
				
				System.out.printf(
					"%n %s %s %n", "Procedure Date: ", 
					p.getProcedureDate());
				System.out.printf("%s %s %n", "Doctor Assigned: ", 
					p.getDoctor());				
				System.out.printf("%n%s %n", p.getNotes());
			}
			
		}
		
		// If records found print amount of records, else print error.
		if (count > 0) {
			System.out.printf("(patient records found: %d) %n", count);
		} else {
			System.out.printf(
				"Error - no records found for %s %n", 
				patientNameInput);	
		}
		
		menuSelection = ' ';
        while (!(menuSelection == 'x' || menuSelection == 'X')) {
		
			// Patient tracking system menu
	        System.out.println();
	        System.out.println("Patient Management Menu");
	        System.out.println("-----------------------");
	        System.out.println();
	        System.out.println("A - Admit Patient");
	        System.out.println("B - Record Procedure");
			System.out.println("C - Discharge Patient");
			System.out.println("D - Display All Patient Records");
	        System.out.println("X - Exit");
	        System.out.println();
	           
	        // Menu option selection line		
	        System.out.print("Enter your selection: ");
	        menuSelection = in.next().charAt(0);
	        in.nextLine();
			
			// Store patient results here
			Patient p;
			
			switch(menuSelection) {
	            case 'a':
	            case 'A':
					// Admit Patient Section
					
					System.out.printf("%nEnter number of patient to be admitted: ");
					patientNumberInput = in.nextLine();				
	            	
					p = retrievePatient(patientNumberInput);
					// If the returned value is null there were no results
					if (p != null) {
						if (p.admit()) {
							System.out.printf(
								"Patient %s was admitted successfully. %n", 
								p.getName());
						} else {
							System.out.printf(
								"Error - patient %s has already been admitted! %n", 
								p.getName());
						}
					} else {
						// No result
						System.out.println("Error - patient number not found!");
					}
	            					
	            	break;
	            case 'b':
	            case 'B':
	            	// Record procedure section
					// Temporary variable for holding input
					String _input;
					
					System.out.printf("%nEnter number of patient number to update: ");
					patientNumberInput = in.nextLine();				
	            	
					p = retrievePatient(patientNumberInput);
					// If the returned value is null there were no results
					if (p != null) {
						// Declare variables for holding input
						String _description;
						int _length;
						
						System.out.printf("%nEnter procedure description: ");
						_description = in.nextLine();
						
						System.out.printf("%nEnter procedure length (in hours): ");
						_length = in.nextInt();
						in.nextLine();
						
						if (p.recordProcedure(_description, _length)) {
							System.out.printf(
								"%nProcedure details recorded successfully for "
								+ "patient %s %n", p.getName());
						} else {
							System.out.printf(
								"%nError - patient %s is not currently admitted! %n", 
								p.getName());
						}
					} else {
						// No result
						System.out.println("Error - patient number not found!");
					}
	            	break;
				case 'c':
	            case 'C':
	            	// Discharge patient section
					System.out.printf("%nEnter number of patient to be discharged: ");
					patientNumberInput = in.nextLine();				
	            	
					p = retrievePatient(patientNumberInput);
					// If the returned value is null there were no results
					if (p != null) {
						// Create variable for holding final invoice amount
						double _invoice = p.discharge();
						
						if (!Double.isNaN(_invoice)) {
							System.out.printf(
								"%nPatient %s has been discharge successfully. %n",
								p.getName());
							System.out.printf(
								"Final invoice amount: $%.2f %n", _invoice);
						} else {
							System.out.printf(
								"%nError - patient %s is not currently in recovery!"
								+ "%n", p.getName());
						}
					} else {
						// No result
						System.out.println("Error - patient number not found!");
					}
	            	break;
				case 'd':
	            case 'D':
	            	// Display details section
					System.out.printf(
						"%nSummary of all patient records in the system:%n");
					System.out.println();
					
					for (Patient _p : patients) {
						System.out.println(
						"----------------------------------------------");
						// Print out patient details.
						_p.printDetails();						
						
					}
	            	break;
	            case 'x':
	            case 'X':
					System.out.println();
	                System.out.println("Program exiting...bye!");
	                break;
	            default:
	                System.out.println("Error - Invalid menu selection!");
			}
				
		}
				
	}
	
	// Using patient number we will always only get one result.
	// Knowing this we can abstract the iteration through the array
	// and return a patient object to work on.
	public static Patient retrievePatient(String patientNumber) {
		Patient result = null;
		// Iterate though patients array and match patients by number.
		for (Patient p : patients) {
								
			// Print out procedure details for patient if name matches.
			if (p.getNumber().compareTo(patientNumber) == 0) {
				result = p;
				// If we get a result, return immediately.
				return result;
			}
			
		}
		
		// No records? Return null Patient
		return result;
	}
}