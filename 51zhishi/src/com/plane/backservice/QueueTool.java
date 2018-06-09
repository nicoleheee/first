package com.plane.backservice;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by User on 2016/8/19.
 */
public class QueueTool
{

    private Map<String,BlockingQueue<?>>  queueMap = new HashMap<String, BlockingQueue<?>>();

    private static  QueueTool instance = new QueueTool();

    private  QueueTool()
    {

    }


    public synchronized  static <T>  BlockingQueue<T> getQueue(Class<T> type,String name)
    {
        BlockingQueue<T> queue = null;
        if(!instance.queueMap.containsKey(name))
        {
            queue = new ArrayBlockingQueue<T>(10000);//
            instance.queueMap.put(name,queue);
        }
        else
        {
            queue = (BlockingQueue<T>) instance.queueMap.get(name);
        }

        return queue;

    }





}
