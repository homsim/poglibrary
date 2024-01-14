package db_conn;

import java.util.StringTokenizer;
import java.lang.NoSuchMethodException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Interpreter {
    // utilty class to interpret the frontend call string
    private final static String DELIM = "&";
    private final static String ARGDELIM = ";";

    private Interpreter() {
    };

    public static void interpret(String s)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        s = s.replace("\n", "").replace("\r", "");
        
        StringTokenizer sTok = new StringTokenizer(s, DELIM, false);
        String requestType = sTok.nextToken();
        String methodName = sTok.nextToken();
        String argsStr = sTok.nextToken();

        StringTokenizer argsTok = new StringTokenizer(argsStr, ARGDELIM, false);
        int nArgs = argsTok.countTokens();
        String[] args = new String[nArgs];
        for (int i = 0; i < nArgs; i++) {
            args[i] = argsTok.nextToken();
        }

        switch (nArgs) {
            case 1: {
                Method method = Requests.class.getMethod(methodName, String.class);
                Object obj = method.invoke(method, args[0]);
                chkReturn(obj);
                //
                break;
            }
            case 2: {
                Method method = Requests.class.getMethod(methodName, String.class, String.class);
                Object obj = method.invoke(method, args[0], args[1]);
                chkReturn(obj);
                //
                break;
            }
            case 3: {
                Method method = Requests.class.getMethod(methodName, String.class, String.class, String.class);
                Object obj = method.invoke(method, args[0], args[1], args[2]);
                chkReturn(obj);
                //
                break;
            }
            default:
                throw new NoSuchMethodException(
                        String.format("No method %s found with %s arguments.", methodName, nArgs));
        }

    }

    private static void chkReturn(Object obj) throws IllegalAccessException {
        int returnCode = (int) obj;
        if (returnCode != 0) {
            throw new IllegalAccessException("Something went wrong.");
        }
    }
}