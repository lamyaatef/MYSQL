/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.utilities;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Array;

/**
 *
 * @author mabdelaal
 */
public class DestoryRunningThread {

    public  boolean stopThreadWithId(String threadId) {
        Boolean isParsedLong = false;
        Boolean isIdExists = false;

        Long lngThreadId = 0L;
        try {
            lngThreadId = Long.parseLong(threadId);
            isParsedLong = true;
        } catch (Exception e) {
        }


        if (threadId != null && threadId.compareTo("") != 0 && isParsedLong) {
        final Thread[] allThreads = getAllThreads();
            for (Thread thread : allThreads) {
                System.out.println("thread id in mem iss "+thread.getId());
                if (thread.getId() == lngThreadId) {
                    System.out.println("thread id that will stop " + thread.getId());
                    thread.stop();
                    isIdExists = true;
                }
            }
        } else {
            System.out.println("Invalid thread id " + threadId);
        }
        return isIdExists;

    }

    Thread[] getAllThreads() {
        final ThreadGroup root = getRootThreadGroup();
        final ThreadMXBean thbean = ManagementFactory.getThreadMXBean();
        int nAlloc = thbean.getThreadCount();
        int n = 0;
        Thread[] threads;
        do {
            nAlloc *= 2;
            threads = new Thread[nAlloc];            
            n = root.enumerate(threads, true);
        } while (n == nAlloc);
        return copyOf(threads, n);        
        
    }
    ThreadGroup rootThreadGroup = null;

    ThreadGroup getRootThreadGroup() {
        if (rootThreadGroup != null) {
            return rootThreadGroup;
        }
        ThreadGroup tg = Thread.currentThread().getThreadGroup();
        ThreadGroup ptg;
        while ((ptg = tg.getParent()) != null) {
            tg = ptg;
        }
        return tg;
    }

    public static <T> T[] copyOf(T[] original, int newLength) {
        if (null == original) {
            throw new NullPointerException();
        }
        if (0 <= newLength) {
            return copyOfRange(original, 0, newLength);
        }
        throw new NegativeArraySizeException();
    }

    public static <T> T[] copyOfRange(T[] original, int start, int end) {
        if (original.length >= start && 0 <= start) {
            if (start <= end) {
                int length = end - start;
                int copyLength = Math.min(length, original.length - start);
                T[] copy = (T[]) Array.newInstance(original.getClass().getComponentType(), length);
                System.arraycopy(original, start, copy, 0, copyLength);
                return copy;
            }
            throw new IllegalArgumentException();
        }
        throw new ArrayIndexOutOfBoundsException();
    }

}
