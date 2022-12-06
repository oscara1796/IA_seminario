package examples.slr;

// Libraries to read file
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.io.IOException;

//JADE libraries
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;

//APache IO libraries
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Cell;


public class SLR extends Agent {
  // double data_set_x [][];
  // double data_set_y [];
  double data_set_x []={230.1,44.5,17.2,151.5,180.8,8.7,57.5,120.2,8.6,199.8,66.1,214.7,23.8,97.5,204.1,195.4,67.8,281.4,69.2,147.3,218.4,237.4,13.2,228.3,62.3,262.9,142.9,240.1,248.8,70.6,292.9,112.9,97.2,265.6,95.7,290.7,266.9,74.7,43.1,228.0,202.5,177.0,293.6,206.9,25.1,175.1,89.7,239.9,227.2,66.9,199.8,100.4,216.4,182.6,262.7,198.9,7.3,136.2,210.8,210.7,53.5,261.3,239.3,102.7,131.1,69.0,31.5,139.3,237.4,216.8,199.1,109.8,26.8,129.4,213.4,16.9,27.5,120.5,5.4,116.0,76.4,239.8,75.3,68.4,213.5,193.2,76.3,110.7,88.3,109.8,134.3,28.6,217.7,250.9,107.4,163.3,197.6,184.9,289.7,135.2,222.4,296.4,280.2,187.9,238.2,137.9,25.0,90.4,13.1,255.4,225.8,241.7,175.7,209.6,78.2,75.1,139.2,76.4,125.7,19.4,141.3,18.8,224.0,123.1,229.5,87.2,7.8,80.2,220.3,59.6,0.7,265.2,8.4,219.8,36.9,48.3,25.6,273.7,43.0,184.9,73.4,193.7,220.5,104.6,96.2,140.3,240.1,243.2,38.0,44.7,280.7,121.0,197.6,171.3,187.8,4.1,93.9,149.8,11.7,131.7,172.5,85.7,188.4,163.5,117.2,234.5,17.9,206.8,215.4,284.3,50.0,164.5,19.6,168.4,222.4,276.9,248.4,170.2,276.7,165.6,156.6,218.5,56.2,287.6,253.8,205.0,139.5,191.1,286.0,18.7,39.5,75.5,17.2,166.8,149.7,38.2,94.2,177.0,283.6,232.1,0.0};
  double data_set_y [] = {22.1,10.4,12.0,16.5,17.9,7.2,11.8,13.2,4.8,15.6,12.6,17.4,9.2,13.7,19.0,22.4,12.5,24.4,11.3,14.6,18.0,17.5,5.6,20.5,9.7,17.0,15.0,20.9,18.9,10.5,21.4,11.9,13.2,17.4,11.9,17.8,25.4,14.7,10.1,21.5,16.6,17.1,20.7,17.9,8.5,16.1,10.6,23.2,19.8,9.7,16.4,10.7,22.6,21.2,20.2,23.7,5.5,13.2,23.8,18.4,8.1,24.2,20.7,14.0,16.0,11.3,11.0,13.4,18.9,22.3,18.3,12.4,8.8,11.0,17.0,8.7,6.9,14.2,5.3,11.0,11.8,17.3,11.3,13.6,21.7,20.2,12.0,16.0,12.9,16.7,14.0,7.3,19.4,22.2,11.5,16.9,16.7,20.5,25.4,17.2,16.7,23.8,19.8,19.7,20.7,15.0,7.2,12.0,5.3,19.8,18.4,21.8,17.1,20.9,14.6,12.6,12.2,9.4,15.9,6.6,15.5,7.0,16.6,15.2,19.7,10.6,6.6,11.9,24.7,9.7,1.6,17.7,5.7,19.6,10.8,11.6,9.5,20.8,9.6,20.7,10.9,19.2,20.1,10.4,12.3,10.3,18.2,25.4,10.9,10.1,16.1,11.6,16.6,16.0,20.6,3.2,15.3,10.1,7.3,12.9,16.4,13.3,19.9,18.0,11.9,16.9,8.0,17.2,17.1,20.0,8.4,17.5,7.6,16.7,16.5,27.0,20.2,16.7,16.8,17.6,15.5,17.2,8.7,26.2,17.6,22.6,10.3,17.3,20.9,6.7,10.8,11.9,5.9,19.6,17.3,7.6,14.0,14.8,25.5,18.4,0.0};
  LinearRegression slr;


