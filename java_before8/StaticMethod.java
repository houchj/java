interface StaticMethodInterface {

    public static String producer() {
        return "from static";
    }

    default String getOverview() {
        return "ATV made by " + producer();
    }
}

class ImplStatic implements StaticMethodInterface {

}

public class StaticMethod {

    public static void main(String[] args) {
        System.out.println(StaticMethodInterface.producer());
        ImplStatic implStatic = new ImplStatic();
        System.out.println(implStatic.getOverview());

    }
}
