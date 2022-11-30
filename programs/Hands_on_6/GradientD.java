package examples.gradient;


import java.util.*;
import java.io.IOException;
import java.util.function.DoubleFunction;
import java.util.Random;


//JADE libraries
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;



/**
 * This example shows the basic usage of JADE behaviours.<br>
 * More in details this agent executes a <code>CyclicBehaviour</code> that shows
 * a printout at each round and a generic behaviour that performs four successive
 * "dummy" operations. The second operation in particular involves adding a
 * <code>OneShotBehaviour</code>. When the generic behaviour completes the
 * agent terminates.
 * @author Giovanni Caire - TILAB
 */
public class GradientD extends Agent {

  public Gradient gradient;





  protected void setup()   {
    System.out.println("Agent "+getLocalName()+" started.");



    // Add the generic behaviour
    addBehaviour(new FourStepBehaviour());
  }


  /**
   * Inner class FourStepBehaviour
   */
  private class FourStepBehaviour extends Behaviour {

    private int step = 1;
    public void action() {
      switch (step) {
      case 1:
        System.out.println("Welcome to Gradient Descent ");
        System.out.println("We are going to optimice this function y=B1*x + B0 ");
          gradient = new  Gradient();
        break;
      case 2:
        System.out.println("The gradient pick these random values to begin with  y=" + gradient.getB1()+"x + "+ gradient.getBo() );
        System.out.println("With iterations:  " + gradient.iter);
        gradient.performGradient();
        break;
      case 3:
        Scanner sc2= new Scanner(System.in);
        System.out.println("The optimize function is  y=" +gradient.getB1()+"x + "+gradient.getBo() );
        System.out.println("Would you like to go again [1] yes | other value nope");
        int ans2  = sc2.nextInt();
        if (ans2 == 1)
          step = 0;
        break;
      }
      step++;
    }

    public boolean done() {
      return step == 4;
    }

    public int onEnd() {
      myAgent.doDelete();
      return super.onEnd();
    }
  }    // END of inner class FourStepBehaviour

    public class Gradient {
      Random rand = new Random();

