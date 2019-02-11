1.install libuv:

sudo apt-get install automake
sudo apt-get install libtool
sh autogen.sh 
./configure
make
make check
sudo make install


2.build
uilding target: libuv-server
Invoking: Cross GCC Linker
gcc -L/usr/local/lib -o "libuv-server"  ./libuv-server.o   -luv

3.run bin:
sudo ldconfig -v
./libuv-server 