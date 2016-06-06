import java.util.ArrayList;
import java.util.List;
/**
 * This class creates the threads and perform the calculation of latency and throughput.
 * @author priyanka
 *
 */

public class DiskBenchmark {

	public static void main(String[] args) {
		
		sequentialWriteAccess();
		sequentialReadAccess();
		randomWriteAccess();
		randomReadAccess();
	}
	
	/**
	 * This method is used to check whether all threads are done or not
	 * @param threads
	 * @return
	 */
	public static boolean isAlive(List<? extends Thread>threads){
		for (Thread thread : threads) {
			if(thread.isAlive())
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * This function creates the 1 or 2 threads and perform sequential write operation by using different block sizes 1B or 1KB or 1MB.
	 * 
	 */
	public static void sequentialWriteAccess(){
		double throughput = 0,latency = 0; 
		long dataWritten = 0;
		List<SeqAccessWriteThread>threads = new ArrayList<SeqAccessWriteThread>();
		long milliSecTime = 0;


		String []blockSizes = {"1B", "1KB", "1MB"};
		int size = 0, iterations = 10;
		
		// consider 3 block sizes 
		System.out.println("\nSequential Disk Access - write operation");
		for(int k = 0; k < 3; k++)
		{
			size = (int)Math.pow(1024, k);
			if(size == 1)
			{
				iterations = 100000;
			}	
			else if(size == 1024){
				iterations = 1000;
			}else{
				iterations = 10;
			}
			
			// create the 1 or 2 threads.
			for(int i = 1; i <= 2; i = (int) Math.pow(2, i))
			{	
				int j = i-1;
				threads = new ArrayList<SeqAccessWriteThread>();
				while(j >= 0){
					//create thread
					SeqAccessWriteThread seqAccessWriteThread = new SeqAccessWriteThread();
					//set no of threads
					seqAccessWriteThread.setNoOfThreads(i);
					//set data size to write
					seqAccessWriteThread.setDataSize(size);
					//set how many number of block to write in file
					seqAccessWriteThread.setIterations(iterations);
					threads.add(seqAccessWriteThread);
					//start the thread
					seqAccessWriteThread.start();
					j--;
				}	
			
				//check weather all the created threads are done or not
				while(true){
					if(!isAlive(threads)){
						break;
					}
				}
				
				//sum the time in millisecond of all threads
				milliSecTime = 0;
				for (SeqAccessWriteThread s1 : threads) {
					milliSecTime = milliSecTime + s1.getTime();
				}
				
				if(i == 1)
				{
					System.out.println("\n\nFor 1 Thread - sequential disk access ");
				}
				else{
					System.out.println("\n\nFor "+i+" Threads - sequential disk access ");
				}
				
				// total bytes written = no of threads * no of iterations * size of block in bytes 
				dataWritten = i * iterations * size;
				
				//convert bytes into MB
				throughput = (dataWritten/Math.pow(1024, 2));

				//take the average of all threads time and convert it into seconds
				double timeSecond = (milliSecTime/i)/Math.pow(10, 3);
				
				// throughput = ((no of threads * no of iterations * size of block in bytes )/ Math.pow(1024, 2))/ (average of time taken to write by all threads in seconds)
				throughput = (throughput/timeSecond);

				System.out.printf("\nFor block size of "+blockSizes[k]+" and write operation , Throughput = "+throughput+" MB/sec");
				
				// latency = total time taken by all the threads in millisecond/ (no of threads * no of block written by each thread)
				latency = ((double)milliSecTime)/(i * iterations);

				System.out.printf("\nFor block size of "+blockSizes[k]+" and write operation , Latency =" +latency+" ms");

			}
		}
	}
	

	/**
	 * This function create 1 or 2 threads and calculate the latency and throughput for sequential read operation, 
	 * from the time get from each thread.
	 */
	
	public static void sequentialReadAccess(){
		double throughput = 0,latency = 0; 
		long dataWritten = 0;
		List<SeqAccessReadThread>threads = new ArrayList<SeqAccessReadThread>();
		long milliSecTime = 0;


		String []blockSizes = {"1B", "1KB", "1MB"};
		int size = 0, iterations = 10;
		
		System.out.println("\nSequential Disk Access - Read Operation");
		
		// 3 blocks
		for(int k = 0; k < 3; k++)
		{
			//calculate size of block in bytes
			size = (int)Math.pow(1024, k);
			if(size == 1)
			{
				iterations = 100000;
			}	
			else if(size == 1024){
				iterations = 10000;
			}else{
				iterations = 10;
			}
			
			//create threads
			for(int i = 1; i <= 2; i = (int) Math.pow(2, i))
			{	
				int j = i-1;
				threads = new ArrayList<SeqAccessReadThread>();
				while(j >= 0){
					//create thread for read operation
					SeqAccessReadThread seqAccessReadThread = new SeqAccessReadThread();
					//set the no of threads
					seqAccessReadThread.setNoOfThreads(i);
					//set the block size to read
					seqAccessReadThread.setDataSize(size);
					//set how many blocks to read
					seqAccessReadThread.setIterations(iterations);
					threads.add(seqAccessReadThread);
					//start the thread
					seqAccessReadThread.start();
					j--;
				}	
			
				/**
				 * check weather all threads are done or not
				 */
				while(true){
					if(!isAlive(threads)){
						break;
					}
				}
				
				/**
				 * sum all the times in millisecond of all threads
				 */
				milliSecTime = 0;
				for (SeqAccessReadThread s1 : threads) {
					milliSecTime = milliSecTime + s1.getTime();
				}
				
				if(i == 1)
				{
					System.out.println("\n\nFor 1 Thread - sequential disk access ");
				}
				else{
					System.out.println("\n\nFor "+i+" Threads - sequential disk access ");
				}
				
				// throughput = ((no. of threads * no. of blocks read * size of block in byte)/ (1024 * 1024))/ (average of all the threads times in seconds)
				dataWritten = i * iterations * size;
				
				throughput = (dataWritten/Math.pow(1024, 2));

				double timeSecond = (milliSecTime/i)/Math.pow(10, 3);
				throughput = (throughput/timeSecond);

				System.out.printf("\nFor block size of "+blockSizes[k]+" and read operation , Throughput = "+throughput+" MB/sec");							
				
				//latency = ( sum of time in millisecond of all the threads / (no. of threads * no. of block read))
				latency = ((double)milliSecTime)/(i * iterations);

				System.out.printf("\nFor block size of "+blockSizes[k]+" and read operation , Latency =" +latency+" ms");

			}
		}
	}
	
	/**
	 * This function is used to create 1 or 2 threads for random write for different block sizes 1B, 1KB, 1MB. 
	 * Also calculates throughput and latency
	 */
	public static void randomWriteAccess(){
		double throughput = 0,latency = 0; 
		long dataWritten = 0;
		List<RandomAccessWriteThread>threads = new ArrayList<RandomAccessWriteThread>();
		long milliSecTime = 0;


		String []blockSizes = {"1B", "1KB", "1MB"};
		int size = 0, iterations = 10;
		
		System.out.println("\nRandom Disk Access - write operation");
		//consider 3 blocks of different size
		for(int k = 0; k < 3; k++)
		{
			size = (int)Math.pow(1024, k);
			if(size == 1)
			{
				iterations = 100000;
			}	
			else if(size == 1024){
				iterations = 1000;
			}else{
				iterations = 10;
			}
			
			// create threads
			for(int i = 1; i <= 2; i = (int) Math.pow(2, i))
			{	
				int j = i-1;
				threads = new ArrayList<RandomAccessWriteThread>();
				while(j >= 0){
					RandomAccessWriteThread randomAccessWriteThread = new RandomAccessWriteThread();
					randomAccessWriteThread.setNoOfThreads(i);
					randomAccessWriteThread.setDataSize(size);
					randomAccessWriteThread.setIterations(iterations);
					threads.add(randomAccessWriteThread);
					randomAccessWriteThread.start();
					j--;
				}	
			
				while(true){
					if(!isAlive(threads)){
						break;
					}
				}
				
				milliSecTime = 0;
				for (RandomAccessWriteThread s1 : threads) {
					milliSecTime = milliSecTime + s1.getTime();
				}
				
				if(i == 1)
				{
					System.out.println("\n\nFor 1 Thread - Random disk access ");
				}
				else{
					System.out.println("\n\nFor "+i+" Threads - Random disk access ");
				}
				
				// throughput = ((no. of threads * no. of blocks written * size of block in bytes)/(1024 * 1024))/ (average of time take by all threads in second)
				dataWritten = i * iterations * size;
				
				throughput = (dataWritten/Math.pow(1024, 2));

				double timeSecond = (milliSecTime/i)/Math.pow(10, 3);
				throughput = (throughput/timeSecond);

				System.out.printf("\nFor block size of "+blockSizes[k]+" and write operation , Throughput = "+throughput+" MB/sec");							
				
				//latency = (total time taken by all threads in millisecond/ (no. of threads * no. of block written))
				latency = ((double)milliSecTime)/(i * iterations);

				System.out.printf("\nFor block size of "+blockSizes[k]+" and write operation , Latency =" +latency+" ms");

			}
		}
	}
	

	/**
	 * This function is used to create 1 or 2 threads which will randomly read 1B, 1KB,1MB
	 */
	public static void randomReadAccess(){
		double throughput = 0,latency = 0; 
		long dataWritten = 0;
		List<RandomAccessReadThread>threads = new ArrayList<RandomAccessReadThread>();
		long milliSecTime = 0;


		String []blockSizes = {"1B", "1KB", "1MB"};
		int size = 0, iterations = 10;
		
		System.out.println("\nRandom Disk Access - Read Operation");
		
		//Consider 3 blocks
		for(int k = 0; k < 3; k++)
		{
			size = (int)Math.pow(1024, k);
			if(size == 1)
			{
				iterations = 100000;
			}	
			else if(size == 1024){
				iterations = 10000;
			}else{
				iterations = 10;
			}
			
			//create threads
			for(int i = 1; i <= 2; i = (int) Math.pow(2, i))
			{	
				int j = i-1;
				threads = new ArrayList<RandomAccessReadThread>();
				while(j >= 0){
					//create thread
					RandomAccessReadThread randomAccessReadThread = new RandomAccessReadThread();
					//set no. of threads
					randomAccessReadThread.setNoOfThreads(i);
					//set block to read in byte
					randomAccessReadThread.setDataSize(size);
					//set no. of blocks to read
					randomAccessReadThread.setIterations(iterations);
					threads.add(randomAccessReadThread);
					//start thread
					randomAccessReadThread.start();
					j--;
				}	
			
				
				//check whether all threads are done or not
				while(true){
					if(!isAlive(threads)){
						break;
					}
				}
				
				//sum the time in millisecond taken by all threads
				milliSecTime = 0;
				for (RandomAccessReadThread s1 : threads) {
					milliSecTime = milliSecTime + s1.getTime();
				}
				
				if(i == 1)
				{
					System.out.println("\n\nFor 1 Thread - Random disk access ");
				}
				else{
					System.out.println("\n\nFor "+i+" Threads - Random disk access ");
				}
				
				
				// throughput = ((no. of threads * no. of block to read * size of block to read in bytes)/(1024 * 1024))/(average of time of all threads in second)
				dataWritten = i * iterations * size;
				
				throughput = (dataWritten/Math.pow(1024, 2));

				double timeSecond = (milliSecTime/i)/Math.pow(10, 3);
				throughput = (throughput/timeSecond);

				System.out.printf("\nFor block size of "+blockSizes[k]+" and read operation , Throughput = "+throughput+" MB/sec");	
				
				//latency = (sum of time in milliseconds taken by all threads )/ (no. of threads * no. of blocks read)
				latency = ((double)milliSecTime)/(i * iterations);

				System.out.printf("\nFor block size of "+blockSizes[k]+" and read operation , Latency =" +latency+" ms");

			}
		}
	}
	

}	
