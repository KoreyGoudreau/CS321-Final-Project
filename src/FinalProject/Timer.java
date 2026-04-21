package FinalProject;

public class Timer {
	
	private static long startTime;
	
	public static void startTimer() {
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * Returns the number of seconds passed since startTimer() was called.
	 *
	 * @return seconds elapsed as an int
	 */
	public static int getTime() {
		long timeInMilliseconds = System.currentTimeMillis() - startTime;
		return (int)(timeInMilliseconds / 1000);
	}
}