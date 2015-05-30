/**
 *  This class implements an individual tile in Take It Easy 
 */


public class Tile {

    int verticalValue, leftDownValue, rightDownValue;
    int simpleWeight = 0;
    int weight = 0;
    boolean placed = false;
    

    /**
     *  Default constructor - creates an empty tile
     */

    public Tile () {

	this(0,0,0);

    }


    /**
     *  Create a tile with these values
     */

    public Tile (int vertical,
		 int downLeft,
		 int downRight) {

        this.simpleWeight = vertical + downLeft + downRight;
        this.weight = calculateWeight();
	this.verticalValue = vertical;
	this.leftDownValue = downLeft;
	this.rightDownValue = downRight;

    }


    /**
     *  This could be used to calculate a more complex weight value for
     *  selecting tiles
     */
    public int calculateWeight () {

	return this.weight; // stub

    }


    /**
     *  Returns the vertical value
     */
    public int getVertical() {

	return this.verticalValue;

    }


    /**
     *  Returns the leftDown value.
     */
    public int getLeftDown() {

	return this.leftDownValue;

    }


    /**
     *  Returns the rightDown value.
     */
    public int getRightDown() {

	return this.rightDownValue;

    }


    /**
     *  Returns the 3 weights (vertical,right down, left down)
     *  concatenated as a string.
     */
    public String getWeights() {

	return "" + this.verticalValue + this.rightDownValue +  
	    this.leftDownValue;

    }


    /**
     *  Returns True if this tile is on the board
     */
    public Boolean isAlreadyUsed() {

	return this.placed;

    }


    /**
     *  Set the tile's use status
     */
    public void alreadyUsed(Boolean used) {

	this.placed = used; 

    }


    /**
     *  Returns true if the non-negative vlaues on this tile match
     */
    public Boolean isCompatible(int vertical,
				int leftDown,
				int rightDown) {

	boolean vertOK, leftDownOK, rightDownOK = false;

	
	return (this.verticalValue  == vertical  || vertical  == -1) && 
	       (this.leftDownValue  == leftDown  || leftDown  == -1) && 
	       (this.rightDownValue == rightDown || rightDown == -1);

    }



    public static void main(String [] args) {

	Tile tile1 = new Tile(1,2,3);
	
	if (tile1.isCompatible(1,-1,3)) 
	    System.out.println("Compatible");
	else
	    System.out.println("NOT compatible!");


    }



 

}

