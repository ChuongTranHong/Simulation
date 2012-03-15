
public class HandOverCommand extends Command{
	double speed;
	double duration;
	public HandOverCommand(double time, double speed, int station, double duration){
		super(time, station);
		type = CALLHANDOVER;
		this.speed = speed;
		this.duration = duration;
	}
}
