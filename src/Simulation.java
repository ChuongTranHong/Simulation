import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Simulation {
	static ArrayList<Car> carList = new ArrayList<Car>();
	static BaseStation[] stationList = new BaseStation[10];
	static int handOver=0, droppedCall = 0, blockedCall = 0;
	static TimeQueueCommand queue = new TimeQueueCommand();
	public static void main(String args[]) {
		System.out.println("station lenght "+stationList.length);
		for(int i=0;i<stationList.length;i++)stationList[i] = new BaseStation();
		inputData();

	}
	public static void callInitiation(double time, double speed, int station, double position, double duration){
		if(!stationList[station].register())blockedCall ++;
		double timeRunOnSection = (BaseStation.MAXLENGHT - position)/speed;
		Command command;
		if(timeRunOnSection >= duration){
			 command = new TerminatedCommand(time + duration, station);
			
			//add to the queue:finish in the block
		}else{
			 command = new HandOverCommand(time + timeRunOnSection, speed, station+1, duration- timeRunOnSection);
			
			//add to the queue: handover to the next station;
		}
		queue.addCommand(command);
	}
	public static void termination(double time, int station){
		stationList[station].callTerminal();
	}
	public static void handOver(double time, double speed, int station, double duration){
		if(!stationList[station].registerHandOver()) handOver++;
		double timeRunOnSection = BaseStation.MAXLENGHT/speed;
		if(timeRunOnSection <= duration){
			//add to the queue: finish in the block;
		}else{
			// add to the queue: handover to the next station
		}
	}

	public static void inputData() {
		try {
			int limitRead = 10;
			int currentRead = 0;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream("call_arrival_g04.txt")));
			String strLine;
			br.readLine();
			br.readLine();
			while ((strLine = br.readLine()) != null && currentRead < limitRead) {
				StringTokenizer st = new StringTokenizer(strLine);
				st.nextToken();
				st.nextToken();
				Car car = new Car();
				car.initTime = Double.parseDouble(st.nextToken());
				car.baseStation = Integer.parseInt(st.nextToken());
				car.initPosition = Math.random() * 2;
				carList.add(car);
				currentRead++;
			}
			currentRead = 0;
			br.close();
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					"car_speed_g04.txt")));
			br.readLine();
			br.readLine();
			while ((strLine = br.readLine()) != null && currentRead < limitRead) {
				StringTokenizer st = new StringTokenizer(strLine);
				int index = Integer.parseInt(st.nextToken()) - 1;
				carList.get(index).speed = Double.parseDouble(st.nextToken());
				currentRead++;
			}
			currentRead = 0;
			br.close();
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					"call_duration_g04.txt")));
			br.readLine();
			br.readLine();
			while ((strLine = br.readLine()) != null && currentRead < limitRead) {
				StringTokenizer st = new StringTokenizer(strLine);
				int index = Integer.parseInt(st.nextToken()) - 1;
				Call call = new Call();
				call.duration = Double.parseDouble(st.nextToken());

				carList.get(index).call = call;
				currentRead++;
			}
			currentRead = 0;
			br.close();
			System.out.println("carlist length " + carList.size());
			System.out.println("car 0 " + carList.get(0).initTime + " base "
					+ carList.get(0).baseStation + "" + " speed "
					+ carList.get(0).speed + " call "
					+ carList.get(0).call.duration);
			System.out.println("car 0 "
					+ carList.get(carList.size() - 1).initTime + " " + "base "
					+ carList.get(carList.size() - 1).baseStation + " speed "
					+ carList.get(carList.size() - 1).speed + " call "
					+ carList.get(carList.size() - 1).call.duration);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
