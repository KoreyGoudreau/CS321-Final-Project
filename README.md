# CS321 Final Project Report

Date: April 24 2026
Name: Korey Goudreau

# Selected Idea:

## Simulate a City’s Morning Traffic.

#

# Overview

# City/Grid

Generate a **City** on a square grid representing the **City’s** layout, which consists of a 2D array of **Cells** (e.g. cells\[row\]\[col\]). Each **Cell** can be of type “intersection”, “road” or “none. Within and around the perimeter of this grid is a series of intersecting roads. At each overlapping road is an **Intersection** with a **Stoplight** and 4 **CarLines**. Between Intersections, each stretch of road is of equal length.

In a **Grid** of size n there are n rows and n columns. The rows, numbered from 0 to (n-1) go from bottom to top, and the columns 0 to (n-1) go from left to right, similar to the cartesian plane.

* Number of Intersections per row or column (minimum value of 2\) \= i
* Road length (minimum value of 3\) \= r
* **Grid** size \= n \= r(i \- 1\) \+ i

# Stop Lights and Intersections

Each **Intersection** has a **Stoplight** that can face one of the four cardinal directions: (north, east, south, or west). When initialized, the **GreenState** begins in a **GreenState**, pointing in a randomly selected direction for a fixed duration. After this period, it switches to a **YellowState** for the same amount of time. After that the **Stoplight** rotates to the next direction in a circular sequence (north, east, south, then west) and returns to a **GreenState**, repeating this cycle continuously.

When a **Car** arrives on a cell right before an **Intersection** it will join 1 of 4 **CarLines** depending on what direction the **Car** is facing. The method used by **CarLine** to add a **Car** to its First-In-First-Out queue of **Cars** is synchronized to only allow 1 **Car** to add itself to the queue at a time. If the **Stoplight** is in a **GreenState**, the first **Car** in the **CarLine** can cross the **Intersection** by acquiring this **Intersection**’s only Semaphore, with the next **Car** only being able to cross once the current **Car** moves once before releasing that Semaphore.

# Buildings

With the **Grid**, there is also a need to create **Buildings** from which **Cars** can travel to and from. There are 2 types, **Homes** and **Destinations**. **Cars** start off the day in their own **Home**, then head to their **Destination**. Multiple cars can be assigned to each **Building**.

Each **Building** has a unique location that corresponds to a **Cell** of type “road”. This **Cell** cannot be adjacent to an intersection or on an intersection itself (which is why the minimum road length is 3). To interact with a **Building** , a **Car** must first move to that **Building’s Cell**.
Each **Destination** has its own expected opening time, for example a shop might open at 60 seconds from the start of the simulation. Using this opening time, a **Destination** can determine if an arriving **Car** is early or late.

# Schedule

The **Timer** class used in the simulation measures the elapsed time in real life seconds. The **Car** and **Stoplight** are subclasses of the **Unit** abstract class. Each **Unit** performs one action per second and logs statistics, allowing **Cars** and **Stoplights** to run concurrently as independent threads.

# Car

Every **Car** has a name (String) and a speed (int), which represents the number of seconds it takes for that **Car** to move 1 **Cell** on the **Grid**.

A **Car** reports:

* If they move, when and where
* If they arrive at an intersection, their current location and the time
* If they wait at an intersection, their current location and the time
* If they interact with a **Building (Home or Destination)**

This can be aggregated to calculate:

* Total wait time
* Total number of moves
* A chronological log of actions a **Car** performed

When a **Car** is initialized, it is given a **RouteStrategy**, which determines how a **Car** moves to its assigned **Destination**. Here are the three I made:

* HorizontalFirst: move along horizontally towards the column of a **Car’s Destination**. If that is not possible, move vertically towards it. If that is not possible, move in a given vertical direction until an **Intersection** is reached. If that is not possible, move in a given horizontal direction until an **Intersection** is reached. Then repeat.
* VerticalFirst: move along vertically towards the row of a **Car’s Destination**. If that is not possible, move horizontally towards it. If that is not possible, move in a given horizontal direction until an **Intersection** is reached. If that is not possible, move in a given vertical direction until an **Intersection** is reached. Then repeat.
* Zigzag: move in zigzags, by first moving horizontally towards a **Car’s Destination** until an **Intersection** is reached. Then move vertically until an **Intersection** is reached. Repeat until the **Destination** is reached.

# Design Patterns Used

# Creational Design Pattern: Factory

There are 2 **Building** types: **Homes** and **Destinations**.

**Buildings** all have:

* A corresponding **Cell**
* An interact method used by **Car**

However a **Destination** type **Building** has added functionality. Such as:

* Setting an opening time, from which a report can be generated on which **Cars** arrive late.

A factory pattern allows me to create **Buildings** with either type depending on input. Both types share commonalities but also differ in behavior. This pattern allows me to later add different **Building** types by simply extending the **Building** abstract class and adding new functionalities, and then modifying the **BuildingFactory** class to be able to instantiate the new type of **Building**.

# Behavioral Design Pattern: Strategy

Each **Car** is assigned a different algorithm used to decide how to reach their **Destination**.
Using the strategy pattern, an interface for **RoutingStrategy** can be created, and multiple implementing classes can use different routing algorithms. Using this pattern decouples the algorithm’s code from the **Car** class code. This allows me to later add different routing algorithms without requiring rewriting all the code in the **Car** class.

# Behavioral Design Pattern: Observer

The **Stoplight** implements the **StoplightSubject** interface, and the 4 **CarLines** at each side of the **Intersection** implement the **StoplightObserver** interface. When the **Stoplight’s** **StoplightState** changes, using the notifyObservers() method it updates all its registered **CarLines.** These in turn call their update() methods which allows the **Cars** waiting in line to move forward if the **Stoplight** is in a **Greenstate** and is facing the same direction.

# Behavioral Design Pattern: State

The **StoplightState** interface provides an action() and getColor() method. The **Stoplight** in an **Intersection** can have 2 different states, **GreenState** or **YellowState** which **Cars** can differentiate by the returned String from the getColor() method. Using the action() method, the **GreenState** notifies **CarLines** to move **Cars** into the intersection, while the **YellowState** halts all traffic. The **YellowState** knows which direction to have the Stoplight rotate to when it changes back to a **GreenState** after a set period of time. The **StoplightState** interface allows new States to be added later, such as a RedState.

# Creational Design Pattern: Singleton

Using singleton for the City class, which contains everything for the simulation. It initializes all the **Units** required for the simulation as well as everything else needed to model a City. The City class also has methods to retrieve the **Stoplights** and **Cars** so that they can be run and executed as **Threads** in the **Matrix** class. The **City** class provides an environment where everything can fit in and ensures only 1 instance exists.