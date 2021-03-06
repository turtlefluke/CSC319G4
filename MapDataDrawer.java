import java.util.*;
import java.io.*;
import java.awt.*;

public class MapDataDrawer
{
  
  private int[][] grid;
  
  public MapDataDrawer(String filename, int rows, int cols){
    // initialize grid 
    //read the data from the file into the grid
    
    try {
      File file = new File(filename);
      Scanner scan = new Scanner(file);
      grid=new int[rows][cols];
      for(int i=0;i<rows;i++){
        for(int j=0;j<cols;j++){
          grid[i][j] = scan.nextInt();
        }
      }
    }catch(FileNotFoundException e){
      System.out.println("File not found");
    }       
  }
  /**
   * @return the min value in the entire grid
   */
  public int findMinValue(){
    int rows = grid.length;
    int cols = grid[0].length;
    int min = grid[0][0];
    for(int i=0;i<rows;i++){
      for(int j=0;j<cols;j++){
        if(grid[i][j]<min)
          min=grid[i][j];
      }
    }
    return min;    
  }
  /**
   * @return the max value in the entire grid
   */
  public int findMaxValue(){
    int rows = grid.length;
    int cols = grid[0].length;
    int max = grid[0][0];
    for(int i=0;i<rows;i++){
      for(int j=0;j<cols;j++){
        if(grid[i][j]>max)
          max=grid[i][j];
      }
    }
    return max;    
  }
  
  /**
   * @param col the column of the grid to check
   * @return the index of the row with the lowest value in the given col for the grid
   */
  public  int indexOfMinInCol(int col){
    int indexminInCol = grid[0][col];
    int id=0;
    for(int i = 0 ; i < grid.length ; i++){
      if(grid[i][col]< indexminInCol){
        indexminInCol = grid[i][col];
        id=i;
      }
    }
    return id;
  }
  
  /**
   * Draws the grid using the given Graphics object.
   * Colors should be grayscale values 0-255, scaled based on min/max values in grid
   */
  public void drawMap(Graphics g){
    int rows = grid.length;
    int cols = grid[0].length;
    int min = findMinValue();
    int max = findMaxValue();
    int delta = max-min;
    for(int x = 0; x<rows;x++) {
      for (int y = 0;y<cols;y++) {
        int c = (grid[x][y]-min)/((delta)/255);
        g.setColor(new Color(c,c,c));
        g.fillRect(y, x, 1,1);
      }
    }
  }
  
  /**
   * Find a path from West-to-East starting at given row.
   * Choose a foward step out of 3 possible forward locations, using greedy method described in assignment.
   * @return the total change in elevation traveled from West-to-East
   */
  public int drawLowestElevPath(Graphics g, int row){
    int top,mid,low,sum=0;
    int lengthTop,lengthMid,lengthLow;
    int findMin;
    for(int j=1;j<grid[0].length;j++){
      
      int currow = grid[row][j-1];
      
      if(row==0){
        mid=grid[row][j];
        low=grid[row+1][j];
        lengthMid = Math.abs(currow-mid);
        lengthLow = Math.abs(currow-low);
        findMin = Math.min(lengthMid,lengthLow);

        if(lengthMid>lengthLow&&lengthMid!=lengthLow){
          row++;
        }
      }
      else if(row==grid.length-1){
        top=grid[row-1][j];
        mid=grid[row][j];
        lengthTop = Math.abs(currow-top);
        lengthMid = Math.abs(currow-mid);
        findMin = Math.min(lengthMid,lengthTop);

        if(lengthMid>lengthTop&&lengthMid!=lengthTop){
          row--;
        }
      }
      else{
        top=grid[row-1][j];
        mid=grid[row][j];
        low=grid[row+1][j];
        lengthTop = Math.abs(currow-top);
        lengthMid = Math.abs(currow-mid);
        lengthLow = Math.abs(currow-low);
        findMin = Math.min(Math.min(lengthMid,lengthTop),lengthLow);

        if(lengthTop!=lengthMid&&lengthMid!=lengthLow){
          if(lengthTop==findMin){
            row--;
          }
          else if(lengthLow==findMin){
            row++;
          }
        }
        else if(findMin==lengthTop||findMin==lengthLow){
          int rd = (int)(Math.random()*1)+1;
          if(rd==2)
            row--;
          else
            row++;
        }
      }
      sum+=findMin;
      g.fillRect(j, row, 1,1);
      
    }
    return sum;
  }
  
  /**
   * @return the index of the starting row for the lowest-elevation-change path in the entire grid.
   */
  public int indexOfLowestElevPath(Graphics g){
    int index=0;
    int min=drawLowestElevPath(g, 0);
    for(int i=1;i<grid.length;i++){
      if(drawLowestElevPath(g, i)<min){
        min=drawLowestElevPath(g, i);
        index=i;
      }
    }
    return index;
    
  }
  
  
}