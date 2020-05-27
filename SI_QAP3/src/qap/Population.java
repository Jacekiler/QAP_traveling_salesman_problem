package qap;

import java.util.Random;

/**
 * Population class.
 */
public class Population 
{
	private Individual[] individuals;
	private int best;
	private double avg;
	private int worst;
	//
	

	public Population(int pop_size)
	{
		individuals = new Individual[pop_size];
		best = Integer.MAX_VALUE;
		avg = 0;
		worst = 0;
	}
	

	public Population(Population population)
	{
		individuals = new Individual[population.individuals.length];
		for (int i = 0; i < individuals.length; i++)
		{
			individuals[i] = new Individual(population.individuals[i]);
		}
		best = population.best;
		avg = population.avg;
		worst = population.worst;
	}
	
	
	/**
	 * Creates whole population with random location assignments
	 *
	 * @param n 
	 */
	public void createRandomIndividuals(int n)
	{
		for (int i = 0; i < individuals.length; i++)
		{
			individuals[i] = new Individual(n);	
			individuals[i].createRandomLocations();
			
		}
	}
	
	/**
	 * Checks if mutation should be made and makes it if so.
	 *
	 * @return the population
	 */
	public Population mutation()
	{
		Random rand = new Random();
		double mutationCheck = rand.nextDouble();
		
		for (int i = 0; i < individuals.length; i++)
		{
			if (mutationCheck < Runner.P_M)
			{
				individuals[i].mutate();
			}
		
		}
	
		return this;
	}
	
	/**
	 * Tournament
	 *
	 * @return the individual who has the best rating of all tournament participants
	 */
	public Individual tournament()
	{
		Random rand = new Random();
		int winner = rand.nextInt(individuals.length);
		int current;
		
		for (int i = 0; i < Runner.TOURNAMENT_SIZE - 1; i++)
		{
			current = rand.nextInt(individuals.length);
			
			if (individuals[current].evaluate(Runner.distances, Runner.flows, Runner.N) < individuals[winner].evaluate(Runner.distances, Runner.flows, Runner.N))
			{
				winner = current;
			}
		}
		
		return individuals[winner];	
	}
	
	/**
	 * Process of selection. Each loop two individuals are chosen via tournament from current population, then are crossed if necessary, and are added to newPopulation.
	 * Loops until new population gain supposed size.
	 *
	 * @return new population.
	 */
	public Population selection()
	{
		Population newPopulation = new Population(individuals.length);
		Individual[] children = new Individual[2];
		Random rand = new Random();
		
		for (int i = 0; i < individuals.length; i++)
		{
			
			children[0] = new Individual(tournament());
			children[1] = new Individual(tournament());
			
			if(rand.nextDouble() < Runner.P_X)
			{
				children[0].cross(children[1]);
			}
			
			if(i < individuals.length - 1)
			{
				newPopulation.individuals[i] = children[0];
				newPopulation.individuals[++i] = children[1];
						
			}
			else
			{
				newPopulation.individuals[i] = children[0];
			}

		}
					
		return newPopulation;
	}
	
	

	
	

	/**
	 * Set population's best, average and worst values
	 *
	 * @param distances matrix
	 * @param flows matrix
	 * @param pop_size population size
	 * @param N matrices size
	 */
	public void ratePopulation(int[][] distances, int[][] flows, int pop_size, int N)
	{
		int avg_count = 0;
		int rating;
		for (int i = 0; i < pop_size; i++)
		{
			rating = individuals[i].evaluate(distances, flows, N);
			
			if (rating > worst)
			{
				worst = rating;
			}
			
			if(rating < best)
			{
				best = rating;
			}
			
			avg_count += rating;
		}
		
		avg = (double)avg_count/pop_size;
	}
	

	
	/**
	 * Prints population stats.
	 */
	public void printPopStats()
	{
		System.out.print(best+"   ");
		System.out.print(worst+"   ");
		System.out.println(avg);
	}

	public int getWorst() {
		return worst;
	}

	public void setWorst(int worst) {
		this.worst = worst;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public int getBest() {
		return best;
	}

	public void setBest(int best) {
		this.best = best;
	}

	public Individual[] getIndividuals() {
		return individuals;
	}

	public void setIndividuals(Individual[] individuals) {
		this.individuals = individuals;
	}
	
	
}
