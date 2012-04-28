package profitbourse;

/* -*-Java-*-
 * ###################################################################
 * 
 *  FILE: "Console.java"
 *                                    created: 2002-07-16 17:38:50 
 *                                last update: 2003-05-05 15:17:39 
 *  Author: Frédéric Boulanger
 *  E-mail: Frederic.Boulanger@supelec.fr
 *    mail: Supélec -- Service Informatique
 *          Plateau de Moulon, 3 rue Joliot-Curie, F-91912 Gif-sur-Yvette cedex
 *     www: http://wwwsi.supelec.fr/fb/
 *  
 *  Description: 
 * 
 *  History
 * 
 *  modified   by  rev reason
 *  ---------- --- --- -----------
 *  2002-07-16 FBO 1.0 original
 *  2002-07-17 FBO 1.1 Console est abstraite (pas d'instanciation)
 *  2002-07-18 FBO 1.2 Nettoyage, messages d'erreur pour les nombres (YN)
 *  2003-05-05 FBO 1.3 Nouvelle méthode sauterBlancsDansLigne
 * ###################################################################
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.HashMap;

/**
 * Classe permettant des entrées-sorties simplifiées sur la console.
 */
public abstract class Console {
	/** Ouvre un nouveau flux de caractères sur <code>System.in</code>. */
	public static void reouvrir() {
		ligne_ = null;
		position_ = 0;
		longueur_ = 0;
		dernierToken_ = null;
		in_ = new BufferedReader(new InputStreamReader(System.in));
		eof_ = false;
	}

	/**
	 * Rend un caractère lu sur la console. Le caractère n'a vraiment été lu que
	 * si eof() est faux après la lecture.
	 */
	public static char lireChar() {
		if (suivantExiste()) {
			return ligne_.charAt(position_++);
		} else {
			return '*'; // on peut rendre n'importe quoi
		}
	}

	/**
	 * Annule l'effet de la dernière invocation de <code>lireChar</code>. Ne
	 * fonctionne que si la dernière methode de lecture invoquée est
	 * <code>lireChar</code> et que <code>eof</code> était faux lors de cette
	 * invocation. Le comportement de <code>remettreDernierChar</code> est
	 * indéfini dans les autres cas.
	 */
	public static void remettreDernierChar() {
		if ((ligne_ == null) || (position_ == 0)) {
			// Si le dernier lireChar() a rencontré EOF, on annule
			eof_ = false;
		} else {
			// Sinon, on revient en arrière d'un caractère
			position_--;
		}
	}

	/**
	 * Ignore tous les caractères restant sur la ligne si on a déjà lu au moins
	 * un caractère de la ligne.
	 */
	public static void jeterResteLigne() {
		if (position_ > 0) {
			position_ = longueur_;
		}
	}

	/**
	 * Ignore tous les caractères "blancs" (espace, tabulation etc.) Après
	 * l'invocation de cette méthode, on est soit en fin de flux, soit sur un
	 * caractère non-blanc.
	 */
	public static void sauterLesBlancs() {
		char c;

		do {
			c = lireChar();
		} while ((!eof()) && Character.isWhitespace(c));

		if (!eof()) {
			remettreDernierChar();
		}
	}

	/**
	 * Ignore tous les caractères "blancs" dans la ligne courante. Après
	 * l'invocation de cette méthode, on est soit en fin de flux, soit en fin de
	 * ligne, soit sur un caractère non-blanc.
	 */
	public static void sauterBlancsDansLigne() {
		if (!suivantExiste()) {
			return;
		}

		while ((position_ < (longueur_ - 1))
				&& Character.isWhitespace(ligne_.charAt(position_))) {
			position_++;
		}
	}

	/** Rend un mot (suite de caractères sans "blanc") lu sur la console. */
	public static String lireMot() {
		sauterLesBlancs();
		if (eof()) {
			return "";
		}
		int blanc = position_;
		while ((blanc < longueur_)
				&& (!Character.isWhitespace(ligne_.charAt(blanc)))) {
			blanc++;
		}
		String mot = ligne_.substring(position_, blanc);
		position_ = blanc;
		return mot;
	}

