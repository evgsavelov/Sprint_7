import java.util.Random;

public class CourierGenerator {

    public static Courier getDefault(){
        //рандомно создать креды
        return new Courier("merry10", "452", "ghtj");
    }

    public static Courier getDefaultWithTheSameLogin() {
        Random ran = new Random();
        int loginLength = 4;
        char data = ' ';
        String login = "";

        for (int i = 0; i <= loginLength; i++) {
            data = (char) (ran.nextInt(25) + 97);
            login = data + login;
        }

        return new Courier(getDefault().getLogin(), login + "p", login + "fN");
    }



    public static Courier getRandom() {
        Random ran = new Random();
        int loginLength = 4;
        char data = ' ';
        String login = "";

        for (int i = 0; i <= loginLength; i++) {
            data = (char) (ran.nextInt(25) + 97);
            login = data + login;
        }

        return new Courier(login, login + "p", login + "fN");
    }

    public static Courier getRandomOnlyLogin() {
        Random ran = new Random();
        int loginLength = 4;
        char data = ' ';
        String login = "";

        for (int i = 0; i <= loginLength; i++) {
            data = (char) (ran.nextInt(25) + 97);
            login = data + login;
        }

        return new Courier(login, "","");
    }

    public static Courier getRandomOnlyPassword() {
        Random ran = new Random();
        int loginLength = 4;
        char data = ' ';
        String login = "";

        for (int i = 0; i <= loginLength; i++) {
            data = (char) (ran.nextInt(25) + 97);
            login = data + login;
        }

        return new Courier("",login, "");
    }

    public static Courier getRandomWithoutFirstName() {
        Random ran = new Random();
        int loginLength = 4;
        char data = ' ';
        String login = "";

        for (int i = 0; i <= loginLength; i++) {
            data = (char) (ran.nextInt(25) + 97);
            login = data + login;
        }

        return new Courier(login,login + "p", "");
    }
}
