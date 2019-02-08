package com.learningjava.antlr.rpcmessage;

message BeerSort
{
	seq<string> names;
};

message Beer
{
	string Brand;
    BeerSort Sort;
	float Alcohol;
    string Brewery;
};

message BeerArray
{
	seq<Beer> beers;
};


interface  queryBeers(float param1, string param2, seq<string> param3, map<string, int> param4, binary param5, seq<int> param6, out BeerArray beerArray);