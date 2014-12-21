package tel.panfilov.documentum.benchmark;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public class Benchmark implements Runnable {

	public static final long SLEEP_TIME = 10 * 1000;

	public static final int ITERATIONS_DEFAULT = 50;

	public static final int THREAD_COUNT_DEFAULT = 1;

	private final IBenchmark _benchmark;

	private final AtomicLong _counter;

	private final Semaphore _semaphore;

	private final Thread _parent;

	public Benchmark(IBenchmark impl, AtomicLong counter, Semaphore semaphore, Thread parent) {
		_benchmark = impl;
		_counter = counter;
		_semaphore = semaphore;
		_parent = parent;
	}

	public static void main(String[] argv) throws Exception {
		String docbase = argv[0];
		String userName = argv[1];
		String password = argv[2];
		String factoryName = argv[3];
		int threadCount = THREAD_COUNT_DEFAULT;
		if (argv.length > 4) {
			threadCount = Integer.valueOf(argv[4]);
		}
		int iterationsCount = ITERATIONS_DEFAULT;
		if (argv.length > 5) {
			iterationsCount = Integer.valueOf(argv[5]);
		}
		long sleepTime = SLEEP_TIME;
		if (argv.length > 6) {
			sleepTime = Long.valueOf(argv[6]);
		}

		boolean success = false;

		Class<? extends IBenchmark> factoryClass = Class.forName(factoryName).asSubclass(IBenchmark.class);
		Constructor<? extends IBenchmark> factoryConstructor = factoryClass.getConstructor(String.class, String.class,
				String.class);

		AtomicLong counter = new AtomicLong(0);
		Semaphore semaphore = new Semaphore(threadCount, true);
		semaphore.acquire(threadCount);
		List<Thread> threads = new ArrayList<Thread>();

		try {
			for (int i = 0; i < threadCount; i++) {
				Thread t = new Thread(new Benchmark(factoryConstructor.newInstance(docbase, userName, password),
						counter, semaphore, Thread.currentThread()));
				threads.add(t);
				t.start();
			}

			while (!counter.compareAndSet(threadCount, 0)) {
				Thread.sleep(1000);
			}
			semaphore.release(threadCount);

			long prevValue = counter.get();
			for (int iteration = 0; iteration < iterationsCount; iteration++) {
				Thread.sleep(sleepTime);
				long curValue = counter.get();
				System.out.println("Executions per " + sleepTime + "ms: " + (curValue - prevValue) + ", iteration: "
						+ (iteration + 1));
				prevValue = curValue;
			}
			success = true;
		} catch (InterruptedException ex) {
			System.out.println("Benchmark failed");
		}

		for (Thread t : threads) {
			t.interrupt();
		}

		System.exit(success ? 0 : 1);
	}

	@Override
	public void run() {
		try {
			_benchmark.doSetup();
			_counter.incrementAndGet();
			try {
				_semaphore.acquire();
				while (true) {
					_benchmark.doOp();
					_counter.incrementAndGet();
				}
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			} finally {
				_semaphore.release();
			}
		} catch (Throwable t) {
			_parent.interrupt();
		} finally {
			_benchmark.doRelease();
		}
	}

}
