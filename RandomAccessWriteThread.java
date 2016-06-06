import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Random;

/**
 * This class is used to randomly write data of the given size
 * 
 * @author priyanka
 *
 */
public class RandomAccessWriteThread extends Thread {
	// number of threads
	private int noOfThreads = 0;
	
	// block size in bytes
	private int dataSize = 0;
	
	//number of blocks to write
	private int iterations = 0;
	
	//time taken to randomly write n blocks of given size
	private long time = 0;

	@Override
	public void run() {
		byte[] data = new byte[getDataSize()];
		RandomAccessFile randomAccessFile = null;
		int n = getIterations();

		try {
			Arrays.fill(data, (byte) 5);
			Random random = new Random();
			//open file
			randomAccessFile = new RandomAccessFile(new File(Constant.FILENAME), "rw");
			
			//get start time
			long beforeTime = System.currentTimeMillis();
			for (int i = 0; i < n; i++) {
				//set file pointer to random location in file and read the corresponding data
				randomAccessFile.seek((long)(random.nextDouble()*randomAccessFile.length()));
				randomAccessFile.write(data);
			}
			//get end time
			long afterTime = System.currentTimeMillis();

			// calculate the time taken to perform random write of n blocks of the given size
			setTime(afterTime - beforeTime);

		} catch (FileNotFoundException e) {
			System.out.println("File is not found. " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error in writing file");
		} finally {

			if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (IOException e) {
					System.out.println("Close of random access disk is failed.");
				}
			}
		}

	}

	public int getNoOfThreads() {
		return noOfThreads;
	}

	public void setNoOfThreads(int noOfThreads) {
		this.noOfThreads = noOfThreads;
	}

	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
