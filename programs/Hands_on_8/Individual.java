package examples.genetic;

import java.util.Random;
import java.util.function.DoubleFunction;


class Individual{
  Random rand = new Random();
  int fitness = 0;
  public int [] betas = {0,0,0,0};


  public Individual(){
    betas[0] = rand.nextInt(100)+1;
    betas[1] = rand.nextInt(100)+1;
    betas[2] = rand.nextInt(100)+1;
    betas[3] = rand.nextInt(100)+1;
    fitness = 0;
  }

  public int sum_betas(){
    int result= 0;
    for (int i = 0; i < betas.length; i++ ) {
      result+= betas[i];
    }
    return result;
  }
  public void print_betas(){
    for (int i = 0; i < betas.length; i++ ) {
      System.out.print( betas[i] + "\t");
    }
    System.out.print("\n");
  }
  public Individual(int betas1[], int betas2[]){
    betas[rand.nextInt(4)] = betas1[rand.nextInt(4)];
    betas[rand.nextInt(4)] = betas1[rand.nextInt(4)];
    betas[rand.nextInt(4)] = betas2[rand.nextInt(4)];
    betas[rand.nextInt(4)] = betas2[rand.nextInt(4)];
    fitness = 0;
  }

  //Calculate fitness
   public int  calcFitness() {
      // System.out.println(fitness);
      fitness =  100 - Math.abs(this.sum_betas()-30);
      if (fitness < 0) {
        fitness= 0;
      }
      return fitness;
     // System.out.println(fitness);
   }
}
