package application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class sixproperty {
	private  StringProperty CA;
	private  StringProperty CB;
	private  StringProperty CC;
	private  StringProperty CD;
	private  StringProperty CE;
	private  StringProperty CF;
	
	public sixproperty(String CA,String CB,String CC, String CD, String CE, String CF)
	{
		this.CA = new SimpleStringProperty(CA);
		this.CB = new SimpleStringProperty(CB);
		this.CC = new SimpleStringProperty(CC);
		this.CD = new SimpleStringProperty(CD);
		this.CE = new SimpleStringProperty(CE);
		this.CF = new SimpleStringProperty(CF);
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

}