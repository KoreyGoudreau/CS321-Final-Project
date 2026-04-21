package Skeleton;

public class LogStatistic extends Statistic {
	
	public LogStatistic(String name) {
		super(name);
	}
	
	/**
	 * Using LogStatistic to log a Chronological sequence of Strings.
	 * Do not need this function.
	 *
	 * @return invalid
	 */
	@Override
	public float summarize() {
		return 0;
	}
	
	/**
	 * Prints out all the Strings.
	 */
	@Override
	public void printStatistic() {
		for (Object value : this.values) {
			System.out.println(value);
		}
	}
}