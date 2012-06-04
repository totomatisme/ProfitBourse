package profitbourse.modele.majweb;

public class ErreurCodeInconnu extends Exception {
	private static final long serialVersionUID = 1603078476899366696L;
	public ErreurCodeInconnu() {
		super("Le code ne correspond pas à un code connu !");
	}
}