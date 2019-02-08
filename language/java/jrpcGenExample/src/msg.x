program msgserv {
   	version MSGSERV_V1 {
	    	string sendmsg(string)=2;  
     }= 1;
     
} = 1234567;

program msgservExtend {
   	version MSGSERV_V2 {
	    	string sendmsg(string)=1;  
	    	string sendmsgEx(string)=2;  
     }= 2;
     
} = 1234568;