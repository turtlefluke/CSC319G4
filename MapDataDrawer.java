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
    for(int x = 0; x<rows;x++) {
      for (int y = 0;y<cols;y++) {
        int c = (grid[x][y]-findMinValue())/((findMaxValue()- findMinValue())/255);
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
    int cols = grid[0].length;
    int res=grid[row][0];
    int thatrow = row;
    int ran;
    int minnoi;
    for (int j = 0;j<cols;j++) {
      int currow = grid[row][j];
      if(j!=0){
        int length1 = Math.abs(currow-grid[row-1][j]);
        int length2 = Math.abs(currow-grid[row][j]);
        int length3 = Math.abs(currow-grid[row+1][j]);
        if(row==0){
          if(length2==length3){
            ran = (int)(Math.random()*1)+1;
            if(ran==1){
              res+=length2;
              row=thatrow;
            }
            if(ran==2){
              res+=length3;
              row++;
            }
          }
          else{
            minnoi = Math.min(length2,length3);
            res+=minnoi;
            if(minnoi==length2){
              row=thatrow;
            }
            if(minnoi==length3){
              row++;
            }
          }
        }
        
        else if(row==grid.length-1){
          if(length1==length2){
            ran = (int)(Math.random()*1)+1;
            if(ran==1){
              res+=length1;
              row--;
            }
            if(ran==2){
              res+=length2;
              row=thatrow;
            }
          }
          else{
            minnoi = Math.min(length1,length2);
            res+=minnoi;
            if(minnoi==length1){
              row--;
            }
            if(minnoi==length2){
              row=thatrow;
            }
          }
        }
        
        else{
          minnoi = Math.min(Math.min(length1,length2),length3);
          if(length1==minnoi && length3==minnoi){
            ran = (int)(Math.random()*1)+1;
            if(ran==1){
              res+=length1;
              row--;
            }
            if(ran==2){
              res+=length3;
              row++;
            }
          }
          else if(length1==minnoi || length2==minnoi){
            res+=length2;
            row=thatrow;
          }
        }
        g.fillRect(j,row,1,1);
      }
    }
    
    return res;
  }
  
  /**
   * @return the index of the starting row for the lowest-elevation-change path in the entire grid.
   */
  public int indexOfLowestElevPath(Graphics g){
    return -1;
    
  }
  
  
}