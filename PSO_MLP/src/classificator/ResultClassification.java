package classificator;

public class ResultClassification {

	private double FAR, FRR, pctCorrect, errorMLP;

	public double getFAR() {
		return FAR;
	}

	public void setFAR(double fAR) {
		FAR = fAR;
	}

	public double getFRR() {
		return FRR;
	}

	public void setFRR(double fRR) {
		FRR = fRR;
	}

	public double getPctCorrect() {
		return pctCorrect;
	}

	public void setPctCorrect(double pctCorrect) {
		this.pctCorrect = pctCorrect;
	}

	public double getErrorMLP() {
		return errorMLP;
	}

	public void setErrorMLP(double errorMLP) {
		this.errorMLP = errorMLP;
	}

}
