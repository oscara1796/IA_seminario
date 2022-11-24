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
         double r2;

        int n = data_set_x.length;

        // first pass
        double sumx = 0.0, sumy = 0.0;
        for (int i = 0; i < n; i++) {
            sumx  += data_set_x[i];
            sumy  += data_set_y[i];
        }
        double xbar = sumx / n;
        double ybar = sumy / n;

        // second pass: compute summary statistics
        double yybar = 0.0;
        for (int i = 0; i < n; i++) {
            yybar += (data_set_y[i] - ybar) * (data_set_y[i] - ybar);
        }
        double slope  = this.betas[1];
        double intercept = this.betas[0];

        // more statistical analysis
        double ssr = 0.0;      // regression sum of squares
        for (int i = 0; i < n; i++) {
            double fit = slope*data_set_x[i] + intercept;
            ssr += (data_set_y[i]-fit) * (data_set_y[i]-fit);
        }

        r2    = 1-(ssr / yybar);
        if (r2 < 0) {
          fitness= 0;
        }else{

          fitness= r2;
        }
      // System.out.println(fitness);
      return r2;
     // System.out.println(fitness);
   }
}
