import java.io.PrintWriter;

public class HandOverCommand extends Command {
	double speed;
	double duration;

	public HandOverCommand(double time, double speed, int station,
			double duration, int id) {
		super(time, station, id);
		type = CALLHANDOVER;
		this.speed = speed;
		this.duration = duration;
	}

	@Override
	public void execute(Simulation simulation, PrintWriter print) {
		simulation.stationList[station - 1].callTerminal();
		if (station >= simulation.stationList.length) {
			if (Simulation.debugMode)
				System.out.println("[OUTOFWAY] at time " + time + " the car "
						+ carId + " go out of the route");
			if (Simulation.debugMode)
				print.println("[OUTOFWAY] out of route");
			// simulation.handOver++;
			// simulation.blockesall++;
			return;
		}
		if (!simulation.stationList[station].registerHandOver()) {
			if (Simulation.debugMode)
				print.println("[DROPCALL] call is dropped");
			if (Simulation.debugMode)
				System.out.println("[DROPCALL] at time " + time + " the car "
						+ carId + " has drop at " + station);
			simulation.droppedCall++;
			return;
		}
		if (Simulation.debugMode)
			System.out.println("[HANDOVER] at time " + time + " the car "
					+ carId + " has hand over to " + station);
		simulation.handOver++;
		double timeRunOnSection = BaseStation.MAXLENGHT / speed;
		Command command;
		if (timeRunOnSection >= duration) {
			command = new TerminatedCommand(time + duration, station, carId);
			if (Simulation.debugMode)
				print.println("add [terminated] at " + (time + duration));
			if (Simulation.debugMode)
				System.out.println(" add terminated command at "
						+ (time + duration) + " station " + station
						+ " car id " + carId);
			// add to the queue: finish in the block;
		} else {

			command = new HandOverCommand(time + timeRunOnSection, speed,
					station + 1, duration - timeRunOnSection, carId);
			// add to the queue: handover to the next station
			if (Simulation.debugMode)
				print.println("add [Handover] at " + (time + timeRunOnSection)
						+ " duration " + (duration - timeRunOnSection));
			if (Simulation.debugMode)
				System.out.println("add new handover at "
						+ (time + timeRunOnSection) + " speed " + speed
						+ " station " + (station + 1) + " duration "
						+ (duration - timeRunOnSection) + " car id " + carId);
		}
		if (Simulation.debugMode)
			print.println("</handover>");
		if (Simulation.debugMode)
			print.println("");
		simulation.queue.addCommand(command);
	}

	@Override
	public void display(PrintWriter print, Simulation simulation) {
		// TODO Auto-generated method stub
		if (!Simulation.debugMode)
			return;
		print.println("<handover> time: " + time + "  car id " + carId
				+ " Station " + station + " speed " + speed + " duration "
				+ duration);

		if (station >= 20)
			print.println("station out of range");
		else
			print.println("number of register channel station has "
					+ simulation.stationList[station].registerChannel);

	}
}