  protected void setup()   {
    System.out.println("Agent "+getLocalName()+" started.");

    // try{
    //   // Perform operation 1: read data sets and transform  them into arrays
    //   FileInputStream fis=new FileInputStream(new File("/home/oscar/Documents/doc_abraham/escuela/JADE-all-4.5.0/jade/classes/advertising.xls"));
    //   //creating workbook instance that refers to .xls file
    //   HSSFWorkbook wb=new HSSFWorkbook(fis);
    //
    //   //creating a Sheet object to retrieve the object
    //   HSSFSheet sheet=wb.getSheetAt(0);
    //   data_set_x = new double[sheet.getLastRowNum()][2];
    //   data_set_y = new double[sheet.getLastRowNum()];
    //   //evaluating cell type
    //   FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator();
    //   for(Row row: sheet)     //iteration over row using for each loop
    //   {
    //     Cell cellx1 = row.getCell(0);
    //     Cell cellx2 = row.getCell(1);
    //     Cell celly = row.getCell(3);
    //     if (cellx1 == null || cellx2 == null ||celly == null) {
    //       break;
    //     }
    //     switch(formulaEvaluator.evaluateInCell(cellx1).getCellType())
    //     {
    //       case NUMERIC:   //field that represents numeric cell type
    //       //getting the value of the cell as a number
    //       System.out.print(cellx1.getNumericCellValue()+ "\t\t");
    //       data_set_x[row.getRowNum()-1][0] = cellx1.getNumericCellValue();
    //       break;
    //       case STRING:    //field that represents string cell type
    //       //getting the value of the cell as a string
    //       System.out.print(cellx1.getStringCellValue()+ "\t\t");
    //       break;
    //     }
    //     switch(formulaEvaluator.evaluateInCell(cellx2).getCellType())
    //     {
    //       case NUMERIC:   //field that represents numeric cell type
    //       //getting the value of the cell as a number
    //       System.out.print(cellx2.getNumericCellValue()+ "\t\t");
    //       data_set_x[row.getRowNum()-1][1] = cellx2.getNumericCellValue();
    //       break;
    //       case STRING:    //field that represents string cell type
    //       //getting the value of the cell as a string
    //       System.out.print(cellx2.getStringCellValue()+ "\t\t");
    //       break;
    //     }
    //     switch(formulaEvaluator.evaluateInCell(celly).getCellType())
    //     {
    //       case NUMERIC:   //field that represents numeric cell type
    //       //getting the value of the cell as a number
    //       System.out.print(celly.getNumericCellValue()+ "\t\t");
    //       data_set_y[row.getRowNum()-1] = celly.getNumericCellValue();
    //       break;
    //       case STRING:    //field that represents string cell type
    //       //getting the value of the cell as a string
    //       System.out.print(celly.getStringCellValue()+ "\t\t");
    //       break;
    //     }
    //     System.out.println();
    //   }
    // } catch(IOException  ex ){
    //   System.out.println(ex);
    // }
    //
    // System.out.print("x={" );
    // for (int i=0;i < data_set_x.length ; i++ ) {
    //   if (i+1 == data_set_x.length) {
    //     System.out.print("{" );
    //     for (int j=0;j< 2; j++ ) {
    //       if (j+1 == data_set_x[i].length) {
    //
    //         System.out.print( data_set_x[i][j]);
    //       } else{
    //         System.out.print( data_set_x[i][j] + ",");
    //       }
    //     }
    //     System.out.print("}");
    //   } else{
    //     System.out.print("{1," );
    //     for (int j=0;j< 2; j++ ) {
    //       if (j+1 == data_set_x[i].length) {
    //
    //         System.out.print( data_set_x[i][j]);
    //       } else{
    //         System.out.print( data_set_x[i][j] + ",");
    //       }
    //     }
    //     System.out.print("},");
    //   }
    // }
    // System.out.print("}\n");
    // System.out.print("y={" );
    // for (int i=0;i < data_set_x.length ; i++ ) {
    //   if (i+1 == data_set_x.length) {
    //     System.out.print( data_set_y[i]);
    //   } else{
    //     System.out.print( data_set_y[i] + ",");
    //   }
    // }
    // System.out.print("}\n");




    // Add the generic behaviour
    addBehaviour(new FourStepBehaviour());
  }

