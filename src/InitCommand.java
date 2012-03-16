
public class InitCommand extends Command {
	double speed;
	double position;
	double duration;
	public InitCommand(double time, double speed, int station, double position, double duration,int id){
		super(time, station,id);
		type = CALLINIT;
		this.speed = speed;
		this.position = position;
		this.duration = duration;
	}
	@Override
	public void execute(Simulation simulation) {
		// TODO Auto-generated method stub
		if(!simulation.stationList[station].register()){
			System.out.println("[BLOCKCALL] at time "+time+" the car "+carId+" has been block in the station "+station);
			simulation.blockedCall ++;
			return;
		}
		System.out.println("[INITCALL] at time "+time+" the car "+carId+" has initted in the station "+station);
		System.out.println("{ the car's speed :"+speed+" position "+position+" base "+station+" duration "+duration+" }");
		System.out.println(" the station "+station+" has number of register "+simulation.stationList[station].registerChannel);
		double timeRunOnSection = (BaseStation.MAXLENGHT - position)/speed;
		Command command;
		if(timeRunOnSection >= duration){
			 command = new TerminatedCommand(time + duration, station,carId);
			System.out.println(" add new terminated command at "+(time+duration)+" station "+station+" car id "+carId);
			//add to the queue:finish in the block
		}else{
			 command = new HandOverCommand(time + timeRunOnSection, speed, station+1, duration- timeRunOnSection,carId);
			 System.out.println("hand over command time "+(time+timeRunOnSection)+" speed "+speed +" station "+(station+1)+ " duration "+(duration- timeRunOnSection+" car id "+carId));
			
			//add to the queue: handover to the next station;
		}
		simulation.queue.addCommand(command);
	}
}
