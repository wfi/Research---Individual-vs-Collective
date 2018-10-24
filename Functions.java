import java.util.ArrayList;
import java.util.Collections;

public class Functions {
	//Decides whether an Agent will express a feature
		// ** Undecided Method - currently set to return binary 0-dont express, 1-express
		public static int express(Agent a){
			double threshold = Math.random();
			
			//determine the agents neutral zone
			double nRight = ((1-a.getCommitment())/3) * (1-a.getBias());
			double nLeft = ((1-a.getCommitment())/3) * a.getBias();
			//System.out.println("Bias: " + a.getBias() + " - " + nLeft + " + " + nRight);
			//System.out.println("Neutral Zone: " + (a.getBias() - nLeft) + ", " + (a.getBias() + nRight));
			//System.out.println("Threshold: " + threshold);
			
			
			//compare a threshold to the boundaries of the agent's neutral zone
			if(threshold < (a.getBias() - nLeft)){//threshold is in the negative expression zone
				return -1;
			}else if(threshold < (a.getBias() + nRight)){//threshold is in the neutral zone
				return 0;
			}else if(threshold <= 1){//threshold is in the positive expression zone
				return 1;
			}else{
				throw new java.lang.Error("Threshold for expression must be less then or equal to 1");
			}
		}
		
		
		//Updates an agent's bias after it has an interaction
		//Agent a is the agent to be updated, expression is the expression that the agent's partner expressed
		//quality is the quality of the interaction ex: positive: 1, negative: -1,
		//		for neutral expression: expression should be 0, quality has no effect
		public static void updateAgent(Agent a, int partnerExpress, int myExpress, int quality, int learningRate){
			int exp = partnerExpress * quality;//if it is a negative interaction, the -1 value for quality will switch directions of the bias shift
			
			
			//want to check to see if the agent is expression the opposite of what it's tendency is 
			//if this is happening we want to change the equation so the outcome is as if they were an agent with an opposite tendency 
			double rBias = a.getBias();
			boolean oppExpression = false;
			oppExpression = !( ((rBias < .5) && (myExpress == 1)) || ((rBias >= .5) && (myExpress == -1)) );
			
			if(oppExpression){
				myExpress = myExpress * -1;
			}
			
			
			double bias = a.getBias();
			double comm = a.getCommitment();
			double neumerator = 1-comm;
			
			
			//calculate new values for the bias and commitment
			double newBias = bias - ( ( neumerator / learningRate) * exp);
			double newComm = comm + ( ( neumerator / learningRate) * myExpress * exp);
			
			
			if(newBias < 0){
				newBias = .001;
			}else if(newBias > 1){
				newBias = .999;
			}
			//update the bias and commitments
			a.setBias(newBias);
			a.setCommitment(newComm);
			
			return;
		}
		
		public static int countExpressions(int e1, int e2){
			int ans = 0;
			if(e1 != 0){
				ans++;
			}
			if(e2 != 0){
				ans++;
			}
			
			return ans;
		}
		
		//Interaction function between 2 agents
		//Should change commitment based off their bias and openness values
		public static int interact(Agent a1, Agent a2){
			int express1 = express(a1);
			int express2 = express(a2);
			
			//Both agents agree - Move commitment up
			if(express1 == express2){

				updateAgent(a1, express2, express1, 1, 10);
				updateAgent(a2, express1, express2, 1, 10);
				
			}else{
				//Agents don't agree - adjust commitment based off openness
				double threshold = Math.random();
				if(threshold < a1.getCommitment()){//agents dont agree and A1 has a bad interaction: reinforce bias + commitment
					updateAgent(a1, express2, express1, -1, 10);
				}else{//agents dont agree but A1 has good interaction: decrease bias + commitment
					updateAgent(a1, express2, express1, 1, 10);
				}
				if(threshold < a2.getCommitment()){//agents dont agree and A2 has a bad interaction: reinforce bias + commitment
					updateAgent(a2, express1, express2, -1, 10);
				}else{//agents dont agree but A2 has a good interaction: decrease bias + commitment
					updateAgent(a2, express1, express2, 1, 10);
				}
				
				
			}
			
			return countExpressions(express1, express2);
		}
		
		
		public static void main(String []args) {

			Agent a = new Agent(.5,.0);
			Agent a1 = new Agent(.5, .0);
			//UpdateAgent(agent, partnerExpr, MyExpr, Qual, LR)
			for(int i = 0; i < 100; i++){
				interact(a, a1);
				
				//System.out.println(a1.getAgent());
			}
			System.out.println(a1.agentReport());
			
		}
	
	
}
