import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

class Contest {

    boolean submit = false;
    boolean isTestCase = true;

    public Contest() throws IOException
    {
        //input
        InputReader in;
        if(!submit)
            in = new InputReader(new FileInputStream("src/input.txt"));
        else
            in = new InputReader(System.in);

        //output
        PrintWriter out = new PrintWriter(System.out);
        int T;
        if(isTestCase)
            T = in.nextInt();
        else
            T=1;

        for(int caseNo = 1; caseNo <= T; caseNo++)
            solve(caseNo, in, out);

        out.close();
    }

    private void solve(int caseNo, InputReader in, PrintWriter out) throws IOException
    {
        // Get Inputs
        int baskets = in.nextInt();
        int fruitTypes = in.nextInt();
        int basketFruitType[] = new int[baskets];
        int basketCost[] = new int[baskets];
        for (int i=0; i< baskets; i++) {
            basketFruitType[i] = in.nextInt();
        }
        for (int i=0; i< baskets; i++) {
            basketCost[i] = in.nextInt();
        }

        // Initialize
        final int[] minimumCost = {Integer.MAX_VALUE};
        Map<Integer, Integer> fruitTypeToCost = new HashMap<>();
        // TODO: MIN_HEAP??

        for (int i=0; i<baskets; i++) {
            int newCost = Integer.MAX_VALUE;

            int fruitType = basketFruitType[i];
            int cost = basketCost[i];

            if (fruitTypeToCost.containsKey(fruitType)) {
                Integer oldCost = fruitTypeToCost.get(fruitType);
                newCost = oldCost + cost;
                fruitTypeToCost.put(fruitType, newCost);
                // TODO: REMOVE OLD_COST FROM HEAP
                // TODO: ADD NEW_COST IN HEAP
            } else {
                newCost = cost;
                fruitTypeToCost.put(fruitType, newCost);
                // TODO: ADD NEW_COST IN HEAP
            }
        }

        fruitTypeToCost.forEach((key, value) -> {
            if (value < minimumCost[0]) {
                minimumCost[0] = value;
            }
        });

        out.println(minimumCost[0]);
    }

    public static class InputReader {

        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }



        public boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return isWhitespace(c);
        }

        public interface SpaceCharFilter {

            public boolean isSpaceChar(int ch);
        }

        public String next() {
            return nextString();
        }

        public String nextString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public int nextInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public Long nextLong() {
            return Long.parseLong(nextString());
        }

        public Double nextDouble() {
            return Double.parseDouble(nextString());
        }
    }

    public static void main(String args[]) throws IOException {
        new Contest();
    }
}