	/**
	 * Rend une chaîne contenant tous les caractères rencontrés sur le flux
	 * jusqu'à une fin de ligne ou la fin du flux. L'éventuel marqueur de fin de
	 * ligne ne fait pas partie de la chaîne rendue.
	 */
	public static String lireLigne() {
		if (!suivantExiste()) { // assure que le tampon est rempli,
			return ""; // contrairement à eof().
		}
		String lue = ligne_.substring(position_, longueur_ - 1);
		position_ = longueur_;
		return lue;
	}

	/**
	 * Rend un booléen dont la représentation est lue à la console. Re-essaye
	 * tant que les caractères lus ne peuvent pas être interprétés comme un
	 * booléen et que la fin du flux de caractères n'est pas rencontrée. Les
	 * mots <code>"true"</code> et <code>"t"</code> sont interprétés comme la
	 * valeur booléenne <code>true</code>, sans tenir compte de la casse. Les
	 * mots <code>"false"</code> et <code>"f"</code> sont interprétés comme la
	 * valeur booléenne <code>false</code>, sans tenir compte de la casse.
	 */
	public static boolean lireBool() {
		sauterLesBlancs();
		while (true) {
			String mot = lireMot();
			if (mot == null) {
				return false;
			}
			if (mot.equalsIgnoreCase("true") || mot.equalsIgnoreCase("t")) {
				return true;
			}
			if (mot.equalsIgnoreCase("false") || mot.equalsIgnoreCase("f")) {
				return false;
			}
			System.err.println("Erreur : booleen '" + mot
					+ "' incorrect; ignore.");
		}
	}

	/**
	 * Rend un octet dont la représentation décimale est lue sur la console.
	 * Re-essaie tant que les caractères lus ne constituent pas la
	 * représentation d'un entier, ou si l'entier correspondant ne peut pas être
	 * codé sur un octet.
	 */
	public static byte lireOctet() {
		format_.setParseIntegerOnly(true); // on cherche à lire un entier
		while (!eof()) { // tant qu'on peut ré-essayer
			Number n = lireNombre(); // on cherche à lire un nombre
			if (n == null) { // si le format est incorrect
				continue; // on ré-essaye
			}
			if (verifBornes(n, "octet")) { // si ce qu'on a lu tient dans un
											// octet
				return n.byteValue(); // on le rend, sinon, on ré-essaye
			}
		}
		return -1; // il faut bien rendre quelquechose...
	}

	/**
	 * Rend un short dont la représentation décimale est lue sur la console.
	 * Re-essaie tant que les caractères lus ne constituent pas la
	 * représentation d'un entier, ou si l'entier correspondant ne peut pas être
	 * codé sur un short.
	 */
	public static short lireShort() {
		format_.setParseIntegerOnly(true); // idem que lireOctet()
		while (!eof()) {
			Number n = lireNombre();
			if (n == null) {
				continue;
			}
			if (verifBornes(n, "short")) {
				return n.shortValue();
			}
		}
		return -1;
	}

	/**
	 * Rend un int dont la représentation décimale est lue sur la console.
	 * Re-essaie tant que les caractères lus ne constituent pas la
	 * représentation d'un entier, ou si l'entier correspondant ne peut pas être
	 * codé sur un int.
	 */
	public static int lireInt() {
		format_.setParseIntegerOnly(true); // idem que lireOctet()
		while (!eof()) {
			Number n = lireNombre();
			if (n == null) {
				continue;
			}
			if (verifBornes(n, "int")) {
				return n.intValue();
			}
		}
		return -1;
	}

	/**
	 * Rend un long dont la représentation décimale est lue sur la console.
	 * Re-essaie tant que les caractères lus ne constituent pas la
	 * représentation d'un entier, ou si l'entier correspondant ne peut pas être
	 * codé sur un long.
	 */
	public static long lireLong() {
		format_.setParseIntegerOnly(true); // idem que lireOctet()
		while (!eof()) {
			Number n = lireNombre();
			if (n == null) {
				continue;
			}
			if (verifBornes(n, "long")) {
				return n.longValue();
			}
		}
		return -1;
	}

	/**
	 * Rend un float dont la représentation décimale est lue sur la console.
	 * Re-essaie tant que les caractères lus ne constituent pas la
	 * représentation d'un flottant, ou si le flottant correspondant ne peut pas
	 * être codé sur un float.
	 */
	public static float lireFloat() {
		format_.setParseIntegerOnly(false); // ici, on accepte les non-entiers
		while (!eof()) {
			Number n = lireNombre();
			if (n == null) {
				continue;
			}
			if (verifBornes(n, "float")) {
				return n.floatValue();
			}
		}
		return -1.0f;
	}

