public class Patient {
	
	// private
	private String m_number;
	private String m_name;
	private String m_procedureDate;
	private String m_notes;
	private String m_doctor;	
	
	private int m_procedureType;
	private int m_procedureTime;
	
	private char m_status;
	
	public Patient(	String number, String name,
					String procedureDate, int procedureType,
					String injuryDescription, String doctor) {
		
		// Initialise member variables
		m_number = number;
		m_name = name;
		m_procedureDate = procedureDate;
		m_procedureType = procedureType;
		m_doctor = doctor;
		
		// Initialise patient status
		m_status = 'S';
		
		// Initialise patient notes
		m_notes = new String();
		setNotes(injuryDescription);
	}
	
	// Accessors
	
	// Patient Number
	public String getNumber() {
		return m_number;
	}
	
	public void setNumber(String value) {
		m_number = value;
	}	
	
	// Patient Name
	public String getName() {
		return m_name;
	}
	
	public void setName(String value) {
		m_name = value;
	}
	
	// Procedure Date
	public String getProcedureDate() {
		return m_procedureDate;
	}
	
	public void setProcedureDate(String value) {
		m_procedureDate = value;
	}
	
	// Patient Notes
	public String getNotes() {
		return m_notes;
	}
	
	public void setNotes(String value) {
		m_notes = String.format("%s - %s %n", m_notes, value);
	}
	
	// Attending Doctor	
	public String getDoctor() {
		return m_doctor;
	}
	
	public void setDoctor(String value) {
		m_doctor = value;
	}
	
	// Remeber to remove!
	public char status() {
		return m_status;
	}
	
	// End Accessors
	
	// Calculates and returns the total charge for services rendered.
	public double calculateCharge() {
		// Determine hourly charge
		double _charge = 0.0;
		switch(m_procedureType) {
			case 1:
				_charge = 600.00;
				break;
			case 2:
				_charge = 800.00;
				break;
			case 3:
				_charge = 400.00;
				break;
			default:
				_charge = 0.00;
		}
		
		return m_procedureTime * _charge;
	}
	
	// Sets patient status to admitted (A) is patient is currently
	// Scheduled (S) and returns true if this is the case. Otherwise
	// returns false.
	public boolean admit() {
		if (m_status == 'S') {
			m_status = 'A';
			return true;
		} else {
			return false;	
		}
	}
	
	// Records details of a patient procedure
	public boolean recordProcedure(String notes, int length) {
		if ((m_status != 'A') && (m_status != 'R')) {
			// cannot record patient details on aninvalid status
			return false;
		}
		
		// set patient status to 'Recovery'
		m_status = 'R';
		
		// concatenate notes - NB. should abstract? Couuld potentially simplify
		// formatting.
		//m_notes = "%s - %s %n".format(m_notes, notes);
		setNotes(notes);
		
		// add the procedure length to total procedure time
		// ceil the time, as hours are charged 'as part thereof'
		m_procedureTime = m_procedureTime + length;
		
		return true;
	}
	
	// Discharge a patient, return the final invoice amount
	public double discharge() {
		if (m_status != 'R') {
			// not in recovery, cannot discharge
			return Double.NaN;
		} 
		
		m_status = 'D';
		
		return calculateCharge();
	}
	
	// Prints formatted patient details to screen
	public void printDetails() {
		// Convert the status to String
		String _status;
		switch (m_status) {
			case 'A':
				_status = "Admitted";
				break;
			case 'S':
				_status = "Scheduled";
				break;
			case 'D':
				_status = "Discharged";
				break;
			case 'R':
				_status = "Recovery";
				break;
			default:
				_status = "Undefined";
		} 		
		
		System.out.println();
		System.out.println("Patient Details:");
		System.out.println();
		
		// Basic details
		System.out.printf("%-25s %40s %n", "Patient No.:", getNumber());
        System.out.printf("%-25s %40s %n", "Patient Name:", getName());
        System.out.printf("%-25s %40s %n", "Patient Status:", _status);
		System.out.println();
		
		// Procedure Details
        System.out.printf("%-25s %40s %n", "Procedure Date:", getProcedureDate());
		System.out.printf("%-25s %40d %n", "Procedure Type:", m_procedureType);
		System.out.printf("%-25s %40s %n", "Doctor Assigned:", getDoctor());
		System.out.printf("%-25s %36d hrs %n", "Procedure Time:", m_procedureTime);
		System.out.println();
				
		// Invoice
		System.out.printf("%-25s %40s %n", "Invoice Charge:", String.format("$%.2f", calculateCharge()));
		System.out.println();
		
		// Notes
		System.out.println("Patient Notes:");
		System.out.printf("%n%s %n", getNotes());
	}
}