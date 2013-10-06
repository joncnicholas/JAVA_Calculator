import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
/**
  * This is the only class that this calculator has and everything is controlled within
  * this class. This class extends the JFrame and implements both the ActionListener and the KeyListener. 
  * This class requires both interfaces because the program requires the ability to recognise events and needs the ability to 
  * realize that the end user has striked a keystoke. 
  * This program contains various methods and a single constructor. The constructor does most of the work and in general builds
  * the whole gui by creating all the buttons via a 2 dimensional array, applies all the colour effects and layout managers. 
  * The constructor builds the GUI through a recursive call on method named createCalc() which contains the main body of the code
  * related to constructing frame such as the: frame size, layout manager, textfield for the display etc. 
  * Due to the class extending the Jframe and implementing the interface of the KeyListener; there are particular methods
  * that are essential to the program functioning fully. These methods are the actionPerformed(), keyRealeased(), keytyped() and 
  * keyPresed(). The actionPerformed() contains the main bulk of the code related to the standard and scientif calculations that
  * are carried out by the calculator. Whereas, the keyPressed(), keyTyped() and keyReleased() are essential methods relating to the
  * KeyListener interface that contains code relating to the end user events or keystrokes. 
  * There are a great variety of variables used within this programe most of which are global variables and some being 
  * local variables. Many are objects relating to the construction of the calculator such as: textfield, JPanel buttongrid and
  * Menu items (File and Help). The great portion of the rest remaining are used in the action performed in aid of the calculations.
  * This would generally be operators or interger/double variables.     
  */
