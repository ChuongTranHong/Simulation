
public class HandOverCommand extends Command{
	double speed;
	double duration;
	public HandOverCommand(double time, double speed, int station, double duration,int id){
		super(time, station,id);
		type = CALLHANDOVER;
		this.speed = speed;
		this.duration = duration;
	}
	@Override
	public void execute(Simulation simulation) {
		simulation.stationList[station-1].callTerminal();
		if(station>=simulation.stationList.length){
			System.out.println("[OUTOFWAY] at time "+time+" the car "+carId+" go out of the route");
			simulation.handOver++;
			return;
		}
		if(!simulation.stationList[station].registerHandOver()){
			System.out.println("[DROPCALL] at time "+time+" the car "+carId+" has drop at "+station);
			simulation.droppedCall++;
			return;
		}
		System.out.println("[HANDOVER] at time "+time+" the car "+carId+" has hand over to "+station);
		simulation.handOver ++;
		double timeRunOnSection = BaseStation.MAXLENGHT/speed;
		Command command;
		if(timeRunOnSection >= duration){
			command = new TerminatedCommand(time + duration , station,carId);
			System.out.println(" add terminated command at "+(time+duration)+" station "+station+ " car id "+carId);
			//add to the queue: finish in the block;
		}else{
			command = new HandOverCommand(time + timeRunOnSection, speed, station+1 ,duration- timeRunOnSection,carId);
			// add to the queue: handover to the next station
			System.out.println("add new handover at "+(time+timeRunOnSection)+" speed "+speed+" station "+(station+1)+" duration "+(duration-timeRunOnSection)+" car id "+carId);
		}
		simulation.queue.addCommand(command);
	}
}
