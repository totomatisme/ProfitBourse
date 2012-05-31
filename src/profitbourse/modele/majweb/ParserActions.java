package profitbourse.modele.majweb;

import profitbourse.modele.Money;
import profitbourse.modele.Action;
import profitbourse.modele.Portefeuille;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Iterator;

import au.com.bytecode.opencsv.CSVReader;

public class ParserActions {

	public static void miseAJour(Portefeuille portefeuille) throws NumberFormatException, IOException{	
		
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader("quotes.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String [] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			// nextLine[] is an array of values from the line

			// prendre le nextLine[1] qui est le cours

			BigDecimal coursBrut = new BigDecimal (nextLine[1]); 

			//en faire un Money 
			
			Currency devise = portefeuille.getDevise();
			Money cours = new Money(coursBrut, devise);// 


			// Mettre à jour l'action
			
			//prendre le nextLine[2] en faire un float
			Float variation = new Float(nextLine[2]);

			
			//on fait défiler les actions 
			
			///////////////////////DOUTE SUR LE it.next(), DECALAGE ?
			Iterator<Action> it = portefeuille.getActions().iterator();
			it.next().majCoursEtVariation(cours,variation);

			
		}

	}
}