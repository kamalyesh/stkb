
package in.kkamalyesh.tool.KB_IE;

public class KBIE {

    private int check = 0;

    public KBIE() {
    }

    public int getCheck() {
        return check;
    }

    private void resetCheck() {
        check = 0;
    }

    private void updateCheck(char c, int length) {
        check += (c * Primes.primes[length]);
    }

    public String next(String currentString) {
        resetCheck();
        for (int i = 0; i < currentString.length(); ++i) {
            updateCheck(currentString.charAt(i), i);
        }
        String returnValue = KBAIHandler.getNexts(currentString.charAt(0), currentString.charAt(currentString.length() - 1), check);

        return returnValue;
    }

}
