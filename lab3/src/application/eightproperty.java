package application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class eightproperty {
	private final StringProperty CA;
	private final StringProperty CB;
	private final StringProperty CC;
	private final StringProperty CD;
	private final StringProperty CE;
	private final StringProperty CF;
	private final StringProperty CG;
	private final StringProperty CH;
	
	public eightproperty(String CA,String CB,String CC, String CD, String CE, String CF,String CG,String CH)
	{
		this.CA = new SimpleStringProperty(CA);
		this.CB = new SimpleStringProperty(CB);
		this.CC = new SimpleStringProperty(CC);
		this.CD = new SimpleStringProperty(CD);
		this.CE = new SimpleStringProperty(CE);
		this.CF = new SimpleStringProperty(CF);
		this.CG = new SimpleStringProperty(CG);
		this.CH = new SimpleStringProperty(CH);
	}
	
	public String getCA() {
		return CA.get();
	}
	
	public String getCB() {
		return CB.get();
	}
	
	public String getCC() {
		return CC.get();
	}
	
	public String getCD() {
		return CD.get();
	}
	
	public String getCE() {
		return CE.get();
	}
	
	public String getCF() {
		return CF.get();
	}
	
	public String getCG() {
		return CG.get();
	}
	
	public String getCH() {
		return CH.get();
	}
	
	public void setCA(String temp) {
		CA.set(temp);
	}
	
	public void setCB(String temp) {
		CB.set(temp);
	}
	public void setCC(String temp) {
		CC.set(temp);
	}
	public void setCD(String temp) {
		CD.set(temp);
	}
	public void setCE(String temp) {
		CE.set(temp);
	}
	public void setCF(String temp) {
		CF.set(temp);
	}
	public void setCG(String temp) {
		CG.set(temp);
	}
	public void setCH(String temp) {
		CH.set(temp);
	}
	
	
	
}