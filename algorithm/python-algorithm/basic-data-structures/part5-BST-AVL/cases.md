1.Insert cases

插入时导致的左平衡处理：插入节点为X.


```
	   A                                  B
	  /  \                              /  \ 
	  B   C(h)    right rotate(A)      /    \
	 /  \         ================>    D(h)  A
	/    \                             |    / \
	D(h)  E                            X    E  C(h)
	|
	X
```


```
	  A                                     A                                   E
	 / \								                   / \ 								  /   \
	/   \					                        /   \                              /     \
	B    C(h)       left rotate(B)        E    C     right rotate(A)         B       A
  / \				================>          / \         ================>      / \     / \
 /   \                                /   \                              /   \   /   \
 D    E                               B    G                             D    F  G   C
      / \                             / \                                     |
      F  G(h-1)                       D  F                                    X 
      |                                  |                                   
      X                                  X
```

```
	  A                                     A                                   E
	 / \								                   / \ 								                /   \
	/   \					                        /   \                              /     \
	B    C(h)       left rotate(B)        E    C     right rotate(A)        B       A
  / \				================>          / \         ================>     / \     / \
 /   \                                /   \                             /   \   /   \
 D    E                               B    G                            D    F  G   C
      / \                             / \   |                                    |
      F  G(h-1)                       D  F  X                                    X 
         |                                                                      
         X                                   
```

```
	   A                               A                             E
	  /                               /                             / \
    B           left roate(B)      E       right rotate(A)       /   \
    \          ================>  /        ================>    B    A
     E                            B

```

| 旋转之前             |  旋转类型        | 旋转之后         |
| :----------------    | --------------:  |   :--:    	     |
| A=-2, B=-1  		     | 一次右旋转       |  A=0, B= 0       |
| A=-2, B=+1, E=-1     | 先左旋转后右旋转 |  A=+1, B=0, E=0  |
| A=-2, B=+1, E=+1     | 先左旋转后右旋转 |  A=0, B=-1, E=0  |
| A=-2, B=+1, E=0      | 先左旋转后右旋转  |  A=0, B=0, E=0  |
```

右平衡处理情况类似，此处略去。



2.remove cases

```
1. Reduce the problem to the case when the node x to be removed has at most one child.
   
2. Delete x. We use a bool variable shorter to show if the height of a subtree has been shortened.
   
3. While shorter is true do the following steps for each node P on the path from the 
parent of x to the root of the tree. When shorter becomes false, the algorithm terminates.
   
Case 1: Node P has balance factor equal. The balance factor of P is changed according 
as its left or right subtree has been shortened, and shorter becomes false.

Case 2: The balance factor of P is not equal, and the taller subtree was shortened. 
Change the balance factor of P to equal, and leave shorter as true.

Case 3: The balance factor of P is not equal, and the shorter subtree was shortened. 
Apply a rotation as follows to restore balance. 
Let Q be the root of the taller subtree of P.

Case 3a: The balance factor of Q is equal. 
A single rotation restores balance, and shorter becomes false.

Case 3b: The balance factor of Q is the same as that of P. 
Apply a single rotation, 
set the balance factors of P and Q to equal, and leave shorter as true.

Case 3c: The balance factors of P and Q are opposite. 
Apply a double rotation (first around Q, then around P), 
set the balance factor of the new root to 
equal and the other balance factors as appropriate, and leave shorter as true.

```


```
1. Reduce the problem to the case when the node x to be removed has at most one child.
   将问题归一化为，删除x节点时，它最多只有一个孩子节点。
   
2. Delete x. We use a bool variable shorter to show if the height of a subtree has been shortened.
   删除x。使用一个布尔变量shorter来记录子树的高度是否变低了。
   
3. While shorter is true do the following steps for each node P on the path from the 
parent of x to the root of the tree. When shorter becomes false, the algorithm terminates.
   当shorter为true时，对x的父节点到根节点路径上的节点P执行下列步骤；当shorter变为false时，算法终止。

Case 1: Node P has balance factor equal. The balance factor of P is changed according 
as its left or right subtree has been shortened, and shorter becomes false.
情形 1： 节点P的平衡因子为0。节点P的左子树或者右子树高度被降低，这时shorter设置为false。

Case 2: The balance factor of P is not equal, and the taller subtree was shortened. 
Change the balance factor of P to equal, and leave shorter as true.
情形 2： P的平衡因子不为0，并且较高的子树高度降低了，这时将P的平衡因子置为0，让shorter保持为true。

Case 3: The balance factor of P is not equal, and the shorter subtree was shortened. 
Apply a rotation as follows to restore balance. 
Let Q be the root of the taller subtree of P.
情形 3: P的平衡因子不为0，并且较矮的子树高度降低了，这个时候需要进行旋转调整。设P的较高子树的根节点为Q。

Case 3a: The balance factor of Q is equal. 
A single rotation restores balance, and shorter becomes false.
情形 3a: Q的平衡因子为0，需要进行一次旋转，并且shorter置为false。

