package openstats.client.openstates;

import java.util.*;

import org.openstates.bulkdata.LoadBulkData;
import org.openstates.data.Bill;

public class OpenStateClasses {

	public static OpenState[] getOpenStates() {
		return new OpenState[] {
/*				
			new OpenStateClasses.GAOpenState(), 
			new OpenStateClasses.MIOpenState(),
			new OpenStateClasses.MTOpenState(),
			new OpenStateClasses.INOpenState(),
//		new OpenStateClasses.IDOpenState(),		
			new OpenStateClasses.CTOpenState(), 
			new OpenStateClasses.VTOpenState(),
*/			 
			new OpenStateClasses.UTOpenState(), 
			new OpenStateClasses.SDOpenState(), 
			new OpenStateClasses.OROpenState(), 
			new OpenStateClasses.OHOpenState(), 
			new OpenStateClasses.WYOpenState(), 
//		new OpenStateClasses.SCOpenState(),
			new OpenStateClasses.RIOpenState(),
			new OpenStateClasses.NVOpenState(),
			new OpenStateClasses.NMOpenState(),
			new OpenStateClasses.NHOpenState(),	
			new OpenStateClasses.NEOpenState(),
			new OpenStateClasses.NDOpenState(),
			new OpenStateClasses.MEOpenState(),
//		new OpenStateClasses.KSOpenState(),	
			new OpenStateClasses.ILOpenState(),	
//		new OpenStateClasses.IAOpenState(),	
			new OpenStateClasses.FLOpenState(), 
			new OpenStateClasses.COOpenState(), 
			new OpenStateClasses.ALOpenState(), 
			new OpenStateClasses.AKOpenState(), 
			new OpenStateClasses.KYOpenState(), 
			new OpenStateClasses.AROpenState(), 
			new OpenStateClasses.OKOpenState(), 
			new OpenStateClasses.MAOpenState(), 
			new OpenStateClasses.NCOpenState(), 
			new OpenStateClasses.AZOpenState(),
//		new OpenStateClasses.MNOpenState(), 
			new OpenStateClasses.HIOpenState(), 
			new OpenStateClasses.LAOpenState(), 
			new OpenStateClasses.TNOpenState(), 
			new OpenStateClasses.VAOpenState(), 
			new OpenStateClasses.NJOpenState(), 
			new OpenStateClasses.PAOpenState(), 
			new OpenStateClasses.MDOpenState(), 
			new OpenStateClasses.MSOpenState(), 
			new OpenStateClasses.MOOpenState(), 
			new OpenStateClasses.TXOpenState(), 
			new OpenStateClasses.NYOpenState(), 
			new OpenStateClasses.CAOpenState(),
		};
	}

	public static class MIOpenState implements OpenState {

		@Override
		public String getState() {
			return "MI";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-11-01-mi-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.startsWith("HB") || bill.bill_id.startsWith("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
					return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.equals("transmitted") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.equals("received on ") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("presented to the governor") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("presented to governor") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("approved by the governor") ) return BILLACTION.ENACTED;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("approved by governor") ) return BILLACTION.ENACTED;
			else if (billType == BILLTYPE.RESOLUTION && act.contains("referred to clerk") ) return BILLACTION.ENACTED;
			else if (billType == BILLTYPE.RESOLUTION && act.contains("referred to the clerk") ) return BILLACTION.ENACTED;
			else if (billType == BILLTYPE.RESOLUTION && act.equals("adopted") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}
		
	}

	public static class MTOpenState implements OpenState {

