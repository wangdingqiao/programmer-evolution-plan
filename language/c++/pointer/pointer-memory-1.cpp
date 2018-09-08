#include <iostream>

void GetMemory(char *p)
{
	p=new char[100];
	strcpy(p,"hello world");
}

void main(void)
{
	char *str=NULL;
	GetMemory(str);
	std::cout <<str;

	delete []str; // crash here
	str=NULL;
}