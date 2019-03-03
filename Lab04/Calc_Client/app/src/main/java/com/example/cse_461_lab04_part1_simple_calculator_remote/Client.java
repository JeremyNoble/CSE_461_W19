package com.example.cse_461_lab04_part1_simple_calculator_remote;

import android.os.AsyncTask;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.SocketException;
import java.io.IOException;
import android.os.Build;

public class Client
{
    private DatagramSocket socket;
    private InetAddress addr;
    private AsyncTask<Void, Void, Void> async_client;
    public String Message;
    String result = "";

    public Client()
    {
        try { socket = new DatagramSocket(); }
        catch(SocketException ex) { ex.printStackTrace(); }
    }

    public String SendMSG()
    {
        async_client = new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                byte[] buf = Message.getBytes();

                try { addr = InetAddress.getByName("96.44.135.45"); }
                catch(UnknownHostException ex) { ex.printStackTrace(); }

                DatagramPacket packet = new DatagramPacket(buf, buf.length, addr, 1337);

                try
                {
                    socket.send(packet);
                }
                catch(IOException ex)
                {
                    ex.printStackTrace();
                }

                packet = new DatagramPacket(buf, buf.length);

                try
                {
                    socket.receive(packet);
                    result = new String(buf, 0, packet.getLength());

                }
                catch(IOException ex)
                {
                    ex.printStackTrace();
                }

                return null;
            }
        };

        if(Build.VERSION.SDK_INT >= 11)
            async_client.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            async_client.execute();

        return result;
    }
}
