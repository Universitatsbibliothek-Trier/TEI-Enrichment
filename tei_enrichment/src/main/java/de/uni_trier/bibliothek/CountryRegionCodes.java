// @author       René Ackels
// Copyright (c) 2023 Universität Trier

// This file is part of OCR-To-TEI.

// OCR-To-TEI is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// OCR-To-TEI is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

package de.uni_trier.bibliothek;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class CountryRegionCodes {
	public static Boolean hasLocation = false;
	public static List<String> getCountries(JSONArray geographicAreaCode)
	{
		List<String> countries = new ArrayList<>();
		for(int i = 0; i < geographicAreaCode.length(); i++)
		{
			JSONObject geogrObject = geographicAreaCode.getJSONObject(i);
			if(geogrObject.has("id"))
			{
				hasLocation = true;
				String geographicURL = geogrObject.getString("id");
						if(geographicURL.contains("DDDE"))
						{
							countries.add("Deutschland <DDR>");
						}
						else if(geographicURL.contains("DXDE"))
						{
							countries.add("Deutschland, Deutsches Reich");
						}
						else if(geographicURL.contains("DE"))
						{
							countries.add("Deutschland");
						}
						else if(geographicURL.contains("AAAT"))
						{
							countries.add("Österreich (-12.11.1918)");
						}
						else if(geographicURL.contains("AT"))
						{
							countries.add("Österreich");
						}
						else if(geographicURL.contains("CH"))
						{
							countries.add("Schweiz");
						}							
						else if(geographicURL.contains("AD"))
						{
							countries.add("Andorra");
						}
						else if(geographicURL.contains("AL"))
						{
							countries.add("Albanien");
						}
						else if(geographicURL.contains("AX"))
						{
							countries.add("Ålandinseln");
						}
						else if(geographicURL.contains("BA"))
						{
							countries.add("Bosnien-Herzegowina");
						}
						else if(geographicURL.contains("BE"))
						{
							countries.add("Belgien");
						}
						else if(geographicURL.contains("BG"))
						{
							countries.add("Bulgarien");
						}
						else if(geographicURL.contains("BY"))
						{
							countries.add("Weißrussland");
						}
						else if(geographicURL.contains("CSHH"))
						{
							countries.add("Tschechoslowakei");
						}
						else if(geographicURL.contains("CSXX"))
						{
							countries.add("Serbien-Montenegro");
						}
						else if(geographicURL.contains("CY"))
						{
							countries.add("Zypern");
						}
						else if(geographicURL.contains("CZ"))
						{
							countries.add("Tschechische Republik");
						}
						else if(geographicURL.contains("DK"))
						{
							countries.add("Dänemark");
						}
						else if(geographicURL.contains("DK"))
						{
							countries.add("Dänemark");
						}
						else if(geographicURL.contains("EE"))
						{
							countries.add("Estland");
						}
						else if(geographicURL.contains("ES"))
						{
							countries.add("Spanien");
						}
						else if(geographicURL.contains("FI"))
						{
							countries.add("Finnland");
						}
						else if(geographicURL.contains("FR"))
						{
							countries.add("Frankreich");
						}
						else if(geographicURL.contains("GB"))
						{
							countries.add("Großbritannien");
						}
						else if(geographicURL.contains("GG"))
						{
							countries.add("Guernsey");
						}
						else if(geographicURL.contains("GI"))
						{
							countries.add("Gibraltar");
						}
						else if(geographicURL.contains("GR"))
						{
							countries.add("Griechenland");
						}
						else if(geographicURL.contains("HR"))
						{
							countries.add("Kroatien");
						}
						else if(geographicURL.contains("HU"))
						{
							countries.add("Ungarn");
						}
						else if(geographicURL.contains("IE"))
						{
							countries.add("Irland");
						}
						else if(geographicURL.contains("IM"))
						{
							countries.add("Man <Insel>");
						}
						else if(geographicURL.contains("IS"))
						{
							countries.add("Island");
						}
						else if(geographicURL.contains("IT-32"))
						{
							countries.add("Trentino-Südtirol");
						}
						else if(geographicURL.contains("IT"))
						{
							countries.add("Italien");
						}
						else if(geographicURL.contains("JE"))
						{
							countries.add("Jersey");
						}
						else if(geographicURL.contains("LI"))
						{
							countries.add("Liechtenstein");
						}
						else if(geographicURL.contains("LT"))
						{
							countries.add("Litauen");
						}
						else if(geographicURL.contains("LU"))
						{
							countries.add("Luxemburg");
						}
						else if(geographicURL.contains("LV"))
						{
							countries.add("Lettland");
						}
						else if(geographicURL.contains("MC"))
						{
							countries.add("Monaco");
						}
						else if(geographicURL.contains("MD"))
						{
							countries.add("Moldawien");
						}
						else if(geographicURL.contains("ME"))
						{
							countries.add("Montenegro");
						}
						else if(geographicURL.contains("MK"))
						{
							countries.add("Makedonien");
						}
						else if(geographicURL.contains("MT"))
						{
							countries.add("Malta");
						}
						else if(geographicURL.contains("NL"))
						{
							countries.add("Niederlande");
						}
						else if(geographicURL.contains("NO"))
						{
							countries.add("Norwegen");
						}
						else if(geographicURL.contains("PL"))
						{
							countries.add("Polen");
						}
						else if(geographicURL.contains("PT"))
						{
							countries.add("Portugal");
						}
						else if(geographicURL.contains("RO"))
						{
							countries.add("Rumänien");
						}
						else if(geographicURL.contains("RS"))
						{
							countries.add("Serbien");
						}
						else if(geographicURL.contains("RU"))
						{
							countries.add("Russland");
						}
						else if(geographicURL.contains("SE"))
						{
							countries.add("Schweden");
						}
						else if(geographicURL.contains("SI"))
						{
							countries.add("Slowenien");
						}
						else if(geographicURL.contains("SK"))
						{
							countries.add("Slowakei");
						}
						else if(geographicURL.contains("SM"))
						{
							countries.add("San Marino");
						}
						else if(geographicURL.contains("SUHH"))
						{
							countries.add("Sowjetunion");
						}
						else if(geographicURL.contains("UA"))
						{
							countries.add("Ukraine");
						}
						else if(geographicURL.contains("VA"))
						{
							countries.add("Vatikanstadt");
						}
						else if(geographicURL.contains("YUCS"))
						{
							countries.add("Jugoslawien <Föderative Republik>, Jugoslawien");
						}
				}
		}
		return countries;
	}

	public static boolean hasLocation()
	{
		return hasLocation;
	}

	public static List<String> getRegions(JSONArray geographicAreaCode)
	{
		List<String> regions = new ArrayList<>();
		for(int i = 0; i < geographicAreaCode.length(); i++)
		{
			JSONObject geogrObject = geographicAreaCode.getJSONObject(i);
			if(geogrObject.has("id"))
			{
				hasLocation = true;
				String geographicURL = geogrObject.getString("id");
						if(geographicURL.contains("AT"))
						{						
							if(geographicURL.contains("-1"))
							{
								regions.add("Burgenland");
							}
							else if(geographicURL.contains("-2"))
							{
								regions.add("Kärnten");
							}
							else if(geographicURL.contains("-3"))
							{
								regions.add("Niederösterreich");
							}
							else if(geographicURL.contains("-4"))
							{
								regions.add("Oberösterreich");
							}
							else if(geographicURL.contains("-5"))
							{
								regions.add("Salzburg");
							}
							else if(geographicURL.contains("-6"))
							{
								regions.add("Steiermark");
							}
							else if(geographicURL.contains("-7"))
							{
								regions.add("Tirol");
							}
							else if(geographicURL.contains("-8"))
							{
								regions.add("Vorarlberg");
							}
							else if(geographicURL.contains("-9"))
							{
								regions.add("Wien");
							}
							
						}

						if(geographicURL.contains("CH"))
						{						
							if(geographicURL.contains("AG"))
							{
								regions.add("Aargau");
							}
							else if(geographicURL.contains("AI"))
							{
								regions.add("Appenzell <Innerrhoden>");
							}
							else if(geographicURL.contains("AR"))
							{
								regions.add("Appenzell <Ausserrhoden>");
							}
							else if(geographicURL.contains("BE"))
							{
								regions.add("Bern <Kanton>");
							}
							else if(geographicURL.contains("BL"))
							{
								regions.add("Basel-Landschaft");
							}
							else if(geographicURL.contains("BS"))
							{
								regions.add("Basel");
							}
							else if(geographicURL.contains("FR"))
							{
								regions.add("Freiburg <Üechtland, Kanton>");
							}
							else if(geographicURL.contains("GE"))
							{
								regions.add("Genf <Kanton>");
							}
							else if(geographicURL.contains("GL"))
							{
								regions.add("Glarus <Kanton>");
							}
							else if(geographicURL.contains("GR"))
							{
								regions.add("Graubünden");
							}
							else if(geographicURL.contains("JU"))
							{
								regions.add("Jura <Kanton>");
							}
							else if(geographicURL.contains("LU"))
							{
								regions.add("Luzern <Kanton>");
							}
							else if(geographicURL.contains("NE"))
							{
								regions.add("Neuenburg <Schweiz, Kanton>");
							}
							else if(geographicURL.contains("NW"))
							{
								regions.add("Nidwalden");
							}
							else if(geographicURL.contains("OW"))
							{
								regions.add("Obwalden");
							}
							else if(geographicURL.contains("SG"))
							{
								regions.add("Sankt Gallen <Kanton>");
							}
							else if(geographicURL.contains("SH"))
							{
								regions.add("Schaffhausen <Kanton>");
							}
							else if(geographicURL.contains("SO"))
							{
								regions.add("Solothurn <Kanton>");
							}
							else if(geographicURL.contains("SZ"))
							{
								regions.add("Schwyz");
							}
							else if(geographicURL.contains("TG"))
							{
								regions.add("Thurgau");
							}
							else if(geographicURL.contains("TI"))
							{
								regions.add("Tessin");
							}
							else if(geographicURL.contains("UR"))
							{
								regions.add("Uri");
							}
							else if(geographicURL.contains("VD"))
							{
								regions.add("Waadt");
							}
							else if(geographicURL.contains("VS"))
							{
								regions.add("Wallis");
							}
							else if(geographicURL.contains("ZG"))
							{
								regions.add("Zug <Kanton>");
							}
							else if(geographicURL.contains("ZH"))
							{
								regions.add("Zürich <Kanton>");
							}
						}

						if(geographicURL.contains("DE"))
						{						
							if(geographicURL.contains("BB"))
							{
								regions.add("Brandenburg");
							}
							else if(geographicURL.contains("BE"))
							{
								regions.add("Berlin");
							}
							else if(geographicURL.contains("BW"))
							{
								regions.add("Baden-Württemberg");
							}
							else if(geographicURL.contains("BY"))
							{
								regions.add("Bayern");
							}
							else if(geographicURL.contains("HB"))
							{
								regions.add("Bremen");
							}
							else if(geographicURL.contains("HE"))
							{
								regions.add("Hessen");
							}
							else if(geographicURL.contains("HH"))
							{
								regions.add("Hamburg");
							}
							else if(geographicURL.contains("MV"))
							{
								regions.add("Mecklenburg-Vorpommern");
							}
							else if(geographicURL.contains("NI"))
							{
								regions.add("Niedersachsen");
							}
							else if(geographicURL.contains("NW"))
							{
								regions.add("Nordrhein-Westfalen");
							}
							else if(geographicURL.contains("RP"))
							{
								regions.add("Rheinland-Pfalz");
							}
							else if(geographicURL.contains("SH"))
							{
								regions.add("Schleswig-Holstein");
							}
							else if(geographicURL.contains("SL"))
							{
								regions.add("Saarland");
							}
							else if(geographicURL.contains("SN"))
							{
								regions.add("Sachsen");
							}
							else if(geographicURL.contains("ST"))
							{
								regions.add("Sachsen-Anhalt");
							}
							else if(geographicURL.contains("TH"))
							{
								regions.add("Thüringen");
							}	
						}
					}
				}
				return regions;
	}		
}