package com.example.cse_461_lab04_part2_rannumgen_client;

import android.app.Activity;
import android.os.AsyncTask;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.SocketException;
import java.io.IOException;
import java.nio.charset.Charset;
import android.os.Build;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Client
{
    private DatagramSocket socket;
    private InetAddress addr;
    private AsyncTask<Void, Void, Void> async_client;
    public String Message = "";
    String ranNumbers = "";

    public Client()
    {
        try { socket = new DatagramSocket(); }
        catch(SocketException ex) { ex.printStackTrace(); }
    }

    public void GetRandomNums(Activity curAct)
    {
        TextView temp = curAct.findViewById(R.id.randomNumbers);
        async_client = new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                byte[] buf = Message.getBytes(Charset.defaultCharset());

                try { addr = InetAddress.getByName("96.44.135.45"); }
                catch(UnknownHostException ex) { ex.printStackTrace(); }

                DatagramPacket packet = new DatagramPacket(buf, buf.length, addr, 1338);

                try
                {
                    socket.send(packet);
                }
                catch(IOException ex)
                {
                    ex.printStackTrace();
                }

                byte[] revBuf = new byte[1024000];
                packet = new DatagramPacket(revBuf,0, revBuf.length);

                try
                {
                    socket.receive(packet);
                    ranNumbers = new String(revBuf, 0, packet.getLength());
                }
                catch(IOException ex) {
                    ex.printStackTrace();
                }

                return null;
            }
        };

        if(Build.VERSION.SDK_INT >= 11)
            async_client.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else {
            async_client.execute();
        }

        try {
            Thread.sleep(1500);
        }
        catch(InterruptedException ex)
        {
            ex.printStackTrace();
        }

        temp.setText(ranNumbers);
    }
}
