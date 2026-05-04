package pc.mandelbrot;

import java.awt.Color;
import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MandlebrotTask extends RecursiveAction {
	
	public static int THRESHOLD = 5000;
	private BoundingBox boundingBox;
	private int maxIterations; 
	private int[] imageBuffer;
	
	public MandlebrotTask(BoundingBox boundingBox, int maxIterations, int[] imageBuffer) {
		// TODO Auto-generated constructor stub
		this.boundingBox = boundingBox;
		this.imageBuffer = imageBuffer;
		this.maxIterations = maxIterations;
	}
	
	
	public void computeRange(int min, int max) {
		long start = System.currentTimeMillis();
		// Iterate over each pixel
		for (int py = min; py < max; py++) {
			for (int px = 0; px < boundingBox.width; px++) {
				int color = computePixelColor(boundingBox, maxIterations, px, py);

				// Set the pixel in the image buffer
				imageBuffer[py * boundingBox.width + px] = color;
			}
		}
		System.out.println("Rendered image in " + (System.currentTimeMillis() - start) + " ms");
	}
	
	@Override
	protected void compute() {
		// TODO Auto-generated method stub
		
		// Traitement séquentiel
		if ( this.boundingBox.height * this.boundingBox.width <= THRESHOLD ) {
			computeRange(this.boundingBox.height, this.boundingBox.width);
			//MandelbrotCalculator.compute(this.boundingBox, this.maxIterations, this.imageBuffer);
		}
		else {
			int mid = (int) ((this.boundingBox.xmax - this.boundingBox.xmin)/2);
			BoundingBox rigthBoundingBox = new BoundingBox(mid, this.boundingBox.xmax, this.boundingBox.ymin, this.boundingBox.ymax, this.boundingBox.width, mid);
			BoundingBox leftBoundingBox = new BoundingBox(this.boundingBox.xmin, mid, this.boundingBox.ymin, this.boundingBox.ymax, this.boundingBox.width, mid);
			
			MandlebrotTask rightTask = new MandlebrotTask(rigthBoundingBox, this.maxIterations, this.imageBuffer);
			MandlebrotTask leftTask = new MandlebrotTask(leftBoundingBox, this.maxIterations, this.imageBuffer);
			rightTask.fork();
			leftTask.fork();
			
			rightTask.join();
			leftTask.join();
		}
	}
	
	public static int computePixelColor(BoundingBox boundingBox, int maxIterations, int px, int py) {
		int width = boundingBox.width;
		int height = boundingBox.height;

		// Map pixel to complex plane with inverted y-axis
		double x0 = boundingBox.xmin + px * (boundingBox.xmax - boundingBox.xmin) / width;
		double y0 = boundingBox.ymax - py * (boundingBox.ymax - boundingBox.ymin) / height;

		double x = 0.0;
		double y = 0.0;
		int iteration = 0;

		while (x * x + y * y <= 4 && iteration < maxIterations) {
			double xtemp = x * x - y * y + x0;
			y = 2 * x * y + y0;
			x = xtemp;
			iteration++;
		}

		if (iteration < maxIterations) {
			// Compute smooth iteration count
			double logZn = Math.log(x * x + y * y) / 2;
			double nu = Math.log(logZn / Math.log(2)) / Math.log(2);
			double smoothIteration = iteration + 1 - nu;

			// Normalize smooth iteration
			float hue = (float) (0.95f + 10 * smoothIteration / maxIterations);
			float saturation = 0.6f;
			float brightness = 1.0f;

			// Wrap hue to [0,1] to prevent overflow
			hue = hue - (float) Math.floor(hue);

			return Color.HSBtoRGB(hue, saturation, brightness);
		} else {
			return Color.BLACK.getRGB();
		}
	}

}
