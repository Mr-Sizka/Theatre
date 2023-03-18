public class Ticket{
    int row;
    int seat;
    double price;
    Person person;

    public Ticket(int row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public void print(){
        System.out.println(
                        "Ticket{" +
                        "Name : "+person.getName()+
                        ", Surname : "+person.getSurname()+
                        ", Email : "+person.getEmail()+
                        ", Row : "+this.row+
                        ", Seat : "+this.seat+
                        " ,Price : Rs."+this.price+
                        " }"
        );
    }
}
