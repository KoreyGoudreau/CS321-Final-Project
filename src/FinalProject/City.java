package FinalProject;

import FinalProject.Factory.Building;
import FinalProject.Factory.BuildingFactory;
import FinalProject.Factory.Home;
import FinalProject.Factory.Destination;
import FinalProject.Observer.Stoplight;
import FinalProject.Skeleton.SimulationInput;

import java.util.ArrayList;

public class City {
	
	private static City instance = null;
	
	private final ArrayList<Stoplight> stoplights;
	private final ArrayList<Car> cars;
	
	private City(SimulationInput input) {
		
		// Setup Grid
		Grid grid = new Grid(input);
		this.stoplights = grid.getStoplights();
		
		// Setup Buildings
		ArrayList<Building> buildings = new ArrayList<>();
		ArrayList<String> buildingNames = input.getInput("BuildingNames");
		ArrayList<String> buildingTypes = input.getInput("BuildingTypes");
		ArrayList<String> openingTimes = input.getInput("OpeningTimes");
		
		// Incremented only for Buildings of type "destination"
		int destinationIndex = 0;
		
		for (int i = 0; i < buildingNames.size(); i++) {
			
			String name = buildingNames.get(i);
			String type = buildingTypes.get(i);
			
			// Example: "Building1" maps to ["0","2","Alex","Bob"]
			// Where (0,2) is the Cell of which is assigned the Building: "Building1"
			ArrayList<String> buildingData = input.getInput(name);
			
			int row = Integer.parseInt(buildingData.get(0));
			int col = Integer.parseInt(buildingData.get(1));
			
			Cell cell = grid.getCell(row, col);
			
			Building building;
			if (type.equals("destination")) {
				
				// Set up a Destination's opening time
				int openingTime = Integer.parseInt(openingTimes.get(destinationIndex));
				destinationIndex++;
				
				// Instantiate a Destination using values from input
				building = BuildingFactory.createDestination(cell, name, openingTime);
				System.out.println("Setting up Destination \"" + building.getBuildingName() + "\" (row = " + row + ", col = " + col + ").");
			} else {
				
				// Instantiate a Home using values from input
				building = BuildingFactory.createHome(cell, name);
				System.out.println("Setting up Home \"" + building.getBuildingName() + "\" (row = " + row + ", col = " + col + ").");
			}
			
			// Assign the instantiated Building if the Cell associated to its location on the Grid allows Buildings
			if (!cell.canSetBuilding()) {
				throw new IllegalArgumentException("Error: Cannot assign a Building to (row = " + cell.getRow() + ", col = " + cell.getCol() + ").");
			}
			buildings.add(building);
		}
		
		// Setup Cars
		this.cars = new ArrayList<>();
		ArrayList<String> names = input.getInput("OwnerNames");
		ArrayList<String> carSpeeds = input.getInput("CarSpeeds");
		ArrayList<String> routeStrategies = input.getInput("RouteStrategies");
		
		for (int i = 0; i < names.size(); i++) {
			
			String name = names.get(i);
			int speed = Integer.parseInt(carSpeeds.get(i));
			String routeStrategy = routeStrategies.get(i);
			
			Car car = new Car(name, speed, routeStrategy, input);
			
			// Add all Cars to this City's ArrayList of Cars
			this.cars.add(car);
		}
		
		// Assign Buildings to Cars (each Car gets a Home and Destination)
		for (int i = 0; i < buildingNames.size(); i++) {
			
			String buildingName = buildingNames.get(i);
			Building building = buildings.get(i);
			
			ArrayList<String> buildingData = input.getInput(buildingName);
			
			// Example: "Building1" maps to ["0","2","Alex","Bob"]
			// Where "Alex" and "Bob" are the names of the Cars assigned the Building named "Building1"
			for (int x = 2; x < buildingData.size(); x++) {
				
				String name = buildingData.get(x);
				
				for (Car car : this.cars) {
					if (car.getName().equals(name)) {
						
						if (building instanceof Home) {
							car.setHome((Home) building);
							System.out.println("Assigning Home \"" + building.getBuildingName() + "\" to Car \"" + car.getName() + "\".");
						} else if (building instanceof Destination) {
							car.setDestination((Destination) building);
							System.out.println("Assigning Destination \"" + building.getBuildingName() + "\" to Car \"" + car.getName() + "\".");
						} else {
							System.out.println("Error: Cannot set Building for Car \"" + car.getName() + "\".");
						}
						
						break;
					}
				}
			}
		}
	}
	
	/**
	 * This method ensures only 1 instance of City exists at a time.
	 *
	 * @param input the Simulation input
	 * @return a new instance of City created using input
	 */
	public static synchronized City getInstance(SimulationInput input) {
		if (instance == null) {
			instance = new City(input);
		}
		return instance;
	}
	
	/**
	 * Returns all the Stoplights in the city.
	 * Used in Matrix.java to be able to run all the Stoplights as threads.
	 *
	 * @return all the Stoplights in the city
	 */
	public ArrayList<Stoplight> getStoplights() {
		return this.stoplights;
	}
	
	/**
	 * Returns all the Cars in the city.
	 * Used in Matrix.java to be able to run all the Cars as threads.
	 *
	 * @return all the Cars in the city
	 */
	public ArrayList<Car> getCars() {
		return this.cars;
	}
}