/**
 *  This class implements the puzzle game Take It Easy and seacrhes for the
 *  16 optimal solutions.
 */

import java.util.*;


public class TakeItEasy {

    LinkedList<Integer> queue;

    Tile [] hexagons; // identify positions using map.

    // sorted pieces with overall weights in descending order
    Tile [] pieces = {new Tile(9,7,8),
		      new Tile(9,6,8),
		      new Tile(5,7,8),
		      new Tile(9,7,4),
		      new Tile(9,6,4),
		      new Tile(9,2,8),
		      new Tile(9,7,3),
		      new Tile(5,6,8),
		      new Tile(9,6,3),
		      new Tile(1,7,8),
		      new Tile(5,7,4),
		      new Tile(5,2,8),
		      new Tile(5,6,4),
		      new Tile(9,2,4),
		      new Tile(5,7,3),
		      new Tile(1,6,8),
		      new Tile(9,2,3),
		      new Tile(5,6,3),
		      new Tile(1,7,4),
		      new Tile(5,2,4),
		      new Tile(1,6,4),
		      new Tile(1,2,8),
		      new Tile(1,7,3),
		      new Tile(5,2,3),
		      new Tile(1,6,3),
		      new Tile(1,2,4),
		      new Tile(1,2,3)};

    Tile blankTile = new Tile(0,0,0);

    /*

      The board consists of 19 hexagons:

           _
         _/1\_   
       _/2\_/3\_   
      /4\_/5\_/6\   
      \_/7\_/8\_/
      /9\_/1\_/1\
      \_/1\0/1\1/
      /1\2/1\3/1\
      \4/1\5/1\6/
        \7/1\8/
          \9/
     

      If we number the six sides of the hexagon in a clockwise direction
      then we can use a 2D array to specify a hexagon's neighbours on each 
      side.
 
          0
         ___
     5  /   \ 1
       /     \
       \     /
     4  \___/ 2
  
          3
    */

    int [] [] neighbours = {{ 0, 0, 3, 5, 2, 0},
			    { 0, 1, 5, 7, 4, 0},
			    { 0, 0, 6, 8, 5, 1},
			    { 0, 2, 7, 9, 0, 0},
			    { 1, 3, 8,10, 7, 2},
			    { 0, 0, 0,11, 8, 3},
			    { 2, 5,10,12, 9, 4},
			    { 3, 6,11,13,10, 5},
			    { 4, 7,12,14, 0, 0},
			    { 5, 8,13,15,12, 7},
			    { 6, 0, 0,16,13, 8},
			    { 7,10,15,17,14, 9},
			    { 8,11,16,18,15,10},
			    { 9,12,17, 0, 0, 0},
			    {10,13,18,19,17,12},
			    {11, 0, 0, 0,18,13},
			    {12,15,19, 0, 0,14},
			    {13,16, 0, 0,19,15},
			    {15,18, 0, 0, 0,17}};

    // Store a list of strings that represent solutions already found
    List<String> solutions = new ArrayList<String>();

    int noBoards = 0; // the total no. of boards tried

    /**
     *  Default constructor - creates an empty Board
     */

    public TakeItEasy () {

	hexagons = new Tile[19];
	initialise();

    }


    /**
     *  clear the board
     */

    public void initialise () {

	// populate the board with blank tiles
	for (int i = 0; i < hexagons.length; i++) {
	    hexagons[i] = blankTile; 
	}

	// set the state of all pieces to "unused"
	for (int i = 0; i < pieces.length; i++) {
	    pieces[i].alreadyUsed(false); 

	}

	queue = new LinkedList<Integer>();
    }


    /**
     *  Returns the total value of this board.
     */
    public int total() {

	int total    = 0;
	int subtotal = 0;

	// Scan for hexagons with no neighbours in the 6, 1, or 2 
	// positions (respectively) and then subTotal their chains

	for (int i = 0; i < hexagons.length; i++) {
	    if (neighbours[i][0] == 0){  // check vertical
		subtotal = subTotal(i+1,4); 
		//System.out.print(subtotal + "\r\n"); // trace
		total = total + subtotal;
	    }
	    if (neighbours[i][1] == 0){ // check down right
		subtotal = subTotal(i+1,5); 
		//System.out.print(subtotal + "\r\n"); // trace
		total = total + subtotal;
	    }
	    if (neighbours[i][5] == 0){ // check down left
		subtotal = subTotal(i+1,3);
		//System.out.print(subtotal + "\r\n"); // trace
		total = total + subtotal;
	    }
	}

	return total; 

    }


