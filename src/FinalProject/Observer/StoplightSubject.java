package FinalProject.Observer;

public interface StoplightSubject {
	void notifyObservers();
	
	void addObserver(StoplightObserver o);
}