using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using System.Net;
using System.Net.Sockets;

namespace CSE_461_Lab04_Part2_RandomNumGen_Server
{
    class Program
    {
        private const int port = 1338;

        private static void Server()
        {
            UdpClient listner = new UdpClient(port);
            IPEndPoint groupEP = new IPEndPoint(IPAddress.Any, port);

            try
            {
                while(true)
                {
                    Console.WriteLine("Waiting for info from client");
                    byte[] clientRev = listner.Receive(ref groupEP);
                    Console.WriteLine($"Received broadcast from {groupEP} :");
                    Console.WriteLine($" {Encoding.UTF8.GetString(clientRev, 0, clientRev.Length)}");

                    listner.Send(ranNumbers(Encoding.UTF8.GetString(clientRev, 0, clientRev.Length)),
                                 Encoding.UTF8.GetString(clientRev, 0, clientRev.Length).Length, groupEP);
                    Console.WriteLine("Randoms numbers sent to client!");
                }
            }
            catch(SocketException ex)
            {
                Console.WriteLine(ex);
            }
            finally
            {
                listner.Close();
            }

        }

        private static byte[] ranNumbers(string input)
        {
            byte[] ranNums = null;
            string[] inputBounds = input.Split(',');
            List<String> ranList = new List<String>();
            string ranNumsString = "";
            Random r = new Random();

            for(int i = 0; i < Convert.ToInt32(inputBounds[2]); i++)
            {
                ranList.Add(r.Next(Convert.ToInt32(inputBounds[0]), Convert.ToInt32(inputBounds[1])).ToString());
            }

            foreach(String t in ranList)
            {
                if(t == ranList.Last())
                {
                    ranNumsString += t;
                }
                else
                {
                    ranNumsString += (t + ",");
                }
            }

            Console.WriteLine("Random Numbers: " + ranNumsString);

            return (ranNums = Encoding.UTF8.GetBytes(ranNumsString.ToString()));
        }

        static void Main(string[] args)
        {
            Server();
        }
    }
}
