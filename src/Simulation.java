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
	 public ArrayList<Car> carList = new ArrayList<Car>();
	 public BaseStation[] stationList = new BaseStation[20];
	 public int handOver=0, droppedCall = 0, blockedCall = 0;
	 public TimeQueueCommand queue;
	 public  Simulation(){
		 for(int i=0;i<stationList.length;i++)stationList[i] = new BaseStation(i);
		 queue = new TimeQueueCommand();
	 }
	public static void main(String args[]) {
		Simulation simulation = new Simulation();
		simulation.inputData();
		simulation.initCall();
		simulation.run();
		simulation.displayResult();
	}
	public void run(){
		while(!queue.isEmpty()){
			Command command = queue.deQueue();
			command.execute(this);
		}
	}
	public void displayResult(){
		System.out.println("--------------------");
		System.out.println("number of hand over "+handOver);
		System.out.println("number of drop call "+droppedCall);
		System.out.println("number of block call "+blockedCall);
	}
	public void initCall(){
		for(int i=0;i<carList.size();i++){
			Car car = carList.get(i);
			Command command = new InitCommand(car.initTime, car.speed, car.baseStation,car.initPosition, car.call.duration, i);
			queue.addCommand(command);
		}
	}

	public  void inputData() {
		try {
			int limitRead = 200;
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
				car.baseStation = Integer.parseInt(st.nextToken())-1;
				car.initPosition = Math.random() * 2000;
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
				carList.get(index).speed = Double.parseDouble(st.nextToken())*1000/3600;
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
//			System.out.println("carlist length " + carList.size());
//			System.out.println("car 0 " + carList.get(0).initTime + " base "
//					+ carList.get(0).baseStation + "" + " speed "
//					+ carList.get(0).speed + " call "
//					+ carList.get(0).call.duration);
//			System.out.println("car 0 "
//					+ carList.get(carList.size() - 1).initTime + " " + "base "
//					+ carList.get(carList.size() - 1).baseStation + " speed "
//					+ carList.get(carList.size() - 1).speed + " call "
//					+ carList.get(carList.size() - 1).call.duration);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