		@Override
		public String getState() {
			return "MT";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.startsWith("HB") || bill.bill_id.startsWith("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("transmitted to senate") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("transmitted to house") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("transmitted to governor") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("signed by governor") ) return BILLACTION.ENACTED;
			else if (billType == BILLTYPE.RESOLUTION && act.contains("filed with secretary of state") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-09-mt-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}
		
	}

	public static class INOpenState implements OpenState {

		@Override
		public String getState() {
			return "IN";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.startsWith("HB") || bill.bill_id.startsWith("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("referred to the senate") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("referred to the house") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("signed by the speaker") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("signed by the president") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("signed by the governor") ) return BILLACTION.ENACTED;
			else if (billType == BILLTYPE.RESOLUTION && act.contains("adopted voice vote") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-09-in-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}
		
	}

	public static class IDOpenState implements OpenState {

		@Override
		public String getState() {
			return "ID";
		}

		@Override
		public String getSession() {
			return "2013";
		}


		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.startsWith("H ") || bill.bill_id.startsWith("S ")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-09-id-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}
		
	}

	public static class CTOpenState implements OpenState {

		@Override
		public String getState() {
			return "CT";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.startsWith("HB") || bill.bill_id.startsWith("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("senate calendar ") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("house calendar ") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("transmitted to secretary of the state") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("signed by governor") ) return BILLACTION.ENACTED;
			else if (billType == BILLTYPE.RESOLUTION && act.startsWith("adopted") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-09-ct-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class WIOpenState implements OpenState {

		@Override
		public String getState() {
			return "WI";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.startsWith("AB") || bill.bill_id.startsWith("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("received from assembly") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("received from senate") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("presented to the governor") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("approved by the governor") ) return BILLACTION.ENACTED;
			else if (billType == BILLTYPE.RESOLUTION && act.startsWith("adopted") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-08-wi-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class VTOpenState implements OpenState {

		@Override
		public String getState() {
			return "VT";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.startsWith("H ") || bill.bill_id.startsWith("S ")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("read third time and passed") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.equals("passed") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("read 3rd time & passed") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("delivered to governor on") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("signed by governor on") ) return BILLACTION.ENACTED;
			else if (billType == BILLTYPE.RESOLUTION && act.contains("adopted in concurrence") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-08-vt-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class UTOpenState implements OpenState {

		@Override
		public String getState() {
			return "UT";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("to senate") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("to house") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("to governor") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("governor signed") ) return BILLACTION.ENACTED;
			else if (billType == BILLTYPE.RESOLUTION && act.equals("filed") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-08-ut-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class SDOpenState implements OpenState {

		@Override
		public String getState() {
			return "SD";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("first read in senate") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("first read in house") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("delivered to the governor") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("signed by governor") ) return BILLACTION.ENACTED;
			else if (billType == BILLTYPE.RESOLUTION && act.contains("passed, yeas") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-08-sd-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}
		
	}

	public static class OROpenState implements OpenState {

		@Override
		public String getState() {
			return "OR";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("referred to president's desk") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("referred to speaker's desk") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("sent to gov.") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("president signed.") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("governor signed.") ) return BILLACTION.ENACTED;
			else if (billType == BILLTYPE.RESOLUTION && act.contains("filed with secretary of state.") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-08-or-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class OHOpenState implements OpenState {

		@Override
		public String getState() {
			return "OH";
		}

		@Override
		public String getSession() {
			return "129";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("senate introduced") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("house introduced") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("sent to gov.") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("concurrence") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("signed by governor") ) return BILLACTION.ENACTED;
			else if (billType == BILLTYPE.RESOLUTION && act.contains("concurrence") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-08-oh-json.zip", "129", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class WYOpenState implements OpenState {

		@Override
		public String getState() {
			return "WY";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SF")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("introduced and referred to s") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("introduced and referred to h") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("assigned number ") ) return BILLACTION.TOGOVERNOR;
			else if (act.contains("governor signed ") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-wy-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class SCOpenState implements OpenState {

		@Override
		public String getState() {
			return "SC";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("H ") || bill.bill_id.contains("S ")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("house read and passed") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("senate read and passed") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("transmitted to governor") ) return BILLACTION.TOGOVERNOR;
			else if (act.contains("signed by governor") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-sc-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}

		
	}

	public static class RIOpenState implements OpenState {

		@Override
		public String getState() {
			return "RI";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("house read and passed") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("senate read and passed") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("transmitted to governor") ) return BILLACTION.TOGOVERNOR;
			else if (act.contains("signed by governor") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-ri-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class NVOpenState implements OpenState {

		@Override
		public String getState() {
			return "NV";
		}

		@Override
		public String getSession() {
			return "77";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("AB") || bill.bill_id.contains("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("in senate.") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("in assembly.") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("enrolled and delivered to governor") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("enrolled and delivered to governor") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("approved by the governor") ) return BILLACTION.ENACTED;
			else if (billType == BILLTYPE.RESOLUTION && act.contains("file no. ") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-nv-json.zip", "77", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class NMOpenState implements OpenState {

		@Override
		public String getState() {
			return "NM";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("passed house") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("passed senate") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("passed senate") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("passed house") ) return BILLACTION.TOGOVERNOR;
			else if (act.contains("signed") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-nm-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class NHOpenState implements OpenState {

		@Override
		public String getState() {
			return "NH";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("introduced and referred") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("introduced and referred") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("enrolled") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("signed by governor") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-nh-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}
		
	}

	public static class NEOpenState implements OpenState {

		@Override
		public String getState() {
			return "NE";
		}

		@Override
		public String getSession() {
			return "102";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("LB") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && act.contains("passed on final reading") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("approved by governor") ) return BILLACTION.ENACTED;
			else if ( billType == BILLTYPE.RESOLUTION && act.contains("adopted") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-ne-json.zip", "102", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class NDOpenState implements OpenState {

		@Override
		public String getState() {
			return "ND";
		}

		@Override
		public String getSession() {
			return "62";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("SB") || bill.bill_id.contains("HB") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("received from house") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("received from senate") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("sent to the governor") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("signed by governor") ) return BILLACTION.ENACTED;
			else if ( billType == BILLTYPE.RESOLUTION && act.contains("filed with secretary of state") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-nd-json.zip", "62", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class MEOpenState implements OpenState {

		@Override
		public String getState() {
			return "ME";
		}

		@Override
		public String getSession() {
			return "125";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("SP") || bill.bill_id.contains("HP") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("sent for concurrence") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("sent down forthwith for concurrence") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("sent to the governor") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("passed to be enacted") ) return BILLACTION.ENACTED;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("governor") ) return BILLACTION.TOGOVERNOR;
			else if ( billType == BILLTYPE.RESOLUTION && act.contains("resolution adopted") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-me-json.zip", "125", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class KSOpenState implements OpenState {

		@Override
		public String getState() {
			return "KS";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("SB") || bill.bill_id.contains("HB") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-ks-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class ILOpenState implements OpenState {

		@Override
		public String getState() {
			return "IL";
		}

		@Override
		public String getSession() {
			return "97";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("SB") || bill.bill_id.contains("HB") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("arrive in senate") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("arrive in house") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("sent to the governor") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("governor approved") ) return BILLACTION.ENACTED;
			else if ( billType == BILLTYPE.RESOLUTION && act.contains("resolution adopted") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-il-json.zip", "97", TimeZone.getTimeZone("GMT-08:00") );			
		}
		
	}

	public static class IAOpenState implements OpenState {

		@Override
		public String getState() {
			return "IA";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("SF") || bill.bill_id.contains("HF") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-ia-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class FLOpenState implements OpenState {

		@Override
		public String getState() {
			return "FL";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("S ") || bill.bill_id.contains("H ") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("in messages") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("in messages") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("ordered enrolled") ) return BILLACTION.ENACTED;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("ordered engrossed, then enrolled") ) return BILLACTION.ENACTED;
			else if ( billType == BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("passed in house by voice vote") ) return BILLACTION.ENACTED;
			else if ( billType == BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("passed by senate") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-fl-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class DEOpenState implements OpenState {

		@Override
		public String getState() {
			return "DE";
		}

		@Override
		public String getSession() {
			return "146";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("SB") || bill.bill_id.contains("HB") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("passed by house of representatives") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("passed by senate") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("passed by house of representatives") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("passed by senate") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("signed by governor") ) return BILLACTION.ENACTED;
			else if ( billType == BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("passed in house by voice vote") ) return BILLACTION.ENACTED;
			else if ( billType == BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("passed by senate") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-de-json.zip", "146", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class COOpenState implements OpenState {

		@Override
		public String getState() {
			return "CO";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("SB") || bill.bill_id.contains("HB") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("introduced in senate") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("introduced in house") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("sent to the governor") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("governor action - signed") ) return BILLACTION.ENACTED;
			else if ( billType == BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("signed by the speaker of the house") ) return BILLACTION.ENACTED;
			else if ( billType == BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("signed by the president of the senate") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-co-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class ALOpenState implements OpenState {

		@Override
		public String getState() {
			return "AL";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("SB") || bill.bill_id.contains("HB") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("referred to the senate") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("referred to the house") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("clerk of the house certification") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("enrolled") ) return BILLACTION.TOGOVERNOR;
			else if (act.contains("assigned act no.") ) return BILLACTION.ENACTED;
			else if ( billType == BILLTYPE.RESOLUTION && act.contains("adopted voice vote") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-al-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class AKOpenState implements OpenState {

		@Override
		public String getState() {
			return "AK";
		}

		@Override
		public String getSession() {
			return "27";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("SB") || bill.bill_id.contains("HB") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("transmitted to (s)") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("upper") && act.contains("transmitted to (h)") ) return BILLACTION.OTHERCHAMBER;
			else if (act.contains("transmitted to governor") ) return BILLACTION.TOGOVERNOR;
			else if ( act.contains("signed into law") ) return BILLACTION.ENACTED;
			else if ( billType == BILLTYPE.RESOLUTION && act.contains("permanently filed") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-ak-json.zip", "27", TimeZone.getTimeZone("GMT-08:00") );			
		}

	}

	public static class KYOpenState implements OpenState {

		@Override
		public String getState() {
			return "KY";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("SB") || bill.bill_id.contains("HB") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (chamber.equals("lower") && act.contains("received in senate") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("upper") && act.contains("received in house") ) return BILLACTION.OTHERCHAMBER;
			else if (act.contains("delivered to governor") ) return BILLACTION.TOGOVERNOR;
			else if ( act.contains("signed by governor") ) return BILLACTION.ENACTED;
			else if ( billType == BILLTYPE.RESOLUTION && act.contains("adopted by voice vote") ) return BILLACTION.ENACTED;
			else if ( billType == BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("; adopted ") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-01-ky-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}
		
	}

	public static class GAOpenState implements OpenState {

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (chamber.equals("lower") && act.contains("senate read and referred") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("upper") && act.contains("house first readers") ) return BILLACTION.OTHERCHAMBER;
			else if (act.contains("house sent to governor") ) return BILLACTION.TOGOVERNOR;
			else if (chamber.equals("lower") && act.contains("read and adopted") ) return BILLACTION.ENACTED;
			else if (chamber.equals("upper") && act.contains("read and adopted") ) return BILLACTION.ENACTED;
			else if ( act.contains("signed by governor") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}
		@Override
		public String getState() {
			return "GA";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-ga-json.zip", "2013", TimeZone.getTimeZone("GMT-06:00") );
		}
	}

	public static class AROpenState implements OpenState {
		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}
		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (chamber.equals("lower") && act.contains("transmitted to the senate") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("upper") && act.contains("transmitted  to the house") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("upper") && act.contains("transmitted to the house") ) return BILLACTION.OTHERCHAMBER;
			else if (act.contains("correctly enrolled and ordered transmitted to the governor's office.") ) return BILLACTION.TOGOVERNOR;
			else if (chamber.equals("lower") && act.contains("read and adopted") ) return BILLACTION.ENACTED;
			else if (chamber.equals("upper") && act.contains("read the third time and adopted.") ) return BILLACTION.ENACTED;
			else if ( act.contains("is now act ") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}
		@Override
		public String getState() {
			return "AR";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-ar-json.zip", "2013", TimeZone.getTimeZone("GMT-06:00") );
		}
	}

	public static class OKOpenState implements OpenState {
		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}
		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (chamber.equals("lower") && act.contains("engrossed, signed, to senate") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("upper") && act.contains("engrossed to house") ) return BILLACTION.OTHERCHAMBER;
			else if (act.contains("sent to governor") ) return BILLACTION.TOGOVERNOR;
			else if (chamber.equals("lower") && act.contains("enrolled, signed, filed with secretary of state") ) return BILLACTION.ENACTED;
			else if (chamber.equals("upper") && act.contains("enrolled, filed with secretary of state") ) return BILLACTION.ENACTED;
			else if ( act.contains("approved by governor") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}
		@Override
		public String getState() {
			return "OK";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-09-ok-json.zip", "2013", TimeZone.getTimeZone("GMT-06:00") );			
		}
	}

	public static class MAOpenState implements OpenState {
		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			String tlc = bill.title.toLowerCase().trim();
			if ( 
					!tlc.startsWith("resolve " ) 
					&& !tlc.startsWith("resolutions ") 
			) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
/*			
			if ( bill.bill_id.contains("H ") || bill.bill_id.contains("S ") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
*/			
		}
		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("passed to be engrossed") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("passed to be engrossed") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("enacted and laid before the governor") ) return BILLACTION.TOGOVERNOR;
			else if ( billType != BILLTYPE.RESOLUTION && act.contains("signed by the governor") ) return BILLACTION.ENACTED;
			else if ( billType == BILLTYPE.RESOLUTION && act.equals("adopted") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}
		@Override
		public String getState() {
			return "MA";
		}
		@Override
		public String getSession() {
			return "187th";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-09-ma-json.zip", "187th", TimeZone.getTimeZone("GMT-05:00") );
		}
	}

	public static class NCOpenState implements OpenState {
		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}
		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (chamber.equals("lower") && act.contains("rec from house") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("rec from senate") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("ratified") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") &&  act.contains("adopted") ) return BILLACTION.ENACTED;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") &&  act.contains("adopted") ) return BILLACTION.ENACTED;
			else if ( act.contains("signed by gov.") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}
		@Override
		public String getState() {
			return "NC";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-08-nc-json.zip", "2013", TimeZone.getTimeZone("GMT-07:00") );
		}
	}

	public static class AZOpenState implements OpenState {
		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}
		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("transmit to house") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("transmitted to house") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("transmitted to governor") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("enrolled to governor") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("resolution adopted in final form") ) return BILLACTION.ENACTED;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("transmitted to secretary of state") ) return BILLACTION.ENACTED;
			else if ( billType == BILLTYPE.RESOLUTION && act.contains("signed") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}
		@Override
		public String getState() {
			return "AZ";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-az-json.zip", "51st-1st", TimeZone.getTimeZone("GMT-07:00") );
		}
	}

	public static class MNOpenState implements OpenState {
		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HF") || bill.bill_id.contains("SF") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}
		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			return BILLACTION.OTHER;
		}
		@Override
		public String getState() {
			return "MN";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-11-01-mn-json.zip", "2013", TimeZone.getTimeZone("GMT-05:00") );
		}
	}

	public static class HIOpenState implements OpenState {
		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}
		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (chamber.equals("lower") && act.contains("transmitted to senate") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("upper") && act.contains("transmitted to house") ) return BILLACTION.OTHERCHAMBER;
			else if (act.contains("transmitted to governor") ) return BILLACTION.TOGOVERNOR;
			else if (act.contains("enrolled to governor") ) return BILLACTION.TOGOVERNOR;
			else if (act.contains("resolution adopted in final form") ) return BILLACTION.ENACTED;
			else if (act.contains("certified copies of resolutions sent") ) return BILLACTION.ENACTED;
			else if ( act.contains("act ") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public String getState() {
			return "HI";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-09-hi-json.zip", "2013", TimeZone.getTimeZone("GMT-05:00") );
		}
	}

	public static class LAOpenState implements OpenState {
		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB") ) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}
		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
//			if ( bill.bill.bill_id.contains("SCR") ) System.out.println(bill.bill_id + ":" + bill.chamber+":"+act);
			if (chamber.equals("lower") && act.contains("received in the senate.") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("lower") && act.contains("enrolled and signed by the speaker of the house.") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("upper") && act.contains("received in the house from the senate") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("upper") && act.contains("ordered sent to the house.") ) return BILLACTION.OTHERCHAMBER;
			else if (act.contains("sent to the governor") ) return BILLACTION.TOGOVERNOR;
			else if (act.contains("sent to the secretary of state by the secretary") ) return BILLACTION.ENACTED;
			else if (act.contains("taken by the clerk of the house and presented to the secretary of state") ) return BILLACTION.ENACTED;
			else if ( act.contains("becomes act no.") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}
		@Override
		public String getState() {
			return "LA";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-la-json.zip", "2013", TimeZone.getTimeZone("GMT-05:00") );
		}
	}

	public static class TNOpenState implements OpenState {
		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}
		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (chamber.equals("lower") && act.contains("ready for transmission to sen") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("upper") && act.contains("ready for transmission to house") ) return BILLACTION.OTHERCHAMBER;
			else if (act.contains("transmitted to gov. for action") ) return BILLACTION.TOGOVERNOR;
			else if (act.contains("adopted as am.,  ayes ") ) return BILLACTION.ENACTED;
			else if (act.contains("adopted,  ayes ") ) return BILLACTION.ENACTED;
			else if ( act.contains("signed by governor") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}
		@Override
		public String getState() {
			return "TN";
		}
		@Override
		public String getSession() {
			return "TN";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-tn-json.zip", "108", TimeZone.getTimeZone("GMT-05:00") );
		}
	}


	public static class VAOpenState implements OpenState {
		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}
		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (chamber.equals("lower") && act.contains("passed house") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("upper") && act.contains("passed senate") ) return BILLACTION.OTHERCHAMBER;
			else if (act.contains("enrolled") ) return BILLACTION.TOGOVERNOR;
			else if ( act.contains("enacted, chapter") ) return BILLACTION.ENACTED;
			else if (chamber.equals("lower") &&  act.contains("agreed to by house") ) return BILLACTION.ENACTED;
			else if (chamber.equals("upper") &&  act.contains("agreed to by senate") ) return BILLACTION.ENACTED;
			else if ( act.contains("approved by governor") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}
		@Override
		public String getState() {
			return "VA";
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-08-va-json.zip", "2013", TimeZone.getTimeZone("GMT-05:00") );
		}
	}

	public static class NJOpenState implements OpenState {
		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("A ") || bill.bill_id.contains("S ")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}
		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("received in the senate") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("received in the assembly") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("passed both houses") ) return BILLACTION.TOGOVERNOR;
			else if ( act.contains("approved p.") ) return BILLACTION.ENACTED;
			else if ( act.contains("filed with secretary of state") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}
		@Override
		public String getState() {
			return "NJ";
		}
		@Override
		public String getSession() {
			return "215";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-11-01-nj-json.zip", "215", TimeZone.getTimeZone("GMT-05:00") );
		}
	}

	public static class PAOpenState implements OpenState {

		@Override
		public String getState() {
			return "PA";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (chamber.equals("lower") && act.contains("laid on the table") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("upper") && act.contains("laid on the table") ) return BILLACTION.OTHERCHAMBER;
			else if (act.contains("presented to the governor") ) return BILLACTION.TOGOVERNOR;
			else if ( act.contains("approved by the governor") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-08-pa-json.zip", "2013", TimeZone.getTimeZone("GMT-05:00") );
		}
	}

	public static class MDOpenState implements OpenState {

		@Override
		public String getState() {
			return "MD";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (chamber.equals("lower") && act.contains("first reading senate rules") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("upper") && act.contains("first reading") && !act.contains("first reading senate rules")) return BILLACTION.OTHERCHAMBER;
			else if (act.contains("passed enrolled") ) return BILLACTION.TOGOVERNOR;
			else if (act.contains("returned passed") ) return BILLACTION.TOGOVERNOR;
			else if ( act.contains("approved by the governor") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-md-json.zip", "2013", TimeZone.getTimeZone("GMT-05:00") );
		}
		
	}
	public static class MSOpenState implements OpenState {

		@Override
		public String getState() {
			return "MS";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (chamber.equals("lower") && act.contains("transmitted to senate") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("upper") && act.contains("transmitted to house") ) return BILLACTION.OTHERCHAMBER;
			else if (act.contains("enrolled bill signed") ) return BILLACTION.TOGOVERNOR;
			else if ( act.contains("approved by governor") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-ms-json.zip", "2013", TimeZone.getTimeZone("GMT-05:00") );
		}

	}

	public static class MOOpenState implements OpenState {
		@Override
		public String getState() {
			return "MO";
		}
		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}
		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (chamber.equals("lower") && act.contains("reported to the senate") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("upper") && act.contains("reported to the assembly") ) return BILLACTION.OTHERCHAMBER;
			else if (act.contains("truly agreed to and finally passed") ) return BILLACTION.TOGOVERNOR;
			else if ( act.contains("approved by governor") ) return BILLACTION.ENACTED;
			else if ( act.contains("signed by governor") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}
		@Override
		public String getSession() {
			return "2013";
		}
		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-09-mo-json.zip", "2013", TimeZone.getTimeZone("GMT-06:00") );
		}
	}
	public static class TXOpenState implements OpenState {

		@Override
		public String getState() {
			return "TX";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("HB") || bill.bill_id.contains("SB")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (chamber.equals("lower") && act.contains("received from the house") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("upper") && act.contains("received from the senate") ) return BILLACTION.OTHERCHAMBER;
			else if (act.contains("sent to the governor") ) return BILLACTION.TOGOVERNOR;
			else if ( act.contains("signed by the governor") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public String getSession() {
			return "83";
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-08-tx-json.zip", "83", TimeZone.getTimeZone("GMT-06:00") );
		}

	}
	public static class NYOpenState implements OpenState {

		@Override
		public String getState() {
			return "NY";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("A ") || bill.bill_id.contains("S ")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (chamber.equals("lower") && act.contains("delivered to senate") ) return BILLACTION.OTHERCHAMBER;
			else if (chamber.equals("upper") && act.contains("delivered to assembly") ) return BILLACTION.OTHERCHAMBER;
			else if (act.contains("delivered to governor") ) return BILLACTION.TOGOVERNOR;
			else if ( act.contains("signed chap.") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-08-ny-json.zip", "2013", TimeZone.getTimeZone("GMT-05:00") );
		}
		
	}
	public static class CAOpenState implements OpenState {

		@Override
		public String getState() {
			return "CA";
		}

		@Override
		public String getSession() {
			return "2013";
		}

		@Override
		public BILLTYPE getBillType(Bill bill, TreeSet<String> currentTopics) {
			if ( bill.bill_id.contains("SB") || bill.bill_id.contains("AB") || bill.bill_id.contains("SBX1") || bill.bill_id.contains("ABX1")) {
				if ( currentTopics.contains(bill.bill_id)) return BILLTYPE.TOPICALBILL;
				return BILLTYPE.BILL;
			}
			return BILLTYPE.RESOLUTION;
		}

		@Override
		public BILLACTION getBillAction(String chamber, String act, BILLTYPE billType) {
			if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("to the senate") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("lower") && act.contains("in senate") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("to the assembly") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && chamber.equals("upper") && act.contains("in assembly") ) return BILLACTION.OTHERCHAMBER;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("to engrossing and enrolling") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("enrolled and presented to the governor") ) return BILLACTION.TOGOVERNOR;
			else if (billType != BILLTYPE.RESOLUTION && act.contains("approved by the governor") ) return BILLACTION.ENACTED;
			else if ( billType == BILLTYPE.RESOLUTION && act.contains("chaptered by secretary of state") ) return BILLACTION.ENACTED;
			return BILLACTION.OTHER;
		}

		@Override
		public void loadBulkData() throws Exception {
			new LoadBulkData().loadCurrentTerm( "2013-10-07-ca-json.zip", "2013", TimeZone.getTimeZone("GMT-08:00") );			
		}
		
	}

}
