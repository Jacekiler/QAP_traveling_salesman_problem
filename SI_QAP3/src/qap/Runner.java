package qap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Alghoritm runner class.
 */
public class Runner 
{
	static final int NUMBER_OF_GENERATIONS = 150;
	static final int POPULATION_SIZE = 150;
	static final double P_X = 0.55;
	static final double P_M = 0.05;
	static final int TOURNAMENT_SIZE = 5;
	
	static int distances[][];
	static int flows[][];
	static int N;
	static Population[] populations;
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args)
	{
		readData("data_hadXX/had20.dat");

		createFirstPopulation();
		createAllPopulations();
		printPopulations();

	
	}
	
	
	
	/**
	 * Creates first population.
	 */
	public static void createFirstPopulation()
	{
		populations = new Population[NUMBER_OF_GENERATIONS];
		populations[0] = new Population(POPULATION_SIZE);
		populations[0].createRandomIndividuals(N);
		populations[0].ratePopulation(distances, flows, POPULATION_SIZE, N);	
	}
	
	/**
	 * Creates all populations.
	 */
	public static void createAllPopulations()
	{
		for (int i = 1; i < NUMBER_OF_GENERATIONS; i++)
		{
			populations[i] = populations[i-1].selection().mutation();
			populations[i].ratePopulation(distances, flows, POPULATION_SIZE, N);
		}
		
	}
	

	
	/**
	 * Reads data from file and set parameters.
	 *
	 * @param file the file
	 */
	public static void readData(String file)
	{
		try (Scanner scanner = new Scanner(new File(file)))
		{
			N = scanner.nextInt();			
			distances = new int[N][N];
			flows = new int[N][N];
			
			for (int i = 0; i < N; i++)
			{
				for (int j = 0; j < N; j++)
				{
					distances[i][j] = scanner.nextInt();
				}
			}
			
			for (int i = 0; i < N; i++)
			{
				for (int j = 0; j < N; j++)
				{
					flows[i][j] = scanner.nextInt();
				}
			}
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}

	}
	
	
	/**
	 * Prints the populations.
	 */
	public static void printPopulations()
	{
	
		for (int i = 0; i < populations.length; i++)
		{
			populations[i].printPopStats();
			
		}
	}
	
	

	
	
	
}
