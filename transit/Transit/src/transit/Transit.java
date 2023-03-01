package transit;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) { trainZero = tz; }
	
	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero () {
		return trainZero;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {
		TNode walkingZero = new TNode();
		TNode busZero = new TNode(0, null, walkingZero);
	    this.trainZero = new TNode(0, null, busZero);
		int walkingLocation = 0, busLocation = 0, trainLocation = 0;
		TNode newWalkingNode = null, newBusNode = null, newTrainNode = null;
		TNode previousWalkingNode = walkingZero;
		TNode previousBusNode = busZero;
		TNode previousTrainNode = this.trainZero;
		for(int walkingIndex = 0, busIndex = 0, trainIndex = 0; walkingIndex < locations.length && busIndex < busStops.length && trainIndex < trainStations.length; walkingIndex ++){
			walkingLocation = locations[walkingIndex];
			newWalkingNode = new TNode(walkingLocation);
			previousWalkingNode.setNext(newWalkingNode);
			previousWalkingNode = newWalkingNode;
			if(walkingLocation == busStops[busIndex]){
				busLocation = busStops[busIndex];
				newBusNode = new TNode(busLocation, null, newWalkingNode);
				previousBusNode.setNext(newBusNode);
				previousBusNode = newBusNode;
				if(busIndex + 1 < busStops.length)
				busIndex ++;
				if(busLocation == trainStations[trainIndex]){
					trainLocation = trainStations[trainIndex];
					newTrainNode = new TNode(trainLocation, null, newBusNode);
					previousTrainNode.setNext(newTrainNode);
					previousTrainNode = newTrainNode;
					if(trainIndex + 1 < trainStations.length)
					trainIndex ++;
				}
			}
		}
	}
	
	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
		TNode currentNode = this.trainZero;
		TNode previousTrainNode = null;
		while(currentNode != null && currentNode.getLocation() != station){
			previousTrainNode = currentNode;
			currentNode = currentNode.getNext();
		}
		if(currentNode == null){
			return;
		}
		if(currentNode.getLocation() == station){
			previousTrainNode.setNext(currentNode.getNext());
			currentNode = null;
		}
	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
	    TNode currentBusNode = this.trainZero.getDown();
		TNode currentWalkingNode = currentBusNode.getDown();
		while(currentWalkingNode != null){
			if(currentBusNode.getNext() != null && currentWalkingNode.getLocation() == busStop && (currentBusNode.getLocation() == busStop || currentBusNode.getNext().getLocation() == busStop)){
				break;
			}
			if(currentWalkingNode.getLocation() == busStop && currentBusNode.getNext() == null){
				TNode newBusNode = new TNode(busStop, currentBusNode.getNext(), currentWalkingNode);
				currentBusNode.setNext(newBusNode);
				break;
			}
			if(currentBusNode.getNext() != null && currentWalkingNode.getLocation() == currentBusNode.getNext().getLocation()){
				currentBusNode = currentBusNode.getNext();
			}
			if(currentBusNode.getNext() != null && currentWalkingNode.getLocation() == busStop && currentBusNode.getNext().getLocation() != busStop){
				TNode newBusNode = new TNode(busStop, currentBusNode.getNext(), currentWalkingNode);
				currentBusNode.setNext(newBusNode);
				break;
			}
			currentWalkingNode = currentWalkingNode.getNext();
		}
		if(currentWalkingNode == null){
			return;
		}
		return;
	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {
		ArrayList<TNode> bestPathList = new ArrayList<TNode>();
		TNode currentNode = this.trainZero;
		TNode tempNode = currentNode.getDown().getDown();
		while(tempNode.getNext() != null){ 
			tempNode = tempNode.getNext(); 
		}
		if(tempNode.getLocation() < destination){ 
			return bestPathList;
		}
		while(currentNode != null && currentNode.getLocation() <= destination){
			if(currentNode.getLocation() == destination){
				bestPathList.add(currentNode);
				if(currentNode.getDown() != null){
					currentNode = currentNode.getDown();
					continue;
				}
				else{
					break;
				}
			}
			if(currentNode.getNext() == null || currentNode.getNext().getLocation() > destination){
				bestPathList.add(currentNode);
				if(currentNode.getDown() != null)
				currentNode = currentNode.getDown();
				continue;
			}
			if(currentNode.getLocation() < destination && currentNode.getNext().getLocation() <= destination){
				bestPathList.add(currentNode);
			}
			currentNode = currentNode.getNext();
		}
	    return bestPathList;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {
		
		int[] trainStationsCopy, busStopsCopy, locationsCopy;
		int count = 0;
		TNode walkingZeroCopy = new TNode();
		TNode busZeroCopy = new TNode(0, null, walkingZeroCopy);
		TNode trainZeroCopy = new TNode(0, null, busZeroCopy);
		for(TNode tempNode = this.trainZero.getNext(); tempNode != null; tempNode = tempNode.getNext()){
			count ++;
		}
		trainStationsCopy = new int[count];
		count = 0;
		for(TNode tempNode = this.trainZero.getDown().getNext(); tempNode != null; tempNode = tempNode.getNext()){
			count ++;
		}
		busStopsCopy = new int[count];
		count = 0;
		for(TNode tempNode = this.trainZero.getDown().getDown().getNext(); tempNode != null; tempNode = tempNode.getNext()){
			count ++;
		}
		locationsCopy = new int[count];
		count = 0;
		for(TNode tempNode = this.trainZero.getNext(); tempNode != null; tempNode = tempNode.getNext()){
			trainStationsCopy[count] = tempNode.getLocation();
			count ++;
		}
		count = 0;
		for(TNode tempNode = this.trainZero.getDown().getNext(); tempNode != null; tempNode = tempNode.getNext()){
			busStopsCopy[count] = tempNode.getLocation();
			count ++;
		}
		count = 0;
		for(TNode tempNode = this.trainZero.getDown().getDown().getNext(); tempNode != null; tempNode = tempNode.getNext()){
			locationsCopy[count] = tempNode.getLocation();
			count ++;
		}
		TNode newWalkingNode = null, newBusNode = null, newTrainNode = null;
		TNode previousWalkingNode = walkingZeroCopy;
		TNode previousBusNode = busZeroCopy;
		TNode previousTrainNode = trainZeroCopy;
		int walkingLocation = 0, busLocation = 0, trainLocation = 0;
		for(int walkingIndex = 0, busIndex = 0, trainIndex = 0; walkingIndex < locationsCopy.length && busIndex < busStopsCopy.length && trainIndex < trainStationsCopy.length; walkingIndex ++){
			walkingLocation = locationsCopy[walkingIndex];
			newWalkingNode = new TNode(walkingLocation);
			previousWalkingNode.setNext(newWalkingNode);
			previousWalkingNode = newWalkingNode;
			if(walkingLocation == busStopsCopy[busIndex]){
				busLocation = busStopsCopy[busIndex];
				newBusNode = new TNode(busLocation, null, newWalkingNode);
				previousBusNode.setNext(newBusNode);
				previousBusNode = newBusNode;
				if(busIndex + 1 < busStopsCopy.length)
				busIndex ++;
				if(busLocation == trainStationsCopy[trainIndex]){
					trainLocation = trainStationsCopy[trainIndex];
					newTrainNode = new TNode(trainLocation, null, newBusNode);
					previousTrainNode.setNext(newTrainNode);
					previousTrainNode = newTrainNode;
					if(trainIndex + 1 < trainStationsCopy.length)
					trainIndex ++;
				}
			}
		}
	    return trainZeroCopy; //reference to trainZero node of the deep copy of the original list
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {
		TNode busZero = this.trainZero.getDown();
		TNode walkingZero = busZero.getDown();
		TNode scooterZero = new TNode(0, null, walkingZero);
		busZero.setDown(scooterZero);
		TNode newScooterNode = null;
		int scooterLocation;
		TNode currentWalkingNode = walkingZero.getNext();
		TNode currentBusNode = busZero.getNext();
		TNode previousScooterNode = scooterZero;
		for(scooterLocation = 0; scooterLocation < scooterStops.length && currentWalkingNode != null; currentWalkingNode = currentWalkingNode.getNext()){
			if(currentWalkingNode.getLocation() == scooterStops[scooterLocation]){
				newScooterNode = new TNode(scooterStops[scooterLocation], null, currentWalkingNode);
				previousScooterNode.setNext(newScooterNode);
				if(currentBusNode != null && currentBusNode.getLocation() == scooterStops[scooterLocation]){
					currentBusNode.setDown(newScooterNode);
					if(currentBusNode.getNext() != null)
					currentBusNode = currentBusNode.getNext();
				}
				previousScooterNode = newScooterNode;
				scooterLocation ++;
			}
		}
	}

	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
