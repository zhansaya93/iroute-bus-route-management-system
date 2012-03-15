package iRoute.android;

import java.util.ArrayList;

public class BusRouteList {

	public static String[] buses;
	public static String[] from;
	public static String[] to;
	public static ArrayList<String> al;
	String[] temp;
	int i = 0;

	public BusRouteList(ArrayList<String> al) {
		this.al = al;
	}

	{

		for (String s : al) {

			temp = s.split(":");
			buses[i] = temp[0];
			from[i] = temp[1];
			to[i] = temp[2];
			i++;
		}

	}

}
