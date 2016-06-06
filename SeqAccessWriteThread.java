import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
/**
 * This class is used perform sequential write on file (append data) for different block size.
 * @author priyanka
 *
 */
public class SeqAccessWriteThread extends Thread {
	//number of threads
	private int noOfThreads = 0;
	
	// size of block in byte
	private int dataSize = 0;
	
	// number of blocks to write
	private int iterations = 0;
	
	// time taken to write n blocks of the given size
	private long time = 0;

	@Override
	public void run() {
		byte[] data = new byte[getDataSize()];
		FileOutputStream fileOutputStream = null;
		long beforeTime = 0,afterTime = 0;

		
		try {
			Arrays.fill(data, (byte) 5);
			
			//open file, if file already exist, data is appended to it.
			fileOutputStream = new FileOutputStream(new File(Constant.FILENAME),true);
			int n = getIterations();
			//get start time
			beforeTime = System.currentTimeMillis();
			for (int i = 0; i < n; i++) {				
				fileOutputStream.write(data);
			}
			fileOutputStream.flush();
			//get end time
			afterTime = System.currentTimeMillis();
			//calculate time taken to write n block of given size
			setTime(afterTime - beforeTime);
			
		} catch (FileNotFoundException e) {
			System.out.println("File is not found. " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error in writing file");
		} finally {

			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					System.out.println("Error in closing file output stream");
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
