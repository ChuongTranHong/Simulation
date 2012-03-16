
public class TerminatedCommand extends Command{
	public TerminatedCommand(double time, int station,int id){
		super(time,station,id);
		type = CALLTERMINATE;
	}

	@Override
	public void execute(Simulation simulation) {
		// TODO Auto-generated method stub
		System.out.println("[TERMINATE] at time "+time+" the car "+carId+" has terminated at "+station);
		simulation.stationList[station].callTerminal();
	}
}
