package redis;

public class InvKeyGenerator {

    public static String genKey(String fpqqlsh){
        return "practice:invoice:"+fpqqlsh;
    }
}
