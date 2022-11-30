package examples.mlr;

// Libraries to read file
import Jama.Matrix;
import Jama.QRDecomposition;

import java.util.*;
import java.io.IOException;


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
public class MLR extends Agent {




  MultipleLinearRegression regression;


  protected void setup()   {
    System.out.println("Agent "+getLocalName()+" started.");
    double[][] x = {{1,230.1,37.8},{1,44.5,39.3},{1,17.2,45.9},{1,151.5,41.3},{1,180.8,10.8},{1,8.7,48.9},{1,57.5,32.8},{1,120.2,19.6},{1,8.6,2.1},{1,199.8,2.6},{1,66.1,5.8},{1,214.7,24.0},{1,23.8,35.1},{1,97.5,7.6},{1,204.1,32.9},{1,195.4,47.7},{1,67.8,36.6},{1,281.4,39.6},{1,69.2,20.5},{1,147.3,23.9},{1,218.4,27.7},{1,237.4,5.1},{1,13.2,15.9},{1,228.3,16.9},{1,62.3,12.6},{1,262.9,3.5},{1,142.9,29.3},{1,240.1,16.7},{1,248.8,27.1},{1,70.6,16.0},{1,292.9,28.3},{1,112.9,17.4},{1,97.2,1.5},{1,265.6,20.0},{1,95.7,1.4},{1,290.7,4.1},{1,266.9,43.8},{1,74.7,49.4},{1,43.1,26.7},{1,228.0,37.7},{1,202.5,22.3},{1,177.0,33.4},{1,293.6,27.7},{1,206.9,8.4},{1,25.1,25.7},{1,175.1,22.5},{1,89.7,9.9},{1,239.9,41.5},{1,227.2,15.8},{1,66.9,11.7},{1,199.8,3.1},{1,100.4,9.6},{1,216.4,41.7},{1,182.6,46.2},{1,262.7,28.8},{1,198.9,49.4},{1,7.3,28.1},{1,136.2,19.2},{1,210.8,49.6},{1,210.7,29.5},{1,53.5,2.0},{1,261.3,42.7},{1,239.3,15.5},{1,102.7,29.6},{1,131.1,42.8},{1,69.0,9.3},{1,31.5,24.6},{1,139.3,14.5},{1,237.4,27.5},{1,216.8,43.9},{1,199.1,30.6},{1,109.8,14.3},{1,26.8,33.0},{1,129.4,5.7},{1,213.4,24.6},{1,16.9,43.7},{1,27.5,1.6},{1,120.5,28.5},{1,5.4,29.9},{1,116.0,7.7},{1,76.4,26.7},{1,239.8,4.1},{1,75.3,20.3},{1,68.4,44.5},{1,213.5,43.0},{1,193.2,18.4},{1,76.3,27.5},{1,110.7,40.6},{1,88.3,25.5},{1,109.8,47.8},{1,134.3,4.9},{1,28.6,1.5},{1,217.7,33.5},{1,250.9,36.5},{1,107.4,14.0},{1,163.3,31.6},{1,197.6,3.5},{1,184.9,21.0},{1,289.7,42.3},{1,135.2,41.7},{1,222.4,4.3},{1,296.4,36.3},{1,280.2,10.1},{1,187.9,17.2},{1,238.2,34.3},{1,137.9,46.4},{1,25.0,11.0},{1,90.4,0.30000000000000004},{1,13.1,0.4},{1,255.4,26.9},{1,225.8,8.2},{1,241.7,38.0},{1,175.7,15.4},{1,209.6,20.6},{1,78.2,46.8},{1,75.1,35.0},{1,139.2,14.3},{1,76.4,0.8},{1,125.7,36.9},{1,19.4,16.0},{1,141.3,26.8},{1,18.8,21.7},{1,224.0,2.4},{1,123.1,34.6},{1,229.5,32.3},{1,87.2,11.8},{1,7.8,38.9},{1,80.2,0.0},{1,220.3,49.0},{1,59.6,12.0},{1,0.7,39.6},{1,265.2,2.9},{1,8.4,27.2},{1,219.8,33.5},{1,36.9,38.6},{1,48.3,47.0},{1,25.6,39.0},{1,273.7,28.9},{1,43.0,25.9},{1,184.9,43.9},{1,73.4,17.0},{1,193.7,35.4},{1,220.5,33.2},{1,104.6,5.7},{1,96.2,14.8},{1,140.3,1.9},{1,240.1,7.3},{1,243.2,49.0},{1,38.0,40.3},{1,44.7,25.8},{1,280.7,13.9},{1,121.0,8.4},{1,197.6,23.3},{1,171.3,39.7},{1,187.8,21.1},{1,4.1,11.6},{1,93.9,43.5},{1,149.8,1.3},{1,11.7,36.9},{1,131.7,18.4},{1,172.5,18.1},{1,85.7,35.8},{1,188.4,18.1},{1,163.5,36.8},{1,117.2,14.7},{1,234.5,3.4},{1,17.9,37.6},{1,206.8,5.2},{1,215.4,23.6},{1,284.3,10.6},{1,50.0,11.6},{1,164.5,20.9},{1,19.6,20.1},{1,168.4,7.1},{1,222.4,3.4},{1,276.9,48.9},{1,248.4,30.2},{1,170.2,7.8},{1,276.7,2.3},{1,165.6,10.0},{1,156.6,2.6},{1,218.5,5.4},{1,56.2,5.7},{1,287.6,43.0},{1,253.8,21.3},{1,205.0,45.1},{1,139.5,2.1},{1,191.1,28.7},{1,286.0,13.9},{1,18.7,12.1},{1,39.5,41.1},{1,75.5,10.8},{1,17.2,4.1},{1,166.8,42.0},{1,149.7,35.6},{1,38.2,3.7},{1,94.2,4.9},{1,177.0,9.3},{1,283.6,42.0},{1,232.1,8.6}};
    double[] y={22.1,10.4,12.0,16.5,17.9,7.2,11.8,13.2,4.8,15.6,12.6,17.4,9.2,13.7,19.0,22.4,12.5,24.4,11.3,14.6,18.0,17.5,5.6,20.5,9.7,17.0,15.0,20.9,18.9,10.5,21.4,11.9,13.2,17.4,11.9,17.8,25.4,14.7,10.1,21.5,16.6,17.1,20.7,17.9,8.5,16.1,10.6,23.2,19.8,9.7,16.4,10.7,22.6,21.2,20.2,23.7,5.5,13.2,23.8,18.4,8.1,24.2,20.7,14.0,16.0,11.3,11.0,13.4,18.9,22.3,18.3,12.4,8.8,11.0,17.0,8.7,6.9,14.2,5.3,11.0,11.8,17.3,11.3,13.6,21.7,20.2,12.0,16.0,12.9,16.7,14.0,7.3,19.4,22.2,11.5,16.9,16.7,20.5,25.4,17.2,16.7,23.8,19.8,19.7,20.7,15.0,7.2,12.0,5.3,19.8,18.4,21.8,17.1,20.9,14.6,12.6,12.2,9.4,15.9,6.6,15.5,7.0,16.6,15.2,19.7,10.6,6.6,11.9,24.7,9.7,1.6,17.7,5.7,19.6,10.8,11.6,9.5,20.8,9.6,20.7,10.9,19.2,20.1,10.4,12.3,10.3,18.2,25.4,10.9,10.1,16.1,11.6,16.6,16.0,20.6,3.2,15.3,10.1,7.3,12.9,16.4,13.3,19.9,18.0,11.9,16.9,8.0,17.2,17.1,20.0,8.4,17.5,7.6,16.7,16.5,27.0,20.2,16.7,16.8,17.6,15.5,17.2,8.7,26.2,17.6,22.6,10.3,17.3,20.9,6.7,10.8,11.9,5.9,19.6,17.3,7.6,14.0,14.8,25.5,18.4};
    regression = new MultipleLinearRegression(x, y);

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
        System.out.println("Welcome to multiple linear regression");
        break;
      case 2:
        System.out.printf("y= %.2f beta 0 + (%.2f beta1)*x1 + (%.2f beta2)*x2  (R^2 = %.2f)\n",
                      regression.beta(0), regression.beta(1), regression.beta(2), regression.R2());

        break;
      case 3:
        while(true){
          Scanner sc= new Scanner(System.in);
          System.out.println("Would insert first x to predict: ");
          double ans  = sc.nextDouble();
          System.out.println("Would insert second x to predict: ");
          double ans2  = sc.nextDouble();
          System.out.println("Answer: " + regression.predict(ans, ans2));
          if (ans < 0) break;
        }
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

    public class MultipleLinearRegression {
      private final Matrix beta;  // regression coefficients
      private double sse;         // sum of squared
      private double sst;         // sum of squared

     /**
       * Performs a linear regression on the data points {@code (y[i], x[i][j])}.
       * @param  x the values of the predictor variables
       * @param  y the corresponding values of the response variable
       * @throws IllegalArgumentException if the lengths of the two arrays are not equal
       */
      public MultipleLinearRegression(double[][] x, double[] y) {
          if (x.length != y.length) {
              throw new IllegalArgumentException("matrix dimensions don't agree");
          }

          // number of observations
          int n = y.length;

          Matrix matrixX = new Matrix(x);

          // create matrix from vector
          Matrix matrixY = new Matrix(y, n);

          // find least squares solution
          QRDecomposition qr = new QRDecomposition(matrixX);
          beta = qr.solve(matrixY);


          // mean of y[] values
          double sum = 0.0;
          for (int i = 0; i < n; i++)
              sum += y[i];
          double mean = sum / n;

          // total variation to be accounted for
          for (int i = 0; i < n; i++) {
              double dev = y[i] - mean;
              sst += dev*dev;
          }

          // variation not accounted for
          Matrix residuals = matrixX.times(beta).minus(matrixY);
          sse = residuals.norm2() * residuals.norm2();

      }

     /**
       * Returns the least squares estimate of &beta;<sub><em>j</em></sub>.
       *
       * @param  j the index
       * @return the estimate of &beta;<sub><em>j</em></sub>
       */
      public double beta(int j) {
          return beta.get(j, 0);
      }

     /**
       * Returns the coefficient of determination <em>R</em><sup>2</sup>.
       *
       * @return the coefficient of determination <em>R</em><sup>2</sup>,
       *         which is a real number between 0 and 1
       */
      public double R2() {
          return 1.0 - sse/sst;
      }

      public double predict(double x1, double x2) {
          return  beta(0) + beta(1)*x1 + beta(2)*x2;
      }


  }




}
