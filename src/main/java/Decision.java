public class Decision {
	private String proposition;
	private Rationale[] rationale;

	// contstructor
	public Decision(String proposition) {
		this.proposition = proposition;
		this.rationale = new Rationale[0];
	}

	public void addRationale(String justification) {
		Rationale newRationale[] = new Rationale[this.rationale.length + 1];
		newRationale = this.rationale;
		this.rationale = new Rationale[this.rationale.length + 1];
		System.out.println("New Rationale Added to your decision");
	}
}
