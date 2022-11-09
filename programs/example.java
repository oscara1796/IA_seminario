*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Behaviours;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import org.apache.commons.math3.stat.StatUtils;
import org.math.plot.Plot2DPanel;
import org.math.plot.plotObjects.BaseLabel;

/**
 *
 * @author sensei
 */
public class Intento3 extends SimpleBehaviour{
    double[] trX;
    double[] trY;


    public Intento3(Agent a, double[] xt,double[] yt){
        myAgent  = a;
        trX = new double[xt.length];
        trY = new double[yt.length];
        trX = xt;
        trY = yt;

    }
        double num_steps = 2;//Numero de iteraciones

        double[] yPredict = new double[(int)num_steps];
        double learningRate =  0.00000005;//0.01;//Velocidad de learn recomendado 0
        double criteria = 2;//Error minimo
        double b_0 = 8;//6.9747753;//Final
        double b_1 = 1;//6.9747753;//Final
        double b_0_error = 1;//0.05546506;//Final
        double b_1_error= 1;//0.05546506;//Final
        double b_0_gradient;//En sumatoria
        double b_1_gradient;//En sumatoria
        double N;//Cantidad de datos
        double f_error= 0;
        double error= 0;
        int step=0;

    public void action(){
            iteration();
            step++;
            }
    public void iteration(){

        //System.out.println("N = "+(double)trX.length);
        b_0_gradient = 0;
        b_1_gradient = 0;
        N = trX.length;
        for (int i = 0; i < trX.length; i++) {
            b_0_gradient += (trY[i]-(b_0 + b_1 * trX[i]));
            f_error += ((trY[i]-(b_0 + b_1 * trX[i]))*(trY[i]-(b_0 + b_1 * trX[i])));
            b_1_gradient += trX[i]*(trY[i]-(b_0 + b_1 * trX[i]));
        }
        b_0_error = ((-2/N)*b_0_gradient) ;
        b_1_error = ((-2/N)*b_1_gradient) ;
        error = (f_error/N) ;
        //yPredict[step] = b_0 + b_1;

        //generateGrapic(trX,trY,yPredict);
        System.out.println("b_0 = "+b_0);
        System.out.println("b_1 = "+b_1);

        System.out.println("Error: "+error);
        }

        public boolean done(){
            if (error<=0.0 ||
                    step == num_steps) {
                System.out.println("Los valores que se obtienen son: "+b_0+" y "+b_1 +" en pasos" + step);
                System.out.println("Error: "+error);

                return true;
            }

              b_0= b_0 - (b_0_error* learningRate);
              b_1= b_1 - (b_1_error* learningRate);
              return false;
        }

        public void generateGrapic(double[] xt,double[] yt,double[] py){
                /*for(int i=0;i<xt.length;i++){
                }*/

                double aux;
                double auy;
                for(int i = 0;i < xt.length-1;i++){
                    for(int j = 0;j < xt.length-i-1;j++){
                        if(xt[j+1] >  xt[j]){
                            aux = xt[j+1];
                            auy = yt[j+1];
                            //index.set(j+1,index.get(j));
                            xt[j+1] = xt[j];
                            yt[j+1] = yt[j];
                            xt[j]=aux;
                            yt[j]=auy;
                        }
                    }
                }

                Plot2DPanel plot = new Plot2DPanel();
                JTextArea resultados = new JTextArea();

                JFrame frame = new JFrame("Grafico");

                double[] yc = new double[xt.length+1];
                double[] x,y;
                x = new double[xt.length];
                y = new double[yt.length];
                x = xt;
                y = yt;

                double[] yp = new double[step+1];
                double[] xp = new double[step+1];
                yp = py;
                System.out.println("Datos nuevos predichos");
                for(int i=0;i<step+1;i++){
                    yp[i] = xt[i]*b_1+b_0;
                    xp[i] = xt[i];
                    System.out.println("X: "+xt[i]+" y: "+yp[i]);
                }

                System.out.println("Datos predichos end");


                plot.addScatterPlot("Datos", xp,yp);
                plot.addLinePlot("Regresion",x,y);
                BaseLabel titulo = new BaseLabel("Regrsion Lineal "+step,Color.BLUE,0.5,1.1);
                plot.addPlotable(titulo);
                resultados.setBackground(Color.LIGHT_GRAY);
                resultados.append("\nValor minimo: "+StatUtils.min(y));
                resultados.append("\nValor maximo: "+StatUtils.max(y));
                resultados.append("\nValor promedio: "+StatUtils.mean(y));
                resultados.append("\nVarianza: "+StatUtils.variance(y));
                resultados.append("\nromedio geometrico: "+StatUtils.geometricMean(y));
                resultados.append("\nsuma: "+StatUtils.sum(y));
                resultados.append("\nProduto: "+StatUtils.product(y));

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(900,700);
                frame.add(plot,BorderLayout.CENTER);
                frame.add(resultados,BorderLayout.SOUTH);
                frame.setVisible(true);
    }
}
