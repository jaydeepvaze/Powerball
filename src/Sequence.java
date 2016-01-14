/**
 * Created by jvaze on 1/13/16.
 */
public class Sequence {
    //int rowNumber;
    //int columnNumber;
    private int matchCount;
    private boolean isPowerballMatch;

    public Sequence(boolean isPowerballMatch) {
        this.isPowerballMatch = isPowerballMatch;
    }

    public Sequence(int matchCount) {
        this.matchCount = matchCount;
    }
    /*
    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }
    */

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public boolean isPowerballMatch() {
        return isPowerballMatch;
    }

    public void setPowerballMatch(boolean powerballMatch) {
        isPowerballMatch = powerballMatch;
    }
    /*
    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
    */
}
