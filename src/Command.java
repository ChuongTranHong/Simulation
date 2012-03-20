import java.io.PrintWriter;


public abstract class  Command implements Comparable<Command> {
	final int CALLINIT = 3;
	final int CALLHANDOVER = 2;
	final int CALLTERMINATE = 1;
	int carId;
	int type;
	double time;
	int station;
	public  Command(double time, int station, int id){
		this.time = time;
		this.station = station;
		this.carId= id;
	}

	@Override
	public int compareTo(Command o) {
		// TODO Auto-generated method stub
		if(this.time< o.time)return -1;
		else if(this.time > o.time)return 1;
		else if(this.time == o.time){
			if(this.type< o.type)return -1;
			else if(this.type > o.type)return 1;
			else return 0;
		}
		return 0;
	}
	public abstract void execute(Simulation simulation,PrintWriter print);
	public abstract void display(PrintWriter print, Simulation simulation);
}
