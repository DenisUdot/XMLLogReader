package denisudot.gmail.com;

public interface Subject {
	public void registerOserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers();
}
