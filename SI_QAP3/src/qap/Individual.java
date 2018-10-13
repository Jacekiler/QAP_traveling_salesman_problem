package qap;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Individual class.
 */
public class Individual 
{
	private Integer[][] assignments;
	

	public Individual(int n)
	{
		assignments = new Integer[2][n];
		for (int i = 0; i < n; i++)
		{
			assignments[0][i] = i;
		}
	}
	
	public Individual(Individual individual)
	{
		int n = individual.assignments[0].length;
		assignments = new Integer[2][n];
		
		for (int i = 0; i < n; i++)
		{
			assignments[0][i] = individual.assignments[0][i];
			assignments[1][i] = individual.assignments[1][i];
		}
	}
	
	/**
	 * Assigns random locations
	 */
	public void createRandomLocations()
	{
		for (int i = 0; i < assignments[0].length; i++)
		{
			assignments[1][i] = i;
		}
		Collections.shuffle(Arrays.asList(assignments[1]));
		
	}
	
	
	/**
	 * makes crossover of two individuals
	 *
	 * @param other - second individual in crossover 
	 */
	public void cross(Individual other)
	{
	
		Random rand = new Random();
		int pivot = rand.nextInt(assignments[0].length -1) + 1;
		int temp;
		
		for(int i = pivot; i < assignments[0].length; i++)
		{
			temp = assignments[1][i];
			assignments[1][i] = other.assignments[1][i];
			other.assignments[1][i] = temp;
		}
		
		checkAssignments();
		other.checkAssignments();
	
	
	}
	
	
	/**
	 * Check if assignments are correct. Repair if not.
	 */
	public void checkAssignments()
	{
		boolean[] in = new boolean[assignments[1].length];
				
		for (int i = 0; i < assignments[1].length; i++)
		{
			if (in[assignments[1][i]] == false)
			{
				in[assignments[1][i]] = true;
			}
			else
			{
				assignments[1][i] = -1;
			}
		}

		for (int i = 0; i < assignments[1].length; i++)
		{
			if (assignments[1][i] == -1)
			{
				boolean found = false;
				int j = 0;
				do
				{
					if (in[j] == false)
					{
						assignments[1][i] = j;
						in[j] = true;
						found = true;
					}
					j++;
				}
				while (!found);
			}
		}
	}
	
	
	
	/**
	 * Mutation process of an individual. Swap two random locations. 
	 */
	public void mutate()
	{
			
		Random rand = new Random();
		int first = rand.nextInt(assignments[0].length);
		int second;
		
		do
		{
			second = rand.nextInt(assignments[0].length);
		}
		while (second == first);
		
		int temp = assignments[1][first];
		assignments[1][first] = assignments[1][second];
		assignments[1][second] = temp;

	}

	
	
	/**
	 * Evaluation function.
	 *
	 * @param distances matrix
	 * @param flows matrix
	 * @param n size of matrices
	 * @return rating of the individual
	 */
	// evaluation function for simple individual
	public int evaluate(int[][] distances, int[][] flows, int n)
	{
		int result = 0;
	   	
		for (int i = 0 ; i < n; i++)
	   	{
	   		for (int j = 0; j < n; j++)
	   		{
	   			if (i != j)
	   			{
	   				result += distances[assignments[0][i]][assignments[0][j]] * flows[assignments[1][i]][assignments[1][j]];
	   			}
	   		   			
	   		}
	   	}
			
		return result;
	}

	public Integer[][] getAssignments() 
	{
		return assignments;
	}

	public void setAssignments(Integer[][] assignments)
	{
		this.assignments = assignments;
	}
	
	
	
}
