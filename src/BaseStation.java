
public class BaseStation {
	final int MAXCHANNEL = 10;
	final static double MAXLENGHT = 2;
	int registerChannel = 0;
	
	public boolean register(){
		if(registerChannel == MAXCHANNEL)return false;
		registerChannel ++;
		return true;
	}
	public boolean registerHandOver(){
		if(registerChannel == MAXCHANNEL)return false;
		registerChannel ++;
		return true;
	}
	public void callTerminal(){
		registerChannel--;
	}
}
