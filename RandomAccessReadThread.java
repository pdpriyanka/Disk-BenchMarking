import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

/**
 * 
 * This class perform random read operation of file.
 *
 */
public class RandomAccessReadThread extends Thread {
	private int noOfThreads = 0;
	private int dataSize = 0;
	private int iterations = 0;
	private long time = 0;

	@Override
	public void run() {

		byte[] data = new byte[getDataSize()];
		RandomAccessFile randomAccessFile = null;
		int n = getIterations();
		Random random = new Random();


		try {
			// open the file in read mode
			File file = new File(Constant.FILENAME);
			randomAccessFile = new RandomAccessFile(file, "r");

			// take the start time in millisecond
			long beforeTime = System.currentTimeMillis();

			long length = randomAccessFile.length();
			// read number of blocks n
			for (int i = 0; i < n; i++) {
				// generate random number between 0 to n and set file pointer to
				// that location
				// and read the corresponding block of given size
				randomAccessFile.seek((long)(random.nextDouble()* length));
				randomAccessFile.read(data);

			}
			long afterTime = System.currentTimeMillis();
			setTime(afterTime - beforeTime);

		} catch (FileNotFoundException e) {
			System.out.println("File is not found. " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Can not write into file. " + e.getMessage());
		} finally {

			if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (IOException e) {
					System.out.println(" Failed to close file input stream " + e.getMessage());
				}
			}
		}

	}

	// number of threads
	public int getNoOfThreads() {
		return noOfThreads;
	}

	public void setNoOfThreads(int noOfThreads) {
		this.noOfThreads = noOfThreads;
	}

	// block size in bytes to read
	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	// how many blocks to read
	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	// time required for read operation of n blocks of the given size (1B, 1KB,
	// 1MB)
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
