import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**This class is used to perform sequential read operation.
 * 
 * @author priyanka
 *
 */
public class SeqAccessReadThread extends Thread {
	//number of threads
	private int noOfThreads = 0;
	
	//block size to read in bytes
	private int dataSize = 0;
	
	//number of blocks to read
	private int iterations = 0;
	
	//time taken to read n blocks of given size
	private long time = 0;

	@Override
	public void run() {

		byte[] data = new byte[getDataSize()];
		FileInputStream fileInputStream = null;
		int n = getIterations();
		
		try {
			//open file
			fileInputStream = new FileInputStream(new File(Constant.FILENAME));
			//get start time
			long beforeTime = System.currentTimeMillis();
			for (int i = 0; i < n; i++) {
				fileInputStream.read(data);
			}
			//get end time
			long afterTime = System.currentTimeMillis();

			//get the time to read n blocks of given size
			setTime(afterTime - beforeTime);
			
		} catch (FileNotFoundException e) {
			System.out.println("File is not found. " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Can not write into file. " + e.getMessage());
		} finally {

			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					System.out.println(" Failed to close file input stream " + e.getMessage());
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
