package UniversalTuringMachine;

/**
 * generic Band
 *
 * @param <T>
 */
public class Band<T> {
    private Object[] band;
    private int position;
    private final T emptyBandSym;
    private final T leaveSymAlone;

    public Band(T emptyBandSym, T leaveSymAlone) {
        this.emptyBandSym = emptyBandSym;
        this.leaveSymAlone = leaveSymAlone;
        position = 5;
        band = new Object[0];
    }

    /**
     * changes bandsymbol and after that the position
     * changes size of band if necessary
     *
     * @param dir change of position
     * @param change change of band symbol at position
     */
    public void change(int dir, T change){
        while (position - 20 < 0){
            add1toStartOfBand();
        }
        while (position + 20 > band.length){
            add1toEndOfBand();
        }
        band[position] = (change == leaveSymAlone) ? band[position] : change;
        position += Integer.compare(dir, 0);
    }

    /**
     * adds one empty space to the end of the band
     */
    private void add1toEndOfBand(){
        Object[] tband = new Object[band.length+1];
        tband[tband.length - 1] = emptyBandSym;
        System.arraycopy(band, 0, tband, 0, tband.length - 1);
        band = tband;
    }

    /**
     * adds one empty space to the start of the band
     */
    private void add1toStartOfBand(){
        Object[] tband = new Object[band.length+1];
        tband[0] = emptyBandSym;
        System.arraycopy(band, 0, tband, 1, tband.length - 1);
        band = tband;
        position++;
    }

    public T getBandState(){
       return (T) band[position];
    }

    /**
     * initalizes band with empty band symbol
     */
    public void initBand(){
        for (Object o : band){
            o = emptyBandSym;
        }
    }

    /**
     * creates string of band from 15 before actual position to 15 after
     *
     * @return string of partial band
     */
    public String toPartialBand(){
        StringBuilder s = new StringBuilder("| ");
        int len = 30;
        for (int i = 0; i < len; i++){
            int curp = (int)(position+(i-Math.floor(len/2)));
            if (curp == position) {
                s.append(band[curp]).append("<| ");
            } else if (curp == position-1){
                s.append(band[curp]).append(" |>");
            } else {
                s.append(band[curp]).append(" | ");
            }
        }
        if (s.length() > 2) {
            s = new StringBuilder(s.substring(0, s.length() - 3));
        }
        s.append(" |");
        return s.toString();
    }

    /**
     * counts the numbers of occurence of specified character
     *
     * @param c
     * @return returns int amount of occurences
     */
    public int count(T c){
        int n = 0;
        for (Object o : band){
            if (o == c){
                n++;
            }
        }
        return n;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for (Object o : band) {
            s.append((T) o).append(", ");
        }
        if (s.length() > 2) {
            s = new StringBuilder(s.substring(0, s.length() - 2));
        }
        s.append("]");
        return s.toString();
    }
}
