#include<iostream>
using namespace std;

class ABC{
public: 
  ABC(int val=-1):value(val)
  {
    cout<<"constructor called."<<endl;
  }

  ~ ABC()
  {
    cout<<"destructor called."<<endl;
  }

 void disp()
 {
   cout<<"disp called, " << value << endl;
 }
private:
  int value;
};

int main()
{

cout << "allocate using malloc " << endl;
ABC* b=(ABC*)malloc(sizeof(ABC));
b->disp();
free(b);

cout << endl << "allocate using new " << endl;

ABC *a=new ABC();
a->disp();
delete a;

// ·ÖÅäÊý×é
cout << endl <<"allocate array using malloc " << endl;
b=(ABC*)malloc(2 * sizeof(ABC));
b->disp();
free(b);

cout << endl << "allocate array using new " << endl;
a=new ABC[2]();
a->disp();
delete[] a;

return 0;
}

/*
allocate using malloc
disp called, 8571248

allocate using new
constructor called.
disp called, -1
destructor called.

allocate array using malloc
disp called, 8570424

allocate array using new
constructor called.
constructor called.
disp called, -1
destructor called.
destructor called.
*/