    /**
     *  Recursively calculates the value of this row
     *  Note that this currently assumes a full row - no missing tiles!
     */
    public int subTotal(int position,
			int direction) {

	int thisValue   = 0;
	int returnValue = 0;

	switch(direction) {
	    case 3: thisValue = hexagons[position - 1].getRightDown(); break;
	    case 4: thisValue = hexagons[position - 1].getVertical(); break;
	    case 5: thisValue = hexagons[position - 1].getLeftDown(); break;
	}

	//System.out.print("[" + position + "] -> "); // trace

	if (neighbours[position - 1][direction - 1] == 0) {
	    return thisValue;
	}
	else {
	    returnValue = subTotal(neighbours[position - 1][direction - 1], 
				   direction, thisValue); 
	    if (returnValue > 0) 
		returnValue = returnValue + thisValue;
	}
	return returnValue;


    }


    /**
     *  Recursively calculates the value of this row
     *  Note that this currently assumes a full row - no missing tiles!
     */
    public int subTotal(int position,
			int direction,
			int lastValue) {

	int thisValue   = 0;
	int returnValue = 0;

	switch(direction) {
	    case 3: thisValue = hexagons[position - 1].getRightDown(); break;
	    case 4: thisValue = hexagons[position - 1].getVertical(); break;
	    case 5: thisValue = hexagons[position - 1].getLeftDown(); break;
	}

	// are the chain values consistent?
	if (thisValue != lastValue)
	    return 0;

	//System.out.print("[" + position + "] -> "); // trace

	if (neighbours[position - 1][direction - 1] == 0) {
	    return thisValue;
	}
	else {
	    returnValue = subTotal(neighbours[position - 1][direction - 1], 
				   direction, thisValue); 
	    if (returnValue > 0) 
		returnValue = returnValue + thisValue;
	}
	return returnValue;

    }


    /**
     *  Draw the board to standard output.
     */
    public void drawBoard() {

	int i = 3;
	int j = 4;
	int k = 5;
	int l = 6;
	int m = 7;


	System.out.println("            ___            ");  
	System.out.println("           / " + getV(0) + " \\           ");         
	System.out.println("       ___/     \\___       ");          
	System.out.println("      / " + getV(1) + " \\ " + getD(0) + " / " + getV(2) + " \\      ");          
	System.out.println("  __ /     \\___/     \\___  ");           
	
	for (int x = 0; x < 3; x++) {	
	    System.out.println(" / " + getV(i) + " \\ " + getD(i - 2) + " / " + getV(j) + " \\ " + getD(i - 1)+ " / " + getV(k) + " \\ ");           
	    System.out.println("/     \\___/     \\___/     \\");           
	    System.out.println("\\ " + getD(i) + " / " + getV(l) + " \\ " + getD(j) + " / " + getV(m) + " \\ " + getD(k) + " /");     
	    System.out.println(" \\___/     \\___/     \\___/ ");          
	    i = m + 1;
	    j = i + 1;
	    k = j + 1;
	    l = k + 1;
	    m = l + 1;
	}

	System.out.println("     \\ " + getD(16)+ " / " + getV(18) + " \\ " + getD(17) + " /     ");          
	System.out.println("      \\___/     \\___/     ");           
	System.out.println("          \\ " + getD(18)+ " /         ");         
	System.out.println("           \\___/                      ");//TOTAL = " + total());   
      
    }


    /**
     * Convenience method for drawing vertical values
     */
    public String getV(int position){

	if (hexagons[position] == blankTile) 
	    return " ";
	else
	    return (hexagons[position].getVertical() + "");

    }

    
    /**
     * Convenience method for drawing the diagonal values
     */
    public String getD(int position){

	if (hexagons[position] == blankTile) 
	    return "   ";
	else
	    return (hexagons[position].getLeftDown() + " " + hexagons[position].getRightDown());

    }


    /**
     * Return true if this solution has already been seen, otherwise
     * store it and return false.
     */
    public boolean alreadySeen() {

	// The tiles don't have a numbering (although they do have an
	// order in the array where they are declared) but we can
	// store each solution as a unique string where each tile is
	// represented by the 3 digits of it's values (vertical,
	// rightDown, leftDown) for each of the 19 hexagons in order
	// giving 3 x 19 = 57 digits.

	String thisBoard = "";

	// Create a string from this board!
	for (int i = 0; i < hexagons.length; i++) {
	    thisBoard = thisBoard + 
		hexagons[i].getVertical() + 
		hexagons[i].getRightDown() + 
		hexagons[i].getLeftDown();
	}

	// compare with existing boards (use a Set?)

	boolean seen = false;

	for (String existingSolution: solutions) {
	    if (existingSolution.equals(thisBoard)) {
		seen = true;
		break;
	    }
	}	

	if (seen) {
	    return true;
	}
	else {
	    solutions.add(thisBoard);
	    System.out.println("\nSolution signature = " + thisBoard + 
			       ",  " + solutions.size() + " found!");
	    return false;
	}

    }

    
    // attempt to populate the board
    public void search() {
	
	int total = 0;
	boolean found = false;

	while (!found) {
	    // intitial piece to try
	    for (int j = 0; j < pieces.length; j++) {
		
		// try every starting position
		for (int i = 0; i < hexagons.length; i++) {
		    initialise(); // clear the board
		    addTile(j, i);
		    processQueue();
		    total = total();
		    noBoards++;
		    if (total > 306) {
			if (!alreadySeen()) {
			    drawBoard();
			    System.out.println("\nTOTAL = " + total() + " (" +
					       noBoards + " boards tried so far...)");



			    // all solutions found?
			    if (solutions.size() == 16) {
				System.exit(0);	
			    }
			} 
		    }
		    //System.out.println("Queue = " + queue);
		}
	    }
	}
	
    }


