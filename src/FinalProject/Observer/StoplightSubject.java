package FinalProject.Observer;

public interface StoplightSubject {
	public void notifyObservers();
	public void addObserver(StoplightObserver o);
}