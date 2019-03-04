using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using System.Net;
using System.Net.Sockets;

namespace CSE_461_Lab04_Part1
{
    class Program
    {
        private const int port = 1337;

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
                    Console.WriteLine($" {Encoding.ASCII.GetString(clientRev, 0, clientRev.Length)}");
                    Console.WriteLine("Doing the math!");

                    //doMath(Encoding.ASCII.GetString(clientRev, 0, clientRev.Length));
                    listner.Send(doMath(Encoding.ASCII.GetString(clientRev, 0, clientRev.Length)), doMath(Encoding.ASCII.GetString(clientRev, 0, clientRev.Length)).Length, groupEP);
                    Console.WriteLine("Result sent to client!");
                }
            }
            catch (SocketException e)
            {
                Console.WriteLine(e);
            }
            finally
            {
                listner.Close();
            }
        }

        private static byte[] doMath(string clientRequest)
        {
            double numResult = 0;
            byte[] result;
            string oper = "";
            double num1 = 0;
            double num2 = 0;

            if(clientRequest.Contains("+"))
            {
                oper = "+";
                string num1S = clientRequest.Substring(0, clientRequest.IndexOf("+"));
                num1 = Convert.ToInt32(num1S);
                string num2S = clientRequest.Substring(clientRequest.IndexOf("+") + 1);
                num2 = Convert.ToInt32(num2S);
            }
            else if(clientRequest.Contains("-"))
            {
                oper = "-";
                string num1S = clientRequest.Substring(0, clientRequest.IndexOf("-"));
                num1 = Convert.ToInt32(num1S);
                string num2S = clientRequest.Substring(clientRequest.IndexOf("-") + 1);
                num2 = Convert.ToInt32(num2S);
            }
            else if(clientRequest.Contains("*"))
            {
                oper = "*";
                string num1S = clientRequest.Substring(0, clientRequest.IndexOf("*"));
                num1 = Convert.ToInt32(num1S);
                string num2S = clientRequest.Substring(clientRequest.IndexOf("*") + 1);
                num2 = Convert.ToInt32(num2S);
            }
            else if(clientRequest.Contains("/"))
            {
                oper = "/";
                string num1S = clientRequest.Substring(0, clientRequest.IndexOf("/"));
                num1 = Convert.ToInt32(num1S);
                string num2S = clientRequest.Substring(clientRequest.IndexOf("/") + 1);
                num2 = Convert.ToInt32(num2S);
            }

            switch (oper)
            {
                case "+":
                    numResult = num1 + num2;
                    break;
                case "-":
                    numResult = num1 - num2;
                    break;
                case "*":
                    numResult = num1 * num2;
                    break;
                case "/":
                    numResult = num1 / num2;
                    break;
                default:
                    break;
            }

            Console.WriteLine("Math done!");
            Console.WriteLine($"The Result is: {numResult}");
            return (result = Encoding.ASCII.GetBytes(numResult.ToString()));
        }

        static void Main(string[] args)
        {
            Server();
        }
    }
}
