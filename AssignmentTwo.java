import java.util.Scanner;
import java.lang.Math;

public class AssignmentTwo { 
    public static void main(String[] args) {
		
		// Create and test patient object
		Patient p = new Patient("14-0910", 
								"Macaffer, B.", 
								"02/05/13", 
								2, 
								"Suspected ACL Rupture",
								"J. Feller");
								
		p.admit();
		if (p.recordProcedure("Did Operation!", 3)) {
			System.out.println("Added Procedure");
			p.printDetails();
		} else {
			System.out.println("Failed to add procedure.");
			System.out.printf("Patient Status: %s", p.status());
		}
		
	}
}