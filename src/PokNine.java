import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class PokNine {

	public final static String initialDeck[] = 
	{"01 Podum","02 Podum","03 Podum","04 Podum","05 Podum","06 Podum","07 Podum","08 Podum","09 Podum","10 Podum",
	 "J  Podum","Q  Podum","K  Podum","01 Huajai","02 Huajai","03 Huajai","04 Huajai","05 Huajai","06 Huajai","07 Huajai",
	 "08 Huajai","09 Huajai","10 Huajai","J  Huajai","Q  Huajai","K  Huajai","01 Lamtud","02 Lamtud","03 Lamtud","04 Lamtud",
	 "05 Lamtud","06 Lamtud","07 Lamtud","08 Lamtud","09 Lamtud","10 Lamtud","J  Lamtud","Q  Lamtud","K  Lamtud","01 Dokjik",
	 "02 Dokjik","03 Dokjik","04 Dokjik","05 Dokjik","06 Dokjik","07 Dokjik","08 Dokjik","09 Dokjik","10 Dokjik","J  Dokjik",
	 "Q  Dokjik","K  Dokjik"};
	
	static int bitPlayer = 100;
	static int bitBot = 100;
	static int playBit = 0;
	
	public static void main(String args[]) throws InterruptedException{
		
		boolean isOver = false;
		String deck[] = new String[initialDeck.length];
		String playerCard[] = new String[3];
		String botCard[] = new String[3];
		Scanner sc = new Scanner(System.in);
		while(!isOver){
			
			System.arraycopy( initialDeck, 0, deck, 0, initialDeck.length );
			playerCard = new String[3];
			botCard = new String[3];
			playBit = playerBit(bitPlayer,bitBot);
			shuffle(deck);
			int whoStart = giveCard(deck, playerCard, botCard);
			showCard(playerCard);
			int result;
			if(checkPok(playerCard) || checkPok(botCard)){
				System.out.println("!!!!!!!!!POK!!!!!!!!!!");
				result = checkResult(playerCard,botCard);
			}else{
				wantCard(deck,playerCard,botCard,whoStart);
				result = checkResult(playerCard,botCard);
			}
			if(result == 1){
				bitPlayer += playBit*checkDeng(playerCard);
				bitBot -= playBit*checkDeng(playerCard);
				System.out.println("You gain : " + playBit*checkDeng(playerCard));
			}else if(result == -1){
				bitPlayer -= playBit*checkDeng(botCard);
				bitBot += playBit*checkDeng(botCard);
				System.out.println("You loss : " + playBit*checkDeng(botCard));
			}
			isOver = isOver();
			
		}
	}
	
	public static void wantCard(String deck[],String playerCard[],String botCard[],int whoStart){
		Scanner sc = new Scanner(System.in);
		System.out.println("Do you want a card?(Y/N)");
		if(sc.nextLine().equalsIgnoreCase("Y")){
			if(whoStart == 1){
				playerCard[2] = deck[4];
			}else if(whoStart == -1){
				playerCard[2] = deck[5];
			}
		}
		if(combineValue(botCard) < 5){
			if(whoStart == -1){
				botCard[2] = deck[4];
			}else if(whoStart == 1){
				botCard[2] = deck[5];
			}
		}
			
	}
	
	public static int checkResult(String[] playerCard, String[] botCard) throws InterruptedException {
		System.out.println("---CHECK RESULT---");
		TimeUnit.SECONDS.sleep(1);
		showCard(playerCard);
		TimeUnit.SECONDS.sleep(1);
		showBotCard(botCard);
		TimeUnit.SECONDS.sleep(1);
		if(combineValue(playerCard)>combineValue(botCard) || (checkYellow(playerCard) && !checkYellow(botCard)))
			return 1;
		else if(combineValue(playerCard)<combineValue(botCard) || (checkYellow(botCard) && !checkYellow(playerCard)))
			return -1;
		else
			return 0;
	}

	public static String[] shuffle(String deck[]){
		Random rnd = new Random();
		for(int i = 0 ; i < deck.length ; i++){
			int index1 = rnd.nextInt(deck.length);
			int index2 = rnd.nextInt(deck.length);
			String temp = deck[index2];
			deck[index2] = deck[index1];
			deck[index1] = temp;
		}
		return deck;
	}
	
	public static int playerBit(int playerBalance,int botBalance){
		Scanner sc = new Scanner(System.in);
		System.out.println("\nHow many bit do you want to play? \nYour total balance : " + playerBalance + "\n Bot balance : "+botBalance);
		int bit = 9999;
		while(bit > playerBalance){
			try{
				bit = sc.nextInt();
				if(bit > playerBalance)
					System.out.println("Please enter again your bit is more than your balance");
			}catch(Exception ex){
				System.out.println("Please input number");
				sc.next();
			}
		}
		return bit;
	}
	
	public static boolean isOver(){
		if(bitPlayer <=0 || bitBot <=0){
			Scanner sc = new Scanner(System.in);
			if(bitPlayer > bitBot)
				System.out.println("Player Win");
			else
				System.out.println("Bot Win");
			System.out.println("Do you want to play again?(Y/N)");
			String ans = sc.nextLine();
			if(ans.equalsIgnoreCase("Y")){
				bitPlayer = 100;
				bitBot = 100;
				return false;
			}
			else
				return true;
		}
		return false;
	}
	
	public static int giveCard(String[] deck,String[] playerCard, String[] botCard){
		if(Math.random() < 0.5){
			playerCard[0] = deck[0];
			botCard[0] = deck[1];
			playerCard[1] = deck[2];
			botCard[1] = deck[3];
			return 1;
		}else{
			botCard[0] = deck[0];
			playerCard[0] = deck[1];
			botCard[1] = deck[2];
			playerCard[1] = deck[3];
			return -1;
		}
	}
	
	public static void showCard(String[] card){
		System.out.print("Your card : ");
		for(int i = 0 ; i < card.length ; i++){
			if(card[i]!=null)
			System.out.print(card[i] + " ");
		}
		System.out.println();
	}
	public static void showBotCard(String[] card){
		System.out.print("Bot card : ");
		for(int i = 0 ; i < card.length ; i++){
			if(card[i]!=null)
			System.out.print(card[i] + " ");
		}
		System.out.println();
	}
	
	public static int combineValue(String card[]){
		return card[2] == null ? (cardToValue(card[0]) + cardToValue(card[1]))%10 : (cardToValue(card[0]) + cardToValue(card[1]) + cardToValue(card[2]))%10;
	}
	
	public static int cardToValue(String card){
		if(card.substring(0, 1).equals("J") || card.substring(0, 1).equals("Q") || card.substring(0, 1).equals("K") || card.substring(0, 2) == "10"){
			return 10;
		}else{
			return Integer.parseInt(card.substring(0, 2));
		}
	}
	
	public static int checkDeng(String card[]){
		if(card[2] != null){
			if( ((card[0].substring(3, 4).equals(card[1].substring(3, 4)) && card[1].substring(3, 4).equals(card[2].substring(3, 4)) &&
				checkYellow(card))))
				return 5;
			if( card[0].substring(3, 4).equals(card[1].substring(3, 4)) && card[1].substring(3, 4).equals(card[2].substring(3, 4)) ||
			    card[0].substring(0, 2).equals(card[1].substring(0, 2)) && card[1].substring(0, 2).equals(card[2].substring(0, 2)) ||
			   checkYellow(card)){
				return 3;
			}
		}else{
			if(card[0].substring(3, 4).equals(card[1].substring(3, 4)) || card[0].substring(0, 2).equals(card[1].substring(0, 2))){
				return 2;
			}
		}
		return 1;
	}
	
	public static boolean checkPok(String card[]){
		if(card[2] != null)
			return false;
		else
			if(combineValue(card) == 9 || combineValue(card) == 8)
				return true;
			else return false;
	}
	
	public static boolean checkYellow(String card[]){
		if(card[2] != null)
			if( (card[0].contains("J") || card[0].contains("Q") || card[0].contains("K")) &&
				(card[1].contains("J") || card[1].contains("Q") || card[1].contains("K")) &&
				(card[2].contains("J") || card[2].contains("Q") || card[2].contains("K")))
				return true;
		return false;
	}
	
}
