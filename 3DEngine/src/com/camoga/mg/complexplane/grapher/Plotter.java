package com.camoga.mg.complexplane.grapher;
import com.camoga.complex.Complex;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.gfx.Color;

public class Plotter {
	
	public static double[] vertexHeight;
	public static Vec3[] vertexColor;
	public static int yRendering = -1;
	
	/**
	 * num of lines = 24/guide
	 */
	private final static double guide = 2;
	/**
	 * thickness
	 */
	private final static double guidelines = .2;
	
	/**
	 * frequency of discontinuities
	 */
	private final static float magnitudmod = 1.5f;
	
	/**
	 * 
	 * @param f
	 * @param width of the window
	 * @param height of the window
	 * @param xs centered at x
	 * @param ys centered at iy
	 * @param scale plot width and height in units
	 * @param coloring 
	 * @return
	 */
	public static void plot(IFunction f, int width, int height, double x0, double y0, double x1, double y1, int coloring) {
		vertexHeight = new double[width*height];
		vertexColor = new Vec3[width*height];
		
		for(int y = 0; y < height; y++) {
			yRendering = y;
			double yr = y1 - (double)(y)/(double)height*(y1-y0);
			for(int x = 0; x < width; x++) {
				double xr = x0 + (double)(x)/(double)width*(x1-x0);
				Complex z = f.evaluate(new Complex(xr,yr));
//				if(xr == 0 && yr == 0) {
//					System.out.println(Math.exp(-0*0));
//					vertexColor[x+y*width] = new Vec3(1,0,0);
//					continue;
//				}
				float hue = (float) (180*Complex.argument(z)/(Math.PI));
				
//				if((coloring & 0b01) > 0) {
//					brightness = (float) (Math.abs(Math.log(Complex.mod(z)))%magnitudmod)/magnitudmod;
//				} else {
//					brightness = (float) (1-Math.pow(2, -Complex.mod(z)));
//				}
				
//				if((coloring & 0b10) > 0) {
//					double mod = Math.abs((Complex.argument(z)/Math.PI*12)%guide);
//					if(mod <= guidelines) {
//						saturation = (float) (mod/guidelines);
//					} else if(guide-mod <= guidelines) {
//						saturation = (float) ((guide-mod)/guidelines);
//					}						
//				}
					
				vertexHeight[x+y*width] = Complex.mod(z);
				int rgb = Color.HSLtoRGB(hue, 1f, 0.5f);
				int r = (rgb&0xff0000) >> 16;
				int g = (rgb&0xff00) >> 8;
				int b = (rgb&0xff);
				vertexColor[x+y*width] = new Vec3(r/255.0d, g/255.0d, b/255.0d);
			}
		}
	}
}