      double precision = 0.0;
      double stepCoefficient = 0.00000005; // step in which we drecrease or decrease x value
      private double b0=rand.nextInt(10)+1;
      private double b1=rand.nextInt(10)+1;
      DoubleFunction<Double> f; //Function of SLR activity
      public int iter = 5000; // Sometimes we don't know if the gradient is going to converge so to avoid getting stuck we set a limit
      double currentX= 0;
      double data_set_x []={230.1,44.5,17.2,151.5,180.8,8.7,57.5,120.2,8.6,199.8,66.1,214.7,23.8,97.5,204.1,195.4,67.8,281.4,69.2,147.3,218.4,237.4,13.2,228.3,62.3,262.9,142.9,240.1,248.8,70.6,292.9,112.9,97.2,265.6,95.7,290.7,266.9,74.7,43.1,228.0,202.5,177.0,293.6,206.9,25.1,175.1,89.7,239.9,227.2,66.9,199.8,100.4,216.4,182.6,262.7,198.9,7.3,136.2,210.8,210.7,53.5,261.3,239.3,102.7,131.1,69.0,31.5,139.3,237.4,216.8,199.1,109.8,26.8,129.4,213.4,16.9,27.5,120.5,5.4,116.0,76.4,239.8,75.3,68.4,213.5,193.2,76.3,110.7,88.3,109.8,134.3,28.6,217.7,250.9,107.4,163.3,197.6,184.9,289.7,135.2,222.4,296.4,280.2,187.9,238.2,137.9,25.0,90.4,13.1,255.4,225.8,241.7,175.7,209.6,78.2,75.1,139.2,76.4,125.7,19.4,141.3,18.8,224.0,123.1,229.5,87.2,7.8,80.2,220.3,59.6,0.7,265.2,8.4,219.8,36.9,48.3,25.6,273.7,43.0,184.9,73.4,193.7,220.5,104.6,96.2,140.3,240.1,243.2,38.0,44.7,280.7,121.0,197.6,171.3,187.8,4.1,93.9,149.8,11.7,131.7,172.5,85.7,188.4,163.5,117.2,234.5,17.9,206.8,215.4,284.3,50.0,164.5,19.6,168.4,222.4,276.9,248.4,170.2,276.7,165.6,156.6,218.5,56.2,287.6,253.8,205.0,139.5,191.1,286.0,18.7,39.5,75.5,17.2,166.8,149.7,38.2,94.2,177.0,283.6,232.1,0.0};
      double data_set_y [] = {22.1,10.4,12.0,16.5,17.9,7.2,11.8,13.2,4.8,15.6,12.6,17.4,9.2,13.7,19.0,22.4,12.5,24.4,11.3,14.6,18.0,17.5,5.6,20.5,9.7,17.0,15.0,20.9,18.9,10.5,21.4,11.9,13.2,17.4,11.9,17.8,25.4,14.7,10.1,21.5,16.6,17.1,20.7,17.9,8.5,16.1,10.6,23.2,19.8,9.7,16.4,10.7,22.6,21.2,20.2,23.7,5.5,13.2,23.8,18.4,8.1,24.2,20.7,14.0,16.0,11.3,11.0,13.4,18.9,22.3,18.3,12.4,8.8,11.0,17.0,8.7,6.9,14.2,5.3,11.0,11.8,17.3,11.3,13.6,21.7,20.2,12.0,16.0,12.9,16.7,14.0,7.3,19.4,22.2,11.5,16.9,16.7,20.5,25.4,17.2,16.7,23.8,19.8,19.7,20.7,15.0,7.2,12.0,5.3,19.8,18.4,21.8,17.1,20.9,14.6,12.6,12.2,9.4,15.9,6.6,15.5,7.0,16.6,15.2,19.7,10.6,6.6,11.9,24.7,9.7,1.6,17.7,5.7,19.6,10.8,11.6,9.5,20.8,9.6,20.7,10.9,19.2,20.1,10.4,12.3,10.3,18.2,25.4,10.9,10.1,16.1,11.6,16.6,16.0,20.6,3.2,15.3,10.1,7.3,12.9,16.4,13.3,19.9,18.0,11.9,16.9,8.0,17.2,17.1,20.0,8.4,17.5,7.6,16.7,16.5,27.0,20.2,16.7,16.8,17.6,15.5,17.2,8.7,26.2,17.6,22.6,10.3,17.3,20.9,6.7,10.8,11.9,5.9,19.6,17.3,7.6,14.0,14.8,25.5,18.4,0.0};


     /**
       * Performs a Gradient Descent regression and optmize a linear  function
       * @param  initial X value
       */
      public void performGradient(){
        double [] previousStep = mse();
        while (previousStep[2] > precision && iter > 0) {
            iter--;
            setBo(this.getBo()-(previousStep[0]*stepCoefficient));
            setB1(this.getB1()-(previousStep[1]*stepCoefficient));
            // System.out.println(this.getB1());
            // for (int i = 0; i < previousStep.length ; i++ ) {
            //   System.out.println(previousStep[0]+", "+previousStep[1]+", "+previousStep[2]);
            // }
            previousStep = mse();
        }

          System.out.println("B0 "+previousStep[0]+", B1 "+previousStep[1]+", MSE: "+previousStep[2]);
      }

     /**
       * @returnthe MSE of the parameters B0, B1 and current MSE of function
       */
      public double [] mse() {
         f=a -> ((b1*a) + b0);
        double mse [] = {0,0, 0};
        double sum_b0 = 0;
        double sum_b1 = 0;
        double sum_q = 0;

        for (int i = 0; i < data_set_x.length; ++i)
        {
              double p1 = data_set_y[i];
              double p2 = f.apply(data_set_x[i]);;
              double err = p1 - p2;
              sum_b0 += err ;
              sum_b1 += (err*data_set_x[i]) ;
              sum_q += (err*err) ;
        }
        mse[0] = (double)(sum_b0*-2) / data_set_x.length;
        mse[1] = (double)(sum_b1*-2) / data_set_x.length;
        mse[2] = (double)sum_q / data_set_x.length;
        return mse;
      }

      public void setBo(double num){
        this.b0 = num;
      }
      public void setB1(double num){
        this.b1 = num;
      }
      public double getBo(){
        return this.b0;
      }
      public double getB1(){
        return this.b1;
      }
  }
}
