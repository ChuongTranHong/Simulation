
public class InitCommand extends Command {
	double speed;
	double position;
	double duration;
	public InitCommand(double time, double speed, int station, double position, double duration){
		super(time, station);
		type = CALLINIT;
		this.speed = speed;
		this.position = position;
		this.duration = duration;
	}
}
