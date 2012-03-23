import java.io.PrintWriter;


public class TerminatedCommand extends Command{
	public TerminatedCommand(double time, int station,int id){
		super(time,station,id);
		type = CALLTERMINATE;
	}

	@Override
	public void execute(Simulation simulation,PrintWriter print) {
		// TODO Auto-generated method stub
		if(Simulation.debugMode)System.out.println("[TERMINATE] at time "+time+" the car "+carId+" has terminated at "+station);
		simulation.stationList[station].callTerminal();
		if(Simulation.debugMode)print.println("</terminated>");
		if(Simulation.debugMode)print.println();
	}

	@Override
	public void display(PrintWriter print, Simulation simulation) {
		// TODO Auto-generated method stub
		if(!Simulation.debugMode)return;
		print.println("<terminated> time: "+time+"  car id "+carId+" Station "+station+" position ");
		print.println("number of register channel station has "+simulation.stationList[station].registerChannel);
	}
}
