import java.io.PrintWriter;


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
	public void execute(Simulation simulation,PrintWriter print) {
		// TODO Auto-generated method stub
		if(!simulation.stationList[station].register()){
			if(Simulation.debugMode)print.println("[Block] call has been block");
			if(Simulation.debugMode)System.out.println("[BLOCKCALL] at time "+time+" the car "+carId+" has been block in the station "+station);
			simulation.blockedCall ++;
			return;
		}
		if(Simulation.debugMode)System.out.println("[INITCALL] at time "+time+" the car "+carId+" has initted in the station "+station);
		if(Simulation.debugMode)System.out.println("{ the car's speed :"+speed+" position "+position+" base "+station+" duration "+duration+" }");
		if(Simulation.debugMode)System.out.println(" the station "+station+" has number of register "+simulation.stationList[station].registerChannel);
		double timeRunOnSection = (BaseStation.MAXLENGHT - position)/speed;
		Command command;
		if(timeRunOnSection >= duration){
			 command = new TerminatedCommand(time + duration, station,carId);
			 if(Simulation.debugMode)print.println("add [terminated] at "+(time + duration)+" station "+station +" carid "+carId);
			 if(Simulation.debugMode)System.out.println(" add new terminated command at "+(time+duration)+" station "+station+" car id "+carId);
			//add to the queue:finish in the block
		}else{
			 command = new HandOverCommand(time + timeRunOnSection, speed, station+1, duration- timeRunOnSection,carId);
			 if(Simulation.debugMode) print.println("add [handover] at "+(time + timeRunOnSection)+" duration"+(duration- timeRunOnSection) );
			 if(Simulation.debugMode)System.out.println("hand over command time "+(time+timeRunOnSection)+" speed "+speed +" station "+(station+1)+ " duration "+(duration- timeRunOnSection+" car id "+carId));
			
			//add to the queue: handover to the next station;
		}
		if(Simulation.debugMode)print.println("</init>");
		if(Simulation.debugMode) print.println();
		simulation.queue.addCommand(command);
	}
	@Override
	public void display(PrintWriter print,Simulation simulation) {
		// TODO Auto-generated method stub
		if(!Simulation.debugMode) return;
		print.println("<init> time: "+time+"  car id "+carId+" Station "+station+" position "+position+" speed "+speed+" duration "+duration);
		print.println("number of register channel station has "+simulation.stationList[station].registerChannel);
	}
}
