public class Ticket {
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
                        "Name : "+person.getName()+
                        "\nSurname : "+person.getSurname()+
                        "\nEmail : "+person.getEmail()+
                        "\nRow : "+this.row+
                        "\nSeat : "+this.seat+
                        "\nPrice : Rs."+this.price
        );
    }
}
