package com.test.rocketmiles;

import java.util.Scanner;

public class CashRegister {

	private static int[] registerDenomination = { 20, 10, 5, 2, 1 };
	private static int[] registerSlots = { 1, 2, 3, 4, 5 };

	public static void showContents(int[] slots) {
		System.out.println("$" + getTotalCash(slots) + ": " + slots[0] + " | " + slots[1] + " | " + slots[2] + " | " + slots[3] + " | " + slots[4]);
	}

	public static int getTotalCash(int[] registerSlots) {
		int total = 0;
		for (int i = 0; i < 5; i++) {
			total += registerSlots[i] * registerDenomination[i];
		}
		return total;
	}

	public static void putMoney(String cash) {
		cash = cash.substring(4);// put__
		String[] cashes = cash.split(" ");
		try {
			if (cashes.length == 5) {
// start with 0 to 5
				for (int i = 0; i < 5; i++) {
					int val = Integer.parseInt(cashes[i]);
					registerSlots[i] = registerSlots[i] + val;
				}
			} else {
				System.err.println(
						"Wrong Input! Enter 5 kinds of bills, if no bill denomination use '0', example 1 0 0 1 0");
			}
		} catch (Exception e) {
			System.err.println("Wrong Input!");
		}
		showContents(registerSlots);
	}

	public static void takeMoney(String cash) {
		cash = cash.substring(5);// take_
		String[] cashes = cash.split(" ");
		try {
// start with 0 to 5
			int[] regslots2 = registerSlots.clone();
			boolean noIssue = true; 
			if (cashes.length == 5) {
				for (int i = 0; i < 5; i++) {
					int val = Integer.parseInt(cashes[i]);
					if(regslots2[i] - val>=0) {
						regslots2[i] = regslots2[i] - val;
					}else {
						System.err.println("Sorry");
						noIssue = false;
						break;
					}
				}
				if(noIssue) {
					for(int i = 0 ; i < 5 ; i++) {
						registerSlots[i] = regslots2[i];
					}
				}
			} else {
				System.err.println(
						"Wrong Input! Enter 5 kinds of bills, if no bill denomination use '0', example 1 0 0 1 0");
			}
		} catch (Exception e) {
			System.err.println("Wrong Input!");
		}
		
		
		showContents(registerSlots);
	}

	public static void giveChange(String change) {
		String[] mychange = change.split(" ");
		int[] changeSlots = { 0, 0, 0, 0, 0 };
		int amt = 0;
		try {
			amt = Integer.parseInt(mychange[1]);// change amount
		} catch (Exception e) {
			System.err.println("Wrong Input!");
		}
//System.out.println(getTotalCash(registerSlots));
		if (getTotalCash(registerSlots) < amt) {
			System.out.println("Sorry!");
		} else {
			int[] regslots2 = registerSlots.clone();
			int counterLoop = 0;
			int origAmt = amt;
			boolean hasMoney = true;
			do {
				for (int i = 0; i < 5; i++) {
					if (regslots2[i] == 0 || amt == 0) {
						continue;
					}
//System.out.println("AMT: " + amt+" % "+ registerDenomination[i] + " : " +(amt%registerDenomination[i]));

					int newamtmod = amt % registerDenomination[i];
					int newamtdiv = amt / registerDenomination[i];
//System.out.println(newamtmod + " " +newamtdiv);

					if (newamtmod == 0 && newamtdiv > 0) {
//return the
						if (regslots2[i] >= newamtdiv) {
							regslots2[i] = regslots2[i] - newamtdiv;
							amt -= registerDenomination[i]*newamtdiv;
							changeSlots[i] = changeSlots[i] + newamtdiv;// get the $bill
							continue;
						} else {
////grab 1
							regslots2[i] = regslots2[i] - 1;
							amt -= registerDenomination[i];
							changeSlots[i] = changeSlots[i] + 1;// get 1 $bill
							continue;
						}
					}
					
					if (regslots2[i] > 0 && newamtdiv>0) {
						regslots2[i] = regslots2[i] - 1;
						amt -= registerDenomination[i];
						changeSlots[i] = changeSlots[i] + 1;// get the $bill
						//continue;
					} 

				}
//				System.out.println(amt);
				
				if(origAmt!=amt) {
					origAmt = amt;
				}else {
					counterLoop++;
				}
				
				if(counterLoop>2) {
					hasMoney = false;
				}
			} while (amt > 0 && hasMoney);
			
			if(amt == 0) {
				registerSlots[0] = regslots2[0];
				registerSlots[1] = regslots2[1];
				registerSlots[2] = regslots2[2];
				registerSlots[3] = regslots2[3];
				registerSlots[4] = regslots2[4];	
				
				System.out.print("Your change : ");
				showContents(changeSlots);
			}else {
				System.out.println("Sorry!");
			}
		}
		

		
	}

	public static void command(String s) {
		String cmds[] = s.split(" ");
		String cmd = cmds[0];
		switch (cmd) {
		case "show":
			showContents(registerSlots);
			break;
		case "put":
			putMoney(s);
			break;
		case "take":
			takeMoney(s);
			break;
		case "change":
			giveChange(s);
			break;
		case "quit":
			System.out.println("Bye!");
			break;
		default:
			System.out.println("Cannot recognize input: try 'show'");
			break;
		}
	}

	public static void main(String[] args) {
// Using Scanner for Getting Input from User
		System.out.println("Ready Cash Register...");
		String input = "";
		Scanner in = new Scanner(System.in);
		while (!(input = in.nextLine()).equals("quit")) {
			command(input);
		}
		System.out.println("Bye!");
	}
}
