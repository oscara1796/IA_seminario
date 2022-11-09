package examples.genetic;

import java.util.Random;
import java.util.function.DoubleFunction;


class Individual{
  Random rand = new Random();
  double fitness = 0;
  public int [] betas = {0,0};
  DoubleFunction<Double> f = a -> ((this.betas[1]*a) + this.betas[0]);
  double data_set_x []={23,26,30,34,43,48,52,57,58};
  double data_set_y [] = {651,762,856,1063,1190,1298,1421,1440,1518};


  public Individual(){
    betas[0] = rand.nextInt(100)+1;
    betas[1] = rand.nextInt(100)+1;
    fitness = 0;
  }
  public Individual(int beta0, int beta1){
    betas[0] = beta0;
    betas[1] = beta1;
    fitness = 0;
  }

  //Calculate fitness
   public double  calcFitness() {
     double mse  = 0;
     double sum_q = 0;

     for (int i = 0; i < data_set_x.length; ++i)
     {
           double p1 = data_set_y[i];
           double p2 = f.apply(data_set_x[i]);;
           double err = p1 - p2;
           sum_q += (err*err) ;
     }

     mse = (double) (sum_q / (double)data_set_x.length);
     fitness = mse;
     // System.out.println(fitness);
     return mse;
   }
}
