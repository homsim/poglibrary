package db_conn;

import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.NoSuchMethodException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Interpreter {
    // utilty class to interpret the frontend call string
    private final static char DELIM = '&';
    private final static char ARG_DELIM = ';';
    private final static char ARRAY_INDICATOR = '|'; // separates String[] elements

    private Interpreter() {
    };

    public static Object interpret(String s) throws InvocationTargetException {
        s = s.replace("\n", "").replace("\r", "");

        StringTokenizer sTok = new StringTokenizer(s, String.valueOf(DELIM), false);
        // String requestType = sTok.nextToken(); // "r" or "w"
        String methodName = sTok.nextToken();
        String argsStr = sTok.nextToken();

        StringTokenizer argsTok = new StringTokenizer(argsStr, String.valueOf(ARG_DELIM), false);
        int nArgs = argsTok.countTokens();
        String[] args = new String[nArgs];
        for (int i = 0; i < nArgs; i++) {
            args[i] = argsTok.nextToken();
        }

        Logger.getLogger("globalLogger").log(Level.INFO,
                "Request for method '" + methodName + "' with args '" + argsStr + "'");

        try {
            switch (nArgs) {
                case 1: {
                    Method method = Requests.class.getMethod(methodName, String.class);
                    Object obj = method.invoke(method, args[0]);
                    chkReturn(obj);
                    return obj;
                }
                case 2: {
                    Method method = Requests.class.getMethod(methodName, String.class, String.class);
                    Object obj = method.invoke(method, args[0], args[1]);
                    chkReturn(obj);
                    return obj;
                }
                case 3: {
                    Method method = Requests.class.getMethod(methodName, String.class, String.class, String.class);
                    Object obj = method.invoke(method, args[0], args[1], args[2]);
                    chkReturn(obj);
                    return obj;
                }
                default:
                    throw new NoSuchMethodException(
                            String.format("No method %s found with %s arguments.", methodName, nArgs));
            }
        } catch (NoSuchMethodException | IllegalAccessException ex) {
            Logger.getLogger("globalLogger").log(Level.WARNING, ex.getCause().toString());
            return null;
        }

    }

    public static String[] interpetArrays(String arrayStr) {
        StringTokenizer arrayTok = new StringTokenizer(arrayStr, String.valueOf(ARRAY_INDICATOR), false);
        int nArray = arrayTok.countTokens();
        String[] array = new String[nArray];
        for (int i = 0; i < nArray; i++) {
            array[i] = arrayTok.nextToken();
        }

        return array;
    }

    private static void chkReturn(Object obj) throws IllegalAccessException {
        try {
            int returnCode = (int) obj;
            if (returnCode != 0) {
                throw new IllegalAccessException("Something went wrong.");
            }
        } catch (ClassCastException ex) {
            // Logger.getLogger("globalLogger").log(Level.SEVERE, ex.getCause());
        }
    }
}