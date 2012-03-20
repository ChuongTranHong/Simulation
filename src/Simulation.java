import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Simulation {
	 public ArrayList<Car> carList = new ArrayList<Car>();
	 public BaseStation[] stationList = new BaseStation[20];
	 public int handOver=0, droppedCall = 0, blockedCall = 0;
	 public TimeQueueCommand queue;
	 public static boolean debugMode = false;
	 public static boolean reserve = true;
	 public  Simulation(){
		 for(int i=0;i<stationList.length;i++)stationList[i] = new BaseStation(i);
		 queue = new TimeQueueCommand();
	 }
	public static void main(String args[]) throws NumberFormatException, IOException {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("debug mode?");
		int mode = sc.nextInt();
		if(mode == 1 ) debugMode = true;
		System.out.println("input number of car");
		
		int numberOfCar = sc.nextInt();
		System.out.println("input number of loop");
		int loop = sc.nextInt();
		double averageHandOVer= 0;
		double averageDropCall = 0;
		double averageblockCall = 0;
//		System.out.println()
		for(int i=0;i<loop;i++){
			Simulation simulation = new Simulation();
//			simulation.inputSampling(numberOfCar);

			simulation.input();
			simulation.initCall();

			simulation.run();
			simulation.displayResult();
			averageblockCall += simulation.blockedCall;
			averageDropCall += simulation.droppedCall;
			averageHandOVer +=simulation.handOver;
		}
		System.out.println(" ********************");
		System.out.println("average No of hand over "+ (averageHandOVer/loop));
		System.out.println("average No of drop call  "+ (averageDropCall/loop));
		System.out.println("average No of block call "+ (averageblockCall/loop));
	}
//	public void displayQueue(TimeQueueCommand queue){
//		for(int i=0;i<queue.size();i++){
//			InitCommand command = (InitCommand)queue.getCommand(i);
//			System.out.println("id "+i +" carid "+command.carId+" time "+command.time+" station "+command.station+" initPo "+command.position+" duration "+command.duration);
//		}
//	}
	public void run() throws IOException{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				"output.xml")));
		while(!queue.isEmpty()){
			Command command = queue.deQueue();
			command.display(out, this);
			command.execute(this,out);
		}
		out.close();
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
//			System.out.println("id "+i +" time "+car.initTime+" station "+car.baseStation+" speed "+car.speed+" duration "+car.call.duration);
			Command command = new InitCommand(car.initTime, car.speed, car.baseStation,car.initPosition, car.call.duration, i);
			queue.addCommand(command);
		}
	}
	public void inputSampling(int numberOfData){
		double currentTime=0;
		for(int i = 0;i<numberOfData;i++){
			Car car = new Car();
			double speed = VariateGenerate.normalDistribution(104, 16.9)/3.6;
			car.speed = speed;
			int station = VariateGenerate.uniformDistributionInteger(0.5, 20.5)-1;
//			car.baseStation = 0;
			car.baseStation = station;
//			System.out.println("station "+station);
			car.initPosition = VariateGenerate.uniformDistributionDouble(0, 2000);
			double interval = VariateGenerate.exponentialDistribution(1.92);
			currentTime+= interval;
			car.initTime = currentTime;
			double duration = VariateGenerate.exponentialDistribution(155);
			car.call.duration = duration;
			carList.add(car);
		}
	}
	/*public  void inputData() {
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
	}*/
	public void input() throws NumberFormatException, IOException{
	
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream("station.txt")));
		String strLine;
		double speed;
		double interval;
		double duration;
		double initPosition;
		int station;
		double currentTime = 0;
		int i=0;
		int count =0;
		while ((strLine = br.readLine()) != null  && count<10000) {
			
			StringTokenizer st = new StringTokenizer(strLine);
			initPosition=Double.parseDouble(st.nextToken());
			interval = Double.parseDouble(st.nextToken());
			currentTime +=interval;
			station = Integer.parseInt(st.nextToken());
			duration = Double.parseDouble(st.nextToken());
			speed = Double.parseDouble(st.nextToken());

			Car car = new Car();
			car.initTime = currentTime;
			car.baseStation = station;
			car.initPosition = initPosition;
			car.speed = speed/3.6;
			car.call.duration = duration;
			carList.add(car);
			count++;
		}
	}
}
