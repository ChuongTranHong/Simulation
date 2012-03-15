import java.util.ArrayList;

import org.omg.CORBA.Current;

public class TimeQueueCommand {
	ArrayList<Command> commandQueue = new ArrayList<Command>();

	public void addCommand(Command command) {
		if (commandQueue.size() == 0) {
			commandQueue.add(command);
			return;
		}
		boolean added = false;
		for (int i = 0; i < commandQueue.size(); i++) {
			Command currentCommand = commandQueue.get(i);
			if (command.compareTo(currentCommand) < 0) {
				commandQueue.add(i, command);
				added = true;
				break;
			}
		}
		if (!added)
			commandQueue.add(commandQueue.size(), command);
	}
	public Command deQueue(){

		return commandQueue.remove(0);
	}
	public boolean isEmpty(){
		return (commandQueue.size()==0);
	}
}
