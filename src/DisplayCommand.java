import java.io.PrintWriter;


public class DisplayCommand extends Command {

	public DisplayCommand(double time, int station, int id) {
		super(time, station, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Simulation simulation, PrintWriter print) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(PrintWriter print, Simulation simulation) {
		print.println(((double)simulation.droppedCall/simulation.numberOfServeCar *100)+","+((double)simulation.blockedCall/simulation.numberOfServeCar*100));
		// TODO Auto-generated method stub
		System.out.println("[WARMUP] at time "+(time/3600)+" average drop call "+((double)simulation.droppedCall/(simulation.numberOfServeCar-simulation.blockedCall) *100));
		System.out.println("average block call "+((double)simulation.blockedCall/simulation.numberOfServeCar*100));
		System.out.println("number of serve car "+simulation.numberOfServeCar);
		System.out.println();
	}

}
