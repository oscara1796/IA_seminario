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
 */
public class NumbersGenetic extends Agent {

  public Genetic genetic;





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
        System.out.println("Welcome to Numbers Genetic Algo ");
        System.out.println("We are going to try to find 4 numbers that add up to 30 ");
        genetic = new Genetic(400, 0.25);
        break;
      case 2:
        genetic.performAlgo();
        break;
      case 3:
        Scanner sc2= new Scanner(System.in);
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
