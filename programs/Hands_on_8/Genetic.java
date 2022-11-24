package examples.genetic;
import java.util.Random;
import java.util.List;


public class Genetic {
  Random rand = new Random();
  Population population = new Population();
  public Individual fittest;
  Individual secondFittest;
  int generationCount = 0;
  int crossoverRate = 7;
  int elitismCount = 2;
  double mutationRate = 0.25;

  Genetic(int popSize, double mutationRate){
    this.mutationRate = mutationRate;
    population.initializePopulation(popSize);
  }

  void performAlgo(){
    population.calculateFitness();
    fittest = population.getFittest();
    // System.out.println(fittest.fitness);
    while( fittest.fitness < 100 ){
      crossover();
      mutation();
      population.calculateFitness();
      fittest = population.getFittest();
      this.generationCount++;
      System.out.println("Generation: " + (this.generationCount) + " Fittest: " + fittest.fitness);
      System.out.println(fittest.betas[0] + " " + fittest.betas[1]+ " " + fittest.betas[2] + " " + fittest.betas[3]);
    }
  }

  //Selection
    void selection() {

        List<Individual>  parents = population.selectIndividuals();
        // System.out.println("1: " + parents.get(0).fitness + " 2: " + parents.get(1).fitness );
        fittest = parents.get(0);
        secondFittest = parents.get(1);
    }

    void selectElite(){
      fittest = population.getFittest();
      secondFittest = population.getFittest();
    }

    //Crossover
    void crossover() {
      Individual [] newPopulation = new Individual[population.Popsize];
      for (int i = 0;i < (population.Popsize-elitismCount); i++ ) {
        if ( crossoverRate<(rand.nextInt(10)+1)) {
          selection();
          newPopulation[i] = new Individual(fittest.betas, secondFittest.betas);
          if (((population.Popsize-elitismCount)-2)-i > 2 ) {
            // System.out.println("Generation: " + (this.generationCount++) + " Fittest: " + fittest.fitness);
            newPopulation[i+1] = new Individual(secondFittest.betas, fittest.betas );
            i+=1;
          }
        }else{
          newPopulation[i] = population.individuals[i];
        }
      }
      selectElite();
      newPopulation[population.Popsize-2] = fittest;
      newPopulation[population.Popsize-1] = fittest;
      population.setOffSpring(newPopulation);
    }

    void mutation(){
      int sizeToMutate = (int) (mutationRate * population.Popsize);

      for (int i = 0;i < sizeToMutate ; i++) {
        int x = rand.nextInt(population.Popsize);
        population.individuals[x].betas[rand.nextInt(4)] = population.individuals[i].betas[rand.nextInt(4)] + rand.nextInt(5);
        population.individuals[x].betas[rand.nextInt(4)] = population.individuals[i].betas[rand.nextInt(4)] +  rand.nextInt(5);
      }
    }


}
