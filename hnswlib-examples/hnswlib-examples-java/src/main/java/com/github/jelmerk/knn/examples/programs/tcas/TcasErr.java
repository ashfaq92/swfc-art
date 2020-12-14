package com.github.jelmerk.knn.examples.programs.tcas;

public class TcasErr {

	public static void main(String[] args) {
		
	}

	final int OLEV = 600; /* in feets/minute */
	final int MAXALTDIFF = 600; /* max altitude difference in feet */
	final int MINSEP = 300; /* min separation in feet */
	final int NOZCROSS = 100; /* in feet */
	/* variables */

	int Cur_Vertical_Sep;
	boolean High_Confidence;
	boolean Two_of_Three_Reports_Valid;

	int Own_Tracked_Alt;
	int Own_Tracked_Alt_Rate;
	int Other_Tracked_Alt;

	int Alt_Layer_Value; /* 0, 1, 2, 3 */
	int[] Positive_RA_Alt_Thresh = new int[4];

	int Up_Separation;
	int Down_Separation;

	/* state variables */
	int Other_RAC; /* NO_INTENT, DO_NOT_CLIMB, DO_NOT_DESCEND */

	final int NO_INTENT = 0;
	final int DO_NOT_CLIMB = 1;
	final int DO_NOT_DESCEND = 2;

	int Other_Capability; /* TCAS_TA, OTHER */
	final int TCAS_TA = 1;
	final int OTHER = 2;

	boolean Climb_Inhibit; /* true/false */

	final int UNRESOLVED = 0;
	final int UPWARD_RA = 1;
	final int DOWNWARD_RA = 2;

	public void initialize() {
		Positive_RA_Alt_Thresh[0] = 400;
		Positive_RA_Alt_Thresh[1] = 550; //Error Positive_RA_Alt_Thresh[1] = 500;
		Positive_RA_Alt_Thresh[2] = 640;
		Positive_RA_Alt_Thresh[3] = 740;
	}

	public int ALIM() {
		return Positive_RA_Alt_Thresh[Alt_Layer_Value];
	}

	public int Inhibit_Biased_Climb() {
		return (Climb_Inhibit ? Up_Separation + NOZCROSS : Up_Separation);  //orig

	}  

	public boolean Non_Crossing_Biased_Climb() {
		boolean upward_preferred;
		int upward_crossing_situation;
		boolean result;

		upward_preferred = Inhibit_Biased_Climb() > Down_Separation;
		if (upward_preferred) {
			result = !(Own_Below_Threat()) || ((Own_Below_Threat()) && (!(Down_Separation >= ALIM())));
			
		} else {
			result = Own_Above_Threat() && (Cur_Vertical_Sep >= MINSEP) && (Up_Separation >= ALIM());  //orig

		}
		return result;
	}

	public boolean Non_Crossing_Biased_Descend() {
		boolean upward_preferred;
		int upward_crossing_situation;
		boolean result;

		upward_preferred = Inhibit_Biased_Climb() > Down_Separation;
		if (upward_preferred) {
			result = Own_Below_Threat() && (Cur_Vertical_Sep >= MINSEP) && (Down_Separation >= ALIM());
		} else {
			result = !(Own_Above_Threat()) || ((Own_Above_Threat()) && (Up_Separation >= ALIM()));
		}
		return result;
	}

	public boolean Own_Below_Threat() {
		return (Own_Tracked_Alt < Other_Tracked_Alt);
		
	}

	public boolean Own_Above_Threat() {
		return (Other_Tracked_Alt < Own_Tracked_Alt);
	}

	public int alt_sep_test() {
		boolean enabled, tcas_equipped, intent_not_known;
		boolean need_upward_RA, need_downward_RA;
		int alt_sep;

		enabled = High_Confidence && (Own_Tracked_Alt_Rate <= OLEV) && (Cur_Vertical_Sep > MAXALTDIFF);
		tcas_equipped = Other_Capability == TCAS_TA;
		intent_not_known = Two_of_Three_Reports_Valid && Other_RAC == NO_INTENT; //orig
		alt_sep = UNRESOLVED;

		if (enabled && ((tcas_equipped && intent_not_known) || !tcas_equipped)) {
			need_upward_RA = Non_Crossing_Biased_Climb() && Own_Below_Threat();
			need_downward_RA = Non_Crossing_Biased_Descend() && Own_Above_Threat();
			if (need_upward_RA && need_downward_RA)
				/*
				 * unreachable: requires Own_Below_Threat and Own_Above_Threat to both be true -
				 * that requires Own_Tracked_Alt < Other_Tracked_Alt and Other_Tracked_Alt <
				 * Own_Tracked_Alt, which isn't possible
				 */
				alt_sep = UNRESOLVED;
			else if (need_upward_RA)
				alt_sep = UPWARD_RA;
			else if (need_downward_RA)
				alt_sep = DOWNWARD_RA;
			else
				alt_sep = UNRESOLVED;
		}

		return alt_sep;
	}

	public int start(int[] argv) {

		initialize();
		Cur_Vertical_Sep = argv[1];
		High_Confidence = argv[2] > 0 ? true : false;
		Two_of_Three_Reports_Valid = argv[3] > 0 ? true : false;
		Own_Tracked_Alt = argv[4];
		Own_Tracked_Alt_Rate = argv[5];
		Other_Tracked_Alt = argv[6];
		Alt_Layer_Value = argv[7];
		Up_Separation = argv[8];
		Down_Separation = argv[9];
		Other_RAC = argv[10];
		Other_Capability = argv[11];
		Climb_Inhibit = argv[12] > 0 ? true : false;
		return alt_sep_test();
	}
}
