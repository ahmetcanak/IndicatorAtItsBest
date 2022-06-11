package process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class mainProcess {
	// public static String filePath = "";
	public static double takeProfit;
	public static double stopLoss;
	DecimalFormat df = new DecimalFormat();
	/*
	  static void smaTrade() throws IOException { 
	  List<Double> myData = getKapanis(filePath); 
	  boolean tasiyor = false; double alim = 0; 
	  double satim= 0; 
	  double toplam = 0; 
	  String date = ""; 
	  double ilkGun = myData.get(0);
	  double sonGun = myData.get(myData.size() - 1); 
	  StringBuilder writeIt = new StringBuilder(); 
	  for (int i = 200; i < myData.size(); i++) { 
	  date = getDate(filePath).get(i); if (getSma(i) && !tasiyor) { 
	  tasiyor = true; 
	  alim =myData.get(i); 
	  writeIt.append((alim) + "\n"); 
	  writeIt.append("Bought at " +date + "\n");
	  } 
	  else if (!getSma(i) && tasiyor == true) { 
	  tasiyor = false;
	  satim = myData.get(i); 
	  writeIt.append("Sold at " + date + "\n"); toplam += (((satim * 100) / alim) - 100); 
	  writeIt.append((satim) + "\n");
	  writeIt.append(("Trade sonu�: %" + (((satim * 100) / alim) - 100)) + "\n");
	  alim = 0; }
	  
	  } 
	  
	  writeIt.append(("Hissenin tarihler arasi son durumu: " + (((sonGun * 100) / ilkGun) - 100)) + "\n"); 
	  writeIt.append(("ilk gün: " + ilkGun) + "\n");
	  writeIt.append(("son gün: " + sonGun) + "\n");
	  writeIt.append(("Tarihler arasi tüm işlemler sonucu: " + toplam) + "\n");
	  yazdir(writeIt.toString()); }
	 */

	public String trade(double takeProfit, double stopLoss, double confirm, String filePath) throws IOException {
		df.setMaximumFractionDigits(2);
		boolean tasiyor = false;
		boolean kaufmanalti = true;
		double alim = 0;
		double satim = 0;
		double kaufmann = 0;
		double toplam = 0;
		List<Double> myData = getKapanis(filePath);
		List<Double> myKaufmann = getKama(filePath);
		String date = "";
		Double ilkGun = myData.get(0);
		Double sonGun = myData.get(myData.size() - 1);
		StringBuilder writeIt = new StringBuilder();
		// String writeIt = "";
		int teyit = 0;
		stopLoss = stopLoss * -1;
		for (int i = 0; i < myData.size(); i++) {
			satim = myData.get(i);
			kaufmann = myKaufmann.get(i);

			if ((satim > kaufmann) && !tasiyor && kaufmanalti == true) {
				teyit = 0;
				date = getDate(filePath).get(i);
				String[] arrOfStr = date.split("T", 2);
				writeIt.append("Bought at '" + arrOfStr[0] + "'\n");
				tasiyor = true;
				kaufmanalti = false;
				writeIt.append(satim + "\n");
				alim = satim;
			} else if ((satim < kaufmann) && tasiyor && teyit == confirm) {
				date = getDate(filePath).get(i);
				String[] arrOfStr = date.split("T", 2);
				writeIt.append("Sold at '" + arrOfStr[0] + "'\n");
				satim = myData.get(i);
				toplam += (((satim * 100) / alim) - 100);
				kaufmanalti = true;
				tasiyor = false;
				writeIt.append((satim) + "\n");
				writeIt.append(("Trade sonuc: %" + df.format((((satim * 100) / alim) - 100))) + "\n");
				alim = 0;
			} else if ((satim < kaufmann) && tasiyor) {
				kaufmanalti = true;
				teyit++;
			} else if ((((satim * 100) / alim) - 100) > takeProfit && tasiyor) {
				date = getDate(filePath).get(i);
				String[] arrOfStr = date.split("T", 2);
				writeIt.append("Sold at get profit point '" + arrOfStr[0] + "'\n");
				toplam += (((satim * 100) / alim) - 100);
				tasiyor = false;
				kaufmanalti = true;
				writeIt.append((satim) + "\n");
				writeIt.append(("Trade sonuc: %" + df.format((((satim * 100) / alim) - 100))) + "\n");
				alim = 0;
			} else if (((((satim * 100) / alim) - 100) < stopLoss) && tasiyor) {
				date = getDate(filePath).get(i);
				String[] arrOfStr = date.split("T", 2);
				writeIt.append("Sold at stoploss point '" + arrOfStr[0] + "'\n");
				toplam += (((satim * 100) / alim) - 100);
				tasiyor = false;
				kaufmanalti = true;
				writeIt.append((satim) + "\n");
				writeIt.append(("Trade sonuc: %" + df.format((((satim * 100) / alim) - 100))) + "\n");
				i = i + 2;
				alim = 0;
			}
		}
		writeIt.append(("Hissenin tarihler arasi son durumu: " + df.format((((sonGun * 100) / ilkGun) - 100))) + "\n");
		writeIt.append(("ilk gun: " + ilkGun) + "\n");
		writeIt.append(("son gun: " + sonGun) + "\n");
		writeIt.append(("Tarihler arasi tum islemler sonucu: " + df.format(toplam)) + "\n");
		return (writeIt.toString());
	}

	static double trainTrade(double takeProfit, double stopLoss, double confirm, String filePath) throws IOException {
		boolean tasiyor = false;
		boolean kaufmanalti = true;
		double alim = 0;
		double satim = 0;
		double kaufmann = 0;
		double toplam = 0;
		int teyit = 0;
		stopLoss = stopLoss * -1;
		List<Double> myData = getKapanis(filePath);
		List<Double> myKaufmann = getKama(filePath);
		// en iyi k�r ve stoploss y�zdesini bulmal�y�m.
		for (int i = 0; i < myData.size(); i++) {
			satim = myData.get(i);
			kaufmann = myKaufmann.get(i);

			if ((myData.get(i) > kaufmann) && !tasiyor && kaufmanalti == true) {
				teyit = 0;
				tasiyor = true;
				kaufmanalti = false;
				alim = myData.get(i);
			} else if ((myData.get(i) < kaufmann) && tasiyor && teyit == confirm) {
				satim = myData.get(i);
				toplam += (((satim * 100) / alim) - 100);
				kaufmanalti = true;
				tasiyor = false;
				alim = 0;
			} else if ((myData.get(i) < kaufmann) && tasiyor) {
				kaufmanalti = true;
				teyit++;
			} else if ((((satim * 100) / alim) - 100) > takeProfit && tasiyor) {
				satim = myData.get(i);
				toplam += (((satim * 100) / alim) - 100);
				tasiyor = false;
				kaufmanalti = true;
				alim = 0;
			} else if (((((satim * 100) / alim) - 100) < stopLoss) && tasiyor) {
				satim = myData.get(i);
				toplam += (((satim * 100) / alim) - 100);
				tasiyor = false;
				kaufmanalti = true;
				i = i + 2;
				alim = 0;
			}
		}
		return toplam;
	}

	public List<Double> getParameters(String filePath) throws IOException {
		double maxProfit = 0;
		double temp = 0;
		List<Double> notebook = new ArrayList<>();
		notebook.add(1.0);
		notebook.add(1.0);
		notebook.add(1.0);

		for (double i = 5; i < 16; i = i + 1) {
			for (double x = 0; x <= 5; x = x + 0.5) {
				for (double j = 1; j <= 3; j++) {
					temp = trainTrade(i, x, j, filePath);
					if (maxProfit < temp) {
						maxProfit = temp;
						notebook.set(0, i);
						notebook.set(1, x);
						notebook.set(2, j);
					}
				}
			}
		}
		return notebook;
	}

	public static List<String> getData(int index, String filename) throws IOException {
		// File csvFile = new
		// File("C:\\Users\\ahmet\\OneDrive\\Masa�st�\\piyasa\\analiz\\" + filename);
		File csvFile = new File("C:\\Users\\user\\Desktop\\analiz\\" + filename);
		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String line = "";
		String[] count = null;
		List<String> data = new ArrayList<>();

		try {
			while ((line = br.readLine()) != null) {
				count = line.split(",");
				data.add(count[index]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		br.close();
		return data;
	}

	static List<Double> getKapanis(String filePath) throws IOException {
		List<String> kapanis = new ArrayList<>();
		List<Double> ftKapanis = new ArrayList<>();
		kapanis = getData(4, filePath);
		for (int x = 1; x < kapanis.size(); x++) {
			ftKapanis.add(Double.parseDouble(kapanis.get(x)));
		}
		return ftKapanis;
	}

	static List<Double> getKama(String filePath) throws IOException {
		List<String> kama = new ArrayList<>();
		List<Double> ftKama = new ArrayList<>();
		kama = getData(5, filePath);
		for (int x = 1; x < kama.size(); x++) {
			ftKama.add(Double.parseDouble(kama.get(x)));
		}

		return ftKama;
	}

	static List<String> getDate(String filePath) throws IOException {
		List<String> date = new ArrayList<>();
		List<String> ftDate = new ArrayList<>();
		date = getData(0, filePath);
		for (int x = 1; x < date.size(); x++) {
			ftDate.add(date.get(x));
		}
		return ftDate;
	}

	static String getFilePath() {
		System.out.println("Dosya yolunu uzant�s� ile birlikte gir...");
		Scanner get = new Scanner(System.in);
		String filename = get.nextLine();
		get.close();
		return filename;

	}
	/*
	static boolean getSma(int day) throws IOException {
		List<Double> myData = new ArrayList<>();
		myData = getKapanis(getFilePath());
		double sma21 = 0;
		double sma50 = 0;
		double sma100 = 0;
		double sma200 = 0;
		double max = 0;
		for (int i = 0; i < myData.size(); i++) {
			sma21 = 0;
			sma50 = 0;
			sma100 = 0;
			sma200 = 0;
			if (i >= 21) {
				for (int x = i - 21; x <= i; x++) {
					sma21 += myData.get(x);
				}
				sma21 /= 21;
			}
			if (i >= 50) {
				for (int x = i - 50; x <= i; x++) {
					sma50 += myData.get(x);
				}
				sma50 /= 50;
			}
			if (i >= 100) {
				for (int x = i - 100; x <= i; x++) {
					sma100 += myData.get(x);
				}
				sma100 /= 100;
			}
			if (i >= 200) {
				for (int x = i - 200; x <= i; x++) {
					sma200 += myData.get(x);
				}
				sma200 /= 200;
			}
			if ((i > 200) && (sma21 < myData.get(day) && (sma50 < myData.get(day)) && (sma100 < myData.get(day))
					&& (sma200 < myData.get(day)))) {
				
				  List<Double> getMax = new ArrayList<>(); getMax.add(sma21);
				  getMax.add(sma50); getMax.add(sma100); getMax.add(sma200);
				  System.out.println("true at " + day); for (int j = 0; j < getMax.size(); j++)
				  { if (getMax.get(j) > max) max = getMax.get(j); }
				 
				return true;
			}
		}

		return false;
	}*/

}
