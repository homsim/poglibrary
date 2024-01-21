package db_conn;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
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

    public static Object interpret(String s)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
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
 
        /*
        StringTokenizer argsTok = new StringTokenizer(argsStr, String.valueOf(ARG_DELIM), false);
        int nArgs = argsTok.countTokens();
        List<String> args = new ArrayList<String>();

        List<String> arrayArgs = new ArrayList<String>();
        String currArg = "";
        boolean inArray = false;
        while (argsTok.hasMoreTokens()) {
            currArg = argsTok.nextToken();
            if (currArg.indexOf(ARRAY_INDICATOR) == 0) {
                // beginning of String[]
                currArg.replace(String.valueOf(ARRAY_INDICATOR), "");
                inArray = true;
                arrayArgs.add(currArg);
            } else if (currArg.indexOf(ARRAY_INDICATOR) == currArg.length()) {
                // end of String[]
                currArg.replace(String.valueOf(ARRAY_INDICATOR), "");
                arrayArgs.add(currArg);
                inArray = false;
                args.add(arrayArgs); // ...shit, right
            } else {
                if (inArray) {
                    arrayArgs.add(currArg);
                } else {
                    args.add(currArg);
                }
            }
        }
        */


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
            // maybe do something else in terms of checking
        }
    }
}