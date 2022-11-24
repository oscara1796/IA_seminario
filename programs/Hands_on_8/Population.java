package examples.genetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class Population{
  int Popsize = 400;
  double fittest = 0;
  int totalFitness = 0;
  public Individual[] individuals;

  //Initialize population
    public void initializePopulation(int size) {
      individuals = new Individual[size];
        for (int i = 0; i < size; i++) {
            individuals[i] = new Individual();
        }
        this.Popsize = size;
    }

    void setOffSpring(Individual [] newPopulation){
      this.individuals = newPopulation;
    }

    //Get the fittest individual
    public Individual getFittest() {
        double maxFit = Double.MIN_VALUE;
        int maxFitIndex = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (maxFit <= individuals[i].fitness) {
                maxFit = individuals[i].fitness;

                maxFitIndex = i;

            }

        }
        fittest = individuals[maxFitIndex].fitness;
        return individuals[maxFitIndex];
    }

    //Get the second most fittest individual
    public Individual getSecondFittest() {
        int maxFit1 = 0;
        int maxFit2 = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (individuals[i].fitness > individuals[maxFit1].fitness) {
                maxFit2 = maxFit1;
                maxFit1 = i;
            } else if (individuals[i].fitness > individuals[maxFit2].fitness) {
                maxFit2 = i;
            }
        }
        return individuals[maxFit2];
    }
    //Get the second most fittest individual
    public   List<Individual>  selectIndividuals() {
        Random rng = new Random();
        calculateFitness();

        double[] cumulativeFitnesses = new double[this.Popsize];
        cumulativeFitnesses[0] = getAdjustedFitness(individuals[0].fitness, true);
        for (int i = 1; i < this.Popsize; i++)
        {
            double fitness = getAdjustedFitness(individuals[i].fitness, true);
            // System.out.println(fitness);
            cumulativeFitnesses[i] = cumulativeFitnesses[i - 1] + fitness;
        }

        List<Individual> selection = new ArrayList<Individual>(2);
        for (int i = 0; i < 2; i++)
        {
            double randomFitness = rng.nextDouble() * cumulativeFitnesses[cumulativeFitnesses.length - 1];
            // System.out.println(cumulativeFitnesses[cumulativeFitnesses.length - 1]);
            // System.out.println(randomFitness);
            int index = Arrays.binarySearch(cumulativeFitnesses, randomFitness);
            if (index < 0)
            {
                // Convert negative insertion point to array index.
                index = Math.abs(index + 1);

            }
            // System.out.println(index);
            selection.add(this.individuals[index]);
        }
        return selection;

    }

    private double getAdjustedFitness(double rawFitness,
                                      boolean naturalFitness)
    {
        if (naturalFitness)
        {
            return rawFitness;
        }
        else
        {
            // If standardised fitness is zero we have found the best possible
            // solution.  The evolutionary algorithm should not be continuing
            // after finding it.
            return rawFitness == 0 ? Double.POSITIVE_INFINITY : 1 / rawFitness;
        }
    }

    //Calculate fitness of each individual
    public void calculateFitness() {

        for (int i = 0; i < individuals.length; i++) {
            totalFitness += individuals[i].calcFitness();
        }
        getFittest();
    }

}
