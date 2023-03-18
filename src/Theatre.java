import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Theatre {
    public static void main(String[] args) {

        int [] row1 = new int[12];
        int [] row2 = new int[16];
        int [] row3 = new int[20];
        int [][] theatre = {row1,row2,row3};
        ArrayList<Ticket> tickets = new ArrayList<>();

        L1:do {
            System.out.print("-------------------------------------------------\n" +
                    "Please select an option:\n" +
                    "1) Buy a ticket\n" +
                    "2) Print seating area\n" +
                    "3) Cancel ticket\n" +
                    "4) List available seats\n" +
                    "5) Save to file\n" +
                    "6) Load from file\n" +
                    "7) Print ticket information and total price\n" +
                    "8) Sort tickets by price\n" +
                    "0) Quit\n" +
                    "-------------------------------------------------\n" +
                    "Enter option: ");
            int option = getOption();
            switch (option) {
                case 0:
                    break L1;
                case 1: buy_ticket(theatre,tickets);break;
                case 2: print_seating_area(theatre);break;
                case 3: cancel_ticket(theatre,tickets);break;
                case 4: show_available(theatre);break;
                case 5: save_toFile(theatre);break;
                case 6: load_fromFile(theatre);break;
                case 7: show_tickets_info(tickets);break;
                case 8: sort_tickets(tickets);
            }
        } while (true);
    }

    private static void sort_tickets(ArrayList<Ticket> tickets) {
        System.out.println("----------Sort Ticket List----------\n");
        ArrayList<Ticket> sort = sort(tickets);
        for (Ticket ticket : sort) {
            ticket.print();
        }
    }

    private static ArrayList<Ticket> sort(ArrayList<Ticket> tickets) {
        ArrayList<Ticket> temp = new ArrayList<>(tickets);
        for (int i = 0; i < temp.size()-1; i++) {
            for (int j = i+1; j<temp.size(); j++){
                if(temp.get(i).price > temp.get(j).price){
                    Ticket swap = temp.get(j);
                    temp.set(j,temp.get(i));
                    temp.set(i,swap);
                }
            }
        }
        return temp;
    }

    private static void show_tickets_info(ArrayList<Ticket> tickets) {
        System.out.println("----------All Tickets Info----------\n");
        double total=0;
        for (Ticket ticket : tickets) {
            ticket.print();
            total += ticket.price;
        }
        System.out.println("Total price : "+total);
    }

    private static void load_fromFile(int[][] theatre) {
        try {
            FileReader file = new FileReader("theatre.txt");
            Scanner reader = new Scanner(file);
            for (int[] row : theatre) {
                String[] strings = reader.nextLine().split(",");
                for (int i = 0; i < strings.length; i++) {
                    row[i]= Integer.parseInt(strings[i]);
                }
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!\nTry again");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void save_toFile(int[][] theatre){
        try {
            FileWriter file = new FileWriter("theatre.txt");
            String data = "";
            System.out.println("hi");
            for (int[] row : theatre) {
                for (int seat : row) {
                    data += seat+",";
                }
                data += "\n";
            }
            file.write(data);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getOption() {
        Scanner input = new Scanner(System.in);
        int option;
            try {
                option = input.nextInt();
                if(option>=0 && option<=8){
                    return option;
                }else {
                    System.out.println("Please enter valid option \nEnter option : ");
                    option = getOption();
                }
            }catch (Exception e){
                System.out.print("Please enter valid option \nEnter option : ");
                option = getOption();
            }
        return option;
    }

    private static void buy_ticket(int[][] theatre, ArrayList<Ticket> tickets) {
        System.out.println("----------Buy Ticket----------\n");
        int rowNumber = getRow();
        int seatNumber = getSeatNumber(rowNumber);
        if(checkSeat( rowNumber,seatNumber,theatre)){
            confirmTicket(rowNumber,seatNumber,theatre,tickets);
        }
    }

    private static int getRow() {
        Scanner input = new Scanner(System.in);
        int rowNumber;
                System.out.print("Enter row number (1 to 3) : ");
                try {
                    rowNumber = input.nextInt();
                    if (rowNumber > 0 && rowNumber < 4) {
                        return rowNumber;
                    }else {
                        System.out.println("Please Enter a valid Number! (1 to 3)");
                        rowNumber = getRow();
                    }

                } catch (Exception e) {
                    System.out.println("Please Enter a Number! ");
                    rowNumber = getRow();
                }
                return rowNumber;
    }
    private static int getSeatNumber(int rowNumber) {
        Scanner input = new Scanner(System.in);
        int seatNumber;
            System.out.print("Enter seat number : ");
            try {
                seatNumber = input.nextInt();
                switch (rowNumber){
                    case 1:{
                        if(seatNumber>12 || seatNumber<=0){
                            System.out.println("out of range");
                            seatNumber = getSeatNumber(rowNumber);
                        }
                    }
                    case 2: {
                        if (seatNumber > 16 || seatNumber <= 0) {
                            System.out.println("out of range");
                            seatNumber = getSeatNumber(rowNumber);
                        }
                    }
                    case 3: {
                        if (seatNumber > 20 || seatNumber <= 0) {
                            System.out.println("out of range");
                            seatNumber = getSeatNumber(rowNumber);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Please Enter a Number! ");
                seatNumber = getSeatNumber(rowNumber);
            }
        return seatNumber;
    }

    private static boolean checkSeat(int rowNumber, int seatNumber, int[][] theatre) {
        return theatre[rowNumber - 1][seatNumber - 1] == 0;
    }

    private static void confirmTicket(int rowNumber, int seatNumber, int[][] theatre, ArrayList<Ticket> tickets) {
        Scanner input = new Scanner(System.in);
        while (true){
            System.out.print("Seat available \nDo you want to buy the ticket (y/n) : ");
            String option = input.nextLine();
            if(option.equalsIgnoreCase("y")){
                Person person = getInformation();
                System.out.print("Price : ");
                double price = input.nextDouble();
                Ticket ticket = new Ticket(rowNumber, seatNumber, price, person);
                tickets.add(ticket);
                theatre[rowNumber-1][seatNumber-1]= 1;
                System.out.println("Success! Thank You!");
                break;
            }else if(option.equalsIgnoreCase("n")) {
                System.out.println("Canceled! ");
                break;
            }else {
                System.out.println("please enter valid option!");
            }
        }
        quite();
    }

    private static Person getInformation() {
        Scanner input = new Scanner(System.in);
        System.out.print("Name : ");
        String name = input.next();
        System.out.print("Surname : ");
        String surname = input.next();
        System.out.print("Email : ");
        String email = input.next();
        return new Person(name,surname,email);
    }

    private static void quite(){
        Scanner input = new Scanner(System.in);
        while (true){
            System.out.print("Enter \" q \" to return to main menu : ");
            String option = input.nextLine();
            if(option.equalsIgnoreCase("q")){
                break;
            }
        }
    }
    private static void print_seating_area(int[][] theatre) {
        System.out.println(" \t\t\t   ***********\n" +
                           " \t\t\t   *  STAGE  *\n" +
                           " \t\t\t   ***********");
        int space = 4;
        for (int[] row : theatre) {
            for (int j = 0; j < space; j++) {
                System.out.print("  ");
            }
            for (int j = 0; j < row.length; j++) {
                if (row.length / 2 == j) {
                    System.out.print("  ");
                }
                if (row[j] == 0) {
                    System.out.print("O ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
            space -= 2;
        }
        System.out.println();
        quite();
    }

    private static void cancel_ticket(int[][] theatre, ArrayList<Ticket> tickets) {
        System.out.println("----------Cancel Ticket----------\n");
        Scanner input = new Scanner(System.in);
        int rowNumber = getRow();
        int seatNumber = getSeatNumber(rowNumber);
            do {
                if(!checkSeat(rowNumber,seatNumber,theatre)){
                    System.out.print("Do you want to cancel ticket (y/n) : ");
                    String option = input.nextLine();
                    if(option.equalsIgnoreCase("y")){
                        Person information = getInformation();
                        if(checkInformation(information,tickets)){
                            theatre[rowNumber-1][seatNumber-1] = 0;
                            System.out.println("Ticket canceled!");
                        }else {
                            System.out.println("User information not correct!\nPlease try again");
                        }
                        break;
                    }else if(option.equalsIgnoreCase("n")){
                        System.out.println("The ticket didn't canceled");
                        break;
                    }else {
                        System.out.println("Enter valid option!");
                    }
                }else {
                    System.out.println("Seat not occupied.");
                    break;
                }
            }while (true);
            quite();
    }

    private static boolean checkInformation(Person information, ArrayList<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            if(ticket.person.getEmail().equalsIgnoreCase(information.getEmail())){
                tickets.remove(ticket);
                return true;
            }
        }
        return false;
    }

    private static void show_available(int[][] theatre) {
        System.out.println("----------Available seats----------\n");
        for (int i = 0;  i<theatre.length; i++) {
            System.out.print("Seats available in row "+(i+1)+" : ");
            for (int j = 0; j<theatre[i].length; j++) {
                if(theatre[i][j]==0){
                    System.out.print(j+1+", ");
                }
            }
            System.out.println("\b\b\n");
        }
        quite();
    }
}