    /**
     *  Add this tile to the board at position
     */
    public void addTile(int piece,
			int position) {

	hexagons[position] = pieces[piece];
	pieces[piece].alreadyUsed(true);
	
	// push empty neighbours onto queue
	for (int i = 0; i < 6; i++) {
	    if ((neighbours[position][i] != 0) &&
	        (hexagons[(neighbours[position][i]) - 1] == blankTile)) {
		queue.add(neighbours[position][i]);
	    }

	}

    }


    /**
     *  Process the blank spaces on the queue
     */
    public void processQueue() {
	
	int thisSpace;
	int nextTile = -1;
	boolean found;
	
	while (queue.size() > 0) {
	    
	    thisSpace = queue.poll();
	    
	    if (hexagons[thisSpace - 1] == blankTile) {
		//nextTile = suggestTileSimple(thisSpace);
		nextTile = suggestTile(thisSpace);

		addTile(nextTile, thisSpace - 1);
		//drawBoard(); // trace
		//System.out.println("Queue = " + queue); // trace
	    }
	}	
    }    


    /**
     *  Suggest first available piece for this space 
     */
    public int suggestTileSimple(int thisSpace) {

	// dummy for now - simply find the first free tile
	for (int i = 0; i < pieces.length; i++) {

	    //if (pieces[i].isAlreadyUsed()) {
	    //	System.out.println("space = " + thisSpace + " and " + i + " is true");
	    //}
	    
	    if (!pieces[i].isAlreadyUsed()) {
		return i;
	    }  
	}

	return -1;  // not found

    }

    
    /**
     *  Suggest the best piece for this space 
     */
    public int suggestTile(int thisSpace) {

	int [] desiredValue = {-1,-1,-1};
	Tile nearbyTile;

	// Find the values of already placed tiles adjacent to this space
	//System.out.print("Tiles nearby " + thisSpace + ": ");

	for (int i = 0; i < 6; i++) {
	    if ((neighbours[thisSpace - 1][i] != 0) &&
	        (hexagons[(neighbours[thisSpace - 1][i]) - 1] != blankTile)) {
		nearbyTile = hexagons[(neighbours[thisSpace - 1][i]) - 1];
		// System.out.print(neighbours[thisSpace - 1][i] + " ");  // trace
		if (i == 0 || i == 3) {
			desiredValue[0] = nearbyTile.getVertical();
		}
		else if (i == 1 || i == 4) {
			desiredValue[1] = nearbyTile.getLeftDown();
		}
		else if  (i == 2 || i == 5) {
		    desiredValue[2] = nearbyTile.getRightDown();
		}
		
	    }
       }

	// print the desired value list for this space
	//System.out.print("\nDesired values =");
	for (int j = 0; j < 3; j++) {
	    //System.out.print(desiredValue[j] + " "); // trace
	}
	//System.out.println("");	    

	Vector<Integer> candidates = new Vector<Integer>();
	
	// Find candidate tiles that match these values
	while (candidates.isEmpty()) {
	    for (int k = 0; k < pieces.length; k++) {
		if (!pieces[k].isAlreadyUsed() && 
		    pieces[k].isCompatible(desiredValue[0], desiredValue[1], desiredValue[2])) {
		    candidates.add(k);
		}
	    }
	    if (candidates.isEmpty()) {
		// would be more efficient to abort queue processing at his point

		int lowest = 10;
		int index = 0;

		// set the lowest non negative weight to be -1
		// System.out.print("desired: "); // trace
		for (int i = 0; i < 3; i++) {
		    // System.out.print(desiredValue[i] + ", "); // trace
		    if (desiredValue[i] != -1 && 
			(desiredValue[i] < lowest)) {
			lowest = desiredValue[i];
			index = i;
		    }
		}
		desiredValue[index] = -1;
		//System.out.println(", Relaxing index " + index); // trace
	    }

	}
	//System.out.println("Candidates: " + candidates);  // trace

	return chooseCandidate(candidates);

    }


    /**
     *  Assumes that candidates is non-empty. Returns a random candidate
     */
    public int chooseCandidate(Vector<Integer> candidates) {

	Random rnd = new Random();

	return candidates.elementAt(rnd.nextInt(candidates.size()));
	
    }

    
    public static void main(String[] args) {

	TakeItEasy testBoard = new TakeItEasy();
	testBoard.search();
	// System.out.println("\r\nBoard total = " + testBoard.total());
	// testBoard.drawBoard();

    }


}