public class Calculator extends JFrame implements ActionListener,KeyListener {
	JPanel 		bGrid = new JPanel(new GridLayout(6,4,4,4));
	Font 		font   = new Font("SansSerif", Font.BOLD, 32);
	JButton		keys[][];
	JTextField	display = new JTextField("0");
	String		calc;
	double		accum;
	int			op;
	JMenu menuFile, menuHelp;
	JMenuItem menuitemExit, menuitemAbout;
	boolean     newNumb = true;
	final int   PLUS 	= 1;
	final int   MINUS 	= 2;
	final int   TIMES 	= 3;
	final int   DIVIDE 	= 4;
	final int 	SIN 	= 5;
	final int 	COS 	= 6;
	final int	CE		= 7;
	final int 	SQRT	= 8;
	final int   CONVERSION = 9;
	String action;
	double secondNumber;
	/**
    * This is my createCalc() method in which the bulk of the code relating to the creation of the GUI is situated.
    * In this method i create my buttons via a 2 dimesional array and manage how all my buttons are layed out in my 
    * frame via the 2 for loops just below the listing of all the buttons. In this method i do all the GUI basics and set were all
    * my components or widgets that the end user interacts with such as the display,and the buttons are placed via the content pane
    * and layout manager.
    * The basics of setting the frame size, colours, font (the font is declared as a global variable) and visibility are also set in this method.  
    */
	public void createCalc(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("G52GUI Calculator");
		int			i, j;
		String 		action;
		// create the keys
		keys = new JButton[6][5];
		keys[0][0] = new JButton("7");
		keys[0][1] = new JButton("8");
		keys[0][2] = new JButton("9");
		keys[0][3] = new JButton("+");
		keys[1][0] = new JButton("4");
		keys[1][1] = new JButton("5");
		keys[1][2] = new JButton("6");
		keys[1][3] = new JButton("-");
		keys[2][0] = new JButton("1");
		keys[2][1] = new JButton("2");
		keys[2][2] = new JButton("3");
		keys[2][3] = new JButton("x");
		keys[3][0] = new JButton("0");
		keys[3][1] = new JButton(".");
		keys[3][2] = new JButton("=");
		keys[3][3] = new JButton("/");
		keys[4][0] = new JButton("sin");
		keys[4][1] = new JButton("cos");
		keys[4][2] = new JButton("tan");
		keys[4][3] = new JButton("sqrt");
		keys[5][0] = new JButton("CE");
		keys[5][1] = new JButton("%");
		keys[5][2] = new JButton("");
		keys[5][3] = new JButton("");
		
		
		//i = rows j =colums
		for(i = 0; i < 6; i++) {
			for(j = 0; j < 4; j++) {
				keys[i][j].setFont(font);
				action = (new Integer(i)).toString() + (new Integer(j)).toString();
				keys[i][j].setActionCommand(action);
				keys[i][j].addActionListener(this);
				bGrid.add(keys[i][j]);
			}//end of for
			
		}//end of for
		
		getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(bGrid, BorderLayout.CENTER);
		display.setHorizontalAlignment(JTextField.RIGHT);
		display.setFont(font);
		display.setEditable(false);
		display.setForeground(Color.green);
		display.setBackground(Color.black);
		
		this.getContentPane().add(display, BorderLayout.NORTH);
		setSize(430,430);
		setVisible(true);
		this.addKeyListener(this);
		display.addKeyListener(this);
		display.requestFocus();
		calc = "";
		accum = 0;
		
		
		}//method
	/**
    * This is my programs constructor. This special method controls most of the program in particular to it creating the GUI and all its
    * components. A recursive call is made to createCalc towards the end. I felt that splitting the code up would have been beneficial
    * in organisation as it has made it easier to read. 
    * In the Constructor i have created the Menu buttons being the Help and File buttons. Also, the menu items have also been created. These
    * include Exit which is located under File and About which is located under Help. The Jmenu buttons and Jmenu items are defined
    * as global variables. In the constructor, after i have intialized and created the menu bar items/buttons, i provide them
    * with mnemonics and shortcuts (setAccelerator()) which makes the calculator more efficient as it saves time. All one has to
    * do is drag the mouse over the 'File' or 'Help' menu buttons and click their shotcuts and the program will either exit (Ctrl + x)
    * or will access the menu item 'About' (Ctrl + a). I state what is said in the 'About' section using the JOptionPane utility.
    * Once the constructor has created the Menu bar and its items and submenus, it carries out the recursive call and creates the rest of the
    * GUI for the calculator.
    */	
	public Calculator(){
		
		//Creating the file button for the Menu bar	
		menuFile = new JMenu("File");
		menuFile.addActionListener(this);
		menuFile.setMnemonic(KeyEvent.VK_F);
		menuitemExit = new JMenuItem("Exit");
		menuitemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, 
								ActionEvent.CTRL_MASK));
							
		menuitemExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a){
				System.exit(0);
			}
		});
		
		menuFile.add(menuitemExit);
		//Creating the Help Button for the Menu bar
		menuHelp = new JMenu("Help");
		menuHelp.addActionListener(this);
		menuHelp.setMnemonic(KeyEvent.VK_H);
		menuitemAbout = new JMenuItem("About Calculator");
		menuHelp.add(menuitemAbout);
		menuitemAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 
								ActionEvent.CTRL_MASK));
		
		
		menuitemAbout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent b){
			JOptionPane.showMessageDialog(null, "Scientific Calculator version 1 by Jonathan Nicholas");
				
			}
		});	
			
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menuFile);
		menuBar.add(menuHelp);
		setJMenuBar(menuBar);
		
		createCalc();
	
	}//end of constructor

	
	/**
    * This is the actionPerformed() method, in which all the calculations are carried out and numbers are displayed in the display.
    * The logic in which my calculations work is basically whenever an the end user clicks the operator the previously entered
    * number is stored in a double variable known as 'accum', which is short for accumulator and then once the equals is pressed,
    * only then does the program make its calculations. This is how it works for all my basic claclulator functions. However, the logic 
    * into my scientific functions is slightly different. This is because when you press the sin, cos, tan and conversion buttons on a standard calculator
    * on your PC the answer appears immediately and there is no use of the equals operator. As a result, using Math.tan, Math.cos, math.sin and
    * Math.sqrt for an added optional function of a square root; i created different variables which were set as doubles once the Math function
    * was used on these variables (e.g sinCalc, cosCalc, tanCalc and sqrt), i displayed the answer directly onto the display after the value has been returned.
	* My Conversion button works slightly different as there is no Math function for percentage or conversion operator. I based my conversion button 
    * logic on the method used by all standard calculators that you may find on the PC. The logic behind it is: for example: what is 10% of 50. The answer is 5. In my calculator
    * i would press my first number then multiply it by the desired percentage and then click the '%' button which will leave you with the answer. 
    *
    * @param e This is a java.awt.event.ActionEvent, which is taken from the package imported from the top.
    */
	public void actionPerformed(java.awt.event.ActionEvent e) {
		
		String pressed = e.getActionCommand();
		
		double add1 = 0;
		double add2 = 0;
		double ans = 0;
		
		//The Number Seven
		if(pressed.equals("00")){
			if(newNumb == true) {calc = ""; accum = 0.0; newNumb = false;}
				calc = calc + "7";
		}//end of if
		
		//The Number 8
		if(pressed.equals("01")){
			if(newNumb == true) {calc = ""; accum = 0.0;
					newNumb = false;}
					calc = calc + "8";	
		}//end of if
		
		//The Number Nine
		if(pressed.equals("02")){
			if(newNumb == true) {calc = ""; accum = 0.0;
				newNumb = false;}
				calc = calc + "9";
				}//end of if
		
		//The Plus Operator
		if(pressed.equals("03")){
			newNumb = false;
			accum = (new Double(calc)).doubleValue();
			op = PLUS;
			calc = "";
		}//end of if
		
		//The Number 4	
		if(pressed.equals("10")){
			if(newNumb == true) {calc = ""; accum = 0.0;newNumb = false;}
				calc = calc + "4";
					}//end of if		
		
		//The Number 5
		if(pressed.equals("11")){
			if(newNumb == true) {calc = ""; accum = 0.0;newNumb = false;}
				calc = calc + "5";
						}//end of if
		
		//The Number 6
		if(pressed.equals("12")){
			if(newNumb == true) {calc = ""; accum = 0.0;newNumb = false;}
				calc = calc + "6";
							}//end of if
		
		//The Minus Operator
		if(pressed.equals("13")){
			newNumb = false;
			accum = (new Double(calc)).doubleValue();
			op = MINUS;
			calc = "";
						}//end of if
		
		//The Number one 
		if(pressed.equals("20")){
			if(newNumb == true) {calc = "";accum = 0.0; newNumb = false;}
				calc = calc + "1";
					}//end of if
		
		//The number Two 
		if(pressed.equals("21")){
			if(newNumb == true) {calc = "";accum = 0.0; newNumb = false;}
				calc = calc + "2";
						}//end of if
		
		//The Number Three 
			if(pressed.equals("22")){
			if(newNumb == true) {calc = ""; accum = 0.0;newNumb = false;}
				calc = calc + "3";
						}//end of if
		
		//Times Button
		if(pressed.equals("23")){
			newNumb = false;
			accum = (new Double(calc)).doubleValue();
			op = TIMES;
			calc = "";
								}//end of if
		
		//The Number Zero 
		if(pressed.equals("30")){
			if(newNumb== true) {calc = ""; accum = 0.0;newNumb = false;}
				calc = calc + "0";
								}//end of if
		
		//Decimal Point 
		if(pressed.equals("31")){
			if(newNumb == true) {calc = "";accum = 0.0;newNumb = false;}
				calc = calc + ".";
								}//end of if
		
		//The Equals operator
		if(pressed.equals("32")){
			ans = (new Double(calc)).doubleValue();
			if(op == PLUS) accum += ans;
				else if(op == MINUS) accum -= ans;
					else if(op == TIMES) accum *= ans;
						else if(op == DIVIDE) accum /= ans;
							calc = (new Double(accum)).toString();
							newNumb = true;
								}//end of if
	
		//The Divide Operator
		if(pressed.equals("33")){
			newNumb = false;
			accum = (new Double(calc)).doubleValue();
			op = DIVIDE;
			calc = "";
																	
								}//end of if
		
		//The Sin Operator															
		if(pressed.equals("40")){
			newNumb = false;
			double sinCalc  = (new Double(calc)).doubleValue();
			sinCalc =  Math.sin(sinCalc);
			display.setText(String.valueOf(sinCalc));
			return;
									}//if
		
		//The Cos Operator								
		if(pressed.equals("41")){
			newNumb = false;
			double cosCalc  = (new 
			Double(calc)).doubleValue();
			cosCalc =  Math.cos(cosCalc);
			display.setText(String.
			valueOf(cosCalc));
			return;
					}//if
		
		//The Tan Operator			
		if(pressed.equals("42")){
			newNumb = false;
			double tanCalc  = (new 
			Double(calc)).doubleValue();
			tanCalc =  Math.tan(tanCalc);
			display.setText(String.
			valueOf(tanCalc));
			return;
		}	
		
		//The Square root 											
		if(pressed.equals("43")){
			newNumb = false;
			double sqrt  = (new Double(calc)).doubleValue();
			sqrt =  Math.sqrt(sqrt);
			display.setText(String.valueOf(sqrt));
			return;
								}//end of if
		
		//Clear All Button																			
		if(pressed.equals("50")){
			newNumb = true;
			op = CE;
			calc = "";	
			}//if
		
		//Conversion or percentage operator		
		if(pressed.equals("51")){
			ans = (new Double(calc)).doubleValue();	
			accum *= ans;
			accum/=100;
			calc = (new Double(accum)).toString();
			newNumb = true;
			//return;	
		}//if										
		System.out.println("accumulator: " +accum);
		System.out.println("answer: "+ans);
		System.out.println("Second Number: "+secondNumber);
			display.setText(calc);										
				display.requestFocus();														
																		
			}//end of method
	
	/**
    * This is an essential method required by the KeyListener interface known as the keyReleased() method. It
    * handles the key typed event.
    *
    * @param e A KeyEvent paramater e is passed to this method
    */  
	public void keyTyped(KeyEvent e) {
	
        
    }//method

    /**
    * This is an essential method required by the KeyListener interface known as the keyReleased() method. It
    * handles the key-pressed event.
    * This method runs all the keyboard functionality for the calculator. I am creating new action events and linking them 
    * to my 2 dimensional array buttons. I also linked the enter button with my equals button allowing for the full ability to 
    * complete sums using the keyboard. There are keylisteners attached to the JFrame which is tested by the system.out.println().    
    *
    * @param e A KeyEvent paramater e is passed to this method
    */   
    public void keyPressed(KeyEvent e) {
		char x;
		 x = e.getKeyChar();
		 System.out.println(x);
		
		if(x == '.') actionPerformed (new ActionEvent(keys[3][1],31, "31"));
    	if(x == '+') actionPerformed (new ActionEvent(keys[0][3],03, "03"));
    	if(x == '-') actionPerformed (new ActionEvent(keys[1][3],13, "13"));
    	if(x == '/') actionPerformed (new ActionEvent(keys[3][3],33, "33"));
		if(x == '*') actionPerformed (new ActionEvent(keys[2][3],23, "23"));   
  		if(x == '0') actionPerformed (new ActionEvent(keys[3][0],30, "30"));
    	if(x == '1') actionPerformed (new ActionEvent(keys[2][0],20, "20"));
    	if(x == '2') actionPerformed (new ActionEvent(keys[2][1],21, "21"));
    	if(x == '3') actionPerformed (new ActionEvent(keys[2][2],22, "22"));
    	if(x == '4') actionPerformed (new ActionEvent(keys[1][0],10, "10"));
    	if(x == '5') actionPerformed (new ActionEvent(keys[1][1],11, "11"));
    	if(x == '6') actionPerformed (new ActionEvent(keys[1][2],12, "12"));
    	if(x == '7') actionPerformed (new ActionEvent(keys[0][0],00, "00"));
    	if(x == '8') actionPerformed (new ActionEvent(keys[0][1],01, "01"));
    	if(x == '9') actionPerformed (new ActionEvent(keys[0][2],02, "02"));
    	if(e.getKeyCode() == 10) actionPerformed (new ActionEvent(keys[3][2],32, "32"));
    	
    }//method;
	
	
	/**
    * This is an essential method required by the KeyListener interface known as the keyReleased() method. It
    * handles the key-released event.
    *
    * @param e A KeyEvent paramater e is passed to this method
    */ 
    public void keyReleased(KeyEvent e) {
	

	}//method														
	
    /**
    * This is my main method. The only thing this does is to call a new instance of the caculator. As the compiler always compiles code
    * from the main method in a chrolological order; once a new instance of the calculator class is made then my constructor is called in which
    * it does all the work for creating the calculator GUI.
    */   
	public static void main(String args[]) {
		Calculator sums = new Calculator();
		
			}//main method
	
}//end of class