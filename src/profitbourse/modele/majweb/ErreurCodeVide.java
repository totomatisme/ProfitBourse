package profitbourse.modele.majweb;

public class ErreurCodeVide extends Exception {
	private static final long serialVersionUID = 8447309210674959240L;
	public ErreurCodeVide() {
		super("Le code est vide !");
	}
}