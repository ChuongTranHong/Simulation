
public class TerminatedCommand extends Command{
	public TerminatedCommand(double time, int station){
		super(time,station);
		type = CALLTERMINATE;
	}
}
