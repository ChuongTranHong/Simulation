import java.util.Random;


public class VariateGenerate {
	public static double uniformDistributionDouble(int begin, int end){
		int margin  = end - begin;
		double returnValue = begin + margin*Math.random();
		return returnValue;
	}
	public static int uniformDistributionInteger(double begin , double end){
		double margin = end - begin;
		int returnValue = (int)Math.round (begin +margin*Math.random());
		return returnValue;
	}
	public static double exponentialDistribution(double parameter){
		double random = Math.random();
		double returnValue = -Math.log(1-random)*parameter;
		return returnValue;
	}
	public static double normalDistribution(double mean, double variance){
		
		return (double)(mean + fRandom.nextGaussian() * variance);
	}
//	public static void main(String... aArgs){
//	    RandomGaussian gaussian = new RandomGaussian();
//	    double MEAN = 100.0f; 
//	    double VARIANCE = 5.0f;
//	    
////	    for (int idx = 1; idx <= 10; ++idx){
////	      log("Generated : " + gaussian.getGaussian(MEAN, VARIANCE));
////	    }
//	  }
	    
	  private static Random fRandom = new Random();
	  
//	  private double getGaussian(double aMean, double aVariance){
//	      return aMean + fRandom.nextGaussian() * aVariance;
//	  }
}