Case 3b: The balance factor of Q is the same as that of P. 
Apply a single rotation, 
set the balance factors of P and Q to equal, and leave shorter as true.
情形 3b: Q的平衡因子和P一致。进行一次旋转，设置P和Q的平衡因子为0，shorter置为true。

Case 3c: The balance factors of P and Q are opposite. 
Apply a double rotation (first around Q, then around P), 
set the balance factor of the new root to 
equal and the other balance factors as appropriate, and leave shorter as true.
情形 3c: Q的平衡因子和P相反，进行两次旋转，首先旋转Q，然后旋转P。
设置新根节点平衡因子为0，其他平衡因子为合适值，shorter置为true。
```


```
1. 将问题归一化为，删除x节点时，它最多只有一个孩子节点。
   
2.删除x。使用一个布尔变量shorter来记录子树的高度是否变低了。
   
3. 当shorter为true时，对x的父节点到根节点路径上的节点P执行下列步骤；当shorter变为false时，算法终止。

情形 1： 节点P的平衡因子为0。节点P的左子树或者右子树高度被降低，这时shorter设置为false。

情形 2： P的平衡因子不为0，并且较高的子树高度降低了，这时将P的平衡因子置为0，让shorter保持为true。

情形 3: P的平衡因子不为0，并且较矮的子树高度降低了，这个时候需要进行旋转调整。设P的较高子树的根节点为Q。

情形 3a: Q的平衡因子为0，需要进行一次旋转，并且shorter置为false。

情形 3b: Q的平衡因子和P一致。进行一次旋转，设置P和Q的平衡因子为0，shorter置为true。

情形 3c: Q的平衡因子和P相反，进行两次旋转，首先旋转Q，然后旋转P。
设置新根节点平衡因子为0，其他平衡因子为合适值，shorter置为true。
```


1. Reduce the problem to the case when the node x to be removed has at most one child.

2. Delete x. We use a bool variable shorter to show if the height of a subtree has been shortened.

3. While shorter is true do the following steps for each node P on the path from the 
parent of x to the root of the tree. When shorter becomes false, the algorithm terminates.

Case 1: Node P has balance factor equal. The balance factor of P is changed according 
as its left or right subtree has been shortened, and shorter becomes false.

```
		  P(0)                                 P(+1)
	    /     \		remove x from A           /    \       shorter = false
	   /       \    ==================>    /      \      =========================
	  A(h)   B(h)                          A(h-1)   B(h)
```

Case 2: The balance factor of P is not equal, and the taller subtree was shortened. 
Change the balance factor of P to equal, and leaves horter as true.

```
	      P(+1)                                P(0)
	    /     \		remove x from B           /    \   	  shorter = true
	   /       \    ==================>      /      \     =========================
	  A(h)   B(h+1)                          A(h)   B(h)
```

Case 3: The balance factor of P is not equal, and the shorter subtree was shortened. 
Apply a rotation as follows to restore balance. Let Q be the root of the taller subtree of P.
Case 3a: The balance factor of Q is equal. A single rotation restores balance, and shorter becomes false.

```
	    P(+1)                                                      Q(-1)
      /      \          remove x from A     left rotate P         /     \
     /        \         ==================================>      /       \          shorter = false
    A(h)      Q(0)                                              P(+1)    Qr(h)    ===========================
     		   /     \                                            /      \
     	    /       \                                          /        \
     	   Ql(h)   Qr(h)                                     A(h-1)     Ql(h)
```


Case 3b: The balance factor of Q is the same as that of P. Apply a single rotation, set the balance factors of P and Q to equal, and leave shorter as true.


```
  		P(+1)                                                      Q(0)
      /      \          remove x from A     left rotate P         /     \
     /        \         ==================================>      /       \          shorter = true
    A(h)      Q(+1)                                              P(0)    Qr(h)    ===========================
     		     /     \                                            /      \
     	      /       \                                          /        \
     	   Ql(h-1)   Qr(h)                                    A(h-1)     Ql(h-1)
```

Case 3c: The balance factors of P and Q are opposite. Apply a double rotation (first around Q, then around P), 
set the balance factor of the new root to equal and the other balance factors as appropriate, and leave shorter as true.

```
  	   P(+1)                                                        P      
      /     \          remove x from A   right rotate Q            /   \              
     /       \         ==================================>         A    Ql           
    A(h)     Q(-1)                                                      / \          
     	     /    \                                                      /   \       
     	    Ql     Qr(h-1)                                               Qll  Q      
          /  \                                                             /  \       
         Qll Qlr						                                               Qlr Qr		
  
                                                             
                           Ql(0)                                
     left rotate P        /     \	  shorter = true          
  ====================>  /       \	==================    
                         P        Q                                       
                       /  \      /  \                                  
                      /    \    /    \                                 
                   A(h-1)  Qll  Qlr  Qr(h-1)                             
  				                                 	
```
