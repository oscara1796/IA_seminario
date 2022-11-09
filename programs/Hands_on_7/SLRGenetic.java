package examples.genetic;


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
public class SLRGenetic extends Agent {

  public Genetic genetic;
  double data_set_x []={23,26,30,34,43,48,52,57,58};
  double data_set_y [] = {651,762,856,1063,1190,1298,1421,1440,1518};





  protected void setup()   {
    System.out.println("Agent "+getLocalName()+" started.");
    System.out.println("Data set: ");
    System.out.print("x={" );
    for (int i=0;i < data_set_x.length ; i++ ) {
      if (i+1 == data_set_x.length) {
        System.out.print( data_set_x[i]);
      } else{
        System.out.print( data_set_x[i] + ",");
      }
    }
    System.out.print("}\n");
    System.out.print("y={" );
    for (int i=0;i < data_set_x.length ; i++ ) {
      if (i+1 == data_set_x.length) {
        System.out.print( data_set_y[i]);
      } else{
        System.out.print( data_set_y[i] + ",");
      }
    }
    System.out.print("}\n");


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
        System.out.println("Welcome to SLR Genetic Algo ");
        System.out.println("We are going to optimice this function y=B1*x + B0 ");
        Individual ind = new Individual();
        ind.betas[1] = 23;
        ind.betas[0] = 168;
        System.out.println(ind.calcFitness());
        genetic = new Genetic(400, 0.25);
        break;
      case 2:
        genetic.performAlgo();
        break;
      case 3:
        Scanner sc2= new Scanner(System.in);
        System.out.println("The optimize function is  y=" + genetic.fittest.betas[1]+"x + "+genetic.fittest.betas[0]);
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


}
