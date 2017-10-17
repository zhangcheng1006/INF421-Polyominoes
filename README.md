# INF421-Polyominoes
This is a project of polyomino tilings and exact cover algorithm.

Test
------------------------
This class test the exact cover problem and the dancing links structure.

There are 7 different instance predefined allowing to test our algorithms.

Simply remove ”/*” after the “case ” will allow you to initialize different Exact Cover problem.

The result will be print in three line, the first is the time consumed by the naïve implement, 
The second line represent the time consumed by dancing_links structure. The third line test if the number of the solutions of the naïve implement equals to the dancing_links implement. If they are equal, we assume that we got the same solutions.


Test1
-------------------------
This class test the correctness of the functions in the class “Polynomio”.

There are 9 functions to test different functions of “Polynomio”.

Simply remove the “//” before each function in the main function to test different functions.

*********Methods**********

- public static void test1()
Test the creation of a polynomio from a String.

- public static void test2() 
- Create a list of polynomio from a text file “Polynomioes INF421.txt”.

- public static void test3()
- Test the elementary transformations.

- public static void test4()
- Generate all fixed polynomios and free polynomios with the naïve method.

- public static void test5()
- Generate all fixed polynomio with the implemented method.

- public static void test6(String s)
- Find all tilings of the polynomios of Figure 5 by all free pentaminos.

- public static void test7a(int n, int length, int width)
- For a given n, find all tilings of a rectangle by all fixed polynomios of area n.

- public static void test7b(int n, int length, int width)
- For a given n, find all tilings of a rectangle by all free polynomios.

- public static void test8(int n, int k)
- For a given n and k, find all polynomios P of size n which can cover their own dilate kP.


ColoredPolygon
----------------------------------
In this class, you will find four fields and one constructor.

*************The fields************

- int[] xcoords: the x coordinates of the vertices of the polygon.
- int[] ycoords:  the y coordinates of the vertices of the polygon.
- Color color :  the color of the polygon.
- Polygon polygon :  the objects predefine by java.

*************Constructor************

- ColoredPolygon(int [] xcoords, int [] ycoords, Color color)
- It will create a polygon according to the coordinates represented by xcoords and the ycoords


Coords_rep
-------------------------------------
In this Class you will find three fields one constructor and different methods;

*************The fields*************

- private int[] Xcoords: the x coordinates of the unite of a polynomio;
- private int[] Ycoords: the y coordinates of the unite of a polynomio;
- private int[] origine: the left bottom coordinates of this unite of a polynomio;

*************Constructor************

- public Coords_rep(int i,int j)
- {x,y} represent the origine of this unite of the polynomial.

*************Methods**************

- public void set_Origine(int i,int j)
- Set the origine as {i,j}

- public int[] get_Xcoords()
- Return the Xcoords in forme of an array.

- public int[] get_Ycoords()
- Return the Ycoords int forme of an array.

- public int[] get_Origine()
- Return the origine in forme of an array, the fist element represent x, the second represent y

- public int get_dist_X(Coords_rep r)
- Return the difference of X coordinates between this and r;

- public int get_dist_Y(Coords_rep r)
- Return the difference of Y coordinates between this and r;

- public Coords_rep Translation(int i,int j)
- Translate the unite by{I,j}

- public Coords_rep Dilate(int ox,int oy,int r)
- Dilate this unite by r

- public Coords_rep rotate(int ox,int oy, int deg)
- Rotate this unite by deg degree, deg can take 4 different values: 1,2,3 which represent
Respectively 90,180,270.

- public Coords_rep reflection(int ox, int oy, int axis)
- Reflect this unite by x axis(1), y axis(2), ascending diagonal (3), and descending diagonal(4).

- public static LinkedList<Coords_rep> successors(Coords_rep cr)
- Return fours neighbors of this unite.


Polynomio
------------------------------
You will find one field LinkedList<Coords_rep> polyo and three different constructors;

************The fiels*********

- private LinkedList<Coords_rep> polyo: represent the set of squares of a polyomino.

************Constructors*********

- public Polynomio(){}
- No parameter and construct an empty polyomino.

- public Polynomio(LinkedList<Coords_rep> p){}
- Construct a polyomino from a set of squares.

- public Polynomio(String S, int ox, int oy){}
- Construct a polyomino from a String S which contains all coordinates of a set of squares. ox and oy are the coordinates of the origin.

*************Methods**************

- public int getSize(){}
- Returns the size of a polyomino.

- public static LinkedList<Polynomio> toList(File file) throws IOException{}
- Returns a list of polyominoes from a file.

- public Image2d toImage(Image2d img){}
- Return the new image after add the polyomino to the image.

- public Polynomio Translation(int i, int j){}
- Translate the polyomino to right-up by {i,j}.

- public Polynomio Dilate(int r){}
- Dilate this polyomino by r

- public Polynomio rotate(int deg){}
- Rotate counterclockwise this polyomino by deg degree, deg can take 3 different values: 1,2,3 which represent respectively 90,180,270.

- public Polynomio reflection(int axis){}
- Reflect this polyomino with respect to x axis(1), y axis(2), ascending diagonal (3), and descending diagonal(4).

- public String toString(){}
- Transform this polyomino to String and return it.

- public static void enumerate_naive(LinkedList<Polynomio> list, Polynomio init, int ox, int oy, int P){}
- Enumerate all fixed polyominoes with size ≤ P by the naive method (with repetitions).
- list is the list which contains all the polyominoes, init is the initial polyomino, ox and oy are the coordinates of the origin, P is the size limite.

- public static LinkedList<Polynomio> generate_fix_naive(LinkedList<Polynomio> list, int size){}
- Generate all fixed polyominoes of size size without repetitions from the list list that we obtained in the last function.

- public LinkedList<Polynomio> all_trans(){}
- Return all the rotations and reflections of a polyomino which is used to generate all free polyominoes.

- public static LinkedList<Polynomio> generate_free(LinkedList<Polynomio> Poly_fix){}
- Generate all free polyominoes from the list of all fixed polyominoes Poly_fix.

- public static void enumerate(LinkedList<Polynomio> list, LinkedList<Coords_rep> neighbours, Polynomio parent, LinkedList<Coords_rep> untried, int ox, int oy, int P){}
- Enumerate all fixed polyominoes with size ≤ P by the implemented method (without repetition).
- list is the list which contains all the polyominoes, neighbours contains all the neighbours which are already used, parent is the initial polyomino, untried contains all the points which have not been tried, ox and oy are the coordinates of the origin, P is the size limite.

- public static LinkedList<Polynomio> generate_fix(LinkedList<Polynomio> list, int size){}
- Generate all fixed polyominoes of size size from the list list that we obtained in the last function.

- public static void HI_enumerate(LinkedList<Polynomio> list, LinkedList<Coords_rep> neighbours, Polynomio parent, LinkedList<Coords_rep> untried, int ox, int oy, int P){}
- A modified version of enumerate. To enumerate all fixed polyominoes of type HI prime.

- public static int HI_prime(LinkedList<Polynomio> list, int p){}
- Return the number of fixed polyominoes of size p of type HI prime from a list list of polyominoes.

- public static void A_enumerate(LinkedList<Polynomio> list, LinkedList<Coords_rep> neighbours, Polynomio parent, LinkedList<Coords_rep> untried, int ox, int oy, int P){}
- A modified version of enumerate. To enumerate all fixed polyominoes of type A prime.

- public static int A_prime(LinkedList<Polynomio> list, int p){}
- Return the number of fixed polyominoes of size p of type A prime from a list list of polyominoes.

- public static LinkedList<LinkedList<Polynomio>> exactCover1(LinkedList<Coords_rep> X, LinkedList<Polynomio> C, int oy){}
- Return all the solutions of a exact cover problem of polyominoes. X is the ground set, C is the collection of the subsets of X, oy is the y coordinate of the origin.

- public static LinkedList<LinkedList<Polynomio>> exactCover2_fix(int n, LinkedList<Coords_rep> X, int ox, int oy){}
- Return all the solutions of a exact cover problem of polyominoes. n is the size of fixed polyominoes that is used to cover, X is the ground set (the polyomino to cover), ox and oy are the coordinates of the origin.

- public static LinkedList<LinkedList<Polynomio>> exactCover2_free(int n, LinkedList<Coords_rep> X, int ox, int oy){}
- Return all the solutions of a exact cover problem of polyominoes. n is the size of free polyominoes that is used to cover, X is the ground set (the polyomino to cover), ox and oy are the coordinates of the origin.

- public boolean equals(Object o){}
- Redefine the function equals. when the two polyominoes are the same after some translations, we consider that they are equals.


Dancing_Links
-----------------------------------
You will find one field and one constructor

*************The fields***********

- public Column H: represent the head of the Dancing_Links structure, it is a Column object.

*************Constructors**********

- public Dancing_Links(int [][]M):
- From a matrix of an Exact_cover problem, create a Dancing_Links structure.

****************Methods************

- public PriorityQueue<Column> toPQ():
- Return a PriorityQueue of the type Column, in increasing order of the size of a Column. It helps us to pick up the minimum Column in O(1).

- public LinkedList<LinkedList<Vector<Integer>>> exactCover(PriorityQueue<Column> pc)
Return all the possible solutions in form of a LinkedList, you should create PQ firstly, because it calculate juste once the order.

- public String toString()
Print the result in form of string in order to visualize the dancing_links object.


Exact_Cover
----------------------------------
You will find two fields and one constructor in this class

************The fields***********

- private Vector<Integer> X:Represent the ground set
- private LinkedList<Vector<Integer>> C:Represent the collection of the subset of X

************Constructors**********

- public Exact_Cover(Vector<Integer> X, LinkedList<Vector<Integer>> C)
- Create a Exact_Cover object using X and C.

************Methods***********

- public int[][] toMatrix():
- Return a matrix which represent the Exact Cover problem.

- public Dancing_Links toDL()
- Return a dancint_links structure corespondent to the Exact Cover problem.

- public Vector<Integer> Copy_X()
- Copy_X copies all elements in X, instead of copying simply the address.

- public LinkedList<Vector<Integer>> Copy_C()
- Copy_C copies all elements in C, instead of copying simply the address.

- public LinkedList<LinkedList<Vector<Integer>>> exactCover()
- Return all possible solutions of the Exact Cover problem in form of a LinkedList.

- public static void Afficher(LinkedList<LinkedList<Vector<Integer>>> s)
- Print the result. If there is no solution, print “No exact cover”, if s is null, return “System not initialized”.


Data
-----------------------------
You will find five fields and two constructors in this class.

*************The fields*******

- Data U:Represent the upper element,
- Data D:Represent the down element,
- Data L:Represent the Left element,
- Data R:Represent the right element,
- Column C:Represent it’s Column.

*************Constructors********

- public Data()
- The constructor implicate, it will be called by Column.

- public Data(Column C)
- Given his Column, we create a Data object, without initializing his neighbor.


Column
-------------------------------
You will find two fields and one constructor, it heritage from Data.We also redefine the method compareTo

*********The fields***********

- public  int S: represent the size of this column.
- public  String N: represent the name of this column.

*********Constructors**********

- public Column(String N)
- Initialize the column with the name N and the size 0.

- public void coverColumn()
- Cover a given column.

- public void uncoverColumn()
- Uncover a given column.

- public int compareTo(Object o)
- If the size of this is smaller than the size of o, return -1
- If the size of this equals to the size of o, we compare the name, by the definition, the name is in fact the number, so we compare the by nature order.
- If the size of this is bigger than the size of o, return 1.


Sudoku
------------------------------------
This class solve the sudoku problem. There are two different sudoku problems represented by String in the main function.

*********Methods******************

- public static int[][] toMatrix(String s)
- Transform s into a matrix which represent a exact cover problem.

- public static void afficher(LinkedList<LinkedList<Vector<Integer>>> l)
- Print all results.