	/**
	 * Rend un double dont la représentation décimale est lue sur la console.
	 * Re-essaie tant que les caractères lus ne constituent pas la
	 * représentation d'un flottant, ou si le flottant correspondant ne peut pas
	 * être codé sur un double.
	 */
	public static double lireDouble() {
		format_.setParseIntegerOnly(false); // ici, on accepte les non-entiers
		while (!eof()) {
			Number n = lireNombre();
			if (n == null) {
				continue;
			}
			if (verifBornes(n, "double")) {
				return n.doubleValue();
			}
		}
		return -1.0;
	}

	/** Indique si la fin du flux est atteinte. */
	public static boolean eof() {
		return eof_;
	}

	/** Affiche une chaîne sur la console. */
	public static void ecrire(String s) {
		System.out.print(s);
	}

	/** Affiche un caractère sur la console. */
	public static void ecrire(char c) {
		System.out.print(c);
	}

	/** Affiche un booléen sur la console. */
	public static void ecrire(boolean b) {
		System.out.print(b);
	}

	/** Affiche la représentation décimale d'un octet sur la console. */
	public static void ecrire(byte b) {
		System.out.print(b);
	}

	/** Affiche la représentation décimale d'un short sur la console. */
	public static void ecrire(short s) {
		System.out.print(s);
	}

	/** Affiche la représentation décimale d'un int sur la console. */
	public static void ecrire(int i) {
		System.out.print(i);
	}

	/** Affiche la représentation décimale d'un long sur la console. */
	public static void ecrire(long l) {
		System.out.print(l);
	}

	/** Affiche la représentation décimale d'un float sur la console. */
	public static void ecrire(float f) {
		System.out.print(f);
	}

	/** Affiche la représentation décimale d'un double sur la console. */
	public static void ecrire(double d) {
		System.out.print(d);
	}

	/** Passe au début de la ligne suivante de la console. */
	public static void alaligne() {
		System.out.println();
	}

	/** Affiche une chaîne sur la console et passe à la ligne. */
	public static void ecrireNL(String s) {
		ecrire(s);
		alaligne();
	}

	/** Affiche un caractère sur la console et passe à la ligne. */
	public static void ecrireNL(char c) {
		ecrire(c);
		alaligne();
	}

	/** Affiche un booléen sur la console et passe à la ligne. */
	public static void ecrireNL(boolean b) {
		ecrire(b);
		alaligne();
	}

	/**
	 * Affiche la représentation décimale d'un octet sur la console et passe à
	 * la ligne.
	 */
	public static void ecrireNL(byte b) {
		ecrire(b);
		alaligne();
	}

	/**
	 * Affiche la représentation décimale d'un short sur la console et passe à
	 * la ligne.
	 */
	public static void ecrireNL(short s) {
		ecrire(s);
		alaligne();
	}

	/**
	 * Affiche la représentation décimale d'un int sur la console et passe à la
	 * ligne.
	 */
	public static void ecrireNL(int i) {
		ecrire(i);
		alaligne();
	}

	/**
	 * Affiche la représentation décimale d'un long sur la console et passe à la
	 * ligne.
	 */
	public static void ecrireNL(long l) {
		ecrire(l);
		alaligne();
	}

	/**
	 * Affiche la représentation décimale d'un float sur la console et passe à
	 * la ligne.
	 */
	public static void ecrireNL(float f) {
		ecrire(f);
		alaligne();
	}

	/**
	 * Affiche la représentation décimale d'un double sur la console et passe à
	 * la ligne.
	 */
	public static void ecrireNL(double d) {
		ecrire(d);
		alaligne();
	}

	/**
	 * Lit un nombre sur le flux associé à la console, selon le format
	 * <code>fmt</code>. Le format est utilisé à partir du premier caractère
	 * non-blanc suivant la position courante. Échoue et rend <code>null</code>
	 * si les caractères lus ne peuvent pas être interprétés comme la
	 * représentation décimale d'un nombre.
	 */
	private static Number lireNombre() {
		// On commence par ignorer les blancs
		sauterLesBlancs();
		if (eof()) {
			return null;
		}
		// L'analyse débute à la position courante dans la ligne
		ParsePosition pos = new ParsePosition(position_);
		Number lu = format_.parse(ligne_, pos);
		// Si l'analyse n'a pas fait progresser la position, il y a
		// une erreur de format.
		if (pos.getIndex() == position_) {
			System.err.println("Erreur : nombre '" + lireMot()
					+ "' incorrect; ignore.");
			return null;
		}
		// On mémorise le token lu, au cas où il devrait être affiché
		dernierToken_ = ligne_.substring(position_, pos.getIndex());
		// On fait progresser la position dans la ligne courante.
		position_ = pos.getIndex();
		return lu;
	}

