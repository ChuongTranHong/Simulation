public class BaseStation {
	final int MAXCHANNEL = 10;
	public int id;
	final static double MAXLENGHT = 2000;
	public int registerChannel = 0;
	public BaseStation(int id){
		this.id = id;
	}
	public boolean register() {
		if (registerChannel >= MAXCHANNEL){
			System.out.println("register station "+id+" register out of channel");
			return false;
		}
		registerChannel++;
		System.out.println("register station "+id+" number of register "+registerChannel);
		return true;
	}

	public boolean registerHandOver() {
		if (registerChannel >= MAXCHANNEL){
			System.out.println("handover  station "+id+" register out of channel");
			return false;
		}
		registerChannel++;
		System.out.println("handover station "+id+" number of register "+registerChannel);
		return true;
	}

	public void callTerminal() {
		registerChannel--;
		System.out.println("terminal station "+id+" number of register "+registerChannel);
	}
}
