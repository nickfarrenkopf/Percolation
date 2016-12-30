package Methods;

/**
 *  PercolationStats inputs size of grid and number of trials and iterates through all percolations.
 *  Outputs mean, standard deviation, and 95% confidence interval of percolation threshold.
 *  Percolation threshold is proportion of percolations needed to percolate system. 
 */
public class PercolationStats {

	// Percolation object variable
	private Percolation perc;
	
	// Array to hold percolation threshold
	private double[] percThresh;
	
	/**
	 * Inputs size of grid and number of trials to perform multiple iterations of percolation.
	 * @param controller Controller variable
	 * @param n Size of n x n percolation grid
	 * @param trials Number of trials to perform.
	 */
	public PercolationStats(Controller controller, int n, int trials)
	{
		// Counter for number of open sites
		double counter;

		// Iterate trials number of times
		percThresh = new double[trials];
		for (int k=0; k<trials; k++)
		{
			// Create new percolation of size n
			perc = new Percolation(controller, n);
			counter = 0;

			// Continue until it percolates
			while (!perc.percolates())
			{
				perc.openRandom();
				counter++;
			}

			// Save number of open sites
			percThresh[k] = counter / (double)(n * n);
		}
	}
	
	/** Returns mean of current iteration
	 * @return double
	 */
	public double mean()
	{
		double sum = 0;
		for (double d:percThresh)
			sum += d;
		return sum / percThresh.length;
	}
	
	/** Returns standard deviation of current iteration
	 * @return double
	 */
	public double stddev()
	{
		double mean = mean();
		double sum = 0;
		for (double d:percThresh)
			sum += (d - mean) * (d - mean);
		return Math.sqrt(sum / percThresh.length);
	}
	
	/** Returns low end point of 95% confidence interval of current iteration
	 * @return double
	 */
	public double confidenceLo()
	{
		return mean() - 1.96 * stddev() / Math.sqrt(percThresh.length); 
	}

	/** Returns high end point of 95% confidence interval of current iteration
	 * @return double
	 */
	public double confidenceHi()
	{
		return mean() + 1.96 * stddev() / Math.sqrt(percThresh.length); 
	}	
}