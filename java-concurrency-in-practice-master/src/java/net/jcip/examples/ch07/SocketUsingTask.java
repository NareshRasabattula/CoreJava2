package net.jcip.examples.ch07;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;

import net.jcip.annotations.*;

/**
 * SocketUsingTask
 * 
 * @list 7.12
 * @smell Good
 * @author Brian Goetz and Tim Peierls
 * 
 * <p>Example of encapsulating nonstandard cancellation in a task with newTaskFor
 */

public abstract class SocketUsingTask <T> implements CancellableTask<T> {
    @GuardedBy("this") private Socket socket;

    protected synchronized void setSocket(Socket s) {
        socket = s;
    }

    public synchronized void cancel() {
        try {
            if (socket != null)
                socket.close();
        } catch (IOException ignored) {
        }
    }

    public RunnableFuture<T> newTask() {                                       // a newTask factory method for constructing a RunnableFuture
        return new FutureTask<T>(this) {  
            public boolean cancel(boolean mayInterruptIfRunning) {             // This method overrides the Future.cancel()
                try {                                                          // If a SocketUsingTask is cancelled through its Future, the socket is closed (by this.cancel) and the executing thread is interrupted (by super.cancel).
                    SocketUsingTask.this.cancel();                             
                } finally {
                    return super.cancel(mayInterruptIfRunning);
                }
            }
        };
    }
}


interface CancellableTask <T> extends Callable<T> {
    void cancel();

    RunnableFuture<T> newTask();
}


@ThreadSafe
class CancellingExecutor extends ThreadPoolExecutor {
    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if (callable instanceof CancellableTask)
            return ((CancellableTask<T>) callable).newTask();
        else
            return super.newTaskFor(callable);
    }
}
