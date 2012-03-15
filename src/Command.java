
public class  Command implements Comparable<Command> {
	final int CALLINIT = 3;
	final int CALLHANDOVER = 2;
	final int CALLTERMINATE = 1;
	
	int type;
	double time;
	int station;
	public  Command(double time, int station){
		this.time = time;
		this.station = station;
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
}
