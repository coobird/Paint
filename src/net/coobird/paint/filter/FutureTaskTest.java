package net.coobird.paint.filter;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureTaskTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		FutureTask task = new FutureTask<Void>(new Callable<Void>() {

			boolean keepRunning = true;
			
			@Override
			public Void call() throws Exception
			{
				return null;
			}
			
			public void run()
			{
				while (keepRunning)
				{
					System.out.println("gagaga");
				}
			}
			
			public boolean cancel(boolean b)
			{
				keepRunning = false;
				return true;
			}
			
		});
		
		ExecutorService es = Executors.newFixedThreadPool(1);
		Future<?> f = es.submit(task);
		
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		task.cancel(true);

	}

}
