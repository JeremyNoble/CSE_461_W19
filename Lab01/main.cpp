//Class: CSE-461 - Lab01
//Coded by: Taylor Pedretti
//Reviewed by: Jeremy Noble

#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>
#include <string.h>
#include <iostream>

using namespace std;

void welcomePrompt();
void getCMD(char command[255], char *para[20]);

int main()
{
	char command[255] = {};
	char *para[20] = {};
	char *env[255] = { (char *)"PATH=/bin", 0 };

	welcomePrompt();
	while (true)
	{
		getCMD(command, para);

		(fork() != 0) ? wait(NULL) : execve(command, para, env);

		if (strstr(para[0], "exit"))
			break;
	}

	return 0;
}

void welcomePrompt()
{
	cout << "Welcome to Lab01 simple shell." << endl;
}

void getCMD(char command[255], char *para[20])
{
	string readLine;
	char temp[20] = { };
	char *pch = nullptr;

	cout << "Waiting for command: ";
	getline(cin, readLine);

	strcpy(command, "/bin/");
	strcat(command, readLine.substr(0, readLine.find(' ')).c_str());
	strcpy(temp, readLine.c_str());

	pch = strtok(temp, " \n");

	int i = 0;
	for (; pch != NULL; i++)
	{
		para[i] = strdup(pch);
		pch = strtok(NULL, " \n");
	}
	para[i] = NULL;

	delete[] pch;
}