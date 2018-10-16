import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Agent {
	double Bias;
	double Commitment;
	double oBias;
	double oComm;
	
	public Agent(double b, double c) {
		Bias = b;
		Commitment = c;
		oBias = b;
		oComm = c;
	}

	//Return the Agent's Bias value
	public double getBias(){
		return Bias;
	}
	//Change the Agents Bias value to given variable
	public void setBias(double e){
		Bias = e;
		return;
	}
	
	//Return the Agent's Commitment Value
	public double getCommitment(){
		return Commitment;
	}
	//Change the Agent's Commitment Value to given variable
	public void setCommitment(double c){
		Commitment = c;
		return;
	}
	

	
	//Returns a list of all the Agent's active variables
	public ArrayList<String> getAgent(){
		ArrayList<String> components = new ArrayList<>();
		components.add("Expr: " + Bias);
		components.add("Comm: " + Commitment);
		
		return components;
	}
	
	public ArrayList<String> agentReport(){
		ArrayList<String> report = new ArrayList<>();
		if(Bias < oBias){
			report.add("The Bias has DROPPED from: " + oBias + " To: " + Bias + "\nA change of: " + (oBias - Bias));
		}else{
			report.add("The Bias has RISEN from: " + oBias + " To: " + Bias + "\nA change of: " + (Bias - oBias));
		}
		if(Commitment < oComm){
			report.add("\nThe Commitment has DROPPED from: " + oComm + " To: " + Commitment + "\nA change of: " + (oComm - Commitment));
		}else{
			report.add("\nThe Commitment has RISEN from: " + oComm + " To: " + Commitment + "\nA change of: " + (Commitment - oComm));
		}
		
		return report;
	}
	
	
}
