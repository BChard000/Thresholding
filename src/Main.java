import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

// V1
class ImageThresholding {
    private Mat image;
    private Mat grayImage;
    private Mat thresholdedImage;

    public void Tresholding(String imagePath) {
        this.image = Imgcodecs.imread(imagePath);
        this.grayImage = new Mat();
        this.thresholdedImage = new Mat();
    }

    public void convertToGrayscale() {
        Imgproc.cvtColor(this.image, this.grayImage, Imgproc.COLOR_RGB2GRAY);
    }

    public void applyThreshold(int thresholdValue, int maxValue) {
        Imgproc.threshold(this.grayImage, this.thresholdedImage, thresholdValue, maxValue, Imgproc.THRESH_BINARY);
    }

    public void saveImage(String outputPath) {
        Imgcodecs.imwrite(outputPath, this.thresholdedImage);
    }

    public void releaseResources() {
        this.image.release();
        this.grayImage.release();
        this.thresholdedImage.release();
    }
}

// V2
class AdaptiveThresholdingProcessor {
    private Mat sourceImage;
    private Mat grayImage;
    private Mat thresholdedImage;

    public void Processor(String imagePath) {
        this.sourceImage = Imgcodecs.imread(imagePath);
        if (this.sourceImage.empty()) {
            throw new IllegalArgumentException("Cannot read image: " + imagePath);
        }
        this.grayImage = new Mat();
        this.thresholdedImage = new Mat();
    }

    public void convertToGrayscale() {
        Imgproc.cvtColor(this.sourceImage, this.grayImage, Imgproc.COLOR_BGR2GRAY);
    }

    public void applyAdaptiveThresholding() {
        int blockSize = 17;
        int C = 1;
        Imgproc.adaptiveThreshold(this.grayImage, this.thresholdedImage, 255,
                Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, blockSize, C);
    }

    public void saveThresholdedImage(String outputPath) {
        Imgcodecs.imwrite(outputPath, this.thresholdedImage);
    }

    public void releaseResources() {
        this.sourceImage.release();
        this.grayImage.release();
        this.thresholdedImage.release();
    }
}

public class Main {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        // Note: Different versions are best suited for different types of usage scenarios
        // Note: Value adjustments might be needed

        // V1
        ImageThresholding thresholding = new ImageThresholding();
        thresholding.Tresholding("filepath");
        try {
            thresholding.convertToGrayscale();
            thresholding.applyThreshold(1, 255);
            thresholding.saveImage("threshold_V1.jpg");
        } finally {
            thresholding.releaseResources();
        }

        // V2
        AdaptiveThresholdingProcessor processor = new AdaptiveThresholdingProcessor();
        processor.Processor("filepath");
        try {
            processor.convertToGrayscale();
            processor.applyAdaptiveThresholding();
            processor.saveThresholdedImage("threshold_V2.jpg");
        } finally {
            processor.releaseResources();
        }
    }
}