/*************************************
    *
    * Function declarations (prototypes).
    *
    *************************************/

   /* Func1 is only visable to functions in this file. */   

   static void Func1(void);

   /* Func2 is visable to all functions. */

   void Func2(void); 

   /*************************************
    *
    * Function definitions
    *
    *************************************/
       
   void Func1(void)
   {
     puts("Func1 called");
   }
   
   /*************************************/
   
   void Func2(void)        
   {
     puts("Func2 called");
   }
   