	/**
	 * Indique si le nombre <code>n</code> peut être représenté dans le domaine
	 * du type <code>type</code>.
	 */
	private static boolean verifBornes(Number n, String type) {
		String erreur = null;

		if (n.doubleValue() > maxRepresentable_.get(type)
				.doubleValue()) {
			erreur = new String("grand");
		} else if (n.doubleValue() < minRepresentable_.get(type)
				.doubleValue()) {
			erreur = new String("petit");
		}
		if (erreur == null) {
			return true;
		}

		System.err.println("Erreur : nombre '" + dernierToken_ + "' trop "
				+ erreur + " pour un " + type + "; ignore.");
		return false;
	}

	/**
	 * Indique s'il existe un caractère suivant dans le flux. Peut provoquer une
	 * lecture sur la console.
	 */
	private static boolean suivantExiste() {
		if (eof_) {
			return false; // Si on a déjà rencontré la fin de fichier, yenaplus
		}
		if (position_ < longueur_) {
			return true; // Si on n'a pas épuisé la ligne courante, yenaencore
		}
		// On n'a rien en stock, il faut lire une nouvelle ligne
		try {
			ligne_ = in_.readLine();
		} catch (Exception e) {
			eof_ = true; // on traite les erreurs d'E/S comme une fin de fichier
			return false;
		}
		// readLine rend null pour indiquer la fin de fichier
		if (ligne_ == null) {
			eof_ = true;
			return false;
		}
		// On ajoute le '\n' qui permettra de détecter les fins de
		// lignes avec lireChar().
		ligne_ = ligne_ + '\n';
		longueur_ = ligne_.length();
		position_ = 0;
		return true;
	}

	/** Ligne courante du flux d'entrée. */
	private static String ligne_;

	/** Position du prochain caractère à lire dans la ligne courante. */
	private static int position_;

	/** Nombre de caractères dans la ligne courante. */
	private static int longueur_;

	/** Flux de caractères duquel sont lues les lignes. */
	private static BufferedReader in_;

	/** Dernier token lu au cours de la lecture d'un nombre. */
	private static String dernierToken_;

	private static HashMap<String,Double> maxRepresentable_;
	private static HashMap<String,Double> minRepresentable_;

	/** Indique si on est à la fin du flux. */
	private static boolean eof_;

	/** Format utilisé pour lire et écrire les nombres. */
	private static NumberFormat format_;

	// Code exécuté au chargement de la classe.
	static {
		// On choisit des réglages "standards" pour avoir un
		// environnement homogène sur toutes les machines
		// quelle que soit leur locale de défaut, ainsi que
		// pour que les méthodes d'affichage produisent un
		// résultat lisible par les méthodes de lecture.
		format_ = NumberFormat.getNumberInstance(Locale.US);

		// Initialisation des tables contenant les bornes représentables
		// des différents types scalaires.
		maxRepresentable_ = new HashMap<String,Double>();
		maxRepresentable_.put("octet", new Double(Byte.MAX_VALUE));
		maxRepresentable_.put("short", new Double(Short.MAX_VALUE));
		maxRepresentable_.put("int", new Double(Integer.MAX_VALUE));
		maxRepresentable_.put("long", new Double(Long.MAX_VALUE));
		maxRepresentable_.put("float", new Double(Float.MAX_VALUE));
		maxRepresentable_.put("double", new Double(Double.MAX_VALUE));
		minRepresentable_ = new HashMap<String,Double>();
		minRepresentable_.put("octet", new Double(Byte.MIN_VALUE));
		minRepresentable_.put("short", new Double(Short.MIN_VALUE));
		minRepresentable_.put("int", new Double(Integer.MIN_VALUE));
		minRepresentable_.put("long", new Double(Long.MIN_VALUE));
		minRepresentable_.put("float", new Double(-Float.MAX_VALUE));
		minRepresentable_.put("double", new Double(-Double.MAX_VALUE));

		reouvrir();
	}
}