  /**
   * Inner class FourStepBehaviour
   */
  private class FourStepBehaviour extends Behaviour {
    private int step = 1;

    public void action() {
      switch (step) {
      case 1:
        slr = new LinearRegression(data_set_x, data_set_y);
        System.out.println("Operation 1");
        break;
      case 2:
        System.out.println(slr.toString());
        break;
      case 3:
        while(true){
          Scanner sc= new Scanner(System.in);
          System.out.println("Would you like to predict a value with that formula: ");
          double ans  = sc.nextDouble();
          System.out.println("Answer: " + slr.predict(ans));
          if (ans < 0) break;
        }
        break;
      }
      step++;
    }

    public boolean done() {
      return step == 4;
    }

    public int onEnd() {
      myAgent.doDelete();
      return super.onEnd();
    }
  }    // END of inner class FourStepBehaviour


  public class LinearRegression {
    private final double intercept, slope;
    private final double r2;
    private final double svar0, svar1;

   /**
     * Performs a linear regression on the data points {@code (y[i], x[i])}.
     *
     * @param  x the values of the predictor variable
     * @param  y the corresponding values of the response variable
     * @throws IllegalArgumentException if the lengths of the two arrays are not equal
     */
    public LinearRegression(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("array lengths are not equal");
        }
        int n = x.length;

        // first pass
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        for (int i = 0; i < n; i++) {
            sumx  += x[i];
            sumx2 += x[i]*x[i];
            sumy  += y[i];
        }
        double xbar = sumx / n;
        double ybar = sumy / n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        slope  = xybar / xxbar;
        intercept = ybar - slope * xbar;

        // more statistical analysis
        double rss = 0.0;      // residual sum of squares
        double ssr = 0.0;      // regression sum of squares
        for (int i = 0; i < n; i++) {
            double fit = slope*x[i] + intercept;
            rss += (fit - y[i]) * (fit - y[i]);
            ssr += (fit - ybar) * (fit - ybar);
        }

        int degreesOfFreedom = n-2;
        r2    = ssr / yybar;
        double svar  = rss / degreesOfFreedom;
        svar1 = svar / xxbar;
        svar0 = svar/n + xbar*xbar*svar1;
    }

   /**
     * Returns the <em>y</em>-intercept &alpha; of the best of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>.
     *
     * @return the <em>y</em>-intercept &alpha; of the best-fit line <em>y = &alpha; + &beta; x</em>
     */
    public double intercept() {
        return intercept;
    }

   /**
     * Returns the slope &beta; of the best of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>.
     *
     * @return the slope &beta; of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>
     */
    public double slope() {
        return slope;
    }

   /**
     * Returns the coefficient of determination <em>R</em><sup>2</sup>.
     *
     * @return the coefficient of determination <em>R</em><sup>2</sup>,
     *         which is a real number between 0 and 1
     */
    public double R2() {
        return r2;
    }

   /**
     * Returns the standard error of the estimate for the intercept.
     *
     * @return the standard error of the estimate for the intercept
     */
    public double interceptStdErr() {
        return Math.sqrt(svar0);
    }

   /**
     * Returns the standard error of the estimate for the slope.
     *
     * @return the standard error of the estimate for the slope
     */
    public double slopeStdErr() {
        return Math.sqrt(svar1);
    }

   /**
     * Returns the expected response {@code y} given the value of the predictor
     * variable {@code x}.
     *
     * @param  x the value of the predictor variable
     * @return the expected response {@code y} given the value of the predictor
     *         variable {@code x}
     */
    public double predict(double x) {
        return slope*x + intercept;
    }

   /**
     * Returns a string representation of the simple linear regression model.
     *
     * @return a string representation of the simple linear regression model,
     *         including the best-fit line and the coefficient of determination
     *         <em>R</em><sup>2</sup>
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("%s n + %s", slope(), intercept()));
        s.append("  (R^2 = " + String.format("%.3f", R2()) + ")");
        return s.toString();
    }

  }
}
